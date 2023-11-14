package christmas.domain.date;

import java.time.LocalDate;

public enum Weekend {
    DEC_1("2023-12-01"),
    DEC_2("2023-12-02"),

    DEC_8("2023-12-08"),
    DEC_9("2023-12-09"),

    DEC_15("2023-12-15"),
    DEC_16("2023-12-16"),

    DEC_22("2023-12-22"),
    DEC_23("2023-12-23"),

    DEC_29("2023-12-29"),
    DEC_30("2023-12-30");

    private final String date;

    Weekend(String date) {
        this.date = date;
    }

    public String getDay() {
        int dayOfMonth = LocalDate.parse(date).getDayOfMonth();
        return String.valueOf(dayOfMonth);
    }
}


