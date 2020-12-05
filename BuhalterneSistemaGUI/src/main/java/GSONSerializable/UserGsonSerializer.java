package GSONSerializable;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import dataStructures.Income;
import dataStructures.User;

import java.lang.reflect.Type;

public class UserGsonSerializer implements JsonSerializer<User> {
    @Override
    public JsonElement serialize(User user, Type type, JsonSerializationContext jsonSerializationContext) {
        JsonObject json = new JsonObject();
        json.addProperty("id", user.getId());
        json.addProperty("loginName", user.getLoginName());
        json.addProperty("name", user.getName());
        json.addProperty("surname", user.getSurname());
        json.addProperty("email", user.getEmail());

        return json;
    }
}