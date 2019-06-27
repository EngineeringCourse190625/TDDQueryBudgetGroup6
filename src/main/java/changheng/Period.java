package changheng;

import java.time.LocalDate;

public class Period {
    private final LocalDate start;
    private final LocalDate end;

    public Period(LocalDate start, LocalDate end) {
        this.start = start;
        this.end = end;
    }

    public int getOverlappingDays(Period another) {
        LocalDate overlappingStart = start.isAfter(another.start) ? start : another.start;
        LocalDate overlappingEnd = end.isBefore(another.end) ? end : another.end;
        if (overlappingEnd.isBefore(overlappingStart)) {
            return 0;
        }
        return new Period(overlappingStart, overlappingEnd).intervalDays();
    }

    private int intervalDays() {
        return java.time.Period.between(start, end).getDays() + 1;
    }
}
