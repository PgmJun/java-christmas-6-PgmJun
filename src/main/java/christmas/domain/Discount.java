package christmas.domain;

public class Discount {

    private final int ddayDiscountPrice;
    private final int dayOfWeekDiscountPrice;
    private final int specialDiscountPrice;

    private static final int DISCOUNT_APPLY_STANDARD = 10_000;

    public Discount(int ddayDiscountPrice, int dayOfWeekDiscountPrice, int specialDiscountPrice) {
        this.ddayDiscountPrice = ddayDiscountPrice;
        this.dayOfWeekDiscountPrice = dayOfWeekDiscountPrice;
        this.specialDiscountPrice = specialDiscountPrice;
    }

    public static Discount noneDiscount() {
        return new Discount(0, 0, 0);
    }

    public static Discount calculateFrom(OrderMenus orderMenus, ReservationDate reservationDate) {
        int totalPriceBeforeDiscount = orderMenus.calculateTotalPrice();
        if (totalPriceBeforeDiscount < DISCOUNT_APPLY_STANDARD) {
            return Discount.noneDiscount();
        }

        int ddayDiscountPrice = calculateDdayDiscountPrice(reservationDate);
        int dayOfWeekDiscountPrice = calculateDayOfWeekDiscountPrice(reservationDate, orderMenus);
        int specialDiscountPrice = calculateSpecialDiscountPrice(reservationDate);

        return new Discount(ddayDiscountPrice, dayOfWeekDiscountPrice, specialDiscountPrice);
    }

    private static int calculateSpecialDiscountPrice(ReservationDate reservationDate) {
        int specialDiscountPrice = 0;
        if (reservationDate.isSpecialDay()) {
            specialDiscountPrice = 1_000;
        }

        return specialDiscountPrice;
    }

    private static int calculateDayOfWeekDiscountPrice(ReservationDate reservationDate, OrderMenus orderMenus) {
        return orderMenus.calculateDayOfWeekDiscountPrice(reservationDate.isWeekends());
    }

    private static int calculateDdayDiscountPrice(ReservationDate reservationDate) {
        return reservationDate.calculateChristmasDdayDiscountPrice();
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
