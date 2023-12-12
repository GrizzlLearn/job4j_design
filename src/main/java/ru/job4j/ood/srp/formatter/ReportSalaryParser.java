package ru.job4j.ood.srp.formatter;

import ru.job4j.ood.srp.model.Employee;
import ru.job4j.ood.srp.store.MemStore;

import java.util.Comparator;
import java.util.List;

public class ReportSalaryParser implements SalaryParser<List<Employee>> {

    @Override
    public List<Employee> sortSalaryByDesc(List<Employee> employees) {
        return employees.stream()
                .sorted(Comparator.comparingDouble(Employee::getSalary).reversed())
                .toList();
    }
}
