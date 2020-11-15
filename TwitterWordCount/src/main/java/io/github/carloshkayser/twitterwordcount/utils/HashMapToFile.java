package io.github.carloshkayser.twitterwordcount.utils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
 
public class HashMapToFile {

    private File file = null;

    public HashMapToFile() {
        this.file = new File("results.txt");

        try {
            this.file.createNewFile();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
 
    public void writeResults(HashMap<String, Long> data) throws IOException {

        BufferedWriter bf = null;;

        try{
            //create new BufferedWriter for the output file
            bf = new BufferedWriter( new FileWriter(this.file) );
            //iterate map entries
            for(Map.Entry<String, Long> entry : data.entrySet()){
                //put key and value separated by a colon
                bf.write( entry.getKey() + ":" + entry.getValue() );
                //new line
                bf.newLine();
            }
            bf.flush();
 
        } catch(IOException e) {
            e.printStackTrace();

        } finally {
            try{
                bf.close();
            } catch(Exception e) {
                e.printStackTrace();
            }
        }
    }
}