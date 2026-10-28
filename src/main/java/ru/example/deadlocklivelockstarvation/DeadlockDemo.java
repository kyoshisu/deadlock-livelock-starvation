package ru.example.deadlocklivelockstarvation;

public class DeadlockDemo {
    private static final Object resourceA = new Object();
    private static final Object resourceB = new Object();

    public static void main(String[] args) {
        Thread thread1 = new Thread(() -> {
            synchronized (resourceA) {
                System.out.println("Thread-1: Захватил resourceA");
                try { Thread.sleep(100); } catch (InterruptedException e) {}
                System.out.println("Thread-1: Ждет resourceB...");
                synchronized (resourceB) {
                    System.out.println("Thread-1: Захватил resourceB");
                }
            }
        });

        Thread thread2 = new Thread(() -> {
            synchronized (resourceB) {
                System.out.println("Thread-2: Захватил resourceB");
                try { Thread.sleep(100); } catch (InterruptedException e) {}
                System.out.println("Thread-2: Ждет resourceA...");
                synchronized (resourceA) {
                    System.out.println("Thread-2: Захватил resourceA");
                }
            }
        });

        thread1.start();
        thread2.start();
    }
}