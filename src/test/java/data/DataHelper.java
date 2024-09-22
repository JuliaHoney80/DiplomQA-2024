package data;

import com.github.javafaker.Faker;
import com.github.javafaker.Number;
import lombok.Value;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class DataHelper {
    private static final Faker FAKER = new Faker(new Locale("en"));

    private DataHelper() {
    }

    public static String getFirstCardNumber() {
        return  ("4444 4444 4444 4441");
    }

    public static String getStatusFirstCard() {
        return ("APPROVED");
    }

    public static String getSecondCard() {
        return ("4444 4444 4444 4442");
    }

    public static String getStatusSecondCard() {

        return ("DECLINED");
    }

    public static String getGenerateCurrentMonthInfo(int shift) {
        return LocalDate.now().plusMonths(shift).format(DateTimeFormatter.ofPattern("MM"));
    }

    public static String getGenerateWrongMonthInfo(int shift) {
        return LocalDate.now().minusMonths(shift).format(DateTimeFormatter.ofPattern("MM"));
    }

    public static String getGenerationMonthWithOneZero(int shift) {
        return LocalDate.now().minusMonths(0).format(DateTimeFormatter.ofPattern("MM"));
    }

    public static String getGenerationMonthWithThirteenMonth(int shift) {
        return LocalDate.now().plusMonths(13).format(DateTimeFormatter.ofPattern("MM"));
    }

    public static String getGenerateEmptyMonth() {

        return LocalDate.now().format(DateTimeFormatter.ofPattern(""));
    }

    public static String getGenerateCurrentYearInfo(int shift) {
        return LocalDate.now().format(DateTimeFormatter.ofPattern("yy"));
    }

    public static String getGenerateLastYearInfo(int shift) {
        return LocalDate.now().minusYears(1).format(DateTimeFormatter.ofPattern("yy"));
    }

    public static String getGenerateFiveYearsInfoAhead(int shift) {
        return LocalDate.now().plusYears(5).format(DateTimeFormatter.ofPattern("yy"));
    }

    public static String getEmptyYearInfo(int shift) {
        return LocalDate.now().format(DateTimeFormatter.ofPattern(""));
    }

    public static String getGenerateYearInfoWithSpecialSymbol() {
        return LocalDate.now().format(DateTimeFormatter.ofPattern("$@^%$;:"));
    }

    public static String getGenerateNameOwnerInfo(String locale) {
        var faker = new Faker(new Locale(locale));
        return faker.name().fullName();
    }

    public static String getGenerateNameOwnerInfoSpecialSymbol(String locale) {
        var faker = new Faker(new Locale("(%$#@*"));
        return faker.name().fullName();
    }

    public static String getGenerateNameOwnerInfoCyrillic() {
        var faker = new Faker(new Locale("ru"));
        return faker.name().fullName();
    }

    public static String getGenerateEmptyOwnerInfo(String locale) {
        var faker = new Faker(new Locale(""));
        return faker.name().fullName();
    }

    //public static String getGenerateNameOwnerInfoMore30Letters(String locale) {
    //var faker = new Faker(new Locale(""));
    //return faker.name().fullName(); НЕ ПОЙМУ КАК НАПИСАТЬ МЕТОД


    public static Number getGenerateCvc(String locale) {
        var faker = new Faker(new Locale(locale));
        return faker.number();

    }
    public static String getGenerateWrongCvc(String locale) {
        var faker = new Faker(new Locale(locale));
        return faker.numerify("####");
    }
    public static String getEmptyCvc() {
        return new Card("853");
    }

@Value
    public static class Card {
        String cardNumber;
        String status;
        String month;
        String year;
        String owner;
        String cvc;
        String amount;



    }
}
















