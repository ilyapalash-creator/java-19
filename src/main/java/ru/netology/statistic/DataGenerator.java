package ru.netology.statistic;

import com.github.javafaker.Faker;
import lombok.Value;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;
import java.util.Random;

public class DataGenerator {
    private DataGenerator() {
    }

    private static final List<String> VALID_CITIES = List.of(
            "Москва", "Санкт-Петербург", "Казань", "Новосибирск",
            "Екатеринбург", "Нижний Новгород", "Самара", "Омск",
            "Челябинск", "Ростов-на-Дону", "Уфа", "Красноярск"
    );

    public static String generateDate(int shift) {
        LocalDate date = LocalDate.now().plusDays(shift);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        return date.format(formatter);
    }

    public static String generateCity(Faker faker) {
        return VALID_CITIES.get(new Random().nextInt(VALID_CITIES.size()));
    }

    public static String generateName(Faker faker) {
        return faker.name().fullName();
    }

    public static String generatePhone(Faker faker) {
        String digits = faker.number().digits(10);
        return String.format("+7 (%s) %s-%s-%s",
                digits.substring(0, 3),
                digits.substring(3, 6),
                digits.substring(6, 8),
                digits.substring(8, 10)
        );
    }

    public static class Registration {
        private static Faker faker;

        private Registration() {
        }

        public static UserInfo generateUser(String locale) {
            faker = new Faker(new Locale(locale));
            String city = generateCity(faker);
            String name = generateName(faker);
            String phone = generatePhone(faker);
            return new UserInfo(city, name, phone);
        }
    }

    @Value
    public static class UserInfo {
        String city;
        String name;
        String phone;
    }
}