package ru.job4j.ood.lsp;

public class ExampleLSP {

    /**
     * Первый пример: нарушения принципа Лискова
     */
    static class Bird {
        void fly() {
            // Реализация полета птицы
        }
    }

    static class Ostrich extends Bird {
        void fly() {
            // Страус не умеет летать, но переопределяет метод fly
        }
    }

    /**
     * Второй пример: Нарушение контракта
     */

    static class Engine {
        void start() {
            // Реализация запуска двигателя
        }
    }

    static class ElectricEngine extends Engine {
        @Override
        void start() {
            // Реализация запуска электрического двигателя
            super.start(); // Нарушение контракта, так как нет необходимости в явном "запуске" как у обычного двигателя.
        }
    }

    /**
     * Третий пример: Нарушение инварианта.
     * Тут вообще могу случить ся приключения, т.к. у квадрата обе стороны равны,
     * а присвоение может быть разных значений.
     */

    class Rectangle {
        int width;
        int height;

        void setWidth(int width) {
            this.width = width;
        }

        void setHeight(int height) {
            this.height = height;
        }
    }

    class Square extends Rectangle {
        @Override
        void setWidth(int width) {
            super.setWidth(width);
        }

        @Override
        void setHeight(int height) {
            super.setHeight(height);
        }
    }
}
