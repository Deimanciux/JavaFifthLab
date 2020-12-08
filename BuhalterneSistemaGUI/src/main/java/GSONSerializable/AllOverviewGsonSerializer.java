package GSONSerializable;

import com.google.gson.*;
import dataModels.Overview;

import java.lang.reflect.Type;
import java.util.List;

public class AllOverviewGsonSerializer implements JsonSerializer<List<Overview>>{
    @Override
    public JsonElement serialize(List<Overview> overviews, Type type, JsonSerializationContext jsonSerializationContext) {
        JsonArray jsonArray = new JsonArray();

        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(Overview.class, new OverviewGsonSerializer());
        Gson parser = gsonBuilder.create();

        for (Overview overview : overviews) {
            jsonArray.add(parser.toJson(overview));
        }

        return jsonArray;
    }
}
