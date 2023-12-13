package ru.job4j.ood.srp.report;

import ru.job4j.ood.srp.formatter.DateTimeParser;
import ru.job4j.ood.srp.model.Employee;
import ru.job4j.ood.srp.store.Store;

import java.util.Calendar;
import java.util.function.Predicate;

public class ReportInCSVFormat implements Report {

    private final Store store;
    private final DateTimeParser<Calendar> dateTimeParser;

    public ReportInCSVFormat(Store store, DateTimeParser<Calendar> dateTimeParser) {
        this.store = store;
        this.dateTimeParser = dateTimeParser;
    }

    @Override
    public String generate(Predicate<Employee> filter) {
        StringBuilder text = new StringBuilder();
        text.append("Name, Hired, Fired, Salary")
                .append(System.lineSeparator());
        for (Employee em : store.findBy(filter)) {
            text.append(em.getName()).append(",")
                    .append(dateTimeParser.parse(em.getHired())).append(",")
                    .append(dateTimeParser.parse(em.getFired())).append(",")
                    .append(em.getSalary())
                    .append(System.lineSeparator());
        }

        return text.toString();
    }
}
