package ru.job4j.ood.srp.report;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.json.JSONArray;
import ru.job4j.ood.srp.formatter.DateTimeParser;
import ru.job4j.ood.srp.model.Employee;
import ru.job4j.ood.srp.store.Store;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.function.Predicate;

public class ReportJSON implements Report {
    private final Store store;
    private final DateTimeParser<Calendar> dateTimeParser;
    private final Gson lib;

    public ReportJSON(Store store, DateTimeParser<Calendar> dateTimeParser, Gson lib) {
        this.store = store;
        this.dateTimeParser = dateTimeParser;
        this.lib = lib;
    }

    @Override
    public String generate(Predicate<Employee> filter) {
        StringBuilder text = new StringBuilder();
        //JSONArray array = new JSONArray();
        List<String> array = new ArrayList<>();
        for (Employee em : store.findBy(filter)) {
            array.add(convertData(em));
        }

        return lib.toJson(array.toString());
    }

    private String convertData(Employee em) {
        JsonObject result = new JsonObject();

        result.addProperty("name", em.getName());
        result.addProperty("hired", dateTimeParser.parse(em.getHired()));
        result.addProperty("fired", dateTimeParser.parse(em.getFired()));
        result.addProperty("salary", em.getSalary());

        /*String result = String.format("{\"name\":\"%s\",\"hired\":\"%s\",\"fired\":\"%s\",\"salary\":%s}",
                em.getName(),
                dateTimeParser.parse(em.getHired()),
                dateTimeParser.parse(em.getFired()),
                em.getSalary());*/

        return result.toString();
    }
}

