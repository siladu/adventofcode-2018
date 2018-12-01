import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.IntSupplier;
import java.util.stream.IntStream;

public class Day1Part2 {

    Map<Integer, Integer> previousFrequencies = new HashMap<>();
    int inputTotalLines;
    int currentLine = -1;
    boolean shouldFinish = false;
    int frequency = 0;

    public static void main(String[] args) throws IOException {
        Day1Part2 day = new Day1Part2();
        day.streams();

        Day1Part2 anotherDay = new Day1Part2();
        anotherDay.naive();
    }

    private void streams() throws IOException {
        long start = System.nanoTime();
        Path path = Paths.get("day1-input.txt");
        List<String> input = Files.readAllLines(path);

        inputTotalLines = input.size();
        IntStream.generate(next())
                .takeWhile(value -> !shouldFinish)
                .forEach(lineIndex -> processLine(input, lineIndex));

        System.out.println("RESULT = " + frequency);
        logTimeSince(start);
    }

    private IntSupplier next() {
        return () -> (currentLine++ + 1) % inputTotalLines;
    }

    private void naive() throws IOException {
        long start = System.nanoTime();
        Path path = Paths.get("day1-input.txt");
        List<String> input = Files.readAllLines(path);

        int lineIndex = -1;
        while (!shouldFinish) {
            if (lineIndex++ == input.size() - 1) {
                lineIndex = 0;
            }
            processLine(input, lineIndex);
        }
        System.out.println("RESULT = " + frequency);
        logTimeSince(start);
    }

    private void processLine(List<String> input, int lineIndex) {
        String line = input.get(lineIndex);
        char op = line.charAt(0);
        int value = Integer.parseInt(line.substring(1));
        if (op == '+') {
            frequency = frequency + value;
        } else {
            frequency = frequency - value;
        }
        if (previousFrequencies.containsKey(frequency)) {
            shouldFinish = true;
        } else {
            previousFrequencies.put(frequency, 0);
        }
    }

    private static void logTimeSince(long start) {
        long timeInNanos = System.nanoTime() - start;
        System.out.println("Time in nanos: " + timeInNanos);
        System.out.println("Time in millis: " + (timeInNanos / Math.pow(10, 6)));
        System.out.println("Time in seconds: " + (timeInNanos / Math.pow(10, 9)));
    }
}