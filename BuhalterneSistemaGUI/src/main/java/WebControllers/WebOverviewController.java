package WebControllers;

import FXcontrollers.AbstractController;
import GSONSerializable.AllOverviewGsonSerializer;
import GSONSerializable.OverviewGsonSerializer;
import HibernateRepository.ExpenseRepository;
import HibernateRepository.FinanceManagementSystemRepository;
import HibernateRepository.IncomeRepository;
import HibernateRepository.UserRepository;
import Utils.BalanceCounter;
import Utils.OverviewFactory;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import dataModels.Balance;
import dataModels.Overview;
import dataStructures.*;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Type;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

@Controller
public class WebOverviewController extends AbstractController {
    private UserRepository userRepository = new UserRepository(entityManagerFactory);

    @RequestMapping(value = "/overview/{id}", method = RequestMethod.GET)
    @ResponseStatus(value = HttpStatus.OK)
    @ResponseBody
    public String getOverviews(@PathVariable int id) {
        ArrayList<Overview> overviews;
        User user = userRepository.getUserById(id);

        overviews = OverviewFactory.createOverview(user.getCategories());

        if (overviews.size() == 0) {
            return "";
        }

        OverviewFactory.sortOverview(overviews);
        GsonBuilder gsonBuilder = new GsonBuilder();

        gsonBuilder.registerTypeAdapter(Overview.class, new OverviewGsonSerializer());
        Gson parser = gsonBuilder.create();
        parser.toJson(overviews.get(0));

        Type overviewsType = new TypeToken<ArrayList<Overview>>() {
        }.getType();
        gsonBuilder.registerTypeAdapter(overviewsType, new AllOverviewGsonSerializer());
        parser = gsonBuilder.create();

        return parser.toJson(overviews);
    }
}