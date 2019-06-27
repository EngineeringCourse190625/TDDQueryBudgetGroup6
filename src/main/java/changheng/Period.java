package changheng;

import java.time.LocalDate;
import java.time.YearMonth;

public class Period {
    private final LocalDate start;
    private final LocalDate end;

    public Period(LocalDate start, LocalDate end) {
        this.start = start;
        this.end = end;
    }

    public LocalDate getStart() {
        return start;
    }

    public LocalDate getEnd() {
        return end;
    }

    public boolean isSameYearMonth() {
        return YearMonth.from(start).equals(YearMonth.from(end));
    }

    public int intervalDays() {
        return java.time.Period.between(start, end).getDays() + 1;
    }
}
