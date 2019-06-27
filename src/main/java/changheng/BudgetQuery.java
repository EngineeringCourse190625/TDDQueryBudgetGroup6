package changheng;

import java.time.LocalDate;
import java.time.Period;
import java.time.YearMonth;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class BudgetQuery {
    public Double query(LocalDate from, LocalDate to) {
        List<Budget> budgets = getAllBudgets();
        if (from.isAfter(to)) {
            throw new IllegalArgumentException("Invalid date range");
        }

        double result = 0;
        if (isSameYearMonth(from, to)) {
            List<Budget> queryResult = query(firstDay(from), lastDay(to), budgets);
            for (Budget budget : queryResult) {
                result += budget.getAmount() / getMonthLength(from) * intervalDays(from, to);
            }
            return result;
        }

        // calc first month
        int diff = getMonthLength(from) - from.getDayOfMonth() + 1;
        List<Budget> queryResult = query(firstDay(from), lastDay(from), budgets);
        for (Budget budget : queryResult) {
            result += budget.getAmount() / getMonthLength(from) * diff;
        }
        from = from.plusMonths(1);
        from = LocalDate.of(from.getYear(), from.getMonthValue(), 1);

        for (LocalDate now = from; now.isBefore(to) && !isSameYearMonth(now, to); now = now.plusMonths(1)) {
            queryResult = query(now, lastDay(now), budgets);
            for (Budget budget : queryResult) {
                result += budget.getAmount();
            }
        }
        // calc last month
        diff = to.getDayOfMonth();
        queryResult = query(LocalDate.of(to.getYear(), to.getMonthValue(), 1), lastDay(to), budgets);
        for (Budget budget : queryResult) {
            result += budget.getAmount() / getMonthLength(to) * diff;
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

    private int getMonthLength(LocalDate date) {
        return YearMonth.of(date.getYear(), date.getMonth()).lengthOfMonth();
    }

    private int intervalDays(LocalDate from, LocalDate to) {
        return Period.between(from, to).getDays() + 1;
    }

    private boolean isSameYearMonth(LocalDate from, LocalDate to) {
        return YearMonth.from(from).equals(YearMonth.from(to));
    }

    private LocalDate lastDay(LocalDate d) {
        return YearMonth.from(d).atEndOfMonth();
    }

    private List<Budget> query(LocalDate from, LocalDate to, List<Budget> budgetList) {
        return budgetList.stream().filter(budget -> {
            LocalDate d = budget.getLocalDate();
            return (d.isAfter(from) || d.isEqual(from)) && (d.isBefore(to) || d.isEqual(to));
        }).collect(Collectors.toList());
    }
}
