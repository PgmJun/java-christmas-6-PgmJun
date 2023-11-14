package christmas.domain;

import christmas.view.InputView;
import christmas.view.OutputView;
import christmas.view.dto.OrderMenuDto;
import java.util.List;
import java.util.Optional;

public class ChristmasEventProgram {

    private final InputView inputView;
    private final OutputView outputView;

    public ChristmasEventProgram(InputView inputView, OutputView outputView) {
        this.inputView = inputView;
        this.outputView = outputView;
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
        outputView.printBenefitsOfDateMessage(reservationDate);
        outputView.printOrderMenuInfoMessage(orderMenus);
        outputView.printTotalPriceBeforeDiscountInfoMessage(orderMenus);
    }

    private void showBenefitsInfo(ReservationDate reservationDate, OrderMenus orderMenus) {
        Optional<GiftMenu> giftMenu = checkGiftMenu(orderMenus);
        Discount discount = checkDiscount(orderMenus, reservationDate);
        Benefits benefits = checkBenefits(reservationDate, discount, giftMenu);

        printAfterAppliedDiscountPrice(orderMenus, benefits);
        checkIssuedBadge(benefits);
    }

    private Optional<GiftMenu> checkGiftMenu(OrderMenus orderMenus) {
        Optional<GiftMenu> giftMenu = GiftMenu.receive(orderMenus.calculateTotalPrice());
        outputView.printGiftMenuInfoMessage(giftMenu);

        return giftMenu;
    }

    private Discount checkDiscount(OrderMenus orderMenus, ReservationDate reservationDate) {
        return Discount.calculateFrom(orderMenus, reservationDate);
    }

    private Benefits checkBenefits(ReservationDate reservationDate, Discount discount, Optional<GiftMenu> giftMenu) {
        Benefits benefits = new Benefits(discount, giftMenu);
        outputView.printBenefitsInfoMessage(benefits, reservationDate);
        outputView.printTotalBenefitsPriceInfo(benefits);
        return benefits;
    }

    private void printAfterAppliedDiscountPrice(OrderMenus orderMenus, Benefits benefits) {
        outputView.printAfterAppliedDiscountPrice(orderMenus.calculateTotalPrice(), benefits.getTotalDiscountPrice());
    }

    private void checkIssuedBadge(Benefits benefits) {
        Badge badge = Badge.issue(benefits.getTotalBenefitPrice());
        outputView.printEventBadgeInfoMessage(badge);
    }
}
