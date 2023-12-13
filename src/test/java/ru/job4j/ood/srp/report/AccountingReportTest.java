package ru.job4j.ood.srp.report;

import org.junit.jupiter.api.Test;
import ru.job4j.ood.srp.currency.Currency;
import ru.job4j.ood.srp.currency.InMemoryCurrencyConverter;
import ru.job4j.ood.srp.formatter.DateTimeParser;
import ru.job4j.ood.srp.formatter.ReportDateTimeParser;
import ru.job4j.ood.srp.model.Employee;
import ru.job4j.ood.srp.store.MemStore;
import java.util.Calendar;

import static org.assertj.core.api.Assertions.assertThat;

class AccountingReportTest {

    @Test
    public void accountingReport() {
        MemStore store = new MemStore();
        Calendar now = Calendar.getInstance();
        Employee worker = new Employee("Ivan", now, now, 100);
        Employee worker1 = new Employee("Ivan1", now, now, 90);
        Employee worker2 = new Employee("Ivan2", now, now, 110);
        store.add(worker);
        store.add(worker1);
        store.add(worker2);
        DateTimeParser<Calendar> parser = new ReportDateTimeParser();
        InMemoryCurrencyConverter inMemoryCurrencyConverter = new InMemoryCurrencyConverter();
        Report engine = new AccountingReport(store, parser, inMemoryCurrencyConverter);

        StringBuilder expect = new StringBuilder()
                .append("Name; Hired; Fired; Salary;")
                .append(System.lineSeparator())
                .append(worker.getName()).append(" ")
                .append(parser.parse(worker.getHired())).append(" ")
                .append(parser.parse(worker.getFired())).append(" ")
                .append(inMemoryCurrencyConverter.convert(Currency.RUB, worker.getSalary(), Currency.USD))
                .append(System.lineSeparator())
                .append(worker1.getName()).append(" ")
                .append(parser.parse(worker1.getHired())).append(" ")
                .append(parser.parse(worker1.getFired())).append(" ")
                .append(inMemoryCurrencyConverter.convert(Currency.RUB, worker1.getSalary(), Currency.USD))
                .append(System.lineSeparator())
                .append(worker2.getName()).append(" ")
                .append(parser.parse(worker2.getHired())).append(" ")
                .append(parser.parse(worker2.getFired())).append(" ")
                .append(inMemoryCurrencyConverter.convert(Currency.RUB, worker2.getSalary(), Currency.USD))
                .append(System.lineSeparator());
        assertThat(engine.generate(em -> true)).isEqualTo(expect.toString());
    }

}
