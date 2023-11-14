package christmas.domain.reservation.date;

import java.time.LocalDate;

public enum WeekDay {
    DEC_03("2023-12-03"),
    DEC_04("2023-12-04"),
    DEC_05("2023-12-05"),
    DEC_06("2023-12-06"),
    DEC_07("2023-12-07"),

    DEC_10("2023-12-10"),
    DEC_11("2023-12-11"),
    DEC_12("2023-12-12"),
    DEC_13("2023-12-13"),
    DEC_14("2023-12-14"),

    DEC_17("2023-12-17"),
    DEC_18("2023-12-18"),
    DEC_19("2023-12-19"),
    DEC_20("2023-12-20"),
    DEC_21("2023-12-21"),

    DEC_24("2023-12-24"),
    DEC_25("2023-12-25"),
    DEC_26("2023-12-26"),
    DEC_27("2023-12-27"),
    DEC_28("2023-12-28"),
    DEC_31("2023-12-31");

    private final String date;

    WeekDay(String date) {
        this.date = date;
    }

    public String getDay() {
        int dayOfMonth = LocalDate.parse(date).getDayOfMonth();
        return String.valueOf(dayOfMonth);
    }
}
