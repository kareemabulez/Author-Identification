import java.util.List;

public class FrequencyHashTable {
    int counter = 0;
    MapEntryList [] hashTable;

    private int BUCKETS = 9973;

    public FrequencyHashTable() { hashTable = new MapEntryList[BUCKETS];

    for(int i = 0; i < BUCKETS; i++){
        hashTable[i] = new MapEntryList();
    } }
    
    public void increment(String key){
        counter++;

        //hash the key to find the bucket
        int hash = getHashCode(key);
        List<MapEntry<String, Integer>> mlist = hashTable[hash].getList();

        int position = -1;
        for(int i = 0; i < mlist.size(); i++) {
            if (mlist.get(i).getK().equals(key)) { position = i; break; }
        }
        if(position == -1){ hashTable[hash].getList().add(new MapEntry<>(key, 1));}
        else { MapEntry<String, Integer> me = hashTable[hash].getList().get(position);
            me.setValue(me.getV()+1);
        }
    }

    public void remove(String key){ }

    public double get(String key){
        int stringHash = getHashCode(key);
        List<MapEntry<String, Integer>> me = hashTable[stringHash].getList();
        int position = -1;
        for(int i = 0; i < me.size(); i++) {
            if (me.get(i).getK().equals(key)) { position = i; break; }
        }
        if(position != -1){ return me.get(position).getV() / (double) totalCount(); }
        else{ return 0.0000001; }
    }

    public boolean isEmpty(){
        if(counter == 0) {return true;}
        else return false; }

    public int size(){
        int sum = 0;
        for(int x = 0; x < BUCKETS; x++) {
            sum += hashTable[x].getList().size();
        }
        return sum;
    }

    public int totalCount(){return counter;}

    private int getHashCode(String key) {
        return Math.abs(key.hashCode() % BUCKETS);

    }
}




