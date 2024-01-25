package ru.job4j.ood.ocp;

public class ExampleOCP {

    /**
     * Если при добавлении нового функционала необходимо изменять существующий код
     */
    public static class Rectangle {
        public double width;
        public double height;

        public double calculateArea() {
            return width * height;
        }

        // Новый функционал
        public double calculateCircleArea(double radius) {
            return Math.PI * radius * radius;
        }
    }

    /**
     * Если при добавлении новых типов данных приходится изменять существующий код
     */
/*    public static class Shape {
        public enum Type { CIRCLE, RECTANGLE }

        public Type type;

        // Нарушение OCP
        @SuppressWarnings("checkstyle:EmptyBlock")
        public double calculateArea() {
            if (type == Type.CIRCLE) {
                // ... рассчет для круга
            } else if (type == Type.RECTANGLE) {
                // ... рассчет для прямоугольника
            }
            return 0;
        }
    }*/

    /**
     * Если добавление новых правил или условий приводит к изменению существующего кода
     */
    /*public static class DiscountCalculator {
        public double calculateDiscount(double amount, CustomerType customerType) {
            if (customerType == CustomerType.REGULAR) {
                return amount * 0.1;  // скидка 10% для обычных клиентов
            } else if (customerType == CustomerType.PREMIUM) {
                return amount * 0.2;  // скидка 20% для премиум клиентов
            }
            return 0;
        }
    }*/

}
