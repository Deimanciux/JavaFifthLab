package GSONSerializable;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import dataStructures.Category;

import java.lang.reflect.Type;

public class CategoryGsonSerializer implements JsonSerializer<Category> {
    @Override
    public JsonElement serialize(Category category, Type type, JsonSerializationContext jsonSerializationContext) {
        JsonObject json = new JsonObject();
        json.addProperty("id", category.getId());
        json.addProperty("name", category.getName());
        json.addProperty("description", category.getDescription());

        return json;
    }
}