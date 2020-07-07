package com.bridgelabz.censusanalyser.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;


import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.List;

public class CensusUtilities {

    /**
     * TASK: to write a list into a json file
     * @param fileName
     * @param listToWrite
     */
    public void writeIntoJson(String fileName, List listToWrite) {
        try (Writer writer = new FileWriter(fileName)) {
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            gson.toJson(listToWrite, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}