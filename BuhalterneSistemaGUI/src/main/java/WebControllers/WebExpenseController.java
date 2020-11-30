package WebControllers;

import FXcontrollers.AbstractController;
import GSONSerializable.AllExpenseGsonSerializer;
import GSONSerializable.ExpenseGsonSerializer;
import HibernateRepository.CategoryRepository;
import HibernateRepository.ExpenseRepository;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import dataStructures.Category;
import dataStructures.Expense;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Type;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Properties;

@Controller
public class WebExpenseController extends AbstractController {
    ExpenseRepository expenseRepository = new ExpenseRepository(entityManagerFactory);
    CategoryRepository categoryRepository = new CategoryRepository(entityManagerFactory);

    @RequestMapping(value = "/expense", method = RequestMethod.GET)
    @ResponseStatus(value = HttpStatus.OK)
    @ResponseBody
    public String getExpenses() {
        List<Expense> expenses = expenseRepository.getExpenses();
        GsonBuilder gsonBuilder = new GsonBuilder();

        gsonBuilder.registerTypeAdapter(Expense.class, new ExpenseGsonSerializer());
        Gson parser = gsonBuilder.create();
        parser.toJson(expenses.get(0));

        Type expenseList = new TypeToken<List<Expense>>() {
        }.getType();
        gsonBuilder.registerTypeAdapter(expenseList, new AllExpenseGsonSerializer());
        parser = gsonBuilder.create();

        return parser.toJson(expenses);
    }

    @RequestMapping(value = "expense/{id}", method = RequestMethod.GET)
    @ResponseStatus(value = HttpStatus.OK)
    @ResponseBody
    public String getExpense(@PathVariable("id") Integer id) {
        if (id == null) return "No data with id provided";

        Expense expense = expenseRepository.getExpenseById(id);
        GsonBuilder gson = new GsonBuilder();

        gson.registerTypeAdapter(Expense.class, new ExpenseGsonSerializer());
        Gson parser = gson.create();

        return parser.toJson(expense);
    }

    @RequestMapping(value = "expense", method = RequestMethod.POST)
    @ResponseStatus(value = HttpStatus.OK)
    @ResponseBody
    public void addExpense(@RequestBody String request) {
        Gson parser = new Gson();
        Properties data = parser.fromJson(request, Properties.class);
        String amount = data.getProperty("amount");
        String description = data.getProperty("description");
        String expenseTaker = data.getProperty("expenseTaker");

        try {
            Expense expense = new Expense(Double.parseDouble(amount), description, expenseTaker, LocalDateTime.now());
            expenseRepository.create(expense);
        } catch (Exception e) {
            System.out.println("Creation failed");
        }
    }

    @RequestMapping(value = "{id}/add-expense", method = RequestMethod.POST)
    @ResponseStatus(value = HttpStatus.OK)
    @ResponseBody
    public void addExpenseToCategory(@PathVariable("id") Integer category_id, @RequestBody String request) {
        if (category_id == null) {
            System.out.println("No category chosen to add income");
            return;
        }

        Category category = categoryRepository.getCategoryById(category_id);
        Gson parser = new Gson();
        Properties data = parser.fromJson(request, Properties.class);
        String amount = data.getProperty("amount");
        String description = data.getProperty("description");
        String expenseTaker = data.getProperty("expenseTaker");

        try {
            Expense expense = new Expense(Double.parseDouble(amount), description, expenseTaker, LocalDateTime.now(), category);
            expenseRepository.create(expense);
        } catch (Exception e) {
            System.out.println("Creation failed");
        }
    }

    @RequestMapping(value = "expense/{id}", method = RequestMethod.PUT)
    @ResponseStatus(value = HttpStatus.OK)
    @ResponseBody
    public void updateExpense(@PathVariable Integer id, @RequestBody String request) {
        if (id == null) {
            System.out.println("No system chosen for update");
            return;
        }

        Expense expense = expenseRepository.getExpenseById(id);
        Gson parser = new Gson();
        Properties data = parser.fromJson(request, Properties.class);
        String amount = data.getProperty("amount");
        String description = data.getProperty("description");
        String expenseTaker = data.getProperty("expenseTaker");

        expense.setAmount(Double.parseDouble(amount));
        expense.setDescription(description);
        expense.setExpenseTaker(expenseTaker);
        expenseRepository.edit(expense);
    }

    @RequestMapping(value = "expense/{id}", method = RequestMethod.DELETE)
    @ResponseStatus(value = HttpStatus.OK)
    @ResponseBody
    public void removeFinanceManagementSystem(@PathVariable Integer id) {
        if (id == null) {
            System.out.println("No system chosen for delete");
            return;
        }

        expenseRepository.remove(id);
    }
}