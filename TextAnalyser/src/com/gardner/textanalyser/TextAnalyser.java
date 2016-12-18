package com.gardner.textanalyser;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;


public class TextAnalyser
{
    /*
     * Analyse a File which contains text.
     * Booleans tell system whether to ignore case and / or punctuation.
     */
    public static Map<String,Integer> analyse(File oFile, boolean bIgnoreCase, boolean bIgnorePunctuation)
    {
        String strContent = "";
        try
        {
            strContent = new String(Files.readAllBytes(Paths.get(oFile.toURI())));
            return analyseString(strContent, bIgnoreCase, bIgnorePunctuation);
        }
        catch (NoSuchFileException e)
        {
            System.out.println("Exception caught reading file in TextAnalyser.analyse() method. Does this file exist?");
        }
        catch (Exception e)
        {
            System.out.println("Exception caught reading file in TextAnalyser.analyse() method.");
        }
        
        return new HashMap<String, Integer>();
    }
    
    /*
     * The analysis work is done here.
     * Even the main analyse() method calls this method eventually.
     */
    public static Map<String,Integer> analyseString(String strInput, boolean bIgnoreCase, boolean bIgnorePunctuation)
    {
        // If ignoring case, transform to lowercase
        if (bIgnoreCase) strInput = strInput.toLowerCase();
        
        // Split the string into an array of words
        String[] astrWords = strInput.split(" ");
        
        Map<String,Integer> oWordCountMap = new HashMap<String,Integer>();
        
        /* For each word either place in the map with a count of 1
         * or increment the count if it's already in the map.
         */
        for (String strWord : astrWords)
        {
            if (bIgnorePunctuation) strWord = strWord.replaceAll("[\\W]", "");
            
            int iWordCount = 1;
           
            // If it's already in the map, increment the count.
            if (oWordCountMap.containsKey(strWord))
            {
                iWordCount += oWordCountMap.get(strWord);
            }
            /* Notice that if the word wasn't already in the map
             * This places it with an initial count of 1 (see line 63)
             */
            oWordCountMap.put(strWord, iWordCount);
        }
        return oWordCountMap;
    }
    
    /* Output analysis to a file
     * Notice that the way this is built, we're supposing a CSV file (see line 84)
    */
    public static void writeOutput(String strFilePath, Map<String,Integer> oInputMap)
    {
        List<String> oList = new ArrayList<String>();
        
        // Build a CSV list of lines eg. K,V
        for (String strKey : oInputMap.keySet()) oList.add(strKey + "," + oInputMap.get(strKey));
        
        try
        {
            Files.write(Paths.get(strFilePath), oList);
        }
        catch (IOException e)
        {
            System.out.println("IOException caught writing file output");
        }
        catch (Exception e)
        {
            System.out.println("General exception caught writing file output");
        }
    }
}
