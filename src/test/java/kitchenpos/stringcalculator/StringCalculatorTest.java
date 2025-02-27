package kitchenpos.stringcalculator;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

class StringCalculatorTest {
    private final StringCalculator calculator = new StringCalculator();

    @DisplayName(value = "빈 문자열 또는 null 값을 입력할 경우 0을 반환해야 한다.")
    @ParameterizedTest
    @NullAndEmptySource
    void emptyOrNull(final String text) {
        Assertions.assertThat(calculator.add(text)).isZero();
    }

    @DisplayName(value = "숫자 이외의 값을 입력할 경우 IllegalArgumentException 예외가 발생한다.")
    @ParameterizedTest
    @ValueSource(strings = {"ㅂ", "asdas"})
    void notNumber(final String text) {
        Assertions.assertThatExceptionOfType(IllegalArgumentException.class)
                  .isThrownBy(() -> calculator.add(text))
                  .withMessageContaining("숫자 이외의 값을 입력할 수 없습니다: " + text);
    }

    @DisplayName(value = "숫자 하나를 문자열로 입력할 경우 해당 숫자를 반환한다.")
    @ParameterizedTest
    @CsvSource(value = {
        "1=1",
        "42=42",
        "0=0",
        "100=100"
    }, delimiter = '=')
    void oneNumber(final String text, int expected) {
        Assertions.assertThat(calculator.add(text)).isSameAs(expected);
    }

    @DisplayName(value = "숫자 두개를 쉼표(,) 구분자로 입력할 경우 두 숫자의 합을 반환한다.")
    @ParameterizedTest
    @CsvSource(value = {
        "1,2=3",
        "10,20=30",
        "3,7=10"
    }, delimiter = '=')
    void twoNumbers(final String text, int expected) {
        Assertions.assertThat(calculator.add(text)).isSameAs(expected);
    }

    @DisplayName(value = "구분자를 쉼표(,) 이외에 콜론(:)을 사용할 수 있다.")
    @ParameterizedTest
    @CsvSource(value = {
        "1,2:3=6",
        "2:4,6=12",
        "1:1:1=3"
    }, delimiter = '=')
    void colons(final String text, int expected) {
        Assertions.assertThat(calculator.add(text)).isSameAs(expected);
    }

    @DisplayName(value = "//와 \\n 문자 사이에 커스텀 구분자를 지정할 수 있다.")
    @ParameterizedTest
    @ValueSource(strings = {"//;\n1;2;3"})
    void customDelimiter(final String text) {
        Assertions.assertThat(calculator.add(text)).isSameAs(6);
    }

    @DisplayName(value = "문자열 계산기에 음수를 전달하는 경우 IllegalArgumentException 예외 처리를 한다.")
    @Test
    void negative() {
        Assertions.assertThatExceptionOfType(IllegalArgumentException.class)
                  .isThrownBy(() -> calculator.add("-1"))
                  .withMessageContaining("음수는 포함될 수 없습니다: -1");
    }
}
