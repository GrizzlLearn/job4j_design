package ru.job4j.ood.srp.report;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import org.json.JSONArray;
import org.json.JSONObject;
import ru.job4j.ood.srp.formatter.DateTimeParser;
import ru.job4j.ood.srp.model.Employee;
import ru.job4j.ood.srp.store.Store;

import java.util.Calendar;
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
        JSONArray jsonArray = new JSONArray();
        JSONObject jsonObject;
        for (Employee em : store.findBy(filter)) {
            jsonObject = new JSONObject();
            jsonObject.put("name", em.getName());
            jsonObject.put("hired", dateTimeParser.parse(em.getHired()));
            jsonObject.put("fired", dateTimeParser.parse(em.getFired()));
            jsonObject.put("salary", em.getSalary());
            jsonArray.put(jsonObject);
        }

        return jsonArray.toString();
    }
}
