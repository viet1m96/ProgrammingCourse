package main_objects;

import enums.Color;

import java.time.format.DateTimeFormatter;
import java.util.List;

public class Person {
    private String name;
    private java.time.LocalDate birthDay;
    private Integer weight;
    private Color eyeColor;
    private Location location;

    public Person(List<String> adminInfo) {
        this.name = adminInfo.get(0);
        this.birthDay = java.time.LocalDate.parse(adminInfo.get(1), DateTimeFormatter.ofPattern("dd-MM-yyyy"));
        this.weight = Integer.parseInt(adminInfo.get(2));
        this.eyeColor = Color.valueOf(adminInfo.get(3).toUpperCase());
        this.location = new Location(Long.parseLong(adminInfo.get(4)), Integer.parseInt(adminInfo.get(5)), Integer.parseInt(adminInfo.get(6)), adminInfo.get(7));
    }

    public String getName() {
        return name;
    }
    public String getBirthDay() {
        return birthDay.format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));
    }
    public String getWeight() {
        return weight.toString();
    }
    public String getEyeColor() {
        return eyeColor.toString();
    }

    public Location getLocation() {
        return location;
    }

    @Override
    public String toString() {
        return "Name: " + this.name + "\n" +
                "\t         Birthday: " + this.birthDay.format(DateTimeFormatter.ofPattern("dd-MM-yyyy")) + "\n" +
                "\t         EyeColor: " + this.eyeColor.toString() + "\n" +
                "\t         Weight: " + this.weight + "\n" +
                "\t         Location: " + this.location.toString();
    }
}
