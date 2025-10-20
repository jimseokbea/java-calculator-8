package calculator.view;

public class OutputView {
    private static final String RESULT_PREFIX = "결과 : ";


    // 계산 결과를 화면에 출력하며 보여주기
    public void printResult(int result) {
        System.out.println(RESULT_PREFIX + result);
    }
}
