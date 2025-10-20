package calculator.view;

public class InputView

     // 사용자 입력을 담당하는 클래스


    public class InputView {

        private static final String INPUT_MESSAGE = "덧셈할 문자열을 입력해 주세요.";


         // 사용자로부터 계산할 문자열을 입력받음
        public String readInput() {
            System.out.println(INPUT_MESSAGE);
            return Console.readLine();
        }
    }

}
