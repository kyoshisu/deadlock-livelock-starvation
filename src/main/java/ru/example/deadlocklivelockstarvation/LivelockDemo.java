package ru.example.deadlocklivelockstarvation;

public class LivelockDemo {
    static class Worker {
        private String name;
        private boolean active = true;

        public Worker(String name) { this.name = name; }

        public String getName() { return name; }
        public boolean isActive() { return active; }

        public void work(Worker other) {
            while (active) {
                if (other.isActive()) {
                    System.out.println(name + ": уступаю дорогу " + other.getName());
                    try { Thread.sleep(100); } catch (InterruptedException e) {}
                } else {
                    System.out.println(name + ": работаю!");
                    active = false;
                }
            }
        }

        public void stop() {
            active = false;
        }
    }

    public static void main(String[] args) {
        Worker w1 = new Worker("Работник-1");
        Worker w2 = new Worker("Работник-2");

        Thread t1 = new Thread(() -> w1.work(w2));
        Thread t2 = new Thread(() -> w2.work(w1));

        t1.start();
        t2.start();

        try { Thread.sleep(2000); } catch (InterruptedException e) {}
        w1.stop();
        w2.stop();
        System.out.println("Программа завершена принудительно.");
    }
}