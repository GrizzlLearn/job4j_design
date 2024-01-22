package ru.job4j.ood.srp.report;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import ru.job4j.ood.srp.formatter.DateTimeParser;
import ru.job4j.ood.srp.model.Employee;
import ru.job4j.ood.srp.store.Store;

import java.text.SimpleDateFormat;
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
        List<ConvertData> list = store.findBy(filter).stream().map(ConvertData::new).toList();

        return lib.toJson(list);
    }

    private static class ConvertData {
        private String name;
        private String hired;
        private String fired;
        private double salary;

        public ConvertData(Employee em) {
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd:MM:yyyy HH:mm");
            name = em.getName();
            hired = dateFormat.format(em.getHired().getTime());
            fired = dateFormat.format(em.getFired().getTime());
            salary = em.getSalary();
        }
    }
}

