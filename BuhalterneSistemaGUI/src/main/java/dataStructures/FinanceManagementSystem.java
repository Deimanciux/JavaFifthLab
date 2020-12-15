package dataStructures;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Entity
public class FinanceManagementSystem implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String company;
    private LocalDate systemCreated;
    private String systemVersion;

    @OneToMany(mappedBy = "financeManagementSystem", orphanRemoval = true)
    @OrderBy("id ASC")
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<User> users = new ArrayList<>();

    @OneToMany(mappedBy = "financeManagementSystem", orphanRemoval = true)
    @OrderBy("id ASC")
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<Category> categories = new ArrayList<>();

    public FinanceManagementSystem() {
    }

    public FinanceManagementSystem(String company, LocalDate systemCreated, String systemVersion) {
        this.company = company;
        this.systemCreated = systemCreated;
        this.systemVersion = systemVersion;
    }

    public FinanceManagementSystem(String company, String systemVersion) {
        this.company = company;
        this.systemVersion = systemVersion;
    }

    public List<User> getIndividualPersons() {
        return users.stream().filter(user -> user.getType().equals(User.TYPE_INDIVIDUAL)).collect(Collectors.toList());
    }

    public List<User> getCompanies() {
        return users.stream().filter(user -> user.getType().equals(User.TYPE_COMPANY)).collect(Collectors.toList());
    }

    public User getUserData(int id) {
        return users.stream().filter(
                user -> user.getId() == id
        ).findFirst().orElse(null);
    }

    public Category getCategoryData(String name) {
        return categories.stream().filter(category -> category.getName().equals(name)).findFirst().orElse(null);
    }

    public List<Category> getCategories() {
        return categories;
    }

    public FinanceManagementSystem addCategory(Category category) {
        if (!categories.contains(category)) {
            categories.add(category);
            category.setFinanceManagementSystem(this);
        }

        return this;
    }

    public FinanceManagementSystem removeCategory(Category category) {
        categories.remove(category);
        category.setFinanceManagementSystem(null);

        return this;
    }

    public FinanceManagementSystem addUser(User user) {
        if (!users.contains(user)) {
            users.add(user);
            user.setFinanceManagementSystem(this);
        }

        return this;
    }

    public int getId() {
        return id;
    }

    public String getCompany() {
        return company;
    }

    public LocalDate getSystemCreated() {
        return systemCreated;
    }

    public String getSystemVersion() {
        return systemVersion;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public void setSystemCreated(LocalDate systemCreated) {
        this.systemCreated = systemCreated;
    }

    public void setSystemVersion(String systemVersion) {
        this.systemVersion = systemVersion;
    }

    public void setCategories(List<Category> categories) {
        this.categories = categories;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    @Override
    public String toString() {
        return company + " " + systemVersion;
    }
}