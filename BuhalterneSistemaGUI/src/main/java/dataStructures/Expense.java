package dataStructures;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
public class Expense implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private double amount;
    private String description;
    private String expenseTaker;
    private LocalDateTime date;

    @ManyToOne
    private Category category;

    public Expense() {
    }

    public Expense(double amount, String description, String expenseTaker, LocalDateTime date, Category category) {
        this.amount = amount;
        this.description = description;
        this.expenseTaker = expenseTaker;
        this.date = date;
        this.category = category;
    }

//Just for api testing
    public Expense(double amount, String description, String expenseTaker, LocalDateTime date) {
        this.amount = amount;
        this.description = description;
        this.expenseTaker = expenseTaker;
        this.date = date;
    }

    @Override
    public String toString() {

        return "Expense{" +
                "id='" + id + '\'' +
                ", amount=" + amount +
                ", description='" + description + '\'' +
                ", category id ='" + category.getId() + '\'' +
                ", category name ='" + category.getName() + '\'' +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getAmount() {
        return amount;
    }

    public String getExpenseTaker() {
        return expenseTaker;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setExpenseTaker(String expenseTaker) {
        this.expenseTaker = expenseTaker;
    }

    public String getDescription() {
        return description;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }
}