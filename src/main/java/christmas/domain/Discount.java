package christmas.domain;

import christmas.domain.menu.MenuType;

public class Discount {

    private final int ddayDiscountPrice;
    private final int dayOfWeekDiscountPrice;
    private final int specialDiscountPrice;

    private static final int DISCOUNT_APPLY_STANDARD = 10_000;
    private static final int DAY_OF_WEEKS_DISCOUNT_PRICE = 2_023;
    private static final int D_DAY_DISCOUNT_DEFAULT_PRICE = 1_000;
    private static final int D_DAY_DISCOUNT_UNIT = 100;
    private static final int SPECIAL_DISCOUNT_PRICE = 1_000;

    public Discount(int ddayDiscountPrice, int dayOfWeekDiscountPrice, int specialDiscountPrice) {
        this.ddayDiscountPrice = ddayDiscountPrice;
        this.dayOfWeekDiscountPrice = dayOfWeekDiscountPrice;
        this.specialDiscountPrice = specialDiscountPrice;
    }

    public static Discount calculateFrom(OrderMenus orderMenus, ReservationDate reservationDate) {
        int totalPriceBeforeDiscount = orderMenus.calculateTotalPrice();
        if (totalPriceBeforeDiscount < DISCOUNT_APPLY_STANDARD) {
            return Discount.noneDiscount();
        }

        int ddayDiscountPrice = calculateChristmasDdayDiscountPrice(reservationDate);
        int dayOfWeekDiscountPrice = calculateDayOfWeekDiscountPrice(orderMenus, reservationDate);
        int specialDiscountPrice = calculateSpecialDiscountPrice(reservationDate);

        return new Discount(ddayDiscountPrice, dayOfWeekDiscountPrice, specialDiscountPrice);
    }

    private static Discount noneDiscount() {
        return new Discount(0, 0, 0);
    }


    private static int calculateChristmasDdayDiscountPrice(ReservationDate reservationDate) {
        if (reservationDate.isAfterChristmas()) {
            return 0;
        }

        int discountPrice = D_DAY_DISCOUNT_DEFAULT_PRICE;
        for (int i = 1; i < reservationDate.getDate(); i++) {
            discountPrice += D_DAY_DISCOUNT_UNIT;
        }
        return discountPrice;
    }

    private static int calculateDayOfWeekDiscountPrice(OrderMenus orderMenus, ReservationDate reservationDate) {
        return (int) (DAY_OF_WEEKS_DISCOUNT_PRICE * getDayOfWeekDiscountCount(orderMenus, reservationDate));
    }

    private static long getDayOfWeekDiscountCount(OrderMenus orderMenus, ReservationDate reservationDate) {
        return orderMenus.getOrderMenus().stream()
                .filter(orderMenu -> orderMenu.getMenuType().equals(getDayOfWeeksDiscountMenuType(reservationDate)))
                .mapToInt(OrderMenu::getAmount)
                .sum();
    }

    private static MenuType getDayOfWeeksDiscountMenuType(ReservationDate reservationDate) {
        if (reservationDate.checkWeekdayOrWeekend().equals(ReservationDate.WEEKEND)) {
            return MenuType.MAIN;
        }
        return MenuType.DESSERT;
    }

    private static int calculateSpecialDiscountPrice(ReservationDate reservationDate) {
        int specialDiscountPrice = 0;
        if (reservationDate.isSpecialDay()) {
            specialDiscountPrice = SPECIAL_DISCOUNT_PRICE;
        }

        return specialDiscountPrice;
    }

    public int getTotalDiscountPrice() {
        return ddayDiscountPrice + dayOfWeekDiscountPrice + specialDiscountPrice;
    }

    public int getDdayDiscountPrice() {
        return ddayDiscountPrice;
    }

    public int getDayOfWeekDiscountPrice() {
        return dayOfWeekDiscountPrice;
    }

    public int getSpecialDiscountPrice() {
        return specialDiscountPrice;
    }
}
