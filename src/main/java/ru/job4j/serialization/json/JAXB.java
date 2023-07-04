package ru.job4j.serialization.json;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Arrays;

public class JAXB {
    public static void main(String[] args) throws Exception {
        Window window = new Window(
                2.0f,
                2.5f,
                "REHAU",
                true,
                new ArrayList<>(Arrays.asList("handle", "hinge", "fasteners")),
                new Glass(1.4f)
        );
        /* Получаем контекст для доступа к АПИ */
        JAXBContext context = JAXBContext.newInstance(Window.class);
        /* Создаем сериализатор */
        Marshaller marshaller = context.createMarshaller();
        /* Указываем, что нам нужно форматирование */
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
        String xml = "";
        try (StringWriter writer = new StringWriter()) {
            /* Сериализуем */
            marshaller.marshal(window, writer);
            xml = writer.getBuffer().toString();
            System.out.println(xml);
        }
        /* Для десериализации нам нужно создать десериализатор */
        Unmarshaller unmarshaller = context.createUnmarshaller();
        try (StringReader reader = new StringReader(xml)) {
            /* десериализуем */
            Window result = (Window) unmarshaller.unmarshal(reader);
            System.out.println(result);
        }
    }
}
