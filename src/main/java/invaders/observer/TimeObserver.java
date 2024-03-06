package invaders.observer;

public class TimeObserver implements observer {
    private int seconds;

    public TimeObserver( int seconds) {
        this.seconds = seconds;
    }

    @Override
    public void update(int num) {
        // Update the time and render it on the game screen
        this.seconds += num;
    }
    public String toString(){
        double elapsedSeconds = this.seconds / 1000;

        int elapsedMinutes = (int) elapsedSeconds / 60;
        int remainingSeconds = (int) elapsedSeconds % 60;
        String time = String.format("Time: %d:%02d", elapsedMinutes, remainingSeconds);
        return time;
    }
}
