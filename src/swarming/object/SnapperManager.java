package swarming.object;


import java.util.HashMap;

public class SnapperManager {

    private HashMap<Integer, Snapper> snappers;

    private static SnapperManager ourInstance = new SnapperManager();

    public static SnapperManager getInstance() {
        return ourInstance;
    }

    private SnapperManager() {
        snappers = new HashMap<Integer, Snapper>();
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        throw new CloneNotSupportedException("Cloning is not allowed");
    }

    public void addSnapper(Snapper snapper) {
        snappers.put(snapper.id, snapper);
    }

    public Snapper getSnapper(int id) {
        return snappers.get(id);
    }

    public void removeSnapper(int id) {
        snappers.remove(id);
    }

    public HashMap<Integer, Snapper> getSnapperMap() {
        return snappers;
    }

    public int getSnapperCount() {
        return snappers.size();
    }

}
