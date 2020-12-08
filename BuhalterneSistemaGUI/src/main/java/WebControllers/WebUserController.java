package WebControllers;

import FXcontrollers.AbstractController;
import GSONSerializable.AllUsersGsonSerializer;
import GSONSerializable.UserGsonSerializer;
import HibernateRepository.CategoryRepository;
import HibernateRepository.FinanceManagementSystemRepository;
import HibernateRepository.UserRepository;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import dataStructures.Category;
import dataStructures.FinanceManagementSystem;
import dataStructures.User;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Type;
import java.util.List;
import java.util.Properties;

@Controller
public class WebUserController extends AbstractController {
    private final UserRepository userRepository = new UserRepository(entityManagerFactory);
    private final FinanceManagementSystemRepository financeManagementSystemRepository = new FinanceManagementSystemRepository(entityManagerFactory);
    private final CategoryRepository categoryRepository = new CategoryRepository(entityManagerFactory);

    @RequestMapping(value = "/user", method = RequestMethod.GET)
    @ResponseStatus(value = HttpStatus.OK)
    @ResponseBody
    public String getUsers() {
        List<User> users = userRepository.getUsers();
        GsonBuilder gsonBuilder = new GsonBuilder();

        gsonBuilder.registerTypeAdapter(User.class, new UserGsonSerializer());
        Gson parser = gsonBuilder.create();
        parser.toJson(users.get(0));

        Type userList = new TypeToken<List<User>>() {
        }.getType();
        gsonBuilder.registerTypeAdapter(userList, new AllUsersGsonSerializer());
        parser = gsonBuilder.create();

        return parser.toJson(users);
    }

    @RequestMapping(value = "/user/{id}", method = RequestMethod.GET)
    @ResponseStatus(value = HttpStatus.OK)
    @ResponseBody
    public String getUser(@PathVariable("id") Integer id) {
        if (id == null) return "No data with id provided";

        User user = userRepository.getUserById(id);
        GsonBuilder gson = new GsonBuilder();

        gson.registerTypeAdapter(User.class, new UserGsonSerializer());
        Gson parser = gson.create();

        return parser.toJson(user);
    }

    @RequestMapping(value = "/user", method = RequestMethod.POST)
    @ResponseStatus(value = HttpStatus.OK)
    @ResponseBody
    public void addUser(@RequestBody String request) {
        Gson parser = new Gson();
        Properties data = parser.fromJson(request, Properties.class);
        String name = data.getProperty("name");
        String loginName = data.getProperty("loginName");
        String password = data.getProperty("password");
        String type = data.getProperty("type");

        User user = new User(name, loginName, password, type);
        userRepository.create(user);
    }

    @RequestMapping(value = "/individual", method = RequestMethod.POST)
    @ResponseStatus(value = HttpStatus.OK)
    @ResponseBody
    public void addIndividual(@RequestBody String request) {
        Gson parser = new Gson();
        Properties data = parser.fromJson(request, Properties.class);
        String name = data.getProperty("name");
        String loginName = data.getProperty("loginName");
        String password = data.getProperty("password");
        String email = data.getProperty("email");
        String phoneNumber = data.getProperty("phoneNumber");
        String surname = data.getProperty("surname");
        String fms_id = data.getProperty("fms_id");
        FinanceManagementSystem fms = financeManagementSystemRepository.getFmsById(Integer.parseInt(fms_id));

        User user = new User(name, loginName, password, email, phoneNumber, User.TYPE_INDIVIDUAL, surname, fms);
        userRepository.create(user);
    }

    @RequestMapping(value = "/company", method = RequestMethod.POST)
    @ResponseStatus(value = HttpStatus.OK)
    @ResponseBody
    public void addCompany(@RequestBody String request) {
        Gson parser = new Gson();
        Properties data = parser.fromJson(request, Properties.class);
        String name = data.getProperty("name");
        String loginName = data.getProperty("loginName");
        String password = data.getProperty("password");
        String email = data.getProperty("email");
        String phoneNumber = data.getProperty("phoneNumber");
        String contactPersonName = data.getProperty("contactPersonName");
        String contactPersonSurname = data.getProperty("contactPersonSurname");
        String fms_id = data.getProperty("fms_id");
        FinanceManagementSystem fms = financeManagementSystemRepository.getFmsById(Integer.parseInt(fms_id));

        User user = new User(name, loginName, password, email, phoneNumber, User.TYPE_COMPANY, contactPersonName, contactPersonSurname, fms);
        userRepository.create(user);
    }

    @RequestMapping(value = "/user/{id}", method = RequestMethod.PUT)
    @ResponseStatus(value = HttpStatus.OK)
    @ResponseBody
    public String updateUser(@PathVariable Integer id, @RequestBody String request) {
        User user = userRepository.getUserById(id);

        if (user == null) {
            System.out.println("No user chosen for update");
            return "0";
        }

        try {
            Gson parser = new Gson();
            Properties data = parser.fromJson(request, Properties.class);
            String name = data.getProperty("name");
            String loginName = data.getProperty("loginName");
            String email = data.getProperty("email");
            String surname = data.getProperty("surname");

            user.setName(name);
            user.setLoginName(loginName);
            user.setEmail(email);
            user.setSurname(surname);
            userRepository.edit(user);
            return "1";
        } catch (Exception e) {
            e.printStackTrace();
            return "0";
        }
    }

    @RequestMapping(value = "/user/{id}", method = RequestMethod.DELETE)
    @ResponseStatus(value = HttpStatus.OK)
    @ResponseBody
    public void removeUser(@PathVariable Integer id) {
        if (id == null) {
            System.out.println("No system chosen for delete");
            return;
        }

        userRepository.remove(id);
    }

    @RequestMapping(value = "/user/login", method = RequestMethod.POST)
    @ResponseStatus(value = HttpStatus.OK)
    @ResponseBody
    public String loginUser(@RequestBody String request) {
        Gson parser = new Gson();
        Properties data = parser.fromJson(request, Properties.class);
        String loginName = data.getProperty("loginName");
        String password = data.getProperty("password");
        String fms_id = data.getProperty("fms_id");

        User user = userRepository.getUserByLogin(loginName);

        if (user == null || user.getFinanceManagementSystem().getId() != Integer.parseInt(fms_id)
                || !user.getPassword().equals(password)) {
            return "";
        }

        GsonBuilder gson = new GsonBuilder();
        gson.registerTypeAdapter(User.class, new UserGsonSerializer());
        Gson parser1 = gson.create();
        return parser1.toJson(user);
    }

    @RequestMapping(value = "/category/{id}/users", method = RequestMethod.GET)
    @ResponseStatus(value = HttpStatus.OK)
    @ResponseBody
    public String getCategoryUsers(@PathVariable Integer id) {
        Category category = categoryRepository.getCategoryById(id);
        System.out.println("users list " + category.getResponsiblePerson());
        List<User> users = category.getResponsiblePerson();
        GsonBuilder gsonBuilder = new GsonBuilder();

        gsonBuilder.registerTypeAdapter(User.class, new UserGsonSerializer());
        Gson parser = gsonBuilder.create();
        parser.toJson(users.get(0));

        Type usersList = new TypeToken<List<User>>() {
        }.getType();
        gsonBuilder.registerTypeAdapter(usersList, new AllUsersGsonSerializer());
        parser = gsonBuilder.create();

        return parser.toJson(users);
    }
}