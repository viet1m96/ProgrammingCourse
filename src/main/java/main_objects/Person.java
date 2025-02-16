package main_objects;

import enums.Color;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Objects;

public class Person {
    private String name;
    private LocalDate birthDay;
    private Integer weight;
    private Color eyeColor;
    private Location location;

    public Person() {

    }

    public String getName() {
        return name;
    }

    public String getBirthDay() {
        if(birthDay == null) return "null";
        return birthDay.format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));
    }

    public String getWeight() {
        if(weight == null) return "null";
        return weight.toString();
    }

    public String getEyeColor() {
        return eyeColor.toString();
    }

    public Location getLocation() {
        return location;
    }

    public void setName(String newName) {
        this.name = newName;
    }
    public void setBirthDay(LocalDate newBirthDay) {
        this.birthDay = newBirthDay;
    }
    public void setWeight(Integer newWeight) {
        this.weight = newWeight;
    }
    public void setEyeColor(Color newEyeColor) {
        this.eyeColor = newEyeColor;
    }
    public void setLocation(Location newLocation) {
        this.location = newLocation;
    }
    @Override
    public String toString() {
        String Bday, weight, location;
        if(birthDay == null){
            Bday = "";
        } else {
            Bday = this.birthDay.format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));
        }
        if(this.weight == null) {
            weight = "";
        } else {
            weight = this.weight.toString();
        }
        if(this.location == null) {
            location = "";
        } else {
            location = this.location.toString();
        }
        return "Name: " + this.name + "\n" +
                "\t         Birthday: " + Bday + "\n" +
                "\t         EyeColor: " + this.eyeColor.toString() + "\n" +
                "\t         Weight: " + weight + "\n" +
                "\t         Location: " + location;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Person person)) return false;
        return Objects.equals(name, person.name) && Objects.equals(birthDay, person.birthDay) && Objects.equals(weight, person.weight) && eyeColor == person.eyeColor && Objects.equals(location, person.location);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, birthDay, weight, eyeColor, location);
    }
}