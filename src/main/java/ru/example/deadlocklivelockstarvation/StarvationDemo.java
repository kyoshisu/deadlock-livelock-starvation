package ru.example.deadlocklivelockstarvation;

public class StarvationDemo {
    public static void main(String[] args) {
        Thread highPriority = new Thread(() -> {
            while (true) {
                System.out.println("Высокоприоритетный поток работает...");
            }
        });

        Thread lowPriority = new Thread(() -> {
            while (true) {
                System.out.println(">>> Низкоприоритетный поток наконец-то получил процессор! <<<");
                try { Thread.sleep(1000); } catch (InterruptedException e) {}
            }
        });

        highPriority.setPriority(Thread.MAX_PRIORITY);
        lowPriority.setPriority(Thread.MIN_PRIORITY);

        highPriority.start();
        lowPriority.start();

        try { Thread.sleep(2000); } catch (InterruptedException e) {}
        highPriority.stop();
        lowPriority.stop();
        System.out.println("Программа завершена.");
    }
}