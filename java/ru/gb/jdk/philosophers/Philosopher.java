package ru.gb.jdk.philosophers;

import java.util.Random;
import java.util.concurrent.CountDownLatch;


/**
 * Класс Philosopher наследует класс Thread. Имитирует поведение философа,
 * который сменяет фазы размышлений с фазами приема пищи одновременно 2-мя
 * столовыми приборами, лежащими слева и справа от него.
 * Условием, при котором философ приступает к еде является то, что его соседи
 * в данный момент не едят и, соответственно, не занимают столовые приборы.
 * Блокировка ресурсов осуществляется оператором synchronized, а подсчет
 * количества приемов пищи - с помощью счетчика CountDownLatch, который
 * инициализируется в конструкторе Philosopher(int, CountDownLatch).
 * Также в конструктор передается глобальный счетчик типа CountDownLatch,
 * который позволяет синхронизировать окончание потока Philosopher.
 * Методы: startEating(), finishEating(), reflectionLunch()
 */
public class Philosopher extends Thread {
    private final Random random = new Random();
    private Philosopher prev;
    private Philosopher next;
    private boolean isEating;

    private static int count = 0;
    private int number;
    private CountDownLatch cdl;
    private CountDownLatch mainCdl;
    private int mealsCount;

    /**
     * @param meals количество приемов пищи
     * @param mainCdl общий счетчик философов
     */
    public Philosopher(int meals, CountDownLatch mainCdl) {
        number = count++;
        isEating = false;
        mealsCount = meals;
        this.mainCdl = mainCdl;
        cdl = new CountDownLatch(meals);
    }

    @Override
    public void run() {
        try {
            startEating();
            while (!isInterrupted()) {
                reflectionLunch();
            }
            finishEating();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

    }

    private void reflectionLunch() throws InterruptedException {
        if (prev.isThink() && next.isThink()) {
            doEat();
            doThink();
            cdl.countDown();
            if (cdl.getCount() == 0) {
                mainCdl.countDown();
                mainCdl.await();  // Ждем, когда отподчуют все
                this.interrupt();
            }
        } else {
            sleep(100);
        }
    }


    public void doEat() throws InterruptedException {
        synchronized (this) {
            isEating = true;
            printState(true);
            sleep(random.nextLong(1000, 3000));
        }
    }

    public void doThink() throws InterruptedException {
        isEating = false;
        printState(false);
        sleep(random.nextLong(2000, 4000));
    }


    public void setPrev(Philosopher prev) {
        this.prev = prev;
    }

    public void setNext(Philosopher next) {
        this.next = next;
    }

    public boolean isThink() {
        return !isEating;
    }

    private void printState(boolean state) {
        if (state) {
            StringBuilder sb = new StringBuilder();
            System.out.println(sb.append(getPhilosopherName()).append(" приступил к еде ").append(mealsCount + 1 - cdl.getCount())
                    .append("-й раз. Приборы: ").append(getForkNumber()).append(" и ").append(next.getForkNumber()));
        } else System.out.println(getPhilosopherName() + " погрузился в размышления");
    }

    private int getForkNumber() {
        return number + 1;
    }

    public String getPhilosopherName() {
        return "Философ " + (number + 1);
    }

    private void startEating() throws InterruptedException {
        System.out.println(getPhilosopherName() + " сел за стол");
        doThink();
    }

    private void finishEating() {
        System.out.println(getPhilosopherName() + " вышел из-за стола");
    }
}
