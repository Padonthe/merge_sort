package external_mergesort;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
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
        currentValues = new ArrayList<Object>();

        for (FileReader reader : readers) {
            if (reader.hasNext()) {
                currentValues.add(reader.getNextValue());
            }
        }

        while (!readers.isEmpty()) {
            OutputData outputValue = findNextOutputValueAndIndex(currentValues);
            int index = outputValue.getIndex();

            if (index < 0) {
                break; // No more values to merge
            }

            if (readers.get(index).hasNext()) {
                FileReader reader = readers.get(index);
                T nextValue = (T) reader.getNextValue();
                if (cmp.compare(nextValue, (T) outputValue.getValue())) {
                    currentValues.set(index, nextValue);
                    writeOutputValue(outputValue.getValue());
                }
            } else {
                currentValues.remove(index);
                readers.remove(index);
                writeOutputValue(outputValue.getValue());
            }
        }
    }

    private <T extends Comparable<T>> OutputData<T> findNextOutputValueAndIndex(ArrayList<Object> values) {
        if (values.isEmpty())
            return new OutputData<>(null, -1);

        T minMaxValue = (T) values.get(0);
        int minMaxIndex = 0;

        for (int i = 1; i < values.size(); i++) {
            if (cmp.compare((T) values.get(i), minMaxValue)) {
                minMaxValue = (T) values.get(i);
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
