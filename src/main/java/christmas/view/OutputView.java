package christmas.view;

import christmas.domain.Badge;
import christmas.domain.Benefits;
import christmas.domain.GiftMenu;
import christmas.domain.OrderMenu;
import christmas.domain.OrderMenus;
import christmas.domain.ReservationDate;
import java.text.DecimalFormat;
import java.util.Optional;

public class OutputView {
    private final DecimalFormat priceFormat = new DecimalFormat("#,###원");
    private final DecimalFormat discountFormat = new DecimalFormat("-#,###원");


    public void printBenefitsOfDateMessage(ReservationDate reservationDate) {
        println(String.format("12월 %d일에 우테코 식당에서 받을 이벤트 혜택 미리 보기!", reservationDate.getDate()));
        printEnter();
    }

    public void printOrderMenuInfoMessage(OrderMenus orderMenus) {
        println("<주문 메뉴>");
        StringBuilder orderMenuMessage = new StringBuilder();
        for (OrderMenu orderMenu : orderMenus.getOrderMenus()) {
            orderMenuMessage.append(String.format("%s %d개\n", orderMenu.getMenuName(), orderMenu.getAmount()));
        }
        println(orderMenuMessage.toString());
    }


    public void printTotalPriceBeforeDiscountInfoMessage(OrderMenus orderMenus) {
        println("<할인 전 총주문 금액>");
        println(priceFormat.format(orderMenus.calculateTotalPrice()));
        printEnter();
    }

    public void printGiftMenuInfoMessage(Optional<GiftMenu> giftMenu) {
        println("<증정 메뉴>");
        if (giftMenu.isPresent()) {
            println(String.format("%s %d개", giftMenu.get().getMenuName(), giftMenu.get().getAmount()));
            printEnter();
            return;
        }
        println("없음");
        printEnter();
    }

    public void printBenefitsInfoMessage(Benefits benefits, ReservationDate reservationDate) {
        println("<혜택 내역>");
        if (benefits.isBeneficiary()) {
            printBeneficiaryInfoMessage(benefits, reservationDate.checkWeekdayOrWeekend());
            return;
        }
        printNoneBeneficiaryInfoMessage();
    }

    private void printNoneBeneficiaryInfoMessage() {
        println("없음");
        printEnter();
    }

    private void printBeneficiaryInfoMessage(Benefits benefits, String dayOfWeekType) {
        if (benefits.isDdayDiscountApplied()) {
            println(String.format("크리스마스 디데이 할인: %s", discountFormat.format(benefits.getDdayDiscountPrice())));
        }
        if (benefits.isDayOfWeekDiscountApplied()) {
            println(String.format("%s 할인 : %s", dayOfWeekType,
                    discountFormat.format(benefits.getDayOfWeekDiscountPrice())));
        }
        if (benefits.isSpecialDiscountApplied()) {
            println(String.format("특별 할인: %s", discountFormat.format(benefits.getSpecialDiscountPrice())));
        }
        if (benefits.isExistGiftMenu()) {
            println(String.format("증정 이벤트: %s", discountFormat.format(benefits.getGiftMenuPrice())));
        }
        printEnter();
    }

    public void printTotalBenefitsPriceInfo(Benefits benefits) {
        println("<총혜택 금액>");
        if(benefits.getTotalBenefitPrice() > 0) {
            println(discountFormat.format(benefits.getTotalBenefitPrice()));
        } else if(benefits.getTotalBenefitPrice() == 0) {
            println(priceFormat.format(0));
        }
        printEnter();
    }

    private String createWeekendsOrWeekDaysInfoMessage(boolean isWeekends) {
        if (isWeekends) {
            return "주말";
        }
        return "평일";
    }

    public void printAfterAppliedDiscountPrice(int totalPrice, int totalBenefitPrice) {
        println("<할인 후 예상 결제 금액>");
        println(priceFormat.format(totalPrice - totalBenefitPrice));
        printEnter();
    }

    public void printEventBadgeInfoMessage(Badge badge) {
        println("<12월 이벤트 배지>");
        println(badge.getName());
    }

    public void println(String text) {
        System.out.println(text);
    }

    private void printEnter() {
        System.out.println();
    }

    public void printErrorMessage(Exception exception) {
        println(exception.getMessage());
    }
}
