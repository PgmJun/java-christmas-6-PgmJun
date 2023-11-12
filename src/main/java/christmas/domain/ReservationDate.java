package christmas.domain;

import java.util.List;

public class ReservationDate {
    private int date;

    private final static List<Integer> weekends = List.of(1, 2, 8, 9, 15, 16, 22, 23, 29, 30);
    private final static List<Integer> specialDay = List.of(3, 10, 17, 24, 25, 31);

    public ReservationDate(int date) {
        validate(date);
        this.date = date;
    }

    private void validate(int date) {
        validateDateRange(date);
    }

    private void validateDateRange(int date) {
        if (date < 1 || date > 31) {
            throw new IllegalArgumentException("[ERROR] 유효하지 않은 날짜입니다. 다시 입력해 주세요.");
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

    public boolean isWeekends() {
        return weekends.contains(date);
    }

    public boolean isSpecialDay() {
        return specialDay.contains(date);
    }

    public int getDate() {
        return date;
    }
}
