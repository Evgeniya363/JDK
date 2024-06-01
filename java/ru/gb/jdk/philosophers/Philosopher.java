package ru.gb.jdk.philosophers;

import java.util.Random;
import java.util.concurrent.CountDownLatch;

public class Philosopher extends Thread {
    private final Random random = new Random();
    private Philosopher prev;
    private Philosopher next;
    private boolean isEating;


    private static int count = 0;
    private int number = 0;
    private int state;
    private CountDownLatch cdl;
    private CountDownLatch parentCdl;
    private int mealsCount;


    public Philosopher(int meals, CountDownLatch parentCdl) {
        number = count++;
        isEating = false;
        mealsCount = meals;
        this.parentCdl = parentCdl;
        cdl = new CountDownLatch(meals);
    }

    @Override
    public void run() {
        while (true) {
            try {
                if (prev.isThink() && next.isThink()) {
                    doEat();
                    doThink();
                    cdl.countDown();
                    if (cdl.getCount() == 0) {
                        parentCdl.countDown();  // Вот так не срабатывает
//                        break;  // Так срабатывает
                    }

                } else {
                    if (isEating) doThink();
                    else sleep(100);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
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

    public CountDownLatch getCdl() {
        return cdl;
    }

    public boolean isThink() {
        return !isEating;
    }

    private void printState(boolean state) {
        if (state) {
            StringBuilder sb = new StringBuilder();
            System.out.println(sb.append(getPhilosopherName()).append(" приступил к еде ").append(mealsCount + 1 - cdl.getCount())
                    .append("-й раз. Приборы: ").append(getForkNumber()).append(" и ").append(next.getForkNumber()));
        } else System.out.println(getPhilosopherName() + " приступил к размышлению");
    }

    private int getForkNumber() {
        return number + 1;
    }

    public String getPhilosopherName() {
        return "Философ " + (number + 1);
    }


}
