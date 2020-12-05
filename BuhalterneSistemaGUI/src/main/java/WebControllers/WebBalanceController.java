package WebControllers;

import GSONSerializable.AllCategoryGsonSerializer;
import GSONSerializable.CategoryGsonSerializer;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import dataStructures.Category;
import dataStructures.User;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Type;
import java.util.List;

public class WebBalanceController {
//    @RequestMapping(value = "/balance/system", method = RequestMethod.GET)
//    @ResponseStatus(value = HttpStatus.OK)
//    @ResponseBody
//    public String getUserCategories() {
//        User user = userRepository.getUserById(id);
//        List<Category> categories = user.getCategories();
//        GsonBuilder gsonBuilder = new GsonBuilder();
//
//        gsonBuilder.registerTypeAdapter(Category.class, new CategoryGsonSerializer());
//        Gson parser = gsonBuilder.create();
//        parser.toJson(categories.get(0));
//
//        Type categoryList = new TypeToken<List<Category>>() {
//        }.getType();
//        gsonBuilder.registerTypeAdapter(categoryList, new AllCategoryGsonSerializer());
//        parser = gsonBuilder.create();
//
//        return parser.toJson(categories);
//    }
}
