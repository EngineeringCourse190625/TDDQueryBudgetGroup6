package changheng;

import org.junit.Test;

import static org.junit.Assert.*;

import java.util.Calendar;
import java.util.Date;

public class HolidayTest {
    @Test
    public void isHoliday_1212_test() {
        assertEquals(givenToday(Calendar.DECEMBER, 12).isHoliday(), "Today is not Xmas");
    }

    @Test
    public void isHoliday_1224_test() {
        assertEquals(givenToday(Calendar.DECEMBER, 24).isHoliday(), "Merry Xmas");
    }

    @Test
    public void isHoliday_1024_test() {
        assertEquals(givenToday(Calendar.OCTOBER, 24).isHoliday(), "Today is not Xmas");
    }

    private class HolidayForTest extends Holiday {
        private Date today;

        public HolidayForTest() {
        }

        public HolidayForTest(Date date) {
            today = date;
        }

        @Override
        public Date getToday() {
            return today;
        }

        public void setToday(Date d) {
            today = d;
        }
    }

    private HolidayForTest givenToday(int month, int day) {
        return new HolidayForTest(new Date(2019, month, day));
    }
}
