package WebControllers;

import FXcontrollers.AbstractController;
import GSONSerializable.AllCategoryGsonSerializer;
import GSONSerializable.CategoryGsonSerializer;
import HibernateRepository.CategoryRepository;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import dataStructures.Category;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Type;
import java.util.List;
import java.util.Properties;
@Controller
public class
WebCategoryController extends AbstractController {
    CategoryRepository categoryRepository = new CategoryRepository(entityManagerFactory);

    @RequestMapping(value = "category", method = RequestMethod.GET)
    @ResponseStatus(value = HttpStatus.OK)
    @ResponseBody
    public String getCategories() {
        List<Category> categories = categoryRepository.getCategories();
        GsonBuilder gsonBuilder = new GsonBuilder();

        gsonBuilder.registerTypeAdapter(Category.class, new CategoryGsonSerializer());
        Gson parser = gsonBuilder.create();
        parser.toJson(categories.get(0));

        Type categoryList = new TypeToken<List<Category>>() {
        }.getType();
        gsonBuilder.registerTypeAdapter(categoryList, new AllCategoryGsonSerializer());
        parser = gsonBuilder.create();

        return parser.toJson(categories);
    }

    @RequestMapping(value = "category/{id}", method = RequestMethod.GET)
    @ResponseStatus(value = HttpStatus.OK)
    @ResponseBody
    public String getCategory(@PathVariable("id") Integer id) {
        if (id == null) return "No data with id provided";

        Category category = categoryRepository.getCategoryById(id);
        GsonBuilder gson = new GsonBuilder();

        gson.registerTypeAdapter(Category.class, new CategoryGsonSerializer());
        Gson parser = gson.create();

        return parser.toJson(category);
    }

    @RequestMapping(value = "category", method = RequestMethod.POST)
    @ResponseStatus(value = HttpStatus.OK)
    @ResponseBody
    public void addCategory(@RequestBody String request) {
        Gson parser = new Gson();
        Properties data = parser.fromJson(request, Properties.class);
        String name = data.getProperty("name");
        String description = data.getProperty("description");

        try {
            Category category = new Category(name, description);
            categoryRepository.create(category);
        } catch (Exception e) {
            System.out.println("Creation failed");
        }
    }

    @RequestMapping(value = "category/{id}", method = RequestMethod.PUT)
    @ResponseStatus(value = HttpStatus.OK)
    @ResponseBody
    public void updateCategory(@PathVariable Integer id, @RequestBody String request) {
        if (id == null) {
            System.out.println("No system chosen for update");
            return;
        }

        Category category = categoryRepository.getCategoryById(id);
        Gson parser = new Gson();
        Properties data = parser.fromJson(request, Properties.class);
        String name = data.getProperty("name");
        String description = data.getProperty("description");

        category.setName(name);
        category.setDescription(description);
        categoryRepository.edit(category);
    }

    @RequestMapping(value = "category/{id}", method = RequestMethod.DELETE)
    @ResponseStatus(value = HttpStatus.OK)
    @ResponseBody
    public void removeFinanceManagementSystem(@PathVariable Integer id) {
        if (id == null) {
            System.out.println("No system chosen for delete");
            return;
        }

        categoryRepository.remove(id);
    }
}