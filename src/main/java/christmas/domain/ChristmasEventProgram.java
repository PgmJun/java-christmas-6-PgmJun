package christmas.domain;

import christmas.view.InputView;
import christmas.view.OutputView;
import christmas.view.converter.ChristmasEventMessageConverter;
import christmas.view.dto.OrderMenuDto;
import java.util.List;
import java.util.Optional;

public class ChristmasEventProgram {

    private final InputView inputView;
    private final OutputView outputView;
    private final ChristmasEventMessageConverter messageConverter;

    public ChristmasEventProgram(InputView inputView, OutputView outputView,
                                 ChristmasEventMessageConverter messageConverter) {
        this.inputView = inputView;
        this.outputView = outputView;
        this.messageConverter = messageConverter;
    }


    public void run() {
        ReservationDate reservationDate = reserveVisitDate();
        OrderMenus orderMenus = reserveMenus();

        showReservationInfo(reservationDate, orderMenus);
        showBenefitsInfo(reservationDate, orderMenus);
    }

    private ReservationDate reserveVisitDate() {
        while (true) {
            try {
                int date = inputView.readReservationDate();
                return new ReservationDate(date);
            } catch (IllegalArgumentException exception) {
                outputView.printErrorMessage(exception);
            }
        }
    }

    private OrderMenus reserveMenus() {
        while (true) {
            try {
                List<OrderMenuDto> orderMenuDtos = inputView.readOrderMenus();
                List<OrderMenu> orderMenus = orderMenuDtos.stream()
                        .map(OrderMenuDto::toOrderMenu)
                        .toList();

                return new OrderMenus(orderMenus);
            } catch (IllegalArgumentException exception) {
                outputView.printErrorMessage(exception);
            }
        }
    }

    private void showReservationInfo(ReservationDate reservationDate, OrderMenus orderMenus) {
        printBenefitsInfoMessage(reservationDate);
        printOrderMenusInfo(orderMenus);
        printTotalPriceBeforeDiscountInfo(orderMenus.calculateTotalPrice());
    }

    private void printBenefitsInfoMessage(ReservationDate reservationDate) {
        outputView.println(messageConverter.convertBenefitsInfoMessage(reservationDate.getDate()));
    }

    private void printOrderMenusInfo(OrderMenus orderMenus) {
        outputView.println(messageConverter.convertOrderMenuInfoMessage(orderMenus));
    }

    private void printTotalPriceBeforeDiscountInfo(int totalPriceBeforeDiscount) {
        outputView.println(messageConverter.convertTotalPriceBeforeDiscountInfoMessage(totalPriceBeforeDiscount));
    }

    private void showBenefitsInfo(ReservationDate reservationDate, OrderMenus orderMenus) {
        Optional<GiftMenu> giftMenu = checkGiftMenu(orderMenus);
        Discount discount = checkDiscount(orderMenus, reservationDate, giftMenu);
        Benefits benefits = new Benefits(discount, giftMenu);

        printAfterAppliedDiscountPrice(orderMenus, benefits);
        checkIssuedBadge(benefits);
    }

    private Optional<GiftMenu> checkGiftMenu(OrderMenus orderMenus) {
        Optional<GiftMenu> giftMenu = receiveGiftMenu(orderMenus, 120_000);
        outputView.println(messageConverter.convertGiftMenuInfoMessage(giftMenu));

        return giftMenu;
    }

    private Optional<GiftMenu> receiveGiftMenu(OrderMenus orderMenus, int standardPrice) {
        Optional<GiftMenu> giftMenu = Optional.empty();
        if (orderMenus.calculateTotalPrice() > standardPrice) {
            giftMenu = Optional.of(new GiftMenu(Menu.샴페인.name(), 1));
            orderMenus.receiveGiftMenu(giftMenu.get());
        }
        return giftMenu;
    }

    private Discount checkDiscount(OrderMenus orderMenus, ReservationDate reservationDate, Optional<GiftMenu> giftMenu) {
        Discount discount = Discount.calculateFrom(orderMenus, reservationDate, 10_000);
        outputView.println(
                messageConverter.covertBenefitsInfoMessage(discount, reservationDate.isWeekends(), giftMenu));
        return discount;
    }

    private void printAfterAppliedDiscountPrice(OrderMenus orderMenus, Benefits benefits) {
        outputView.println(
                messageConverter.convertAfterAppliedDiscountPrice(orderMenus.calculateTotalPrice(), benefits.getTotalDiscountPrice()));
    }

    private void checkIssuedBadge(Benefits benefits) {
        Badge badge = Badge.issue(benefits.getTotalBenefitPrice());
        outputView.println(messageConverter.convertEventBadgeInfoMessage(badge));
    }
}