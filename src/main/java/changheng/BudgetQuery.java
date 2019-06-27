package changheng;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.Arrays;
import java.util.List;

public class BudgetQuery {
    public Double query(LocalDate from, LocalDate to) {
        List<Budget> budgets = getAllBudgets();
        if (from.isAfter(to)) {
            throw new IllegalArgumentException("Invalid date range");
        }

        return queryTotalAmount(new Period(from, to), budgets);
    }

    protected List<Budget> getAllBudgets() {
        Budget a = new Budget("2019/02", 28000d);
        Budget b = new Budget("2019/03", 31000d);
        return Arrays.asList(a, b);
    }

    private LocalDate firstDay(LocalDate d) {
        return YearMonth.from(d).atDay(1);
    }

    private LocalDate firstDayOfNextMonth(LocalDate current) {
        return YearMonth.from(current.plusMonths(1)).atDay(1);
    }

    private Double getAmountOfPeriod(Period period, List<Budget> budgets) {
        double total = 0;
        for (Budget budget : budgets) {
            if (isInPeriod(firstDay(period.getStart()), period.getEnd(), budget.firstDay())) {
                total += budget.dailyAmount() * period.intervalDays();
            }
        }
        return total;
    }

    private boolean isInPeriod(LocalDate from, LocalDate to, LocalDate d) {
        return (d.isAfter(from) || d.isEqual(from)) && (d.isBefore(to) || d.isEqual(to));
    }

    private LocalDate lastDay(LocalDate d) {
        return YearMonth.from(d).atEndOfMonth();
    }

    private Double queryTotalAmount(Period period, List<Budget> budgets) {
        if (period.isSameYearMonth()) {
            return getAmountOfPeriod(period, budgets);
        }

        double result = 0;

        for (LocalDate current = firstDay(period.getStart()); (current.isBefore(period.getEnd()) || current.equals(period.getEnd())); current = current.plusMonths(1)) {
            LocalDate overlappingStart = current;
            if (YearMonth.from(period.getStart()).equals(YearMonth.from(current))) {
                overlappingStart = period.getStart();
            }
            LocalDate overlappingEnd = lastDay(current);
            if (YearMonth.from(period.getEnd()).equals(YearMonth.from(current))) {
                overlappingEnd = period.getEnd();
            }
            result += getAmountOfPeriod(new Period(overlappingStart, overlappingEnd), budgets);
        }

        return result;
    }
}
