package WebControllers;

import FXcontrollers.AbstractController;
import GSONSerializable.AllIncomeGsonSerializer;
import GSONSerializable.IncomeGsonSerializer;
import HibernateRepository.CategoryRepository;
import HibernateRepository.IncomeRepository;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import dataStructures.Category;
import dataStructures.Income;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Type;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Properties;

@Controller
public class WebIncomeController extends AbstractController {
    IncomeRepository incomeRepository = new IncomeRepository(entityManagerFactory);
    CategoryRepository categoryRepository = new CategoryRepository(entityManagerFactory);

    @RequestMapping(value = "/income", method = RequestMethod.GET)
    @ResponseStatus(value = HttpStatus.OK)
    @ResponseBody
    public String getIncomes() {
        List<Income> incomes = incomeRepository.getIncomes();
        GsonBuilder gsonBuilder = new GsonBuilder();

        gsonBuilder.registerTypeAdapter(Income.class, new IncomeGsonSerializer());
        Gson parser = gsonBuilder.create();
        parser.toJson(incomes.get(0));

        Type incomeList = new TypeToken<List<Income>>() {
        }.getType();
        gsonBuilder.registerTypeAdapter(incomeList, new AllIncomeGsonSerializer());
        parser = gsonBuilder.create();

        return parser.toJson(incomes);
    }

    @RequestMapping(value = "/income/{id}", method = RequestMethod.GET)
    @ResponseStatus(value = HttpStatus.OK)
    @ResponseBody
    public String getIncome(@PathVariable("id") Integer id) {
        if (id == null) return "No data with id provided";

        Income income = incomeRepository.getIncomeById(id);
        GsonBuilder gson = new GsonBuilder();

        gson.registerTypeAdapter(Income.class, new IncomeGsonSerializer());
        Gson parser = gson.create();

        return parser.toJson(income);
    }

    @RequestMapping(value = "/income", method = RequestMethod.POST)
    @ResponseStatus(value = HttpStatus.OK)
    @ResponseBody
    public void addIncome(@RequestBody String request) {
        Gson parser = new Gson();
        Properties data = parser.fromJson(request, Properties.class);
        String amount = data.getProperty("amount");
        String description = data.getProperty("description");
        String incomeGiver = data.getProperty("incomeGiver");

        try {
            Income income = new Income(Double.parseDouble(amount), description, incomeGiver, LocalDateTime.now());
            incomeRepository.create(income);
        } catch (Exception e) {
            System.out.println("Creation failed");
        }
    }

    @RequestMapping(value = "/{id}/add-income", method = RequestMethod.POST)
    @ResponseStatus(value = HttpStatus.OK)
    @ResponseBody
    public void addIncomeToCategory(@PathVariable("id") Integer category_id, @RequestBody String request) {
        if (category_id == null) {
            System.out.println("No category chosen to add income");
            return;
        }

        Category category = categoryRepository.getCategoryById(category_id);
        Gson parser = new Gson();
        Properties data = parser.fromJson(request, Properties.class);
        String amount = data.getProperty("amount");
        String description = data.getProperty("description");
        String incomeGiver = data.getProperty("incomeGiver");

        try {
            Income income = new Income(Double.parseDouble(amount), description, incomeGiver, LocalDateTime.now(), category);
            incomeRepository.create(income);
        } catch (Exception e) {
            System.out.println("Creation failed");
        }
    }

    @RequestMapping(value = "/income/{id}", method = RequestMethod.PUT)
    @ResponseStatus(value = HttpStatus.OK)
    @ResponseBody
    public void updateIncome(@PathVariable Integer id, @RequestBody String request) {
        if (id == null) {
            System.out.println("No system chosen for update");
            return;
        }

        Income income = incomeRepository.getIncomeById(id);
        Gson parser = new Gson();
        Properties data = parser.fromJson(request, Properties.class);
        String amount = data.getProperty("amount");
        String description = data.getProperty("description");
        String incomeGiver = data.getProperty("incomeGiver");

        income.setAmount(Double.parseDouble(amount));
        income.setDescription(description);
        income.setIncomeGiver(incomeGiver);
        incomeRepository.edit(income);
    }

    @RequestMapping(value = "/income/{id}", method = RequestMethod.DELETE)
    @ResponseStatus(value = HttpStatus.OK)
    @ResponseBody
    public void removeFinanceManagementSystem(@PathVariable Integer id) {
        if (id == null) {
            System.out.println("No system chosen for delete");
            return;
        }

        incomeRepository.remove(id);
    }
}