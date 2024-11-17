package data;

import com.github.javafaker.Faker;
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


    public static String getWrongSecondCardNumberField() {
        return "(%$#@*";

    }
    public static String getLessSecondCardNumberField() {
        return "8";
    }

    public static String getMoreSecondCardNumberField() {
        return "13";
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

    public static String getMonthPlusTwo(int shift) {
        return LocalDate.now().plusMonths(2).format(DateTimeFormatter.ofPattern("MM"));
    }

    public static String getMonthWithOneGigit(int shift) {
        return LocalDate.now().minusMonths(1).format(DateTimeFormatter.ofPattern("MM"));
    }

    public static String getMonthWithTwoZero(int shift) {
        return LocalDate.now().minusMonths(00).format(DateTimeFormatter.ofPattern("MM"));
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

    public static String getFiveYearsAhead(int shift) {
        return LocalDate.now().plusYears(5).format(DateTimeFormatter.ofPattern("yy"));
    }

    public static String getEmptyYear() {
        return LocalDate.now().format(DateTimeFormatter.ofPattern(""));
    }
    public static String getYearWithOneGigit(int shift) {
        return LocalDate.now().minusYears(1).format(DateTimeFormatter.ofPattern("yy"));
    }
    public static String getYearWithTwoZero(int shift) {
        return LocalDate.now().minusYears(00).format(DateTimeFormatter.ofPattern("yy"));
    }

    public static String getYearWithSpecialSymbol(String s) {
        return LocalDate.now().format(DateTimeFormatter.ofPattern("$@^%$;:"));
    }

    public static String getNameOwner() {
        return fakerLangEN.name().fullName();
    }

    public static String getNameOwnerSpecialSymbol(String s) {
        return "(%$#@*";
    }

    public static String getNameOwnerCyrillic(Locale ru) {
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
    public static String getEmptyCvc() {
        return "";
    }

    public static String getGenerateWrongCvc() {
        return fakerLangEN.numerify("0");
    }

    public static String getSpecialSymbolCvc(String s) {
        return "@#$%";
    }
    }




















