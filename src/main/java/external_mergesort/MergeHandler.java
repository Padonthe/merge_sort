package external_mergesort;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.ArrayList;
import java.util.Iterator;

public class MergeHandler {
    private Comparator cmp;
    private String outputFilePath;
    private ArrayList<String> inputFiles;
    private ArrayList<FileReader> readers = new ArrayList<FileReader>();
    private ArrayList<Object> currentValues;
    private ValuesType type;

    public MergeHandler(ArrayList<String> files, SortOrder order, ValuesType type) {
        outputFilePath = files.remove(0);
        inputFiles = files;

        for (String file : inputFiles) {
            if (type == ValuesType.INT)
                readers.add(new IntegerFileReader(file));
            else if (type == ValuesType.STRING)
                readers.add(new StringFileReader(file));
        }

        this.readers = readers;
        this.cmp = new Comparator(order);
        this.type = type;
    }

    public <T extends Comparable<T>> void mergeFiles() {
        currentValues = new ArrayList<>();

        Iterator<FileReader> iter = readers.iterator();
        while (iter.hasNext()) {
            FileReader reader = iter.next();
            if (!reader.hasNext()) {
                iter.remove();
            } else {
                currentValues.add(reader.getNextValue());
            }
        }

        while (!readers.isEmpty()) {
        	OutputData<T> outputValue = findNextOutputValueAndIndex1(currentValues);
            if (outputValue == null) {
                break; 
            }
            int index = outputValue.getIndex();

            if (readers.get(index).hasNext()) {
                FileReader reader = readers.get(index);
                T nextValue = (T) reader.getNextValue();
                if (cmp.<T>compare(nextValue, outputValue.getValue())) {
                    currentValues.set(index, nextValue);
                    writeOutputValue(outputValue.getValue());
                }
            } else {
                currentValues.remove(index);
                readers.remove(index);
                writeOutputValue(outputValue.getValue());
                if (index < currentValues.size()) {
                    currentValues.set(index, readers.get(index).getNextValue());
                }
            }
        }
    }

    private <T> OutputData<T> findNextOutputValueAndIndex(ArrayList<T> currentValues2) {
		return null;
	}

    private <T extends Comparable<T>> OutputData<T> findNextOutputValueAndIndex1(List<Object> currentValues2) {
        if (currentValues2.isEmpty())
            return new OutputData<>(null, -1);

        T minMaxValue = (T) currentValues2.get(0);
        int minMaxIndex = 0;

        for (int i = 1; i < currentValues2.size(); i++) {
            if (cmp.compare((T) currentValues2.get(i), minMaxValue)) {
                minMaxValue = (T) currentValues2.get(i);
                minMaxIndex = i;
            }
        }

        return new OutputData<>(minMaxValue, minMaxIndex);
    }


    private <T> void writeOutputValue(T value) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(outputFilePath, true))) {
            writer.write(value.toString() + "\n");
        } catch (IOException e) {
            System.out.println("Ошибка при записи в файл " + outputFilePath);
            e.printStackTrace();
        }
    }
}
