package christmas.domain;

import christmas.domain.menu.MenuType;
import christmas.global.message.ErrorMessage;
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

    public int calculateDayOfWeekDiscountPrice(ReservationDate reservationDate) {
        int discountPrice = 2023;
        return (int) (discountPrice * getDayOfWeekDiscountCount(reservationDate));
    }

    private long getDayOfWeekDiscountCount(ReservationDate reservationDate) {
        return orderMenus.stream()
                .filter(orderMenu -> orderMenu.getMenuType().equals(getMenuTypeForDayOfWeek(reservationDate)))
                .mapToInt(OrderMenu::getAmount)
                .sum();
    }

    private MenuType getMenuTypeForDayOfWeek(ReservationDate reservationDate) {
        String dayOfWeek = reservationDate.checkWeekdayOrWeekend();

        if (dayOfWeek.equals(ReservationDate.WEEKEND)) {
            return MenuType.MAIN;
        }
        return MenuType.DESSERT;
    }

    public List<OrderMenu> getOrderMenus() {
        return Collections.unmodifiableList(orderMenus);
    }
}
