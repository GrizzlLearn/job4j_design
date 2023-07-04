package ru.job4j.serialization.json;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.*;

public class JSON {
    public static void main(String[] args) {

        /* JSONArray из ArrayList */
        List<String> fittings = new ArrayList<>();
        fittings.add("handle");
        fittings.add("hinge");
        fittings.add("fasteners");
        JSONArray jsonFittings = new JSONArray(fittings);

        Window window = new Window(
                2.0f,
                2.5f,
                "REHAU",
                true,
                fittings,
                new Glass(1.4f)
        );

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("height", window.getHeight());
        jsonObject.put("weight", window.getWeight());
        jsonObject.put("manufacturer", window.getManufacturer());
        jsonObject.put("isHasSpecialGlass", window.isHasSpecialGlass());
        jsonObject.put("fittings", jsonFittings);
        jsonObject.put("glass", window.getGlass());

        /* Выведем результат в консоль */
        System.out.println(jsonObject);

        /* Преобразуем объект person в json-строку */
        System.out.println(new JSONObject(window));
    }
}
