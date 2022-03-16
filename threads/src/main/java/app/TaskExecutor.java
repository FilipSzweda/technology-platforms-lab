package app;

public class TaskExecutor implements Runnable {
    private TaskForwarder taskForwarder;
    private ResultsCollector resultsCollector;

    public TaskExecutor(TaskForwarder taskForwarder, ResultsCollector resultsCollector) {
        this.taskForwarder = taskForwarder;
        this.resultsCollector = resultsCollector;
    }

    @Override
    public void run() {
        while(!Thread.interrupted()){
            try {
                Task task = this.taskForwarder.forward();
                Thread.sleep(1000);
                int number = task.getNumber();
                boolean result = task.isPrime();
                this.resultsCollector.saveData(number, result);
            } catch(InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }
}