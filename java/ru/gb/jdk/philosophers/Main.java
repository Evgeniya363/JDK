package ru.gb.jdk.philosophers;
/*
Пять безмолвных философов сидят вокруг круглого стола, перед каждым философом стоит тарелка спагетти.
Вилки лежат на столе между каждой парой ближайших философов.
Каждый философ может либо есть, либо размышлять.
Философ может есть только тогда, когда держит две вилки — взятую справа и слева.
Философ не может есть два раза подряд, не прервавшись на размышления (можно не учитывать)
Описать в виде кода такую ситуацию. Каждый философ должен поесть три раза

 */

import java.util.concurrent.CountDownLatch;

public class Main{
    public static final int COUNT = 5;
    public static final int MEALS = 3;
    private static Philosopher[] philosophers = new Philosopher[COUNT];
    static CountDownLatch cdl = new CountDownLatch(COUNT);

    private static void init() {
        for (int i = 0; i < COUNT; i++) {
            philosophers[i] = new Philosopher(MEALS, cdl);
        }
        for (int i = 0; i < COUNT; i++) {
            philosophers[i].setPrev(philosophers[((i - 1) < 0 ? COUNT - 1 : (i - 1))]);
            philosophers[i].setNext(philosophers[(i + 1) % COUNT]);
        }
    }

    private static void start() {
        for (int i = 0; i < philosophers.length; i++) {
            philosophers[i].start();
        }
    }

    public static void main(String[] args) throws InterruptedException {

        init();
        start();
//        finish();
       cdl.await();

    }

    private static void finish()  {
        for (int i = 0; i < COUNT; i++) {
            try {
                philosophers[i].getCdl().await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
