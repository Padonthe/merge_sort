package external_mergesort;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public abstract class FileReader {
    protected Scanner reader;
    private String path;
	public FileReader(String path) {
		try {
			reader = new Scanner(new File(path)); 
			this.path = path;
		} catch (FileNotFoundException e) {
			System.out.println("Файл " + path + " не найден");
		}
	}
	public boolean hasNext() {
		boolean result = false;
		try {
			result = reader.hasNext();
		} catch (IllegalStateException e) {
			System.out.println("Сканнер файла " + this.path + " закрыт");
			result = false;
		}
		return result;
	}
	
	abstract public Object getNextValue();
	
	public Scanner getReader() {
		return this.reader;
	}
}
