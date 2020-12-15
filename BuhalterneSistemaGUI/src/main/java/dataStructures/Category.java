package dataStructures;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Category implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name = "none";
    private String description;

    @ManyToMany(mappedBy = "categories")
    @OrderBy("id ASC")
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<User> responsiblePerson = new ArrayList<>();

    @ManyToOne
    private Category parentCategory = null;

    @OneToMany(mappedBy = "parentCategory", cascade = {CascadeType.REMOVE}, orphanRemoval = true)
    @OrderBy("id ASC")
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<Category> subCategories = new ArrayList<>();

    @OneToMany(mappedBy = "category", orphanRemoval = true)
    @OrderBy("id ASC")
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<Income> incomes = new ArrayList<>();

    @OneToMany(mappedBy = "category", orphanRemoval = true)
    @OrderBy("id ASC")
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<Expense> expenses = new ArrayList<>();

    @ManyToOne
    private FinanceManagementSystem financeManagementSystem;

    public Category() {

    }

    //naudojamas testuoi api
    public Category(
            String name,
            String description,
            FinanceManagementSystem fms
    ) {
        this.name = name;
        this.description = description;
        this.financeManagementSystem = fms;
    }

    public Category(
            String name,
            String description,
            Category parentCategory,
            FinanceManagementSystem financeManagementSystem
    ) {
        this.name = name;
        this.description = description;
        this.parentCategory = parentCategory;
        this.financeManagementSystem = financeManagementSystem;
    }

    @Override
    public String toString() {
        return id + " " + name;
    }

    public String printCategoryData() {
        return "id='" + id + '\n' +
                " name='" + name + '\n' +
                " description='" + description + '\n' +
                " responsiblePerson=" + responsiblePerson + '\'' + "\n" +
                " subCategories=" + subCategories + '\'' + "\n" +
                " income=" + incomes + '\'' + "\n" +
                " expense=" + expenses;
    }

    public Expense getExpenseData(int id) {
        return expenses.stream().filter(
                expense -> expense.getId() == id
        ).findFirst().orElse(null);
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public List<User> getResponsiblePerson() {
        return responsiblePerson;
    }

    public List<Category> getSubCategories() {
        return subCategories;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public FinanceManagementSystem getFinanceManagementSystem() {
        return financeManagementSystem;
    }

    public void setFinanceManagementSystem(FinanceManagementSystem financeManagementSystem) {
        this.financeManagementSystem = financeManagementSystem;
    }

    public Category getParentCategory() {
        return parentCategory;
    }

    public void setParentCategory(Category parentCategory) {
        this.parentCategory = parentCategory;
    }

    public String getParentName() {
        if (this.parentCategory == null) {
            return null;
        }

        return this.parentCategory.getName();
    }

    public List<Income> getIncomes() {
        return incomes;
    }

    public Category addExpense(Expense expense) {
        if (!expenses.contains(expense)) {
            expenses.add(expense);
            expense.setCategory(this);
        }

        return this;
    }

    public List<Expense> getExpenses() {
        return expenses;
    }

    public void setExpenses(List<Expense> expenses) {
        this.expenses = expenses;
    }

    public void addResponsiblePerson(User user) {
        if (!responsiblePerson.contains(user)) {
            responsiblePerson.add(user);
            user.getCategories().add(this);
        }
    }
}