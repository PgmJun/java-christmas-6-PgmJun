package christmas.global.message;

public enum ErrorMessage {
    INVALID_DATE("유효하지 않은 날짜입니다. 다시 입력해 주세요."),
    INVALID_ORDER("유효하지 않은 주문입니다. 다시 입력해 주세요."),
    ;

    private final String text;

    ErrorMessage(String text) {
        this.text = text;
    }

    public String getText() {
        return String.format("[ERROR] %s", text);
    }
}
