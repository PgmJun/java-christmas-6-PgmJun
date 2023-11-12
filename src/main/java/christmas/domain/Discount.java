package christmas.domain;

public class Discount {

    private final int ddayDiscountPrice;
    private final int dayOfWeekDiscountPrice;
    private final int specialDiscountPrice;

    public Discount(int ddayDiscountPrice, int dayOfWeekDiscountPrice, int specialDiscountPrice) {
        this.ddayDiscountPrice = ddayDiscountPrice;
        this.dayOfWeekDiscountPrice = dayOfWeekDiscountPrice;
        this.specialDiscountPrice = specialDiscountPrice;
    }

    public static Discount noneDiscount() {
        return new Discount(0, 0, 0);
    }

    public boolean isDiscountApplied() {
        if(ddayDiscountPrice == 0 && dayOfWeekDiscountPrice == 0 && specialDiscountPrice == 0) {
            return false;
        }
        return true;
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
