package invaders.observer;

import java.util.ArrayList;
import java.util.List;

public class ConcreteTime extends ConcreteSubject {
    private List<observer> observerList = new ArrayList<>();

    public void addObserver(observer observer) {
        this.observerList.add(observer);
    }

    public void removeObserver(observer observer) {
        this.observerList.remove(observer);
    }
    public void update(int num) {
        for (observer observer: observerList){
            observer.update(num);
        }
    }

    public List<observer> getObserverList(){
        return this.observerList;
    }
}
