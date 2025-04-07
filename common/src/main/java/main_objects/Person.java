package main_objects;

import enums.Color;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * The {@code Person} class represents a person with attributes such as name, birthday, weight, eye color, and location.
 */
public class Person implements Serializable {
    @Serial
    private static final long serialVersionUID = 4L;
    private Integer id;
    private String name;
    private LocalDate birthDay;
    private Integer weight;
    private Color eyeColor;
    private Location location;

    /**
     * Constructs a new {@code Person} object with default values.
     */
    public Person() {

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
     * Returns the birthday of the person as a formatted {@code String}.
     * If the birthday is {@code null}, it returns "null".
     *
     * @return The birthday of the person as a formatted {@code String}.
     */
    public LocalDate getBirthDay() {
        if (birthDay == null) return null;
        return birthDay;
    }

    /**
     * Returns the weight of the person as a {@code String}.
     * If the weight is {@code null}, it returns "null".
     *
     * @return The weight of the person as a {@code String}.
     */
    public Integer getWeight() {
        if (weight == null) return null;
        return weight;
    }

    /**
     * Returns the eye color of the person as a {@code String}.
     *
     * @return The eye color of the person as a {@code String}.
     */
    public Color getEyeColor() {
        return eyeColor;
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
     * Sets the name of the person.
     *
     * @param newName The new name to set.
     */
    public void setName(String newName) {
        this.name = newName;
    }

    /**
     * Sets the birthday of the person.
     *
     * @param newBirthDay The new birthday to set.
     */
    public void setBirthDay(LocalDate newBirthDay) {
        this.birthDay = newBirthDay;
    }

    /**
     * Sets the weight of the person.
     *
     * @param newWeight The new weight to set.
     */
    public void setWeight(Integer newWeight) {
        this.weight = newWeight;
    }

    /**
     * Sets the eye color of the person.
     *
     * @param newEyeColor The new eye color to set.
     */
    public void setEyeColor(Color newEyeColor) {
        this.eyeColor = newEyeColor;
    }

    /**
     * Sets the location of the person.
     *
     * @param newLocation The new location to set.
     */
    public void setLocation(Location newLocation) {
        this.location = newLocation;
    }

    public void setId(Integer newId) {
        this.id = newId;
    }
    public Integer getId() {
        return id;
    }

    /**
     * Returns a string representation of the {@code Person} object.
     * It includes the name, birthday, eye color, weight, and location of the person.
     *
     * @return A string representation of the {@code Person} object.
     */
    @Override
    public String toString() {
        String Bday, weight, location;
        if (birthDay == null) {
            Bday = "";
        } else {
            Bday = this.birthDay.toString();
        }
        if (this.weight == null) {
            weight = "";
        } else {
            weight = this.weight.toString();
        }
        if (this.location == null) {
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

    /**
     * Compares this {@code Person} object to another object for equality.
     *
     * @param o The object to compare to.
     * @return {@code true} if the objects are equal, {@code false} otherwise.
     */
    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Person person)) return false;
        return Objects.equals(name, person.name) && Objects.equals(birthDay, person.birthDay) && Objects.equals(weight, person.weight) && eyeColor == person.eyeColor && Objects.equals(location, person.location);
    }

    /**
     * Returns a hash code value for this {@code Person} object.
     *
     * @return A hash code value for this {@code Person} object.
     */
    @Override
    public int hashCode() {
        return Objects.hash(name, birthDay, weight, eyeColor, location);
    }
}
