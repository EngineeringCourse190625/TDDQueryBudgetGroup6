package changheng;

import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;

public class Budget {
    private String date; // yyyy/MM
    private Double amount;

    public Budget(String date, Double amount) {
        this.date = date;
        this.amount = amount;
    }

    public double getOverlappingAmount(Period period) {
        return dailyAmount() * period.getOverlappingDays(getPeriod());
    }

    private double dailyAmount() {
        return amount / yearMonth().lengthOfMonth();
    }

    private LocalDate firstDay() {
        return yearMonth().atDay(1);
    }

    private Period getPeriod() {
        return new Period(firstDay(), lastDay());
    }

    private LocalDate lastDay() {
        return yearMonth().atEndOfMonth();
    }

    private YearMonth yearMonth() {
        return YearMonth.parse(date, DateTimeFormatter.ofPattern("yyyy/MM"));
    }
}
