package app;

import java.util.ArrayList;
import java.util.List;

public class ResultsCollector {
    List<Integer> numbers = new ArrayList<>();
    List<Boolean> results = new ArrayList<>();

    public synchronized void saveData(int number, boolean result) {
        this.numbers.add(number);
        this.results.add(result);
    }

    public List<Integer> getNumbers() {
        return this.numbers;
    }

    public List<Boolean> getResults() {
        return this.results;
    }
}
