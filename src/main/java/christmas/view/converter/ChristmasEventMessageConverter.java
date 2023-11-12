package christmas.view.converter;

import christmas.domain.OrderMenu;
import christmas.domain.OrderMenus;
import java.text.DecimalFormat;

public class ChristmasEventMessageConverter {
    private final DecimalFormat priceFormat = new DecimalFormat("#,###원\n");

    public String convertOrderMenuMessage(OrderMenus orderMenus) {
        StringBuilder orderMenuMessage = new StringBuilder();
        orderMenuMessage.append("<주문 메뉴>\n");
        for (OrderMenu orderMenu : orderMenus.getOrderMenus()) {
            orderMenuMessage.append(String.format("%s %d개\n", orderMenu.getMenuName(), orderMenu.getAmount()));
        }
        return orderMenuMessage.toString();
    }

    public String convertTotalPriceBeforeDiscountInfoMessage(int totalPrice) {
        StringBuilder totalPriceBeforeDiscountInfoMessage = new StringBuilder();
        totalPriceBeforeDiscountInfoMessage.append("<할인 전 총주문 금액>\n");
        totalPriceBeforeDiscountInfoMessage.append(priceFormat.format(totalPrice));

        return totalPriceBeforeDiscountInfoMessage.toString();
    }
}

