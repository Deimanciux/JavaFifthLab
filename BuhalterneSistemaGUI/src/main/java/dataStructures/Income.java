package dataStructures;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
public class Income implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private double amount;
    private String description;
    private String incomeGiver;
    private LocalDateTime date;

    @ManyToOne
    private Category category;

    public Income() {
    }

    public Income(double amount, String description, String incomeGiver, LocalDateTime date, Category category) {
        this.amount = amount;
        this.description = description;
        this.incomeGiver = incomeGiver;
        this.date = date;
        this.category = category;
    }

    //naudojamas api testavimui
    public Income(double amount, String description, String incomeGiver, LocalDateTime date) {
        this.amount = amount;
        this.description = description;
        this.incomeGiver = incomeGiver;
        this.date = date;
    }

    public Income(double amount, String description, Category category) {
        this.amount = amount;
        this.description = description;
        this.category = category;
    }

    @Override
    public String toString() {
        return "Income{" +
                "id='" + id + '\'' +
                ", amount=" + amount +
                ", description='" + description + '\'' +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setIncomeGiver(String incomeGiver) {
        this.incomeGiver = incomeGiver;
    }

    public String getDescription() {
        return description;
    }

    public double getAmount() {
        return amount;
    }

    public String getIncomeGiver() {
        return incomeGiver;
    }

    public LocalDateTime getDate() {
        return date;
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