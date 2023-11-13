package christmas.domain;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class OrderMenus {

    private final List<OrderMenu> orderMenus;

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
        int totalOrderMenuAmount = 0;
        for (OrderMenu orderMenu : orderMenus) {
            totalOrderMenuAmount += orderMenu.getAmount();
        }

        if (totalOrderMenuAmount < 1 || totalOrderMenuAmount > 20) {
            throw new IllegalArgumentException("[ERROR] 유효하지 않은 주문입니다. 다시 입력해 주세요.");
        }
    }

    private void validateMenuDuplication(List<OrderMenu> orderMenus) {
        int orderMenuCount = orderMenus.size();
        long distinctOrderMenuCount = orderMenus.stream().map(OrderMenu::getMenu)
                .distinct()
                .count();

        if (orderMenuCount != distinctOrderMenuCount) {
            throw new IllegalArgumentException("[ERROR] 유효하지 않은 주문입니다. 다시 입력해 주세요.");
        }
    }

    private void validateHasOnlyDrinkMenu(List<OrderMenu> orderMenus) {
        int orderMenuCount = orderMenus.size();
        long drinkMenuCount = orderMenus.stream()
                .map(OrderMenu::getMenu)
                .filter(menu -> menu.getType().equals("음료"))
                .count();

        if (orderMenuCount == drinkMenuCount) {
            throw new IllegalArgumentException("[ERROR] 유효하지 않은 주문입니다. 다시 입력해 주세요.");
        }
    }

    public int calculateTotalPrice() {
        return orderMenus.stream()
                .map(OrderMenu::getTotalPrice)
                .reduce(0, Integer::sum);
    }

    public int calculateDayOfWeekDiscountPrice(boolean isWeekends) {
        int discountPrice = 2023;
        return (int) (discountPrice * getDayOfWeekDiscountCount(isWeekends));
    }

    private long getDayOfWeekDiscountCount(boolean isWeekends) {
        List<OrderMenu> menus = new ArrayList<>();
        if (isWeekends) {
            menus = orderMenus.stream()
                    .filter(orderMenu -> orderMenu.getMenuType().equals("메인"))
                    .toList();
        } else if (!isWeekends) {
            menus = orderMenus.stream()
                    .filter(orderMenu -> orderMenu.getMenuType().equals("디저트"))
                    .toList();
        }

        return menus.stream()
                .mapToInt(OrderMenu::getAmount).sum();
    }

    public List<OrderMenu> getOrderMenus() {
        return Collections.unmodifiableList(orderMenus);
    }
}
