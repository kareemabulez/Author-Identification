import java.util.*;

public class MapEntryList {
    private ArrayList<MapEntry<String, Integer> > list;
    public MapEntryList( ) {
        list = new ArrayList<MapEntry<String, Integer>>();
    }
    public List<MapEntry<String, Integer> > getList() {
        return list;
    }
}
