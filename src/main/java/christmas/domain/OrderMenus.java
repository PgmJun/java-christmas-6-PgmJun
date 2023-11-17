package christmas.domain;

import christmas.domain.menu.MenuType;
import christmas.global.message.ErrorMessage;
import java.util.Collections;
import java.util.List;

public class OrderMenus {

    private final List<OrderMenu> orderMenus;

    private static final int MIN_ORDER_AMOUNT = 1;
    private static final int MAX_ORDER_AMOUNT = 20;

    public OrderMenus(List<OrderMenu> orderMenus) {
        validate(orderMenus);
        this.orderMenus = orderMenus;
    }

    private void validate(List<OrderMenu> orderMenus) {
        validateOrderMenuAmount(orderMenus);
        validateMenuDuplication(orderMenus);
        validateHasOnlyDrinkMenu(orderMenus);
    }

    private void validateOrderMenuAmount(List<OrderMenu> orderMenus) {
        int totalOrderMenuAmount = orderMenus.stream()
                .mapToInt(OrderMenu::getAmount)
                .sum();

        if (totalOrderMenuAmount < MIN_ORDER_AMOUNT || totalOrderMenuAmount > MAX_ORDER_AMOUNT) {
            throw new IllegalArgumentException(ErrorMessage.INVALID_ORDER.getText());
        }
    }

    private void validateMenuDuplication(List<OrderMenu> orderMenus) {
        int orderMenuCount = orderMenus.size();
        long distinctOrderMenuCount = orderMenus.stream().map(OrderMenu::getMenu)
                .distinct()
                .count();

        if (orderMenuCount != distinctOrderMenuCount) {
            throw new IllegalArgumentException(ErrorMessage.INVALID_ORDER.getText());
        }
    }

    private void validateHasOnlyDrinkMenu(List<OrderMenu> orderMenus) {
        int orderMenuCount = orderMenus.size();
        long drinkMenuCount = orderMenus.stream()
                .map(OrderMenu::getMenu)
                .filter(menu -> menu.getType().equals(MenuType.DRINK))
                .count();

        if (orderMenuCount == drinkMenuCount) {
            throw new IllegalArgumentException(ErrorMessage.INVALID_ORDER.getText());
        }
    }

    public int calculateTotalPrice() {
        return orderMenus.stream()
                .map(OrderMenu::getTotalPrice)
                .reduce(0, Integer::sum);
    }

    public List<OrderMenu> getOrderMenus() {
        return Collections.unmodifiableList(orderMenus);
    }
}
