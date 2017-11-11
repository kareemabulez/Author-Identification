## Spooky Author Identification using Word Counting (... and also hash maps)

## Due: Nov 21 23:59h

Inspired by Kaggle's Spooky Author Identification challenge and the associated tutorial (https://www.kaggle.com/c/spooky-author-identification) you will implement a simple author 'guesser', I use guesser here because classifier is a bit strong. This guesser will use your own implementation of a hash table for counting word frequencies.

General overview of how this will work:

- Read in a bunch of sentences written by famous authors.
- Find out how often each author uses each word
- Now given a new sentence see how frequently the words in that sentence are used by those authors
- Use the product of those frequencies to guess which author wrote that sentence (largest product wins)

You will implement a version of a hash table in a class called `FrequencyHashTable` it will have room for a fixed `9973` buckets - your hash table will use separate chaining as a collision resolution scheme. Your hash table will store the word as a key and the count of how many times that word has been seen as the value. You can use Java's built in `hashCode()` method for Strings (adjusted for your HashTable size - noting that the hashCode might return a negative so you should convert it to positive values), and it will store a count of how many times it has seen that String. However, when you go to retrieve a value for a certain key you should return that key's frequency: (its count / sum of all counts). i.e., the frequency that word occurs in the text as a whole (for that author). Note you should store the total count somewhere rather than computing it constantly.

Your hash table should have the following methods:

```java
FrequencyHashTable() //create a frequency hash table with fixed size 9973
void increment (K, V) //increment the current count for this key or insert key with count 1 if it is a new key
void remove (K) //remove a given word from the table
double get(K)//return the frequency (count / (double) totalCount()) for a given Key
boolean isEmpty()//are there any keys in the table?
int size()//how many keys are in the table
int totalCount()//total sum of all the counts
```

You'll start by looking at a CSV (comma separated values) file.
`train.csv` Create a `FrequencyHashTable` for each of the 3 authors: Edgar Allen Poe, Mary Shelley, and HP Lovecraft, denoted as EAP, MWS and HPL in the data table.

There is a helper file provided called `Record.java` that processes the text into a friendly format such that you can get the formatted text using `.getAuthor` `.getText` and (not used by us) `.getId`. 

An example from my solution for reading files into records and then storing into a frequency hashtable looks like this:

```java

//hashtable for author EAP
FrequencyHashTable eapHashTable = new FrequencyHashTable(); 

//setup scanner
File f = new File("train.csv"); 
Scanner scan = new Scanner (f); 

//iterate through the file
while(scan.hasNext() ) { 
    String rawLine = scan.nextLine(); 
    Record r = new Record(rawLine); 
    if (r.getAuthor().equals("EAP")) {    
    	//it's written by EAP, increment each word              
    	for (String word : r.getText().split(" ") ) { 
            eapHashTable.increment(word); 
        }                 
    } 
    /* ... */
 ```

A note on separate chaining. In java you cannot create an array of a generic class. i.e., you cannot do this: 

```java
ArrayList<MapEntry<K, V> > [] hashTable = new ArrayList<MapEntry<K, V> > [9973]; //
```

You can however create an ArrayList of ArrayLists, or you can wrap an ArrayList in your own class.

```java 
//code snippet
public class MapEntryList {
	private ArrayList<MapEntry<String, Integer> > list;
	public MapEntryList( ) {   
		list = new ArrayList<MapEntry<String, Integer> ();
	}
	public List<MapEntry<String, Integer> > getList() {
		return list;
	}
	/* ... */
}
```

In the above since MapEntryList is not a generic class it is valid to create an array of `MapEntryList` :

```java
MapEntryList [] hashTable = new MapEntryList[9973];

/* ... *
hashTable[0].getList(); //gives access to the underlying arraylist in hash table buckey 0
```

This a valid solution that allows you to take advantage of java's arraylist to help with your separate chaining. You may also create an array of Node's (where Node implements your own linked list). Which ever solution you like for separate chaining is accepatable.

## Process
- Create 3 FrequencyHashTables, one for each author
- Scan all the lines of `train.csv` and create a Record for each line.
- For each word in the Record's getText() string increment the counter in that author's hashtable - using the word as a key
- Now scan the lines of `test.csv` and create a Record for each line
- For each word in the Record's getText() string retrieve the frequecy of that word for each author. Multiply the frequencies together for each author for each sentence. 
- Among the author's whichever product is largest is deemed to be the author of that sentence.
- Compare against the actual author (Record:getAuthor) and count your correct guesses.

There are 100 tests in `test.csv`, my test solution appears to be pretty good at guessing authors with around 87 / 100 correct guesses.

## Tips
- Copy a few lines from `train.csv` into a new file (or just write a couple lines from scratch) and do your testing on that. Make sure your frequencies are correct and your hashtable is working. 
- If you use the ArrayList solution mentioned about - feel free to use any methods in that class that might help you out (perhaps `size()`). 
- while you might not use the remove method from your hashTable the grader may do that when grading your assignment. You may like to remove a few common words from your hash tables and see if it improves (or decreases) performance. (eg. remove `the`, `and` and `a`) and see if that helps or hurts your guesses.


