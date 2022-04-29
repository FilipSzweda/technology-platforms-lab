package app;

public class Task {
    private int number;

    public Task(int number) {
        this.number = number;
    }

    public int getNumber() {
        return this.number;
    }

    public boolean isPrime(){
        System.out.println("Thread id " + Thread.currentThread().getId() + " checking number: " + String.valueOf(this.number));
        if (this.number <= 1) {
            return false;
        }
        for(int i = 2; i*i <= this.number; i++){
            if(this.number%i == 0) {
                return false;
            }
        }
        return true;
    }
}
