package WebControllers;

import FXcontrollers.AbstractController;
import GSONSerializable.AllFmsGsonSerializer;
import GSONSerializable.FmsGsonSerializer;
import HibernateRepository.FinanceManagementSystemRepository;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import dataStructures.FinanceManagementSystem;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Type;
import java.time.LocalDate;
import java.util.List;
import java.util.Properties;

@Controller
public class WebFinanceManagementSystemController extends AbstractController {
    FinanceManagementSystemRepository financeManagementSystemRepository = new FinanceManagementSystemRepository(entityManagerFactory);

    @RequestMapping(value = "/fms", method = RequestMethod.GET)
    @ResponseStatus(value = HttpStatus.OK)
    @ResponseBody
    public String getFinanceManagementSystems() {
        List<FinanceManagementSystem> allFms = financeManagementSystemRepository.getAllFms();

        if (allFms.size() == 0) {
            return "";
        }

        GsonBuilder gsonBuilder = new GsonBuilder();

        gsonBuilder.registerTypeAdapter(FinanceManagementSystem.class, new FmsGsonSerializer());
        Gson parser = gsonBuilder.create();
        parser.toJson(allFms.get(0));

        Type fmsList = new TypeToken<List<FinanceManagementSystem>>() {
        }.getType();
        gsonBuilder.registerTypeAdapter(fmsList, new AllFmsGsonSerializer());
        parser = gsonBuilder.create();

        return parser.toJson(allFms);
    }

    @RequestMapping(value = "/fms/{id}", method = RequestMethod.GET)
    @ResponseStatus(value = HttpStatus.OK)
    @ResponseBody
    public String getFinanceManagementSystem(@PathVariable("id") Integer id) {
        if (id == null) return "No finance management system exists with id provided";

        FinanceManagementSystem fms = financeManagementSystemRepository.getFmsById(id);
        GsonBuilder gson = new GsonBuilder();

        gson.registerTypeAdapter(FinanceManagementSystem.class, new FmsGsonSerializer());
        Gson parser = gson.create();

        return parser.toJson(fms);
    }

    @RequestMapping(value = "/fms", method = RequestMethod.POST)
    @ResponseStatus(value = HttpStatus.OK)
    @ResponseBody
    public void addFinanceManagementSystem(@RequestBody String request) {
        Gson parser = new Gson();
        Properties data = parser.fromJson(request, Properties.class);
        String company = data.getProperty("company");
        String version = data.getProperty("systemVersion");

        try {
            FinanceManagementSystem fms = new FinanceManagementSystem(company, LocalDate.now(), version);
            financeManagementSystemRepository.create(fms);
        } catch (Exception e) {
            System.out.println("Creation failed");
        }
    }

    @RequestMapping(value = "/fms/{id}", method = RequestMethod.PUT)
    @ResponseStatus(value = HttpStatus.OK)
    @ResponseBody
    public void updateUser(@PathVariable Integer id, @RequestBody String request) {
        if (id == null) {
            System.out.println("No system chosen for update");
            return;
        }

        FinanceManagementSystem fms = financeManagementSystemRepository.getFmsById(id);
        Gson parser = new Gson();
        Properties data = parser.fromJson(request, Properties.class);
        String company = data.getProperty("company");
        String systemVersion = data.getProperty("systemVersion");

        fms.setCompany(company);
        fms.setSystemVersion(systemVersion);
        financeManagementSystemRepository.edit(fms);
    }

    @RequestMapping(value = "/fms/{id}", method = RequestMethod.DELETE)
    @ResponseStatus(value = HttpStatus.OK)
    @ResponseBody
    public void updateUser(@PathVariable Integer id) {
        if (id == null) {
            System.out.println("No system chosen for delete");
            return;
        }

        financeManagementSystemRepository.remove(id);
    }
}