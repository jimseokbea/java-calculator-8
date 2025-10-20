package calculator.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;


class StringCalculatorTest {

    private StringCalculator calculator;


    @BeforeEach
    void setUp() {
        calculator = new StringCalculator();
    }

    // ========== 정상 케이스 ==========

    @Test
    @DisplayName("쉼표 구분자로 숫자 3개 더하기")
    void 쉼표_구분자_테스트() {
        // given: 주어진 상황
        String input = "1,2,3";

        // when: 실행
        int result = calculator.calculate(input);

        // then: 검증
        assertThat(result).isEqualTo(6);
    }

    @Test
    @DisplayName("콜론 구분자로 숫자 더하기")
    void 콜론_구분자_테스트() {
        String input = "1:2:3";
        int result = calculator.calculate(input);
        assertThat(result).isEqualTo(6);
    }

    @Test
    @DisplayName("쉼표와 콜론을 함께 사용")
    void 혼합_구분자_테스트() {
        String input = "1,2:3";
        int result = calculator.calculate(input);
        assertThat(result).isEqualTo(6);
    }

    @Test
    @DisplayName("커스텀 구분자로 세미콜론 사용")
    void 커스텀_구분자_세미콜론_테스트() {
        String input = "//;\n1;2;3";
        int result = calculator.calculate(input);
        assertThat(result).isEqualTo(6);
    }

    @Test
    @DisplayName("커스텀 구분자로 특수문자 사용")
    void 커스텀_구분자_특수문자_테스트() {
        String input = "//*\n1*2*3";
        int result = calculator.calculate(input);
        assertThat(result).isEqualTo(6);
    }

    @Test
    @DisplayName("큰 숫자들의 합")
    void 큰_숫자_테스트() {
        String input = "100,200,300";
        int result = calculator.calculate(input);
        assertThat(result).isEqualTo(600);
    }

    // ========== 경계값 테스트 ==========

    @Test
    @DisplayName("빈 문자열은 0을 반환")
    void 빈_문자열_테스트() {
        String input = "";
        int result = calculator.calculate(input);
        assertThat(result).isEqualTo(0);
    }

    @Test
    @DisplayName("null 입력은 0을 반환")
    void null_입력_테스트() {
        String input = null;
        int result = calculator.calculate(input);
        assertThat(result).isEqualTo(0);
    }

    @Test
    @DisplayName("숫자 하나만 입력")
    void 숫자_하나만_테스트() {
        String input = "5";
        int result = calculator.calculate(input);
        assertThat(result).isEqualTo(5);
    }

    @Test
    @DisplayName("커스텀 구분자 뒤에 숫자 없음")
    void 커스텀_구분자_숫자_없음_테스트() {
        String input = "//;\n";
        int result = calculator.calculate(input);
        assertThat(result).isEqualTo(0);
    }

    @Test
    @DisplayName("공백이 포함된 숫자 처리")
    void 공백_포함_테스트() {
        String input = " 1 , 2 , 3 ";
        int result = calculator.calculate(input);
        assertThat(result).isEqualTo(6);
    }

    // ========== 예외 케이스 ==========

    @Test
    @DisplayName("음수 입력시 예외 발생")
    void 음수_예외_테스트() {
        String input = "1,-2,3";

        assertThatThrownBy(() -> calculator.calculate(input))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("음수");
    }

    @Test
    @DisplayName("숫자가 아닌 문자 입력시 예외")
    void 숫자가_아닌_값_예외_테스트() {
        String input = "1,abc,3";

        assertThatThrownBy(() -> calculator.calculate(input))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("숫자가 아닌");
    }

    @Test
    @DisplayName("잘못된 커스텀 구분자 형식")
    void 잘못된_형식_예외_테스트() {
        String input = "//;1;2;3";  // \n 누락

        assertThatThrownBy(() -> calculator.calculate(input))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("형식");
    }

    @Test
    @DisplayName("연속된 구분자 입력시 예외")
    void 연속_구분자_예외_테스트() {
        String input = "1,,2";

        assertThatThrownBy(() -> calculator.calculate(input))
                .isInstanceOf(IllegalArgumentException.class);
    }
}