package swarming.object;


import java.util.HashMap;

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

    public HashMap<Integer, Fish> getFishMap() {
        return fishMap;
    }

    public int getFishCount() {
        return fishMap.size();
    }

}
