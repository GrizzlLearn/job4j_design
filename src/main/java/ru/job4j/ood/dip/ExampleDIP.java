package ru.job4j.ood.dip;

public class ExampleDIP {
    /**
     * 1. Нарушение принципа инверсии зависимостей через наследование
     */

    class ReportGenerator {
        String generateReport() {
            return ""; //какая-то реализация
        }
    }

    class SalesReportGenerator extends ReportGenerator {
        // Код для создания отчета о продажах
    }

    //Как исправить

    interface ReportGenerator1 {
        void generateReport();
    }

    class SalesReportGenerator1 {
        private final ReportGenerator1 reportGenerator;

        public SalesReportGenerator(ReportGenerator1 reportGenerator) {
            this.reportGenerator = reportGenerator;
        }

        public void generateSalesReport() {
            // Какая-то логика для создания отчета о продажах
            reportGenerator.generateReport(); // Вызов метода через абстракцию
        }
    }

    class HTMLReportGenerator implements ReportGenerator1 {
        @Override
        public void generateReport() {
            // Логика для создания HTML отчета
        }
    }

    class PDFReportGenerator implements ReportGenerator1 {
        @Override
        public void generateReport() {
            // Логика для создания PDF отчета
        }
    }

    /**
     * 2. Использование new внутри класса
     */

    class Car {
        int driveForward() {
            return 0;
        }
    }

    class TaxiService {
        Car car = new Car(); // Инициализация конкретного объекта внутри класса.
    }

    //Как исправить

    class TaxiService1 {
        private Car car; // Поле для хранения объекта типа Car

        public TaxiService1(Car car) {
            this.car = car; // Внедрение зависимости через конструктор
        }
    }

    /**
     * 3. Прямая зависимость от конкретной реализации
     */

    class GasolineEngine1 {
        public void start() {
        }

        public void stop() {
        }
    }

    class Car1 {
        GasolineEngine1 engine = new GasolineEngine1(); // Прямая зависимость от конкретной реализации
    }

    //Как исправить

    interface Engine {
        void start();
        void stop();
    }

    class GasolineEngine implements Engine {
        @Override
        public void start() {
            // Логика запуска бензинового двигателя
        }

        @Override
        public void stop() {
            // Логика остановки бензинового двигателя
        }
    }

    class Car2 {
        private final Engine engine; // Интерфейс Engine вместо конкретной реализации

        public Car2(Engine engine) {
            this.engine = engine; // Внедрение зависимости через конструктор
        }

        public void start() {
            engine.start(); // Вызов метода через абстракцию
        }

        public void stop() {
            engine.stop(); // Вызов метода через абстракцию
        }
    }
}


