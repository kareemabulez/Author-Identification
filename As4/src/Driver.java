import java.util.*;
import java.io.*;

public class Driver {

    static FrequencyHashTable eapHashTable = new FrequencyHashTable(),
                              mwsHashTable = new FrequencyHashTable(),
                              hplHashTable = new FrequencyHashTable();
    static int correctCounter = 0;
    static String actualAuthor = "";


    public static void main(String [] args)  {
        trainScan();
        testScan();
        System.out.println("Number of correct guess: " + correctCounter + "/100");
    }

    public static void trainScan() {
        //setup scanners
        try {
            File trainFile = new File("train.csv");
            Scanner trainScan = new Scanner(trainFile);

            //iterate through the file
            while (trainScan.hasNext()) {
                String rawLine = trainScan.nextLine();
                Record r = new Record(rawLine);
                if (r.getAuthor().equals("EAP")) {
                    //it's written by EAP, increment each word
                    for (String word : r.getText().split(" ")) {
                        eapHashTable.increment(word);
                    }
                }
                if (r.getAuthor().equals("MWS")) {
                    //it's written by EAP, increment each word
                    for (String word : r.getText().split(" ")) {
                        mwsHashTable.increment(word);
                    }
                }
                if (r.getAuthor().equals("HPL")) {
                    //it's written by EAP, increment each word
                    for (String word : r.getText().split(" ")) {
                        hplHashTable.increment(word);
                    }
                }
            }
        } catch (IOException e) { System.out.println("Can't load file"); }
    }

    public static void testScan() {
        try {
            File testFile = new File("test.csv");
            Scanner testScan = new Scanner(testFile);

            while (testScan.hasNext()) {
                double eapFreq = 1.0, mwsFreq = 1.0, hplFreq = 1.0;
                String checkLine = testScan.nextLine();
                Record lines = new Record(checkLine);

                for(String word : lines.getText().split(" ")) {
                    eapFreq = eapFreq * eapHashTable.get(word);
                    mwsFreq = mwsFreq * mwsHashTable.get(word);
                    hplFreq = hplFreq * hplHashTable.get(word);
                }

                if(eapFreq > mwsFreq && eapFreq > hplFreq) { actualAuthor = "EAP"; }
                if(mwsFreq > eapFreq && mwsFreq > hplFreq) { actualAuthor = "MWS"; }
                if(hplFreq > eapFreq && hplFreq > mwsFreq) { actualAuthor = "HPL"; }

                if (actualAuthor.equals(lines.getAuthor())) { correctCounter++; }
            }
        } catch (IOException e) { System.out.println("Can't load file"); }
    }
}
