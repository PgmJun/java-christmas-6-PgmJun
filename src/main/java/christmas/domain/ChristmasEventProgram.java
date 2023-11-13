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
        OrderMenus orderMenus = orderMenus();

        // 예약정보 출력
        printBenefitsInfoMessage(reservationDate);
        printOrderMenusInfo(orderMenus);
        printTotalPriceBeforeDiscountInfo(orderMenus.calculateTotalPrice());

        Optional<GiftMenu> giftMenu = checkGiftMenu(orderMenus);
        Discount discount = checkDiscount(orderMenus, reservationDate, giftMenu);
        Benefits benefits = new Benefits(discount, giftMenu);

        printAfterAppliedDiscountPrice(orderMenus, benefits);
        checkIssuedBadge(benefits);
    }

    private void checkIssuedBadge(Benefits benefits) {
        Badge badge = Badge.issue(benefits.getTotalBenefitPrice());
        outputView.println(messageConverter.convertEventBadgeInfoMessage(badge));
    }

    private void printAfterAppliedDiscountPrice(OrderMenus orderMenus, Benefits benefits) {
        outputView.println(
                messageConverter.convertAfterAppliedDiscountPrice(orderMenus.calculateTotalPrice(), benefits.getTotalDiscountPrice()));
    }

    private Discount checkDiscount(OrderMenus orderMenus, ReservationDate reservationDate, Optional<GiftMenu> giftMenu) {
        Discount discount = calculateDiscountPrice(orderMenus, reservationDate, 10_000);
        outputView.println(
                messageConverter.covertBenefitsInfoMessage(discount, reservationDate.isWeekends(), giftMenu));
        return discount;
    }

    private Optional<GiftMenu> checkGiftMenu(OrderMenus orderMenus) {
        Optional<GiftMenu> giftMenu = receiveGiftMenu(orderMenus, 120_000);
        printGiftMenuInfo(giftMenu);

        return giftMenu;
    }

    private void printBenefitsInfoMessage(ReservationDate reservationDate) {
        outputView.println(messageConverter.convertBenefitsInfoMessage(reservationDate.getDate()));
    }

    private Discount calculateDiscountPrice(OrderMenus orderMenus, ReservationDate reservationDate,
                                            int discountApplyStandard) {
        int totalPriceBeforeDiscount = orderMenus.calculateTotalPrice();
        if (totalPriceBeforeDiscount < discountApplyStandard) {
            return Discount.noneDiscount();
        }

        int ddayDiscountPrice = calculateDdayDiscountPrice(reservationDate, totalPriceBeforeDiscount);
        int dayOfWeekDiscountPrice = calculateDayOfWeekDiscountPrice(reservationDate, orderMenus);
        int specialDiscountPrice = calculateSpecialDiscountPrice(reservationDate);

        return new Discount(ddayDiscountPrice, dayOfWeekDiscountPrice, specialDiscountPrice);
    }

    private int calculateSpecialDiscountPrice(ReservationDate reservationDate) {
        int specialDiscountPrice = 0;
        if (reservationDate.isSpecialDay()) {
            specialDiscountPrice = 1_000;
        }

        return specialDiscountPrice;
    }

    private int calculateDayOfWeekDiscountPrice(ReservationDate reservationDate, OrderMenus orderMenus) {
        return orderMenus.calculateDayOfWeekDiscountPrice(reservationDate.isWeekends());
    }

    private int calculateDdayDiscountPrice(ReservationDate reservationDate, int totalPriceBeforeDiscount) {
        return reservationDate.calculateChristmasDdayDiscountPrice();
    }

    private void printOrderMenusInfo(OrderMenus orderMenus) {
        outputView.println(messageConverter.convertOrderMenuInfoMessage(orderMenus));
    }

    private void printTotalPriceBeforeDiscountInfo(int totalPriceBeforeDiscount) {
        outputView.println(messageConverter.convertTotalPriceBeforeDiscountInfoMessage(totalPriceBeforeDiscount));
    }

    private void printGiftMenuInfo(Optional<GiftMenu> giftMenu) {
        outputView.println(messageConverter.convertGiftMenuInfoMessage(giftMenu));
    }

    private Optional<GiftMenu> receiveGiftMenu(OrderMenus orderMenus, int standardPrice) {
        Optional<GiftMenu> giftMenu = Optional.empty();
        if (orderMenus.calculateTotalPrice() > standardPrice) {
            giftMenu = Optional.of(new GiftMenu(Menu.샴페인.name(), 1));
            orderMenus.receiveGiftMenu(giftMenu.get());
        }
        return giftMenu;
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

    private OrderMenus orderMenus() {
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

}
