package sber;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    // Разделитель полей CSV-файла
    private static final String SEMICOLON_DELIMITER = ";";

    // Метод для разбора записей из CSV-строки
    private static List<String> getRecordFromLine(String line) {
        List<String> values = new ArrayList<>();
        try (Scanner rowScanner = new Scanner(line)) {
            rowScanner.useDelimiter(SEMICOLON_DELIMITER);
            while (rowScanner.hasNext()) {
                values.add(rowScanner.next());
            }
        }
        return values;
    }

    public static void main(String[] args) {
        // Получаем список записей из CSV-файла
        List<List<String>> records;
        try {
            records = getRecordsFromCSVFile("Cities.csv");
        } catch (RuntimeException e) {
            // Если возникла ошибка при чтении файла, выводим сообщение об ошибке и завершаем работу программы
            System.out.println(e.getLocalizedMessage());
            return;
        }
        // Преобразуем записи в список объектов City
        List<City> cities = records.stream().map(
                City::recordToCity // Используем метод recordToCity класса City для создания объекта City из записи
        ).toList();
        // Выводим список городов в консоль
        System.out.println(cities);

    }

    // Метод для чтения записей из CSV-файла
    private static List<List<String>> getRecordsFromCSVFile(String filePath) {
        List<List<String>> records = new ArrayList<>();
        try (Scanner scanner = new Scanner(new File(filePath))) {
            while (scanner.hasNextLine()) {
                records.add(
                        getRecordFromLine(scanner.nextLine()) // Получаем список полей из CSV-строки
                );
            }
        } catch (FileNotFoundException e) {
            // Если файл не найден, выбрасываем исключение с сообщением "File not found"
            throw new RuntimeException("File not found", e);
        }
        return records;
    }
}