package WebControllers;

import FXcontrollers.AbstractController;
import GSONSerializable.BalanceGsonSerializer;
import HibernateRepository.CategoryRepository;
import HibernateRepository.FinanceManagementSystemRepository;
import Utils.BalanceCounter;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dataModels.Balance;
import dataStructures.Category;
import dataStructures.FinanceManagementSystem;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
public class WebBalanceController extends AbstractController {
    private final FinanceManagementSystemRepository financeManagementSystemRepository;
    private final CategoryRepository categoryRepository;

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
}