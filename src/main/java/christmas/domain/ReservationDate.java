package christmas.domain;

import christmas.global.message.ErrorMessage;
import java.util.List;

public class ReservationDate {
    private int date;

    private final static List<Integer> weekends = List.of(1, 2, 8, 9, 15, 16, 22, 23, 29, 30);
    private final static List<Integer> specialDay = List.of(3, 10, 17, 24, 25, 31);
    public final static String WEEKDAY = "평일";
    public final static String WEEKEND = "주말";

    public ReservationDate(int date) {
        validate(date);
        this.date = date;
    }

    private void validate(int date) {
        validateDateRange(date);
    }

    private void validateDateRange(int date) {
        if (date < 1 || date > 31) {
            throw new IllegalArgumentException(ErrorMessage.INVALID_DATE.getText());
        }
    }

    public int calculateChristmasDdayDiscountPrice() {
        if (date > 25) {
            return 0;
        }
        int discountPrice = 1_000;
        int discountUnit = 100;
        for (int i = 1; i < date; i++) {
            discountPrice += discountUnit;
        }

        return discountPrice;
    }

    public String checkWeekdayOrWeekend() {
        if(weekends.contains(date)) {
            return WEEKEND;
        }
        return WEEKDAY;
    }

    public boolean isSpecialDay() {
        return specialDay.contains(date);
    }

    public int getDate() {
        return date;
    }
}
