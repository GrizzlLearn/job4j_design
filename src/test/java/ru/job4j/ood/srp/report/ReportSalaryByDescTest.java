package ru.job4j.ood.srp.report;

import org.junit.jupiter.api.Test;
import ru.job4j.ood.srp.formatter.ReportSalaryParser;
import ru.job4j.ood.srp.model.Employee;
import ru.job4j.ood.srp.store.MemStore;
import java.util.Calendar;

import static org.assertj.core.api.Assertions.assertThat;

class ReportSalaryByDescTest {

    @Test
    public void descFormatting() {
        MemStore store = new MemStore();
        Calendar now = Calendar.getInstance();
        Employee worker = new Employee("Ivan", now, now, 100);
        Employee worker1 = new Employee("Ivan1", now, now, 90);
        Employee worker2 = new Employee("Ivan2", now, now, 110);
        store.add(worker);
        store.add(worker1);
        store.add(worker2);
        ReportSalaryParser reportSalaryParser = new ReportSalaryParser();
        Report engine = new ReportSalaryByDesc(store, reportSalaryParser);

        StringBuilder expect = new StringBuilder()
                .append("Name; Salary;")
                .append(System.lineSeparator())
                .append(worker2.getName()).append(" ")
                .append(worker2.getSalary())
                .append(System.lineSeparator())
                .append(worker.getName()).append(" ")
                .append(worker.getSalary())
                .append(System.lineSeparator())
                .append(worker1.getName()).append(" ")
                .append(worker1.getSalary())
                .append(System.lineSeparator());
        assertThat(engine.generate(em -> true)).isEqualTo(expect.toString());
    }
}
