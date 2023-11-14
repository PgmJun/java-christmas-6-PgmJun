package christmas.global.util;

import christmas.global.message.ErrorMessage;
import java.util.regex.Pattern;

public class Validator {
    private final static Pattern ORDER_MENUS_FORMAT = Pattern.compile("([가-힣]+-\\d+)(,([가-힣]+-\\d+))*");
    private final static Pattern INT_FORMAT = Pattern.compile("\\d+");


    public static void validateReservationDateFormat(String value) {
        if (!INT_FORMAT.matcher(value).matches()) {
            throw new IllegalArgumentException(ErrorMessage.INVALID_DATE.getText());
        }
    }

    public static void validateOrderMenusFormat(String value) {
        if (!ORDER_MENUS_FORMAT.matcher(value).matches()) {
            throw new IllegalArgumentException(ErrorMessage.INVALID_ORDER.getText());
        }
    }
}

