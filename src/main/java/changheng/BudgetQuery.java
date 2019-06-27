package changheng;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.Arrays;
import java.util.List;

import static java.util.stream.Collectors.toList;

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

    private double getAmountOfPeriod(LocalDate from, LocalDate to, List<Budget> budgets) {
        double total = 0;
        List<Budget> queryResult = query(firstDay(from), to, budgets);
        for (Budget budget : queryResult) {
            total += budget.dailyAmount() * intervalDays(from, to);
        }
        return total;
    }

    private int intervalDays(LocalDate from, LocalDate to) {
        return java.time.Period.between(from, to).getDays() + 1;
    }

    private boolean isInPeriod(LocalDate from, LocalDate to, LocalDate d) {
        return (d.isAfter(from) || d.isEqual(from)) && (d.isBefore(to) || d.isEqual(to));
    }

    private LocalDate lastDay(LocalDate d) {
        return YearMonth.from(d).atEndOfMonth();
    }

    private List<Budget> query(LocalDate from, LocalDate to, List<Budget> budgets) {
        return budgets.stream().
                filter(budget -> isInPeriod(from, to, budget.firstDay())).
                collect(toList());
    }

    private Double queryTotalAmount(Period period, List<Budget> budgets) {
        if (new Period(period.getStart(), period.getEnd()).isSameYearMonth()) {
            return getAmountOfPeriod(period.getStart(), period.getEnd(), budgets);
        }

        // calc first month
        double result = getAmountOfPeriod(period.getStart(), lastDay(period.getStart()), budgets);

        for (LocalDate current = firstDayOfNextMonth(period.getStart()); current.isBefore(period.getEnd()) && !new Period(current, period.getEnd()).isSameYearMonth(); current = current.plusMonths(1)) {
            result += getAmountOfPeriod(current, lastDay(current), budgets);
        }

        // calc last month
        result += getAmountOfPeriod(firstDay(period.getEnd()), period.getEnd(), budgets);
        return result;
    }
}
