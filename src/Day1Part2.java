import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class Day1Part2 {

    public static void main(String[] args) throws IOException {
        Path path = Paths.get("day1-input.txt");
        List<String> input = Files.readAllLines(path);
        List<Integer> previousFrequencies = new ArrayList<>();

        int frequency = 0;
        int count = -1;
        while (true) {
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
            if (previousFrequencies.contains(frequency)) {
                System.out.println("RESULT = " + frequency);
                return;
            } else {
                previousFrequencies.add(frequency);
            }
        }
    }
}