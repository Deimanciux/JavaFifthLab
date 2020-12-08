package WebControllers;

import FXcontrollers.AbstractController;
import GSONSerializable.BalanceGsonSerializer;
import HibernateRepository.*;
import Utils.BalanceCounter;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dataModels.Balance;
import dataStructures.*;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

@Controller
public class WebBalanceController extends AbstractController {
    private final FinanceManagementSystemRepository financeManagementSystemRepository;
    private final CategoryRepository categoryRepository;
    private IncomeRepository incomeRepository = new IncomeRepository(entityManagerFactory);
    private ExpenseRepository expenseRepository = new ExpenseRepository(entityManagerFactory);
    private UserRepository userRepository = new UserRepository(entityManagerFactory);

    public WebBalanceController() {
        this.financeManagementSystemRepository = new FinanceManagementSystemRepository(entityManagerFactory);
        this.categoryRepository = new CategoryRepository(entityManagerFactory);
    }

    @RequestMapping(value = "/balance/system/{id}", method = RequestMethod.GET)
    @ResponseStatus(value = HttpStatus.OK)
    @ResponseBody
    public String getSystemBalance(@PathVariable("id") Integer id) {
        FinanceManagementSystem fms = financeManagementSystemRepository.getFmsById(id);
        Balance balance = BalanceCounter.countSystemBalance(fms);
        GsonBuilder gsonBuilder = new GsonBuilder();

        gsonBuilder.registerTypeAdapter(Balance.class, new BalanceGsonSerializer());
        Gson parser = gsonBuilder.create();

        return parser.toJson(balance);
    }

    @RequestMapping(value = "/balance/category/{id}", method = RequestMethod.GET)
    @ResponseStatus(value = HttpStatus.OK)
    @ResponseBody
    public String getCategoryBalance(@PathVariable("id") Integer id) {
        List<Category> categories = new ArrayList<>();

        Category category = categoryRepository.getCategoryById(id);
        categories.add(category);
        Balance balance = BalanceCounter.countCategoryBalance(categories);
        GsonBuilder gsonBuilder = new GsonBuilder();

        gsonBuilder.registerTypeAdapter(Balance.class, new BalanceGsonSerializer());
        Gson parser = gsonBuilder.create();

        return parser.toJson(balance);
    }

    @RequestMapping(value = "balance/byDate", method = RequestMethod.POST)
    @ResponseStatus(value = HttpStatus.OK)
    @ResponseBody
    public String addCategory(@RequestBody String request) {
        List<Income> incomes;
        List<Expense> expenses;
        Balance balance;
        Gson parser = new Gson();

        Properties data = parser.fromJson(request, Properties.class);
        String fromDate = data.getProperty("fromDate");
        String toDate = data.getProperty("toDate");
        String userId = data.getProperty("userId");

        User user = userRepository.getUserById(Integer.parseInt(userId));

        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDate fromDateLocalDate = LocalDate.parse(fromDate, formatter);
            LocalDate toDateLocalDate = LocalDate.parse(toDate, formatter);

            incomes = incomeRepository.getIncomesByDate(fromDateLocalDate, toDateLocalDate);
            expenses = expenseRepository.getExpensesByDate(fromDateLocalDate, toDateLocalDate);

            balance =  BalanceCounter.countBalanceByDate(incomes, expenses, user);

            GsonBuilder gsonBuilder = new GsonBuilder();

            gsonBuilder.registerTypeAdapter(Balance.class, new BalanceGsonSerializer());
            Gson parser1 = gsonBuilder.create();

            return parser1.toJson(balance);
        } catch (Exception e) {
            return "";
        }
    }
}