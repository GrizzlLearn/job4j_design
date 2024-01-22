package ru.job4j.ood.srp.report;

import ru.job4j.ood.srp.formatter.DateTimeParser;
import ru.job4j.ood.srp.model.Employee;
import ru.job4j.ood.srp.store.Store;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.PropertyException;
import javax.xml.bind.annotation.*;
import java.io.IOException;
import java.io.StringWriter;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.function.Predicate;

public class ReportXML implements Report {
    private final Store store;
    private final DateTimeParser<Calendar> dateTimeParser;

    public ReportXML(Store store, DateTimeParser<Calendar> dateTimeParser) {
        this.store = store;
        this.dateTimeParser = dateTimeParser;
    }

    @Override
    public String generate(Predicate<Employee> filter) {
        String result = "";
        JAXBContext context = null;
        try {
            context = JAXBContext.newInstance(Workers.class);
        } catch (JAXBException e) {
            throw new RuntimeException(e);
        }
        Marshaller marshaller = null;
        try {
            marshaller = context.createMarshaller();
        } catch (JAXBException e) {
            throw new RuntimeException(e);
        }
        try {
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
        } catch (PropertyException e) {
            throw new RuntimeException(e);
        }
        try (StringWriter writer = new StringWriter()) {
            marshaller.marshal(new Workers(store.findBy(filter).stream().toList()), writer);
            result = writer.getBuffer().toString();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JAXBException e) {
            throw new RuntimeException(e);
        }
        return result;
    }

    @XmlRootElement(name = "employees")
    @XmlAccessorType(XmlAccessType.FIELD)
    private static class Workers {

        @XmlElement(name = "employee")
        private List<Worker> workerList;

        public Workers() { }

        public Workers(List<Employee> list) {
            this.workerList = list.stream().map(Worker::new).toList();
        }

        private static class Worker {
            @XmlElement(name = "name")
            private String name;
            @XmlElement(name = "hired")
            private String hired;
            @XmlElement(name = "fired")
            private String fired;
            @XmlElement(name = "salary")
            private double salary;

            public Worker(Employee em) {
                SimpleDateFormat dateFormat = new SimpleDateFormat("dd:MM:yyyy HH:mm");
                name = em.getName();
                hired = dateFormat.format(em.getHired().getTime());
                fired = dateFormat.format(em.getFired().getTime());
                salary = em.getSalary();
            }
        }
    }
}
