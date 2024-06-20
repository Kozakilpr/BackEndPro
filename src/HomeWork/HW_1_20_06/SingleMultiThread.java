package HomeWork.HW_1_20_06;

import java.util.concurrent.atomic.AtomicInteger;

public class SingleMultiThread {

    private static AtomicInteger count = new AtomicInteger(0);

    public static void main(String[] args) {

        int singleThreadCount = runSingleThreaded();
        System.out.println("В одном потоке: " + singleThreadCount);


        count.set(0);


        int multiThreadCount = runMultiThreaded();
        System.out.println("В многопоточном: " + multiThreadCount);
    }

    private static int runSingleThreaded() {
        int localCount = 0;
        for (int i = 1; i <= 2_000_000; i++) {
            if (isDivisibleBy21AndContains3(i)) {
                localCount++;
            }
        }
        return localCount;
    }

    private static int runMultiThreaded() {
        Thread thread1 = new Thread(() -> {
            for (int i = 1; i <= 1_000_000; i++) {
                if (isDivisibleBy21AndContains3(i)) {
                    count.incrementAndGet();
                }
            }
        });

        Thread thread2 = new Thread(() -> {
            for (int i = 1_000_001; i <= 2_000_000; i++) {
                if (isDivisibleBy21AndContains3(i)) {
                    count.incrementAndGet();
                }
            }
        });

        thread1.start();
        thread2.start();

        try {
            thread1.join();
            thread2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return count.get();
    }

    private static boolean isDivisibleBy21AndContains3(int number) {
        return number % 21 == 0 && String.valueOf(number).contains("3");

    }
}
