package sber;

import java.util.List;
import java.util.regex.Pattern;

public class City {
    // Регулярные выражения для проверки корректности ввода
    public static final String CITY_NAME_REGEX = "[А-ЯёЁ][А-ЯЁа-яё\\- ]+";
    public static final String CITY_REGION_REGEX = "[А-ЯёЁ][А-ЯЁа-яё\\- .]+";
    public static final String CITY_DISTRICT_REGEX = "[А-ЯёЁ][А-ЯЁа-яё\\- ]+";

    // Поля класса
    private String name;
    private String region;
    private String district;
    private int population;
    private String foundation;

    // Конструктор класса
    public City(String name, String region, String district, int population, String foundation) throws IllegalArgumentException {
        setName(name);
        setRegion(region);
        setDistrict(district);
        setPopulation(population);
        setFoundation(foundation);
    }

    // Статический метод для создания объекта City на основе записи из списка строк
    public static City recordToCity(List<String> record) {
        String name, region, district, populationStr, foundation;

        // Проверяем, что запись содержит не менее 5 полей
        if (record.size() < 5) throw new IllegalArgumentException("Incorrect record input! (id=" + record.get(0) + ")");

        // Извлекаем значения полей из записи
        name = record.get(1);
        region = record.get(2);
        district = record.get(3);
        populationStr = record.get(4);
        foundation = record.size() < 6 ? null : record.get(5);

        // Преобразуем строковое значение населения в целое число
        int population = getPopulationFromString(populationStr);

        try {
            return new City(name, region, district, population, foundation);
        } catch (IllegalArgumentException e) { // got incorrect record
            System.out.println(e.getLocalizedMessage());
            return null;
        }
    }

    // Вспомогательный метод для преобразования строки в целое число
    private static int getPopulationFromString(String populationStr) {
        int population;
        try {
            population = Integer.parseInt(populationStr);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Population isn't an integer!", e);
        }
        return population;
    }

    // Геттер и сеттер для поля name
    public String getName() {
        return name;
    }

    public void setName(String name) throws IllegalArgumentException {
        // Проверяем, что имя соответствует регулярному выражению CITY_NAME_REGEX
        boolean correctName = Pattern.matches(CITY_NAME_REGEX, name);
        if (!correctName) throw new IllegalArgumentException("Incorrect city name!");
        this.name = name;
    }

    // Геттер и сеттер для поля region
    public String getRegion() {
        return region;
    }

    public void setRegion(String region) throws IllegalArgumentException {
        // Проверяем, что регион соответствует регулярному выражению CITY_REGION_REGEX
        boolean correctRegion = Pattern.matches(CITY_REGION_REGEX, region);
        if (!correctRegion) throw new IllegalArgumentException("Incorrect city region!");
        this.region = region;
    }

    // Геттер и сеттер для поля district
    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) throws IllegalArgumentException {
        // Проверяем, что район соответствует регулярному выражению CITY_DISTRICT_REGEX
        boolean correctDistrict = Pattern.matches(CITY_DISTRICT_REGEX, district);
        if (!correctDistrict) throw new IllegalArgumentException("Incorrect city district!");
        this.district = district;
    }

    // Геттер и сеттер для поля population
    public int getPopulation() {
        return population;
    }

    public void setPopulation(int population) throws IllegalArgumentException {
        // Проверяем, что население положительно
        boolean correctPopulation = population > 0;
        if (!correctPopulation) throw new IllegalArgumentException("Incorrect city population!");
        this.population = population;
    }

    // Геттер и сеттер для поля foundation
    public String getFoundation() {
        if (foundation == null)
            return "н/у";
        return foundation;
    }

    public void setFoundation(String foundation) throws IllegalArgumentException {
        if (foundation == null) {
            this.foundation = null;
            return;
        }
        // Проверяем, что дата основания не является пустой строкой
        boolean correctFoundation = !foundation.isEmpty();
        if (!correctFoundation) throw new IllegalArgumentException("Incorrect foundation!");
        this.foundation = foundation;
    }

    // Переопределение метода toString() для вывода информации об объекте City в виде строки
    @Override
    public String toString() {
        return "CityModel{" +
                "name='" + getName() + '\'' +
                ", region='" + getRegion() + '\'' +
                ", district='" + getDistrict() + '\'' +
                ", population=" + getPopulation() +
                ", foundation='" + getFoundation() + '\'' +
                '}' + '\n';
    }
}