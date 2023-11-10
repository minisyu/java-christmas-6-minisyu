package christmas.domain.policy;

import java.time.LocalDate;
import java.util.Arrays;

public enum SpecialDate {
    THREE(LocalDate.of(2023, 12, 3)),
    TEN(LocalDate.of(2023, 12, 10)),
    SEVENTEEN(LocalDate.of(2023, 12, 17)),
    TWENTY_FOUR(LocalDate.of(2023, 12, 24)),
    TWENTY_FIVE(LocalDate.of(2023, 12, 25)),
    THIRTY_ONE(LocalDate.of(2023, 12, 31));

    private final LocalDate date;

    SpecialDate(LocalDate date) {
        this.date = date;
    }

    /**
     * @param date 사용자가 입력한 날짜
     * @return 특별 할인 날짜 중에 입력한 날짜와 동일한 날짜가 있으면 true 반환, 없으면 false 반환
     */
    public static boolean isSameAs(LocalDate date) {
        return Arrays.stream(SpecialDate.values())
                .anyMatch(specialDate -> specialDate.date.equals(date));
    }
}
