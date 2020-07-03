package com.bridgelabz.censusanalyser.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.*;
import java.util.Comparator;
import java.util.List;

public class CensusUtilities<T> {
    /**
     * TASK: Generic Method to sort entries in ascending order
     * @param censusCSVComparator
     * @param listToSort
     * @param <T>
     */
    public List sortAscending(Comparator<T> censusCSVComparator, List listToSort) {
        for (int i = 0; i < listToSort.size() - 1; i++) {
            for (int j = 0; j < listToSort.size() - i - 1; j++) {
                T census1 = (T) listToSort.get(j);
                T census2 = (T) listToSort.get(j + 1);
                if (censusCSVComparator.compare(census1, census2) > 0) {
                    listToSort.set(j, census2);
                    listToSort.set(j + 1, census1);
                }
            }
        }
        return listToSort;
    }

    /**
     * TASK: Generic Method to sort entries in descending order
     * @param censusCSVComparator
     * @param listToSort
     * @param <T>
     */
    public List sortDescending(Comparator<T> censusCSVComparator,List listToSort) {
        for (int i = 0; i < listToSort.size() - 1; i++) {
            for (int j = 0; j < listToSort.size() - i - 1; j++) {
                T census1 = (T) listToSort.get(j);
                T census2 = (T) listToSort.get(j + 1);
                if (censusCSVComparator.compare(census1, census2) < 0) {
                    listToSort.set(j, census2);
                    listToSort.set(j + 1, census1);
                }
            }
        }
        return listToSort;
    }


    /**
     * TASK: to write a list into a json file
     * @param fileName
     * @param listToWrite
     */
    public void writeIntoJson(String fileName,List listToWrite) {
        try (Writer writer = new FileWriter(fileName)) {
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            gson.toJson(listToWrite, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
