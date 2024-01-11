package ru.job4j.ood.srp.report;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.junit.jupiter.api.Test;
import ru.job4j.ood.srp.formatter.DateTimeParser;
import ru.job4j.ood.srp.formatter.ReportDateTimeParser;
import ru.job4j.ood.srp.model.Employee;
import ru.job4j.ood.srp.store.MemStore;

import java.util.Calendar;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class ReportJSONTest {

    @Test
    public void jsonFormat() {
        MemStore store = new MemStore();
        Calendar now = Calendar.getInstance();
        Employee worker = new Employee("Ivan", now, now, 100);
        Employee worker1 = new Employee("Ivan1", now, now, 90);
        Employee worker2 = new Employee("Ivan2", now, now, 110);
        store.add(worker);
        store.add(worker1);
        store.add(worker2);
        DateTimeParser<Calendar> parser = new ReportDateTimeParser();
        Gson lib = new GsonBuilder().create();
        Report engine = new ReportJSON(store, parser, lib);

        StringBuilder expect = new StringBuilder()
                .append("[")
                .append(System.lineSeparator())
                .append("\t").append("{")
                .append(System.lineSeparator())
                .append("\t").append("\t").append("\"name\": ").append("\"").append(worker.getName()).append("\"").append(",")
                .append(System.lineSeparator())
                .append("\t").append("\t").append("\"hired\": ").append("\"").append(parser.parse(worker.getHired())).append("\"").append(",")
                .append(System.lineSeparator())
                .append("\t").append("\t").append("\"fired\": ").append("\"").append(parser.parse(worker.getFired())).append("\"").append(",")
                .append(System.lineSeparator())
                .append("\t").append("\t").append("\"salary\": ").append("\"").append(worker.getSalary()).append("\"")
                .append(System.lineSeparator())
                .append("\t").append("}").append(",")
                .append(System.lineSeparator())
                .append("\t").append("{")
                .append(System.lineSeparator())
                .append("\t").append("\t").append("\"name\": ").append(worker1.getName()).append(",")
                .append(System.lineSeparator())
                .append("\t").append("\t").append("\"hired\": ").append(parser.parse(worker1.getHired())).append(",")
                .append(System.lineSeparator())
                .append("\t").append("\t").append("\"fired\": ").append(parser.parse(worker1.getFired())).append(",")
                .append(System.lineSeparator())
                .append("\t").append("\t").append("\"salary\": ").append(worker1.getSalary())
                .append(System.lineSeparator())
                .append("\t").append("}").append(",")
                .append(System.lineSeparator())
                .append("\t").append("{")
                .append(System.lineSeparator())
                .append("\t").append("\t").append("\"name\": ").append(worker2.getName()).append(",")
                .append(System.lineSeparator())
                .append("\t").append("\t").append("\"hired\": ").append(parser.parse(worker2.getHired())).append(",")
                .append(System.lineSeparator())
                .append("\t").append("\t").append("\"fired\": ").append(parser.parse(worker2.getFired())).append(",")
                .append(System.lineSeparator())
                .append("\t").append("\t").append("\"salary\": ").append(worker2.getSalary())
                .append(System.lineSeparator())
                .append("\t").append("}")
                .append(System.lineSeparator())
                .append("]");
        assertThat(engine.generate(em -> true)).isEqualTo(expect.toString());
    }
}
