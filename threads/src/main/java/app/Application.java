package app;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Application {
    private ResultsCollector resultsCollector = new ResultsCollector();
    private TaskForwarder taskForwarder = new TaskForwarder();
    private List<Thread> threads = new ArrayList<>();

    public void createThreads(int threadsNumber) {
        for(int i = 0; i < threadsNumber; i++){
            Thread thread = new Thread(new TaskExecutor(this.taskForwarder, this.resultsCollector));
            this.threads.add(thread);
            thread.start();
        }
    }

    public void addTask(int number){
        this.taskForwarder.addAndNotify(new Task(number));
    }

    public void createTasks() {
        addTask(1);
        addTask(3);
        addTask(7);
        addTask(111);
        addTask(213);
        addTask(1000);
        addTask(9372);
    }

    public void stopThreads() {
        for (Thread thread : this.threads){
            thread.interrupt();
        }
    }

    public void writeResults() throws IOException {
        File file = new File("out.txt");
        FileOutputStream fileOutputStream = new FileOutputStream(file);
        BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(fileOutputStream));
        List <Integer> numbers = this.resultsCollector.getNumbers();
        List <Boolean> results = this.resultsCollector.getResults();
        if(numbers.size() == results.size()) {
            for (int i = 0; i < numbers.size(); i++) {
                bufferedWriter.write("Is ");
                bufferedWriter.write(String.valueOf(numbers.get(i)));
                bufferedWriter.write(" a prime number? Result: ");
                bufferedWriter.write(Boolean.toString(results.get(i)));
                bufferedWriter.newLine();
            }
        } else {
            bufferedWriter.write("Error: the data is inconsistent");
            bufferedWriter.newLine();
        }
        bufferedWriter.close();
    }
}