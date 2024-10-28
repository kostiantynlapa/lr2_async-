import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.*;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) {
        int arraySize = getRandomArraySize(40, 60);
        double min = 0.5;
        double max = 99.5;
        double[] array = generateRandomArray(arraySize, min, max);

        int chunkSize = 10;
        ExecutorService executorService = Executors.newFixedThreadPool(5);

        List<Future<Set<Double>>> futures = new ArrayList<>();

        long startTime = System.nanoTime();

        for (int i = 0; i < array.length; i += chunkSize) {
            int end = Math.min(i + chunkSize, array.length);
            double[] chunk = new double[end - i];
            System.arraycopy(array, i, chunk, 0, chunk.length);

            Callable<Set<Double>> task = new SquareCalculator(chunk);
            Future<Set<Double>> future = executorService.submit(task);
            futures.add(future);
        }

        Set<Double> squaredResults = new CopyOnWriteArraySet<>();
        for (Future<Set<Double>> future : futures) {
            try {
                squaredResults.addAll(future.get());
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        }

        List<String> results = squaredResults.stream()
                .limit(10)
                .map(num -> String.format("%.2f", num))
                .collect(Collectors.toList());

        System.out.println("Squared results (first 10): " + results);

        long endTime = System.nanoTime();
        System.out.println("Execution time (ms): " + (endTime - startTime) / 1_000_000);

        executorService.shutdown();
    }

    private static int getRandomArraySize(int min, int max) {
        return new Random().nextInt(max - min + 1) + min;
    }

    private static double[] generateRandomArray(int size, double min, double max) {
        Random random = new Random();
        double[] array = new double[size];
        for (int i = 0; i < size; i++) {
            array[i] = min + (max - min) * random.nextDouble();
        }
        return array;
    }
}

class SquareCalculator implements Callable<Set<Double>> {
    private final double[] numbers;

    public SquareCalculator(double[] numbers) {
        this.numbers = numbers;
    }

    @Override
    public Set<Double> call() {
        Set<Double> result = new CopyOnWriteArraySet<>();
        for (double number : numbers) {
            result.add(number * number);
        }
        return result;
    }
}
