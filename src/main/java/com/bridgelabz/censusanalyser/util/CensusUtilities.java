package com.bridgelabz.censusanalyser.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.*;
import java.util.Comparator;
import java.util.List;

public class CensusUtilities<T> {
    /**
     * TASK: To sort Input list according to given field name and order, then return the sorted list.
     * @param listToSort
     * @param fieldName
     * @param sortInReverseOrder
     * @return
     * @throws NoSuchFieldException
     */
    public List sortList(List listToSort,String fieldName, boolean sortInReverseOrder) throws NoSuchFieldException {
        if (sortInReverseOrder) {
            listToSort.sort(Comparator.comparing(report -> {
                try {
                    return (Comparable) report.getClass().getDeclaredField(fieldName).get(report);
                } catch (Exception e) {
                    throw new RuntimeException("INVALID FIELD", e);
                }
            }).reversed());
            return listToSort;
        }
        listToSort.sort(Comparator.comparing(report -> {
            try {
                return (Comparable) report.getClass().getDeclaredField(fieldName).get(report);
            } catch (Exception e) {
                throw new RuntimeException("INVALID FIELD", e);
            }
        }));
        return listToSort;
    }

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