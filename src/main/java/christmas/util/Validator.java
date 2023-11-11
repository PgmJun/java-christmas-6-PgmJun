package christmas.util;

public class Validator {

    public static void validateIntFormat(String value) {
        try {
            Integer.parseInt(value);
        } catch (NumberFormatException exception) {
            throw new IllegalArgumentException("[ERROR] 입력 값이 정수 형태가 아닙니다.");
        }
    }
}
