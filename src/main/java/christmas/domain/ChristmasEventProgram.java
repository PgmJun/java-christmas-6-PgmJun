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

        printBenefitsInfoMessage(reservationDate);

        printOrderMenusInfo(orderMenus);

        printTotalPriceBeforeDiscountInfo(orderMenus.calculateTotalPrice());

        Optional<OrderMenu> giftMenu = receiveGiftMenuWhenOverStandardPrice(orderMenus, 120_000);
        printGiftMenuInfo(giftMenu);

        Discount discount = calculateDiscountPrice(orderMenus, reservationDate, 10_000);
        outputView.println(
                messageConverter.covertBenefitsInfoMessage(discount, reservationDate.isWeekends(), giftMenu));

        int totalDiscountPrice = discount.getTotalDiscountPrice();
        outputView.println(
                messageConverter.convertAfterApplyBenefitPrice(orderMenus.calculateTotalPrice(), totalDiscountPrice));

        Badge badge = Badge.issue(calculateTotalBenefitPrice(discount, giftMenu));
        outputView.println(messageConverter.convertEventBadgeInfoMessage(badge));
    }

    private int calculateTotalBenefitPrice(Discount discount, Optional<OrderMenu> giftMenu) {
        int totalDiscountPrice = discount.getTotalDiscountPrice();
        if (giftMenu.isPresent()) {
            totalDiscountPrice += giftMenu.get().getTotalPrice();
        }

        return totalDiscountPrice;
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

    private void printGiftMenuInfo(Optional<OrderMenu> giftMenu) {
        outputView.println(messageConverter.convertGiftMenuInfoMessage(giftMenu));
    }

    private Optional<OrderMenu> receiveGiftMenuWhenOverStandardPrice(OrderMenus orderMenus, int standardPrice) {
        Optional<OrderMenu> giftMenu = Optional.empty();
        if (orderMenus.calculateTotalPrice() > standardPrice) {
            giftMenu = Optional.of(new OrderMenu(Menu.샴페인.name(), 1));
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
