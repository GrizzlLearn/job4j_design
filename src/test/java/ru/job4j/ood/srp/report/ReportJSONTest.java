package ru.job4j.ood.srp.report;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.json.JSONArray;
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
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.setDateFormat("yyyy-MM-dd");
        Gson lib = gsonBuilder.create();
        Report engine = new ReportJSON(store, parser, lib);
        StringBuilder expect = new StringBuilder()
                .append("[")
                .append("{")
                .append("\"name\":").append("\"").append(worker.getName()).append("\"").append(",")
                .append("\"hired\":").append("\"").append(parser.parse(worker.getHired())).append("\"").append(",")
                .append("\"fired\":").append("\"").append(parser.parse(worker.getFired())).append("\"").append(",")
                .append("\"salary\":").append((int) worker.getSalary())
                .append("}").append(",")
                .append("{")
                .append("\"name\":").append("\"").append(worker1.getName()).append("\"").append(",")
                .append("\"hired\":").append("\"").append(parser.parse(worker1.getHired())).append("\"").append(",")
                .append("\"fired\":").append("\"").append(parser.parse(worker1.getFired())).append("\"").append(",")
                .append("\"salary\":").append((int) worker1.getSalary())
                .append("}").append(",")
                .append("{")
                .append("\"name\":").append("\"").append(worker2.getName()).append("\"").append(",")
                .append("\"hired\":").append("\"").append(parser.parse(worker2.getHired())).append("\"").append(",")
                .append("\"fired\":").append("\"").append(parser.parse(worker2.getFired())).append("\"").append(",")
                .append("\"salary\":").append((int) worker2.getSalary())
                .append("}")
                .append("]");
        assertThat(engine.generate(em -> true)).isEqualTo(expect.toString());
    }
}
