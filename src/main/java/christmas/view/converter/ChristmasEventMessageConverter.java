package christmas.view.converter;

import christmas.domain.OrderMenu;
import christmas.domain.OrderMenus;

public class ChristmasEventMessageConverter {

    public String convertOrderMenuMessage(OrderMenus orderMenus) {
        StringBuilder orderMenuMessage = new StringBuilder();
        orderMenuMessage.append("<주문 메뉴>\n");
        for (OrderMenu orderMenu : orderMenus.getOrderMenus()) {
            orderMenuMessage.append(String.format("%s %d개\n", orderMenu.getMenuName(), orderMenu.getAmount()));
        }
        return orderMenuMessage.toString();
    }
}

