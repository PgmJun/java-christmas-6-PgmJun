package christmas.domain;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

public class ReservationDateTest {

    @ParameterizedTest
    @ValueSource(ints = {0, 32, 45, 100})
    void 방문일자가_1이상_31이하의_숫자가_아닌_경우_예외발생(int date) {
        Assertions.assertThatThrownBy(() -> new ReservationDate(date))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("[ERROR] 유효하지 않은 날짜입니다. 다시 입력해 주세요.");
    }

    @ParameterizedTest
    @ValueSource(ints = {1, 31, 25, 11, 23})
    void 방문일자가_1이상_31이하의_숫자인_경우_정상적으로_객체_생성(int date) {
        new ReservationDate(date);
    }
}
