package main_objects;

import enums.Color;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Objects;

/**
 * The {@code Person} class represents a person with a name, birthday, weight, eye color, and location.
 */
public class Person {
    private String name;
    private LocalDate birthDay;
    private Integer weight;
    private Color eyeColor;
    private Location location;

    /**
     * Constructs a new {@code Person} object from a list of administrative information.
     * @param adminInfo A list containing the person's administrative information.
     * @throws IllegalArgumentException if the list is null, has an incorrect number of elements,
     *                                  or if any of the elements cannot be parsed correctly.
     * @throws NullPointerException if any element of adminInfo is null where it is not allowed.
     */
    public Person(List<String> adminInfo) {
        if (adminInfo == null) {
            throw new IllegalArgumentException("Admin info list cannot be null.");
        }
        if (adminInfo.size() != 8) {
            throw new IllegalArgumentException("Admin info list must contain 8 elements.");
        }

        try {
            this.name = adminInfo.get(0);
            this.birthDay = LocalDate.parse(adminInfo.get(1), DateTimeFormatter.ofPattern("dd-MM-yyyy"));
            this.weight = Integer.parseInt(adminInfo.get(2));
            this.eyeColor = Color.valueOf(adminInfo.get(3).toUpperCase());
            this.location = new Location(Long.parseLong(adminInfo.get(4)), Integer.parseInt(adminInfo.get(5)), Integer.parseInt(adminInfo.get(6)), adminInfo.get(7));
        } catch (Exception e) {
            throw new IllegalArgumentException("Invalid admin info: " + e.getMessage(), e); // Include original exception
        }
    }

    /**
     * Returns the name of the person.
     *
     * @return The name of the person.
     */
    public String getName() {
        return name;
    }

    /**
     * Returns the birthday of the person as a String formatted as "dd-MM-yyyy".
     *
     * @return The birthday of the person as a String.
     */
    public String getBirthDay() {
        return birthDay.format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));
    }

    /**
     * Returns the weight of the person as a String.
     *
     * @return The weight of the person as a String.
     */
    public String getWeight() {
        return weight.toString();
    }

    /**
     * Returns the eye color of the person as a String.
     *
     * @return The eye color of the person as a String.
     */
    public String getEyeColor() {
        return eyeColor.toString();
    }


    /**
     * Returns the location of the person.
     *
     * @return The location of the person.
     */
    public Location getLocation() {
        return location;
    }

    /**
     * Returns a string representation of the {@code Person} object, including their name, birthday, eye color, weight, and location.
     *
     * @return A string representation of the person.
     */
    @Override
    public String toString() {
        return "Name: " + this.name + "\n" +
                "\t         Birthday: " + this.birthDay.format(DateTimeFormatter.ofPattern("dd-MM-yyyy")) + "\n" +
                "\t         EyeColor: " + this.eyeColor.toString() + "\n" +
                "\t         Weight: " + this.weight + "\n" +
                "\t         Location: " + this.location.toString();
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