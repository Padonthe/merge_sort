package external_mergesort;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.*;
import java.util.Iterator;

public class ExternalMergeSort {
    public static void main(String[] args) {
    	ArrayList<String> inputFiles = new ArrayList<String>();
    	SortOrder order = SortOrder.NONE;
    	ValuesType type = ValuesType.NONE;
    	
    	for (String arg : args) {
    		if (arg.equals("-a") && order == SortOrder.NONE) {
    			order = SortOrder.ASCENDING;
    		}
    		if (arg.equals("-d") && order == SortOrder.NONE) {
    			order = SortOrder.DESCENDING;
    		}
    		if (arg.equals("-i") && type == ValuesType.NONE) {
    			type = ValuesType.INT;
    		}
    		if (arg.equals("-s") && type == ValuesType.NONE) {
    			type = ValuesType.STRING;
    		}
    		
    		if (arg.endsWith(".txt")) {
    			inputFiles.add(arg);
    		}
    	}
    	
		if (type == ValuesType.NONE) {
			System.out.println("Не был задан тип данных");
			return;
		}
		
    	if (inputFiles.size() < 2) {
			System.out.println("Среди аргументов командной строки не хватает входных файлов");
			return;	
    	}
    	
    	MergeHandler mergeHandler = new MergeHandler(inputFiles, order, type);
    	mergeHandler.mergeFiles();
    	System.out.println("Слияние завершено");
    }


}