package utils;

import com.github.javafaker.Faker;

public class FakerUtils {
    private static final Faker faker = new Faker();

    public static String getFirstName() {
        return faker.name().firstName();
    }

    public static String getLastName() {
        return faker.name().lastName();
    }

    public static String getEmail() {
        return faker.internet().emailAddress();
    }

    public static String getPhoneNumber() {
        return faker.phoneNumber().subscriberNumber(10);
    }

    public static String getAddress() {
        return faker.address().fullAddress();
    }

    public static int getAge() {
        return faker.number().numberBetween(18, 70);
    }

    public static int getSalary() {
        return faker.number().numberBetween(1000, 10000);
    }

    public static String getDepartment() {
        return faker.company().industry();
    }
}
