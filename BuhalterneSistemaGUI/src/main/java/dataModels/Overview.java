package dataModels;

import java.time.LocalDateTime;

public class Overview implements Comparable<Overview> {
    private String description;
    private String amount;
    private LocalDateTime date;

    public Overview() {
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    @Override
    public int compareTo(Overview o) {
        return o.getDate().compareTo(getDate());
    }

    @Override
    public String toString() {
        return "Overview{" +
                "description='" + description + '\'' +
                ", amount='" + amount + '\'' +
                ", date=" + date +
                '}';
    }
}