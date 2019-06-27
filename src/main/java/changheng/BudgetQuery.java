package changheng;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

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

    private Optional<Budget> currentBudget(List<Budget> budgets, YearMonth currentMonth) {
        return budgets.stream().
                filter(b -> currentMonth.equals(YearMonth.from(b.firstDay()))).
                findFirst();
    }

    private LocalDate firstDay(LocalDate d) {
        return YearMonth.from(d).atDay(1);
    }

    private LocalDate lastDay(LocalDate d) {
        return YearMonth.from(d).atEndOfMonth();
    }

    private Double queryTotalAmount(Period period, List<Budget> budgets) {
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
            Period overlappingPeriod = new Period(overlappingStart, overlappingEnd);

            YearMonth currentMonth = YearMonth.from(current);
            Optional<Budget> currentBudget = currentBudget(budgets, currentMonth);
            if (!currentBudget.isPresent()) {
                continue;
            }

            result += currentBudget.get().dailyAmount() * overlappingPeriod.intervalDays();
        }

        return result;
    }
}
