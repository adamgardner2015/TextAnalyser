package com.gardner.textanalyser;

import java.io.File;
import java.util.Map;

public class TextAnalyserDriver
{

    public static void main(String[] args)
    {
        File oFile = new File("C:/temp/test.txt");
        
        boolean bIgnoreCase = true;
        boolean bIgnorePunctuation = true;
       
        Map<String,Integer> oWordMap = TextAnalyser.analyse(oFile, bIgnoreCase, bIgnorePunctuation);
        //Map<String,Integer> oWordMap = TextAnalyser.analyseString("first second third first",bIgnoreCase);
        
        System.out.println("Total Words Analysed: " + oWordMap.size());
        
        TextAnalyser.writeOutput("C:/temp/output.csv", oWordMap);
    }
    
}
