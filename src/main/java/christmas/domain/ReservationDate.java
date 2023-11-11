package christmas.domain;

public class ReservationDate {
    private int date;

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
}
