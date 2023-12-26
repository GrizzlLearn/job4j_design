package ru.job4j.ood.srp.report;

import com.google.gson.Gson;
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
        StringBuilder text = new StringBuilder();
        for (Employee em : store.findBy(filter)) {
            text.append(lib.toJson(em.getName())).append(dateTimeParser.parse(em.getHired()));
        }
/*        text.append("name").append(":").append(em.getName()).append(",")
                .append("hired").append(":").append(dateTimeParser.parse(em.getHired())).append(",");*/
        return lib.toJson(text);
    }
}
