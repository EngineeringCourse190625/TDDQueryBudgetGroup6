package changheng;

import java.time.LocalDate;

public class Budget {
    private String date; // yyyy/MM
    private Double amount;

    public Budget(String date, Double amount) {
        this.date = date;
        this.amount = amount;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public int getYear() {
        return Integer.valueOf(date.substring(0, 4));
    }

    public int getMonth() {
        return Integer.valueOf(date.substring(6));
    }

    public LocalDate getLocalDate() {
        return LocalDate.of(getYear(), getMonth(), 1);
    }
}
