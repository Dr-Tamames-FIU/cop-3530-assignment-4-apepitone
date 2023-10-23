package Dictionary;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.LinkedList;

import List.ListInterface;
import List.MyLinkedList;

public class MyHashtable implements DictionaryInterface {

    protected int tableSize;
    protected int size;
    // The LinkedList is of type Entry
    protected MyLinkedList[] table;

    public MyHashtable(int tableSize) {
        table = new MyLinkedList[tableSize];
        this.tableSize = tableSize;
    }

    public int biggestBucket()
    {
        int biggestBucket = 0;
        for(int i = 0; i < table.length; i++) {
            // Loop through the table looking for non-null locations.
            if (table[i] != null) {
                // If you find a non-null location, compare the bucket size against the largest
                // bucket size found so far. If the current bucket size is bigger, set biggestBucket
                // to this new size.
                MyLinkedList bucket = table[i];
                if (biggestBucket < bucket.size())
                    biggestBucket = bucket.size();
            }
        }
        return biggestBucket; // Return the size of the biggest bucket found.
    }

    // Returns the average bucket length. Gives a sense of how many collisions are happening overall.
    public float averageBucket() {
        float bucketCount = 0; // Number of buckets (non-null table locations)
        float bucketSizeSum = 0; // Sum of the size of all buckets
        for(int i = 0; i < table.length; i++) {
            // Loop through the table
            if (table[i] != null) {
                // For a non-null location, increment the bucketCount and add to the bucketSizeSum
                MyLinkedList bucket = table[i];
                bucketSizeSum += bucket.size();
                bucketCount++;
            }
        }

        // Divide bucketSizeSum by the number of buckets to get an average bucket length.
        return bucketSizeSum/bucketCount;
    }

    public String toString()
    {
        String s = "";
        for(int tableIndex = 0; tableIndex < tableSize; tableIndex++) {
            if (table[tableIndex] != null) {
                MyLinkedList bucket = table[tableIndex];
                for(int listIndex = 0; listIndex < bucket.size(); listIndex++) {
                    Entry e = (Entry)bucket.get(listIndex);
                    s = s + "key: " + e.key + ", value: " + e.value + "\n";
                }
            }
        }
        return s;
    }

    protected class Entry
    {
        String key;
        Object value;

        Entry(String key, Object value) {
            this.key = key;
            this.value = value;
        }
    }

    // Implement these methods
    
    public boolean isEmpty() {

        return size == 0;

    }

    public int size() {

        return size;

    }
    
    public Object put(String key, Object value){

        int index = Math.abs(key.hashCode()) % tableSize;
        
        MyLinkedList bucket = table[index];
    
        if (bucket == null) {

            bucket = new MyLinkedList();

            table[index] = bucket;

        }
    
    
        for (int i = 0; i < bucket.size(); i++) {

            Entry e = (Entry) bucket.get(i);

            if (e.key.equals(key)) {

                Object oldValue = e.value;

                e.value = value;

                return oldValue;

            }
        }
    
        bucket.add(0, new Entry(key, value));

        size++;

        return null;

    }

    public Object get(String key){

        int index = Math.abs(key.hashCode()) % tableSize;

        MyLinkedList bucket = table[index];

    
        if (bucket == null) {

            return null;

        }
    
        for (int i = 0; i < bucket.size(); i++) {

            Entry entry = (Entry) bucket.get(i);

            if (entry.key.equals(key)) {

                return entry.value;

            }

        }
    
        return null;
    }

    public void remove(String key){

        int index = Math.abs(key.hashCode()) % tableSize;

        MyLinkedList bucket = table[index];
    
        if (bucket == null) {

            return;

        }
    
        for (int i = 0; i < bucket.size(); i++) {

            Entry entry = (Entry) bucket.get(i);

            if (entry.key.equals(key)) {

                bucket.remove(i);

                size--;

                return;
            }
        }
    }

    public void clear() {

        table = new MyLinkedList[tableSize];

        size = 0;

    }

    public String[] getKeys() {

        ArrayList<String> keysList = new ArrayList<>();

    
        for (MyLinkedList bucket : table) {

            if (bucket != null) {

                for (int i = 0; i < bucket.size(); i++) {

                    Entry entry = (Entry) bucket.get(i);

                    keysList.add(entry.key);
                }
            }
        }
    
        return keysList.toArray(new String[0]);
    }
}

   
    

  

