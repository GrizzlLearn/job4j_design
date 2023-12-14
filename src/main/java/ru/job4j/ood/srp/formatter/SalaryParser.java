package ru.job4j.ood.srp.formatter;

public interface SalaryParser<T> {

    T sortSalaryByDesc(T employees);
}
