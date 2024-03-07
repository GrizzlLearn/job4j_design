package ru.job4j.ood.isp;

public class ExampleISP {
    /**
     * Много методов, возможно не все необходимы.
     */
    interface Worker {
        void doWork();

        void eat();

        void sleep();
    }

    /**
     * Если класс реализует больше одного интерфейса с одинаковыми методами,
     * то не обходимо реализовывать оба или делать заглушки
     */

    interface Engine {
        void start();

        void stop();
    }

    interface Radio {
        void play();

        void stop();
    }

    /**
     * Класс, реализующий Manager, вынужден предоставить реализацию метода work,
     * даже если он для него не имеет смысла.
     */

    interface Worker1 {
        void work();
    }

    interface Manager extends Worker1 {
        void manageTeam();
    }
}
