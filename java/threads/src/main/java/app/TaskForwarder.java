package app;

import java.util.ArrayList;
import java.util.List;

public class TaskForwarder {
    private List<Task> tasks = new ArrayList<>();

    public synchronized Task forward() throws InterruptedException {
        while (this.tasks.isEmpty()) {
            wait();
        }
        return this.tasks.remove(0);
    }

    public synchronized void addAndNotify(Task task) {
        this.tasks.add(task);
        notifyAll();
    }
}
