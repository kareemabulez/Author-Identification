public class MapEntry<K, V>{

    private K k; //The Key
    private V v; //The Value

    public MapEntry(K key, V value){
        k = key;
        v = value;
    }

    public K getK(){ return k; }
    public V getV(){ return v; }
    public void setKey(K key){ k = key;}
    public V setValue(V value){
        V old = v;
        v = value;
        return old;
    }
}
