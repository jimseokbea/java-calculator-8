package calculator;

import calculator.domain.StringCalculator;
import calculator.view.InputView;
import calculator.view.OutputView;


public class Application {

    public static void main(String[] args) {
        // 필요한 객체들 생성한다
        InputView inputView = new InputView();
        OutputView outputView = new OutputView();
        StringCalculator calculator = new StringCalculator();

        try {
            // 1. 입력하기
            String input = inputView.readInput();

            // 2. 계산하기
            int result = calculator.calculate(input);

            // 3. 출력하기
            outputView.printResult(result);

        } catch (IllegalArgumentException e) {
            // 예외 발생시 그대로 던져서 프로그램 종료한다
            throw e;
        }
    }
}