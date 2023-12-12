package ru.job4j.ood.srp.report;

import ru.job4j.ood.srp.formatter.ReportSalaryParser;
import ru.job4j.ood.srp.model.Employee;
import ru.job4j.ood.srp.store.MemStore;
import java.util.function.Predicate;

public class ReportSalaryByDesc implements Report {

    private final MemStore store;
    private final ReportSalaryParser reportSalaryParser;

    public ReportSalaryByDesc(MemStore store, ReportSalaryParser reportSalaryParser) {
        this.store = store;
        this.reportSalaryParser = reportSalaryParser;
    }

    @Override
    public String generate(Predicate<Employee> filter) {
        StringBuilder text = new StringBuilder();
        text.append("Name; Salary;")
                .append(System.lineSeparator());
        for (Employee em :  reportSalaryParser.sortSalaryByDesc(store.getAll())) {
            text.append(em.getName()).append(" ")
                    .append(em.getSalary())
                    .append(System.lineSeparator());
        }
        return text.toString();
    }
}
