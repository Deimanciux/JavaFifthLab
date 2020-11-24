package dataStructures;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
public class User implements Serializable {
    public static final String TYPE_INDIVIDUAL = "individual";
    public static final String TYPE_COMPANY = "company";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(unique = true)
    private String loginName;
    private String name;
    private String password;
    private String email;
    private String phoneNumber;
    private String type;
    private String surname;
    private String contactPersonName;
    private String contactPersonSurname;

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @OrderBy("id ASC")
    @LazyCollection(LazyCollectionOption.FALSE)
    protected List<Category> categories = new ArrayList<>();

    @ManyToOne
    @LazyCollection(LazyCollectionOption.FALSE)
    private FinanceManagementSystem financeManagementSystem;

    public User(String name, String loginName, String password, String email, String phoneNumber, String type, String surname, FinanceManagementSystem fms) {
        this.name = name;
        this.loginName = loginName;
        this.password = password;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.type = type;
        this.surname = surname;
        this.financeManagementSystem = fms;
    }

    //naudojamas api testavimui
    public User(String name, String loginName, String password, String type) {
        this.name = name;
        this.loginName = loginName;
        this.password = password;
        this.type = type;
    }

    public User(
            String name,
            String loginName,
            String password,
            String email,
            String phoneNumber,
            String type,
            String contactPersonName,
            String contactPersonSurname,
            FinanceManagementSystem fms
    ) {
        this.name = name;
        this.loginName = loginName;
        this.password = password;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.type = type;
        this.contactPersonName = contactPersonName;
        this.contactPersonSurname = contactPersonSurname;
        this.financeManagementSystem = fms;
    }

    public User() {
        this.name = "name";
        this.loginName = "loginName";
        this.password = "password";
        this.surname = "surname";
        this.type = User.TYPE_INDIVIDUAL;
    }

    public String printCompany() {
        return "Company{" +
                "loginName='" + loginName + '\n' +
                "id='" + id + '\n' +
                "name='" + name + '\n' +
                "password='" + password + '\n' +
                "email='" + email + '\n' +
                "phoneNumber='" + phoneNumber + '\n' +
                "contactPersonName='" + contactPersonName + '\n' +
                "contactPersonSurname='" + contactPersonSurname + '\'' +
                "}\n";
    }

    public String printIndividualPerson() {
        return "User{" +
                "loginName='" + loginName + '\n' +
                "id='" + id + '\n' +
                "name='" + name + '\n' +
                "surname='" + surname + '\n' +
                "password='" + password + '\n' +
                "email='" + email + '\n' +
                "phoneNumber='" + phoneNumber + '\n' +
                "}\n";
    }

    @Override
    public String toString() {
        return type +
                ", id=" + id +
                ", name='" + name + '\'' +
                ", loginName='" + loginName + '\'' +
                '}';
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLoginName() {
        return loginName;
    }

    public String getPassword() {
        return password;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getContactPersonName() {
        return contactPersonName;
    }

    public void setContactPersonName(String contactPersonName) {
        this.contactPersonName = contactPersonName;
    }

    public String getContactPersonSurname() {
        return contactPersonSurname;
    }

    public void setContactPersonSurname(String contactPersonSurname) {
        this.contactPersonSurname = contactPersonSurname;
    }

    public List<Category> getCategories() {
        return categories;
    }

    public void setCategories(List<Category> categories) {
        this.categories = categories;
    }

    public FinanceManagementSystem getFinanceManagementSystem() {
        return financeManagementSystem;
    }

    public void setFinanceManagementSystem(FinanceManagementSystem financeManagementSystem) {
        this.financeManagementSystem = financeManagementSystem;
        financeManagementSystem.addUser(this);
    }

    public void removeCategory(Category category) {
        categories.remove(category);
        category.getResponsiblePerson().remove(this);
    }

    public void addCategory(Category category) {
        if (!categories.contains(category)) {
            categories.add(category);

            category.getResponsiblePerson().add(this);
        }
    }
}