package io_utilities.printers;

public class RainbowPrinter {

    private static final String YELLOW = "\u001B[33m";
    private static final String RED = "\u001B[31m";
    private static final String GREEN = "\u001B[32m";
    private static final String CYAN = "\u001B[36m";
    private static final String WHITE = "\u001B[37m";

    public static void printInfo(Object toOut) {
        System.out.println(GREEN + toOut + WHITE);
    }

    public static void printError(Object toError) {
        System.out.println(RED + toError + WHITE);
    }

    public static void printCondition(Object toOut) {
        System.out.println(YELLOW + toOut + WHITE);
    }

    public static void printResult(Object toOut) {
        System.out.println(CYAN + toOut + WHITE);
    }
}