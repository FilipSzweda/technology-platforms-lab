package app;

import java.io.Serial;
import java.io.Serializable;
import java.util.concurrent.ThreadLocalRandom;

public class Message implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    private int [] array = new int[10];

    public Message(int number) {
        for(int i = 0; i < 10; i++) {
            this.array[i] = ThreadLocalRandom.current().nextInt(0, number + 1);
        }
    }

    @Override
    public String toString() {
        String message = "Message: ";
        for(int i = 0; i < 10; i++) {
            message += this.array[i] + " ";
        }
        return message;
    }
}