package swarming.object;


import java.util.HashMap;
import java.util.Set;

public class FishManager {

    private HashMap<Integer, Fish> fishMap;

    private static FishManager ourInstance = new FishManager();

    public static FishManager getInstance() {
        return ourInstance;
    }

    private FishManager() {
        fishMap = new HashMap<Integer, Fish>();
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        throw new CloneNotSupportedException("Cloning is not allowed");
    }

    public void addFish(Fish fish) {
        fishMap.put(fish.id, fish);
    }

    public Fish getFish(int id) {
        return fishMap.get(id);
    }

    public void removeFish(int id) {
        fishMap.remove(id);
    }

    public void removeAll() {
        int count = getFishCount();
        for (int i = 1; i <= count; i++) {
            removeFish(i);
        }
        Snapper.snapperCount = 0;
        Barracuda.barracudaCount = 0;
        Shark.sharkCount = 0;
        BaseObject.resetCounter();
    }

    public HashMap<Integer, Fish> getFishMap() {
        return fishMap;
    }

    public int getFishCount() {
        return fishMap.size();
    }

    public Set<Integer> getKeySet() {
        return fishMap.keySet();
    }

}
