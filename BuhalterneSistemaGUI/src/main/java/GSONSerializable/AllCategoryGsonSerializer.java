package GSONSerializable;

import com.google.gson.*;
import dataStructures.Category;

import java.lang.reflect.Type;
import java.util.List;

public class AllCategoryGsonSerializer implements JsonSerializer<List<Category>> {
    @Override
    public JsonElement serialize(List<Category> categories, Type type, JsonSerializationContext jsonSerializationContext) {
        JsonArray jsonArray = new JsonArray();

        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(Category.class, new ExpenseGsonSerializer());
        Gson parser = gsonBuilder.create();

        for(Category category : categories) {
            jsonArray.add(parser.toJson(category));
        }

        return jsonArray;
    }
}
