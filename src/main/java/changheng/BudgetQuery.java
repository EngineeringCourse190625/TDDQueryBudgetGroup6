package changheng;

import java.time.LocalDate;
import java.time.Period;
import java.time.YearMonth;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class BudgetQuery {
    public Double query(LocalDate from, LocalDate to) {
        List<Budget> budgetList = getAllBudgets();
        if (from.isAfter(to)) {
            throw new IllegalArgumentException("Invalid date range");
        }

        double result = 0;
        if (sameYearMonth(from, to)) {
            int diff = Period.between(from, to).getDays() + 1;
            List<Budget> queryResult = query(getBeginDayDate(from), getEndDayDate(to), budgetList);
            for (Budget budget : queryResult) {
                result += budget.getAmount() / getMonthLength(from) * diff;
            }
            return result;
        }

        // calc first month
        int diff = getMonthLength(from) - from.getDayOfMonth() + 1;
        List<Budget> queryResult = query(getBeginDayDate(from), getEndDayDate(from), budgetList);
        for (Budget budget : queryResult) {
            result += budget.getAmount() / getMonthLength(from) * diff;
        }
        from = from.plusMonths(1);
        from = LocalDate.of(from.getYear(), from.getMonthValue(), 1);

        for (LocalDate now = from; now.isBefore(to) && !sameYearMonth(now, to); now = now.plusMonths(1)) {
            queryResult = query(now, getEndDayDate(now), budgetList);
            for (Budget budget : queryResult) {
                result += budget.getAmount();
            }
        }
        // calc last month
        diff = to.getDayOfMonth();
        queryResult = query(LocalDate.of(to.getYear(), to.getMonthValue(), 1), getEndDayDate(to), budgetList);
        for (Budget budget : queryResult) {
            result += budget.getAmount() / getMonthLength(to) * diff;
        }
        return result;
    }

    private LocalDate getBeginDayDate(LocalDate d) {
        return LocalDate.of(d.getYear(), d.getMonthValue(), 1);
    }

    private LocalDate getEndDayDate(LocalDate d) {
        return LocalDate.of(d.getYear(), d.getMonthValue(), getMonthLength(d));
    }

    private List<Budget> query(LocalDate from, LocalDate to, List<Budget> budgetList) {
        return budgetList.stream().filter(budget -> {
            LocalDate d = budget.getLocalDate();
            return (d.isAfter(from) || d.isEqual(from)) && (d.isBefore(to) || d.isEqual(to));
        }).collect(Collectors.toList());
    }

    private int getMonthLength(LocalDate date) {
        return YearMonth.of(date.getYear(), date.getMonth()).lengthOfMonth();
    }

    private boolean sameYearMonth(LocalDate a, LocalDate b) {
        return a.getMonthValue() == b.getMonthValue() && a.getYear() == b.getYear();
    }

    protected List<Budget> getAllBudgets() {
        Budget a = new Budget("2019/02", 28000d);
        Budget b = new Budget("2019/03", 31000d);
        return Arrays.asList(a, b);
    }
}
