package calculator.domain;

import java.util.regex.Pattern;

public class StringCalculator {

    private static final String DEFAULT_DELIMITER = "[,:]";
    private static final String CUSTOM_PREFIX = "//";
    private static final String NEWLINE = "\n";

    public int calculate(String input) {
        // 1단계: null이나 빈 문자열 체크
        if (isNullOrEmpty(input)) {
            return 0;
        }

        // 2단계: 커스텀 구분자가 있는지 확인
        if (hasCustomDelimiter(input)) {
            return calculateWithCustomDelimiter(input);
        }

        // 3단계: 기본 구분자로 계산
        return calculateWithDefaultDelimiter(input);
    }

    // null이나 빈 문자열인지 확인하기
    // 메서드로 만든 이유는 코드가 읽기 쉬워지기 때문
    private boolean isNullOrEmpty(String input) {
        return input == null || input.isEmpty();
    }

    // 커스텀 구분자가 있는지 확인
    private boolean hasCustomDelimiter(String input) {
        return input.startsWith(CUSTOM_PREFIX);
    }

    // 기본 구분자(쉼표, 콜론)로 계산하기
    private int calculateWithDefaultDelimiter(String input) {
        String[] numbers = input.split(DEFAULT_DELIMITER);
        return sumNumbers(numbers);
    }

    // 커스텀 구분자로 계산하기
    private int calculateWithCustomDelimiter(String input) {
        // 형식 검증: \n이 있어야 함
        validateCustomDelimiterFormat(input);

        // 구분자 추출
        String delimiter = extractCustomDelimiter(input);

        // 숫자 부분 추출
        String numbersText = extractNumbersText(input);

        // 빈 문자열 체크
        if (numbersText.isEmpty()) {
            return 0;
        }

        // 계산
        String[] numbers = numbersText.split(Pattern.quote(delimiter));
        return sumNumbers(numbers);
    }

    // 커스텀 구분자 형식이 올바른지 검증
    private void validateCustomDelimiterFormat(String input) {
        if (!input.contains(NEWLINE)) {
            throw new IllegalArgumentException("잘못된 형식입니다. 올바른 형식: //[구분자]\\n[숫자들]");
        }
        int newlineIndex = input.indexOf(NEWLINE);
        if (newlineIndex == CUSTOM_PREFIX.length()) {
            throw new IllegalArgumentException("커스텀 구분자는 비어있을 수 없습니다.");
        }
    }

    // 커스텀 구분자 추출하기
    private String extractCustomDelimiter(String input) {
        int newlineIndex = input.indexOf(NEWLINE);
        return input.substring(CUSTOM_PREFIX.length(), newlineIndex);
    }

    // 숫자 텍스트 부분만 추출
    private String extractNumbersText(String input) {
        int numbersStartIndex = input.indexOf(NEWLINE) + NEWLINE.length();
        return input.substring(numbersStartIndex);
    }

    // 문자열 배열을 받아서 합계계산하기
    private int sumNumbers(String[] numbers) {
        int sum = 0;
        for (String number : numbers) {
            sum += parseAndValidate(number);
        }
        return sum;
    }

    // 문자열을 숫자로 변환하고 검증하기
    private int parseAndValidate(String input) {
        if (input == null || input.trim().isEmpty()) {
            throw new IllegalArgumentException("연속된 구분자 또는 잘못된 숫자 형식이 있습니다.");
        }
        try {
            // 앞뒤 공백 제거 후 변환하기
            int number = Integer.parseInt(input.trim());

            // 음수 체크하기
            validatePositive(number);

            return number;

        } catch (NumberFormatException e) {
            // 숫자로 변환 실패
            throw new IllegalArgumentException(
                    "숫자가 아닌 값이 포함되어 있습니다: '" + input + "'"
            );
        }
    }

    // 양수인지 검증
    // 음수면 예외 발생
    private void validatePositive(int number) {
        if (number < 0) {
            throw new IllegalArgumentException(
                    "음수는 입력할 수 없습니다: " + number
            );
        }
    }
}

