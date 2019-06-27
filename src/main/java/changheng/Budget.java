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

    public double dailyAmount() {
        return amount / firstDay().lengthOfMonth();
    }

    public Period getPeriod() {
        return new Period(firstDay(), lastDay());
    }

    private LocalDate firstDay() {
        return YearMonth.parse(date, DateTimeFormatter.ofPattern("yyyy/MM")).atDay(1);
    }

    private LocalDate lastDay() {
        return YearMonth.parse(date, DateTimeFormatter.ofPattern("yyyy/MM")).atEndOfMonth();
    }
}
