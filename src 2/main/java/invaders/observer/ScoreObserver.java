package invaders.observer;

public class ScoreObserver implements observer{
    int score = 0;
    public ScoreObserver() {
    }

    @Override
    public void update(int num) {
        this.score +=num;
    }

    public String toString(){

        String score = String.format("Score: %d", this.score);
        return score;
    }
}
