import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Day1Part2 {

    public static void main(String[] args) throws IOException {
        long start = System.nanoTime();
        Path path = Paths.get("day1-input.txt");
        List<String> input = Files.readAllLines(path);
        Map<Integer, Integer> previousFrequencies = new HashMap<>();

        int frequency = 0;
        int count = -1;
        boolean shouldFinish = false;
        while (!shouldFinish) {
            if (count++ == input.size() - 1) {
                count = 0;
            }
            String line = input.get(count);
            char op = line.charAt(0);
            int value = Integer.parseInt(line.substring(1));
            if (op == '+') {
                frequency = frequency + value;
            } else if (op == '-') {
                frequency = frequency - value;
            } else {
                System.out.println("WTF op = " + op);
                return;
            }
            if (previousFrequencies.containsKey(frequency)) {
                shouldFinish = true;
            } else {
                previousFrequencies.put(frequency, 0);
            }
        }
        System.out.println("RESULT = " + frequency);
        long timeInNanos = System.nanoTime() - start;
        System.out.println("Time in nanos: " + timeInNanos);
        System.out.println("Time in millis: " + (timeInNanos / Math.pow(10, 6)));
        System.out.println("Time in seconds: " + (timeInNanos / Math.pow(10, 9)));
    }
}