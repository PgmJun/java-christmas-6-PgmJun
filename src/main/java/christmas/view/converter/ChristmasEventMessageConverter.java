package christmas.view.converter;

import christmas.domain.Badge;
import christmas.domain.Benefits;
import christmas.domain.GiftMenu;
import christmas.domain.OrderMenu;
import christmas.domain.OrderMenus;
import christmas.domain.ReservationDate;
import java.text.DecimalFormat;
import java.util.Optional;

public class ChristmasEventMessageConverter {
    private final DecimalFormat priceFormat = new DecimalFormat("#,###원\n");
    private final DecimalFormat discountFormat = new DecimalFormat("-#,###원\n");

    public String convertBenefitsOfDateMessage(ReservationDate reservationDate) {
        return String.format("12월 %d일에 우테코 식당에서 받을 이벤트 혜택 미리 보기!\n", reservationDate.getDate());
    }

    public String convertOrderMenuInfoMessage(OrderMenus orderMenus) {
        StringBuilder orderMenuMessage = new StringBuilder();
        orderMenuMessage.append("<주문 메뉴>\n");
        for (OrderMenu orderMenu : orderMenus.getOrderMenus()) {
            orderMenuMessage.append(String.format("%s %d개\n", orderMenu.getMenuName(), orderMenu.getAmount()));
        }
        return orderMenuMessage.toString();
    }

    public String convertTotalPriceBeforeDiscountInfoMessage(OrderMenus orderMenus) {
        StringBuilder totalPriceBeforeDiscountInfoMessage = new StringBuilder();
        totalPriceBeforeDiscountInfoMessage.append("<할인 전 총주문 금액>\n");
        totalPriceBeforeDiscountInfoMessage.append(priceFormat.format(orderMenus.calculateTotalPrice()));

        return totalPriceBeforeDiscountInfoMessage.toString();
    }

    public String convertGiftMenuInfoMessage(Optional<GiftMenu> giftMenu) {
        StringBuilder giftMenuInfoMessage = new StringBuilder();
        giftMenuInfoMessage.append("<증정 메뉴>\n");

        if (giftMenu.isPresent()) {
            giftMenuInfoMessage.append(
                    String.format("%s %d개\n", giftMenu.get().getMenuName(), giftMenu.get().getAmount()));
            return giftMenuInfoMessage.toString();
        }
        giftMenuInfoMessage.append("없음\n");
        return giftMenuInfoMessage.toString();
    }

    public String covertBenefitsInfoMessage(Benefits benefits, boolean isWeekends) {
        StringBuilder benefitsInfoMessage = new StringBuilder();
        benefitsInfoMessage.append("<혜택 내역>\n");
        if (benefits.isBeneficiary()) {
            return createDiscountInfoMessage(benefits, isWeekends, benefitsInfoMessage);
        }
        return createNoneDiscountInfoMessage(benefitsInfoMessage);
    }

    private String createNoneDiscountInfoMessage(StringBuilder benefitsInfoMessage) {
        benefitsInfoMessage.append("없음\n\n");
        benefitsInfoMessage.append("<총혜택 금액>\n0원\n");

        return benefitsInfoMessage.toString();
    }

    private String createDiscountInfoMessage(Benefits benefits, boolean isWeekends,
                                             StringBuilder benefitsInfoMessage) {
        if (benefits.isDdayDiscountApplied()) {
            benefitsInfoMessage.append(
                    String.format("크리스마스 디데이 할인: %s", discountFormat.format(benefits.getDdayDiscountPrice())));
        }
        if (benefits.isDayOfWeekDiscountApplied()) {
            benefitsInfoMessage.append(String.format("%s 할인 : %s", createWeekendsOrWeekDaysInfoMessage(isWeekends),
                    discountFormat.format(benefits.getDayOfWeekDiscountPrice())));
        }
        if (benefits.isSpecialDiscountApplied()) {
            benefitsInfoMessage.append(
                    String.format("특별 할인: %s", discountFormat.format(benefits.getSpecialDiscountPrice())));
        }
        if (benefits.isExistGiftMenu()) {
            benefitsInfoMessage.append(
                    String.format("증정 이벤트: %s\n", discountFormat.format(benefits.getGiftMenuPrice())));
        }
        addTotalBenefitsPriceInfo(benefits, benefitsInfoMessage);
        return benefitsInfoMessage.toString();
    }

    private void addTotalBenefitsPriceInfo(Benefits benefits, StringBuilder benefitsInfoMessage) {
        benefitsInfoMessage.append("<총혜택 금액>\n");
        benefitsInfoMessage.append(discountFormat.format(benefits.getTotalBenefitPrice()));
    }

    private String createWeekendsOrWeekDaysInfoMessage(boolean isWeekends) {
        if (isWeekends) {
            return "주말";
        }
        return "평일";
    }

    public String convertAfterAppliedDiscountPrice(int totalPrice, int totalBenefitPrice) {
        StringBuilder afterAppliedDiscountInfoMessage = new StringBuilder();
        afterAppliedDiscountInfoMessage.append("<할인 후 예상 결제 금액>\n");
        afterAppliedDiscountInfoMessage.append(priceFormat.format(totalPrice - totalBenefitPrice));

        return afterAppliedDiscountInfoMessage.toString();
    }

    public String convertEventBadgeInfoMessage(Badge badge) {
        StringBuilder eventBadgeInfoMessage = new StringBuilder();
        eventBadgeInfoMessage.append("<12월 이벤트 배지>\n");
        eventBadgeInfoMessage.append(badge.getName());

        return eventBadgeInfoMessage.toString();
    }
}

