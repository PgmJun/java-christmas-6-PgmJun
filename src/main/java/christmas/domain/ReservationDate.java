package christmas.domain;

import christmas.global.message.ErrorMessage;
import java.util.List;

public class ReservationDate {
    private final int date;

    private static final List<Integer> weekends = List.of(1, 2, 8, 9, 15, 16, 22, 23, 29, 30);
    private static final List<Integer> specialDay = List.of(3, 10, 17, 24, 25, 31);
    public static final int CHRISTMAS_DATE = 25;
    public static final int FIRST_DAY_OF_DECEMBER = 1;
    public static final int LAST_DAY_OF_DECEMBER = 31;
    public static final String WEEKDAY = "평일";
    public static final String WEEKEND = "주말";


    public ReservationDate(int date) {
        validate(date);
        this.date = date;
    }

    private void validate(int date) {
        validateDateRange(date);
    }

    private void validateDateRange(int date) {
        if (date < FIRST_DAY_OF_DECEMBER || date > LAST_DAY_OF_DECEMBER) {
            throw new IllegalArgumentException(ErrorMessage.INVALID_DATE.getText());
        }
    }

    public String checkWeekdayOrWeekend() {
        if (weekends.contains(date)) {
            return WEEKEND;
        }
        return WEEKDAY;
    }

    public boolean isSpecialDay() {
        return specialDay.contains(date);
    }

    public boolean isAfterChristmas() {
        return date > CHRISTMAS_DATE;
    }

    public int getDate() {
        return date;
    }
}
