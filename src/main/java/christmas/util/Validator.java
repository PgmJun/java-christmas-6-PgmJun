package christmas.util;

import java.util.regex.Pattern;

public class Validator {
    private final static Pattern ORDER_MENUS_FORMAT = Pattern.compile("([가-힣]+-\\d+)(,([가-힣]+-\\d+))*");
    private final static Pattern INT_FORMAT = Pattern.compile("\\d+");


    public static void validateReservationDateFormat(String value) {
        if (!INT_FORMAT.matcher(value).matches()) {
            throw new IllegalArgumentException("[ERROR] 유효하지 않은 날짜입니다. 다시 입력해 주세요.");
        }
    }

    public static void validateOrderMenusFormat(String value) {
        if (!ORDER_MENUS_FORMAT.matcher(value).matches()) {
            throw new IllegalArgumentException("[ERROR] 유효하지 않은 주문입니다. 다시 입력해 주세요.");
        }
    }


}

