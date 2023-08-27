package external_mergesort;

import java.io.IOException;

public class IntegerFileReader extends FileReader {

    public IntegerFileReader(String path) {
        super(path);
    }

    @Override
    public Object getNextValue() {
        try {
            return Integer.valueOf(super.getReader().nextLine());
        } catch (NumberFormatException e) {
            System.out.println("Ошибка преобразования строки в целое число.");
            e.printStackTrace();
            return null;  // или выбросить исключение, в зависимости от логики
        }
    }
    
}
