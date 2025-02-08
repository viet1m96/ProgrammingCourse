package io_utilities.working_with_input;

public class InputPartition {
    public InputPartition() {}
    public static String part1st(String inp) {
        return inp.split(" ")[0];
    }
    public static String part2nd(String inp) {
        if(inp.split(" ").length < 2) return null;
        return inp.split(" ")[1];
    }
}
