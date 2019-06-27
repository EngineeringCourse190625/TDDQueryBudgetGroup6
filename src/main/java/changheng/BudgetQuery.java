package changheng;

import java.time.LocalDate;
import java.time.Period;
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

        if (isSameYearMonth(from, to)) {
            return getAmountOfPeriod(from, to, budgets);
        }

        // calc first month
        double result = getAmountOfPeriod(from, lastDay(from), budgets);

        for (LocalDate current = firstDayOfNextMonth(from); current.isBefore(to) && !isSameYearMonth(current, to); current = current.plusMonths(1)) {
            List<Budget> queryResult = query(current, lastDay(current), budgets);
            for (Budget budget : queryResult) {
                result += budget.getAmount();
            }
        }

        // calc last month
        int diff1 = to.getDayOfMonth();
        List<Budget> queryResult = query(LocalDate.of(to.getYear(), to.getMonthValue(), 1), lastDay(to), budgets);
        for (Budget budget : queryResult) {
            result += budget.getAmount() / getMonthLength(to) * diff1;
        }
        return result;
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

    private int getMonthLength(LocalDate date) {
        return date.lengthOfMonth();
    }

    private int intervalDays(LocalDate from, LocalDate to) {
        return Period.between(from, to).getDays() + 1;
    }

    private boolean isInPeriod(LocalDate from, LocalDate to, LocalDate d) {
        return (d.isAfter(from) || d.isEqual(from)) && (d.isBefore(to) || d.isEqual(to));
    }

    private boolean isSameYearMonth(LocalDate from, LocalDate to) {
        return YearMonth.from(from).equals(YearMonth.from(to));
    }

    private LocalDate lastDay(LocalDate d) {
        return YearMonth.from(d).atEndOfMonth();
    }

    private List<Budget> query(LocalDate from, LocalDate to, List<Budget> budgets) {
        return budgets.stream().
                filter(budget -> isInPeriod(from, to, budget.firstDay())).
                collect(toList());
    }
}
