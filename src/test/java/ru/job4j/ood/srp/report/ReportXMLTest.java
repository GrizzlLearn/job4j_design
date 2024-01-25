package ru.job4j.ood.srp.report;

import org.junit.jupiter.api.Test;
import ru.job4j.ood.srp.formatter.DateTimeParser;
import ru.job4j.ood.srp.formatter.ReportDateTimeParser;
import ru.job4j.ood.srp.model.Employee;
import ru.job4j.ood.srp.store.MemStore;

import javax.xml.bind.JAXBException;
import java.util.Calendar;
import static org.xmlunit.assertj3.XmlAssert.assertThat;

class ReportXMLTest {
    @Test
    public void xmlFormat() throws JAXBException {
        MemStore store = new MemStore();
        Calendar now = Calendar.getInstance();
        Employee worker = new Employee("Ivan", now, now, 100);
        Employee worker1 = new Employee("Ivan1", now, now, 90);
        Employee worker2 = new Employee("Ivan2", now, now, 110);
        store.add(worker);
        store.add(worker1);
        store.add(worker2);
        DateTimeParser<Calendar> parser = new ReportDateTimeParser();
        Report engine = new ReportXML(store, parser);
        StringBuilder expect = new StringBuilder()
                .append("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\n")
                .append("<employees>")
                .append("\n")
                .append("\t").append("<employee>").append("\n")
                .append("\t").append("\t").append("<name>").append(worker.getName()).append("</name>").append("\n")
                .append("\t").append("\t").append("<hired>").append(parser.parse(worker.getHired())).append("</hired>").append("\n")
                .append("\t").append("\t").append("<fired>").append(parser.parse(worker.getFired())).append("</fired>").append("\n")
                .append("\t").append("\t").append("<salary>").append(worker.getSalary()).append("</salary>").append("\n")
                .append("\t").append("</employee>").append("\n")
                .append("\t").append("<employee>").append("\n")
                .append("\t").append("\t").append("<name>").append(worker1.getName()).append("</name>").append("\n")
                .append("\t").append("\t").append("<hired>").append(parser.parse(worker1.getHired())).append("</hired>").append("\n")
                .append("\t").append("\t").append("<fired>").append(parser.parse(worker1.getFired())).append("</fired>").append("\n")
                .append("\t").append("\t").append("<salary>").append(worker1.getSalary()).append("</salary>").append("\n")
                .append("\t").append("</employee>").append("\n")
                .append("\t").append("<employee>").append("\n")
                .append("\t").append("\t").append("<name>").append(worker2.getName()).append("</name>").append("\n")
                .append("\t").append("\t").append("<hired>").append(parser.parse(worker2.getHired())).append("</hired>").append("\n")
                .append("\t").append("\t").append("<fired>").append(parser.parse(worker2.getFired())).append("</fired>").append("\n")
                .append("\t").append("\t").append("<salary>").append(worker2.getSalary()).append("</salary>").append("\n")
                .append("\t").append("</employee>").append("\n")
                .append("</employees>")
                .append("\n");
        assertThat(engine.generate(em -> true)).and(expect.toString()).ignoreWhitespace().areIdentical();
    }
}
