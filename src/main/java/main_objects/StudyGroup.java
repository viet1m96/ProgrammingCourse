package main_objects;

import enums.FormOfEducation;
import enums.Semester;
import io_utilities.printers.RainbowPrinter;


import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Represents a study group with various attributes, including its name, coordinates,
 * student count, and associated administrator. Implements the {@link Comparable} interface
 * for comparing study groups based on their student count.
 */
public class StudyGroup implements Comparable<StudyGroup> {
    private Integer id;
    private String name;
    private Coordinates coordinates;
    private java.time.LocalDate creationDate;
    private Long studentsCount;
    private long expelledStudents;
    private FormOfEducation formOfEducation;
    private Semester semesterEnum;
    private Person groupAdmin;

    /**
     * Default constructor for creating an empty {@code StudyGroup} object.
     */
    public StudyGroup() {}

    /**
     * Constructs a {@code StudyGroup} object from lists of string data.
     *
     * @param groupInfo A list of strings containing information about the study group.
     *                  Expected order: id, name, x coordinate, y coordinate, creation date,
     *                  students count, expelled students, form of education, semester.
     * @param adminInfo A list of strings containing information about the group administrator.
     *                  This list is passed to the {@link Person} constructor.
     */
    public StudyGroup(List<String> groupInfo, List<String> adminInfo) {
        this.id = Integer.parseInt(groupInfo.get(0));
        this.name = groupInfo.get(1);
        this.coordinates = new Coordinates(Integer.parseInt(groupInfo.get(2)), Integer.parseInt(groupInfo.get(3)));
        this.creationDate = java.time.LocalDate.parse(groupInfo.get(4), DateTimeFormatter.ofPattern("dd-MM-yyyy"));
        this.studentsCount = Long.parseLong(groupInfo.get(5));
        this.expelledStudents = Long.parseLong(groupInfo.get(6));
        this.formOfEducation = FormOfEducation.valueOf(groupInfo.get(7).toUpperCase());
        this.semesterEnum = Semester.valueOf(groupInfo.get(8).toUpperCase());
        this.groupAdmin = new Person(adminInfo);
    }

    /**
     * Prints all the information of the study group to the console using {@link RainbowPrinter}.
     */
    public void printEverything() {
        RainbowPrinter.printCondition("*Information of the group:");
        RainbowPrinter.printResult("ID: " + this.id);
        RainbowPrinter.printResult("Name: " + this.name);
        RainbowPrinter.printResult("Coordinates: " + this.coordinates.toString());
        RainbowPrinter.printResult("Creation Date: " + this.creationDate.format(DateTimeFormatter.ofPattern("dd-MM-yyyy")));
        RainbowPrinter.printResult("Student Count: " + this.studentsCount);
        RainbowPrinter.printResult("Expelled Students: " + this.expelledStudents);
        RainbowPrinter.printResult("Form of Education: " + this.formOfEducation.toString());
        RainbowPrinter.printResult("Semester: " + this.semesterEnum.toString());
        RainbowPrinter.printResult("Group Admin: " + this.groupAdmin.toString());
    }

    /**
     * Compares this {@code StudyGroup} to another {@code StudyGroup} based on the student count.
     *
     * @param o The other {@code StudyGroup} to compare to.
     * @return A negative integer, zero, or a positive integer as this {@code StudyGroup}'s student count
     *         is less than, equal to, or greater than the specified {@code StudyGroup}'s student count.
     */
    @Override
    public int compareTo(StudyGroup o) {
        return Long.compare(this.studentsCount, o.studentsCount);
    }

    /**
     * Gets the ID of the study group.
     *
     * @return The ID of the study group.
     */
    public Integer getId() {
        return id;
    }

    /**
     * Sets the ID of the study group.
     *
     * @param id The new ID of the study group.
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * Gets the name of the study group.
     *
     * @return The name of the study group.
     */
    public String getName() {
        return name;
    }

    /**
     * Gets the semester of the study group.
     *
     * @return The semester of the study group as a {@link Semester} enum.
     */
    public Semester getSemesterEnum() {
        return semesterEnum;
    }

    /**
     * Gets all the fields of the study group and its associated {@link Person} object as a list of {@link StringBuilder} objects.
     * The list is ordered as follows:
     *  - StudyGroup fields: id, name, x coordinate, y coordinate, creation date, student count, expelled students, form of education, semester
     *  - Person fields: name, birthday, weight, eye color
     *  - Location fields: x, y, z, name
     *
     * @return A list of {@link StringBuilder} objects representing all the fields of the study group.
     */
    public List<StringBuilder> getAllFields() {
        List<StringBuilder> fields = new ArrayList<>();
        fields.add(new StringBuilder(Integer.toString(this.id)));
        fields.add(new StringBuilder(this.name));
        fields.add(new StringBuilder(String.valueOf(this.coordinates.getX())));
        fields.add(new StringBuilder(String.valueOf(this.coordinates.getY())));
        fields.add(new StringBuilder(this.creationDate.format(DateTimeFormatter.ofPattern("dd-MM-yyyy"))));
        fields.add(new StringBuilder(String.valueOf(this.studentsCount)));
        fields.add(new StringBuilder(String.valueOf(this.expelledStudents)));
        fields.add(new StringBuilder(this.formOfEducation.toString()));
        fields.add(new StringBuilder(this.semesterEnum.toString()));
        fields.add(new StringBuilder(this.groupAdmin.getName()));
        fields.add(new StringBuilder(this.groupAdmin.getBirthDay()));
        fields.add(new StringBuilder(this.groupAdmin.getWeight()));
        fields.add(new StringBuilder(this.groupAdmin.getEyeColor()));
        Location location = groupAdmin.getLocation();
        fields.add(new StringBuilder(location.getX()));
        fields.add(new StringBuilder(location.getY()));
        fields.add(new StringBuilder(location.getZ()));
        fields.add(new StringBuilder(location.getName()));
        return fields;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof StudyGroup that)) return false;
        return expelledStudents == that.expelledStudents && Objects.equals(id, that.id) && Objects.equals(name, that.name) && Objects.equals(coordinates, that.coordinates) && Objects.equals(creationDate, that.creationDate) && Objects.equals(studentsCount, that.studentsCount) && formOfEducation == that.formOfEducation && semesterEnum == that.semesterEnum && Objects.equals(groupAdmin, that.groupAdmin);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, coordinates, creationDate, studentsCount, expelledStudents, formOfEducation, semesterEnum, groupAdmin);
    }
}