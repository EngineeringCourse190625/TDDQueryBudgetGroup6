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

    private int getOverlappingPeriod(Period period, LocalDate budgetFirstDay) {
        LocalDate overlappingStart = period.getStart().isAfter(budgetFirstDay) ? period.getStart() : budgetFirstDay;
        LocalDate overlappingEnd = period.getEnd().isBefore(lastDay(budgetFirstDay)) ? period.getEnd() : lastDay(budgetFirstDay);
        if (overlappingEnd.isBefore(overlappingStart)) {
            return 0;
        }
        return new Period(overlappingStart, overlappingEnd).intervalDays();
    }

    private LocalDate lastDay(LocalDate d) {
        return YearMonth.from(d).atEndOfMonth();
    }

    private Double queryTotalAmount(Period period, List<Budget> budgets) {
        double result = 0;

        for (Budget budget : budgets) {
//            YearMonth currentMonth = YearMonth.from(current);
//            Optional<Budget> currentBudget = currentBudget(budgets, currentMonth);
//            if (!currentBudget.isPresent()) {
//                continue;
//            }

            result += budget.dailyAmount() * getOverlappingPeriod(period, budget.firstDay());
        }

        return result;
    }
}
