package GSONSerializable;

import com.google.gson.*;
import dataStructures.FinanceManagementSystem;
import dataStructures.Income;
import dataStructures.User;

import java.lang.reflect.Type;
import java.util.List;

public class AllUsersGsonSerializer implements JsonSerializer<List<User>>{
    @Override
    public JsonElement serialize(List<User> users, Type type, JsonSerializationContext jsonSerializationContext) {
        JsonArray jsonArray = new JsonArray();

        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(User.class, new FmsGsonSerializer());
        Gson parser = gsonBuilder.create();

        for(User user : users) {
            jsonArray.add(parser.toJson(user));
        }

        return jsonArray;
    }
}
