package data;

import com.github.javafaker.Faker;
import com.github.javafaker.Number;
import lombok.AllArgsConstructor;
import lombok.Value;
import org.apache.commons.lang3.StringUtils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class DataHelper {
    private static Faker fakerLangEN = new Faker(new Locale("en"));


    public static String getFirstCardNumber() {
        return "4444 4444 4444 4441";
    }


    public static String getStatusFirstCard() {
        return "APPROVED";
    }

    public static String getSecondCardNumber() {
        return "4444 4444 4444 4442";
    }

    public static String getStatusSecondCard() {

        return "DECLINED";
    }

    public static String getEmptyCardField() {
        return "";


    }

    public static String getEmptyCardNumberField() {
        return "";
    }

    public static String getCurrentMonth(int shift) {
        return LocalDate.now().plusMonths(shift).format(DateTimeFormatter.ofPattern("MM"));
    }

    public static String getWrongMonth(int shift) {
        return LocalDate.now().minusMonths(shift).format(DateTimeFormatter.ofPattern("MM"));
    }

    public static String getMonthWithOneZero(int shift) {
        return LocalDate.now().minusMonths(0).format(DateTimeFormatter.ofPattern("MM"));
    }

    public static String getMonthWithThirteenMonth(int shift) {
        return LocalDate.now().plusMonths(13).format(DateTimeFormatter.ofPattern("MM"));
    }

    public static String getEmptyMonth() {
        return LocalDate.now().format(DateTimeFormatter.ofPattern(""));
    }

    public static String getCurrentYear(int shift) {
        return LocalDate.now().format(DateTimeFormatter.ofPattern("yy"));
    }

    public static String getLastYear(int shift) {
        return LocalDate.now().minusYears(1).format(DateTimeFormatter.ofPattern("yy"));
    }

    public static String getFiveYearsInfoAhead(int shift) {
        return LocalDate.now().plusYears(5).format(DateTimeFormatter.ofPattern("yy"));
    }

    public static String getEmptyYear(int shift) {
        return LocalDate.now().format(DateTimeFormatter.ofPattern(""));
    }

    public static String getYearInfoWithSpecialSymbol() {
        return LocalDate.now().format(DateTimeFormatter.ofPattern("$@^%$;:"));
    }

    public static String getNameOwner() {
        return fakerLangEN.name().fullName();
    }

    public static String getNameOwnerInfoSpecialSymbol(String locale) {
        var faker = new Faker(new Locale("(%$#@*"));
        return faker.name().fullName();
    }

    public static String getNameOwnerInfoCyrillic() {
        var faker = new Faker(new Locale("ru"));
        return faker.name().fullName();
    }

    public static String getEmptyOwner(String locale) {
        var faker = new Faker(new Locale(""));
        return faker.name().fullName();
    }

    public static String getNameOwnerInfoMore30Letters() {
        return StringUtils.leftPad(fakerLangEN.name().fullName(), 31, 'z');
    }


    public static String getCvc() {
        return String.valueOf(fakerLangEN.number().numberBetween(111, 999));

    }

    public static String getGenerateWrongCvc() {
        return fakerLangEN.numerify("####");
    }

    public static String getEmptyCvc() {
        return "";


    }
}
















