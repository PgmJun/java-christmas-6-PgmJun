package christmas.domain;

import christmas.global.message.ErrorMessage;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

public class ReservationDateTest {

    @ParameterizedTest
    @ValueSource(ints = {0, 32, 45, 100})
    void 방문일자가_1이상_31이하의_숫자가_아닌_경우_예외발생(int date) {
        Assertions.assertThatThrownBy(() -> new ReservationDate(date))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining(ErrorMessage.INVALID_DATE.getText());
    }

    @ParameterizedTest
    @ValueSource(ints = {1, 31, 25, 11, 23})
    void 방문일자가_1이상_31이하의_숫자인_경우_정상적으로_객체_생성(int date) {
        new ReservationDate(date);
    }
}
