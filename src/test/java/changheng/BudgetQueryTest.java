package changheng;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;

import static org.junit.Assert.*;


public class BudgetQueryTest {

    @Test
    public void test_1day() {
        LocalDate from = LocalDate.of(2019, 2, 1);
        LocalDate to = LocalDate.of(2019, 2, 1);
        assertEquals(Double.valueOf(1000), givenBudget().query(from, to));
    }

    @Test
    public void test_part_of_the_month() {
        LocalDate from = LocalDate.of(2019, 2, 3);
        LocalDate to = LocalDate.of(2019, 2, 15);
        assertEquals(Double.valueOf(13000), givenBudget().query(from, to));
    }

    @Test
    public void test_one_month_plus_one_day() {
        LocalDate from = LocalDate.of(2019, 2, 1);
        LocalDate to = LocalDate.of(2019, 3, 1);
        assertEquals(Double.valueOf(30000), givenBudget().query(from, to));
    }

    @Test
    public void test_two_month_plus_one_day() {
        LocalDate from = LocalDate.of(2019, 2, 1);
        LocalDate to = LocalDate.of(2019, 4, 1);
        assertEquals(Double.valueOf(93000), givenBudget().query(from, to));
    }

    @Test
    public void test_part_of_mont_plus_one_month() {
        LocalDate from = LocalDate.of(2019, 2, 2); // 27000 62000 9000
        LocalDate to = LocalDate.of(2019, 4, 3);
        assertEquals(Double.valueOf(98000), givenBudget().query(from, to));
    }

    @Test
    public void test_different_end_day_with_different_month() {
        LocalDate from = LocalDate.of(2019, 2, 27); // 2000 62000 48000
        LocalDate to = LocalDate.of(2019, 4, 16);
        assertEquals(Double.valueOf(112000), givenBudget().query(from, to));
    }

    @Test
    public void test_different_day_with_less_than_30_day() {
        LocalDate from = LocalDate.of(2019, 5, 27); // 2000 62000 48000
        LocalDate to = LocalDate.of(2019, 6, 16);
        assertEquals(Double.valueOf(0), givenBudget().query(from, to));
    }

    @Test
    public void test_part_of_range_has_budget() {
        LocalDate from = LocalDate.of(2019, 4, 18); // 13
        LocalDate to = LocalDate.of(2019, 6, 16);
        assertEquals(Double.valueOf(39000), givenBudget().query(from, to));
    }

    @Test(expected = IllegalArgumentException.class)
    public void test_invalid_date_range() {
        LocalDate from = LocalDate.of(2019, 4, 27); // 2000 62000 48000
        LocalDate to = LocalDate.of(2019, 4, 16);
        givenBudget().query(from, to);
        fail("Expect to throw");
    }

    private BudgetQuery givenBudget() {
        return new BudgetQueryForTest();
    }

    private class BudgetQueryForTest extends BudgetQuery {
        protected List<Budget> getAllBudgets() {
            Budget a = new Budget("2019/02", 28000d);
            Budget b = new Budget("2019/03", 62000d);
            Budget c = new Budget("2019/04", 90000d);
            return Arrays.asList(a, b, c);
        }
    }
}
