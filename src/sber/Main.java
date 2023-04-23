package sber;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Main {
    // Определение разделителя полей CSV-файла
    private static final String SEMICOLON_DELIMITER = ";";
    // Путь к файлу CSV
    public static final String CSV_FILE_PATH = "Cities.csv";

    // Метод, который разбивает строку на поля по разделителю и возвращает список полей
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
        // Получение списка городов из файла
        List<City> cities = getCitiesFromFile();
        if (cities == null) return;

        // Демонстрация различных сортировок
        sortingDemonstration(cities);

        // Вывод города с наибольшей численностью населения
        showMaxPopulation(cities);

        // Вывод количества городов в каждом регионе
        showRegionsCitiesCount(cities);
    }

    // Метод выводит количество городов в каждом регионе
    private static void showRegionsCitiesCount(List<City> cities) {
        System.out.println("Region's cities count:");

        cities
                .stream().map(city -> city.getRegion())
                .collect(
                        Collectors.groupingBy(
                                Function.identity(), Collectors.counting()
                        )
                )
                .forEach((reg, count) -> System.out.println(reg + " - " + count));
    }

    // Метод выводит город с наибольшей численностью населения
    private static void showMaxPopulation(List<City> cities) {
        // Преобразование списка в массив городов
        City[] citiesArray = cities.toArray(City[]::new);
        int max_population = -1, max_index = 0;
        for (int index = 0; index < citiesArray.length; index++) {
            // Поиск города с наибольшей численностью населения
            if (max_population < citiesArray[index].getPopulation()) {
                max_population = citiesArray[index].getPopulation();
                max_index = index;
            }
        }

        // Вывод информации о найденном городе
        System.out.println("Max population:");
        System.out.println("[" + max_index + "] = " + citiesArray[max_index].getPopulation());
    }

    // Метод демонстрирует различные сортировки списка городов
    private static void sortingDemonstration(List<City> cities) {
        System.out.println("Without sort");
        System.out.println(cities);

        // Сортировка по имени города
        Comparator<City> nameComparator = Comparator.comparing(city -> city.getName().toLowerCase());

        System.out.println("Sorted by name");
        System.out.println(
                getSortedList(cities, nameComparator)
        );

        // Сортировка по округу и имени города
        Comparator<City> districtComparator = Comparator.comparing(City::getDistrict);
        districtComparator = districtComparator.thenComparing(City::getName);

        System.out.println("Sorted by district and name");
        System.out.println(
                getSortedList(cities, districtComparator)
        );
    }

    // Метод, который возвращает список городов из CSV-файла
    private static List<City> getCitiesFromFile() {
        // Получение списка записей из CSV-файла
        List<List<String>> records;
        try {
            records = getRecordsFromCSVFile(CSV_FILE_PATH); // вызываем метод для чтения CSV-файла и получения списка записей
        } catch (RuntimeException e) {
            System.out.println(e.getLocalizedMessage()); // печатаем сообщение об ошибке в консоль
            return null; // возвращаем null, чтобы указать на ошибку
        }
        return records.stream().map(
                City::recordToCity // преобразуем каждую запись в объект City
        ).toList(); // создаем и возвращаем список городов
    }

    // Метод для получения отсортированного списка городов
    private static List<City> getSortedList(List<City> cities, Comparator<City> nameComparator) {
        return cities.stream().sorted(nameComparator).toList(); // сортируем города по заданному компаратору
    }

    // Метод для чтения CSV-файла и получения списка записей
    private static List<List<String>> getRecordsFromCSVFile(String filePath) {
        List<List<String>> records = new ArrayList<>(); // создаем список для хранения записей
        try (Scanner scanner = new Scanner(new File(filePath))) { // открываем файл для чтения
            while (scanner.hasNextLine()) { // читаем файл построчно
                records.add(getRecordFromLine(scanner.nextLine())); // добавляем запись в список
            }
        } catch (FileNotFoundException e) { // обрабатываем ошибку открытия файла
            throw new RuntimeException("File not found", e); // генерируем исключение с сообщением об ошибке
        }
        return records; // возвращаем список записей
    }
}
