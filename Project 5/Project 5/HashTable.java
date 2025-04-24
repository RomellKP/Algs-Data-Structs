

import java.util.Scanner;
import java.io.*;
//These two import statements were used in TextScan.java.
//They are able to be used because of this.

public class HashTable<T>{
    public String type = "general";
    private NGen<T>[] hTable;
    private int index;

    public HashTable(String t){
        //sets this.type to t, the type
        //sets the hash table to a length of 100
        this.type = t;
        this.hTable = new NGen[100];
    }

    public HashTable(String t, int num){
        //sets this.type to t, the type
        //if num is less than 2, it creates a hash table with a length of 97, a prime number that we chose
        //if num is 2 or greater, ite creates a hash table with a length of num
        this.type = t;
        if(num == 0 || num == 1){
            this.hTable = new NGen[97];
        }
        else{
            this.hTable = new NGen[num];
        }
    }

    private int hash1(T key){
        //hash1 first tests if the key has a length of 1 so that it does not attempt to use a second letter that does not exist.
        //hash1 then sorts keys based off of the position of the first letter in the alphabet and the position of the second letter
        //26, the number of letters in the alphabet, is multiplied by the position of the first letter which then adds the position of the second letter
        //and finally uses modulus by the length of the hash table to ensure the index value is never larger than the length of hash table.
        //hash1 is the worst hash function when it comes to collision and has an average collision length of 4 on gettysburg.txt and its longest chain
        //Is nearly double the size of the longest chain in hash2.
        //There is no notable difference in whether prime, odd, or even lengths are used for the length of hTable. The results
        //are similar for every hash function(hash1, hash2, and hash3). The only time there is a significant uptick in collisions
        //in every function is when the length of hTable is significantly shortened.
        if(key.toString().length() == 1){
            index = key.toString().charAt(0) % hTable.length;
        }

        else
            index = (26 * key.toString().charAt(0) + key.toString().charAt(1)) % hTable.length;

        return index;
    }

    private int hash2(T key){
        //hash2 is the best method for when the file is in the general case
        //hash2 first checks if the length of the converted token is 2 or less
        //if true, it takes the value of the first character and multiplies it by the number of letter in the alphabet, 26
        //then adds the value of the last character and modulus the sum by the length of the hash table making that the index
        //if false, multiplies the first character by 26, adds the value of the second character, and adds the value of the last character
        //hash2 then takes the sum and modulus by the length of the hash table making that the index
        //hash2 returns index
        if(key.toString().length() <= 2){
            index = (26 * key.toString().charAt(0) + key.toString().charAt(key.toString().length()-1)) % hTable.length;
        }

        else {
            index = (26 * key.toString().charAt(0) + key.toString().charAt(1) + key.toString().charAt(key.toString().length() - 1) + key.toString().length()) % hTable.length;

        }
        return index;
    }

    private int hash3(T key){
        //hash3 is the best method for when the file is in the specific case
        //hash3 works better than hash1 but worse than hash2 for the general case
        //hash3 takes the first letter of the string, gives it a numerical value based on the letter and multiplies it by 26, the number of letters in the alphabet
        //it then adds the length of the string to the value and then modulus by the length of the hash table
        //this value is the index in which the item should be inserted and hash3() returns the index
        index = (26 * key.toString().charAt(0) + key.toString().length()) % hTable.length;
        return index;
    }

    public void add(T item){
        //Adds item to hTable at a specific index based off two cases. If there is a collision and if there is not.
        //Also determines which hash function to call based off if it is a specific or general case.
        int index;
        if (type == "general") {
            index = hash2(item);
        } else {
            index = hash3(item);
        }
        NGen<T> lst;
        if (hTable[index] == null) {
            //Checks to see if the value at index is null, item is the first value being added at this index
            // and creates a new linked list.
            lst = new NGen(item, null);
            hTable[index] = lst;
        } else {
            //If item is not the first value being added at that index it adds it to the front of the linked list
            //at index in hTable.
            lst = new NGen(item, hTable[index]);
            hTable[index] = lst;
        }

    }

    public void display(){
        //creates array of integers to calculate with later
        //creates a pointer and counters for the nodes and rows
        int[] counts = new int[hTable.length];
        int rCounter = 0;
        int nCounter = 0;
        NGen<T> ptr;

        //for each location in hTable count the number of items hashed there and print it
        for(int i = 0; i < hTable.length; i++){
            ptr = hTable[i];
            rCounter++;
            //iterates through the list at hTable[i] and counts the bumber of items in the list as nCounter
            if(ptr != null) {
                nCounter++;
                while(ptr.getNext() != null) {
                    nCounter++;
                    ptr = ptr.getNext();
                }
            }
            System.out.println(rCounter + ": " + nCounter);
            counts[i] = nCounter;
            nCounter = 0;
        }

        //finds the number of empty and non-empty indices in hash table
        int numEmpty = 0;
        int nonEmpty = 0;
        for(int i = 0; i < counts.length; i++){
            if(counts[i] == 0){
                numEmpty++;
            }
        }
        nonEmpty = counts.length - numEmpty;

        //iterates through counts and creates total, then creates and prints the average
        int total = 0;
        int average;
        for(int i = 0; i < counts.length; i++){
            total += counts[i];
        }
        average = total / nonEmpty;
        System.out.println("average collision length: " + average);

        //iterates through counts and finds and prints longest chain
        int longest = 0;
        for(int i = 0; i < counts.length; i++) {
            if(counts[i] > longest){
                longest = counts[i];
            }
        }
        System.out.println("Longest chain: " + longest);

        //finds and prints the number of unique tokens in the hash table
        int uTokens = 0;
        for(int i = 0; i < counts.length; i++){
            if(counts[i] > 0){
                uTokens += counts[i];
            }
        }
        System.out.println("unique tokens: " + uTokens);

        //finds and prints the number of empty indices in hash table
        for(int i = 0; i < counts.length; i++){
            if(counts[i] == 0){
                numEmpty++;
            }
        }

        //prints out non-empty and empty indices
        System.out.println("empty indices: " + numEmpty);
        System.out.println("non-empty indices: " + nonEmpty);
    }

    public static void main(String args[]){
        //majority of main was taken from TextScan.java
        //we edited to have it create a HashTable(hasher) and display hasher
        Scanner s = new Scanner(System.in);
        System.out.println("What is the file?");
        String theFile = s.nextLine();
        String theType;
        HashTable hasher;
        if (theFile == "keywords.txt") {
            theType = "specific";
            hasher = new HashTable(theType, 50);
        }
        else{
            theType = "general";
            hasher = new HashTable(theType);
        }
        Scanner readFile = null;
        int count = 0;
        System.out.println();
        System.out.println("Attempting to read from file: " + theFile);
        try {
            readFile = new Scanner(new File(theFile));
        }
        catch (FileNotFoundException e) {
            System.out.println("File: " + theFile + " not found");
            System.exit(1);
        }
        String st;
        System.out.println("Connection to file: " + theFile + " successful");
        System.out.println();
        while (readFile.hasNext()) {
            st = readFile.next();
            hasher.add(st);
            count++;
        }

        System.out.println();
        System.out.println(count + " Tokens found");
        System.out.println();
        hasher.display();
    }
}