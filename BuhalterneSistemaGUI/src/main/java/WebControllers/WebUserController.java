package WebControllers;

import FXcontrollers.AbstractController;
import GSONSerializable.AllUsersGsonSerializer;
import GSONSerializable.UserGsonSerializer;
import HibernateRepository.UserRepository;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import dataStructures.User;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Type;
import java.util.List;
import java.util.Properties;

@Controller
public class WebUserController extends AbstractController {
    UserRepository userRepository = new UserRepository(entityManagerFactory);

    @RequestMapping(value = "user", method = RequestMethod.GET)
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

    @RequestMapping(value = "user/{id}", method = RequestMethod.GET)
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

    @RequestMapping(value = "user", method = RequestMethod.POST)
    @ResponseStatus(value = HttpStatus.OK)
    @ResponseBody
    public void addUser(@RequestBody String request) {
        Gson parser = new Gson();
        Properties data = parser.fromJson(request, Properties.class);
        String name = data.getProperty("name");
        String loginName = data.getProperty("loginName");
        String password = data.getProperty("password");
        String type = data.getProperty("type");

        try {
            User user = new User(name, loginName, password, type);
            userRepository.create(user);
        } catch (Exception e) {
            System.out.println("Creation failed");
        }
    }

    @RequestMapping(value = "user/{id}", method = RequestMethod.PUT)
    @ResponseStatus(value = HttpStatus.OK)
    @ResponseBody
    public void updateUser(@PathVariable Integer id, @RequestBody String request) {
        if (id == null) {
            System.out.println("No system chosen for update");
            return;
        }

        User user = userRepository.getUserById(id);
        Gson parser = new Gson();
        Properties data = parser.fromJson(request, Properties.class);
        String name = data.getProperty("name");
        String loginName = data.getProperty("loginName");
        String password = data.getProperty("password");
        String type = data.getProperty("type");

        user.setName(name);
        user.setLoginName(loginName);
        user.setPassword(password);
        user.setType(type);
        userRepository.edit(user);
    }

    @RequestMapping(value = "user/{id}", method = RequestMethod.DELETE)
    @ResponseStatus(value = HttpStatus.OK)
    @ResponseBody
    public void removeFinanceManagementSystem(@PathVariable Integer id) {
        if (id == null) {
            System.out.println("No system chosen for delete");
            return;
        }

        userRepository.remove(id);
    }
}