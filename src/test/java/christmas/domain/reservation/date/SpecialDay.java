package christmas.domain.reservation.date;

import java.time.LocalDate;

public enum SpecialDay {
    DEC_03("2023-12-03"),
    DEC_10("2023-12-10"),
    DEC_17("2023-12-17"),
    DEC_24("2023-12-24"),
    DEC_25("2023-12-25"),
    DEC_31("2023-12-31");

    private final String date;

    SpecialDay(String date) {
        this.date = date;
    }

    public String getDay() {
        int dayOfMonth = LocalDate.parse(date).getDayOfMonth();
        return String.valueOf(dayOfMonth);
    }
}
