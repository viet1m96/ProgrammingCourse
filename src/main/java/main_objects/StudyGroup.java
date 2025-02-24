package main_objects;

import enums.FormOfEducation;
import enums.Semester;
import io_utilities.printers.RainbowPrinter;


import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * The {@code StudyGroup} class represents a study group with attributes such as ID, name, coordinates,
 * creation date, student count, expelled students, form of education, semester, and group admin.
 * It implements the {@code Comparable} interface to allow comparing study groups based on their student count.
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
     * Constructs a new {@code StudyGroup} object with default values.
     */
    public StudyGroup() {
    }

    /**
     * Prints all information of the study group to the console.
     */
    public void printEverything() {
        RainbowPrinter.printCondition("*Information of the group:");
        RainbowPrinter.printResult("ID: " + this.id);
        RainbowPrinter.printResult("Name: " + this.name);
        RainbowPrinter.printResult("Coordinates: " + this.coordinates.toString());
        RainbowPrinter.printResult("Creation Date: " + this.creationDate.format(DateTimeFormatter.ofPattern("dd-MM-yyyy")));
        RainbowPrinter.printResult("Student Count: " + this.studentsCount);
        RainbowPrinter.printResult("Expelled Students: " + this.expelledStudents);
        if (formOfEducation == null) {
            RainbowPrinter.printResult("Form of Education: ");
        } else {
            RainbowPrinter.printResult("Form of Education: " + this.formOfEducation.toString());
        }
        RainbowPrinter.printResult("Semester: " + this.semesterEnum.toString());
        RainbowPrinter.printResult("Group Admin: " + this.groupAdmin.toString());
    }

    /**
     * Compares this {@code StudyGroup} object to another {@code StudyGroup} object based on their student count.
     *
     * @param o The {@code StudyGroup} object to compare to.
     * @return A negative integer, zero, or a positive integer as this object is less than, equal to, or greater than the specified object.
     */
    @Override
    public int compareTo(StudyGroup o) {
        return Long.compare(this.studentsCount, o.studentsCount);
    }

    /**
     * Returns the ID of the study group.
     *
     * @return The ID of the study group.
     */
    public Integer getId() {
        return id;
    }

    /**
     * Sets the ID of the study group.
     *
     * @param id The new ID to set.
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * Returns the name of the study group.
     *
     * @return The name of the study group.
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the study group.
     *
     * @param name The new name to set.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Returns the coordinates of the study group.
     *
     * @return The coordinates of the study group.
     */
    public Coordinates getCoordinates() {
        return coordinates;
    }

    /**
     * Sets the coordinates of the study group.
     *
     * @param coordinates The new coordinates to set.
     */
    public void setCoordinates(Coordinates coordinates) {
        this.coordinates = coordinates;
    }

    /**
     * Returns the creation date of the study group.
     *
     * @return The creation date of the study group.
     */
    public LocalDate getCreationDate() {
        return creationDate;
    }

    /**
     * Sets the creation date of the study group.
     *
     * @param creationDate The new creation date to set.
     */
    public void setCreationDate(LocalDate creationDate) {
        this.creationDate = creationDate;
    }

    /**
     * Returns the number of students in the study group.
     *
     * @return The number of students in the study group.
     */
    public Long getStudentsCount() {
        return studentsCount;
    }

    /**
     * Sets the number of students in the study group.
     *
     * @param studentsCount The new number of students to set.
     */
    public void setStudentsCount(Long studentsCount) {
        this.studentsCount = studentsCount;
    }

    /**
     * Returns the number of expelled students in the study group.
     *
     * @return The number of expelled students in the study group.
     */
    public long getExpelledStudents() {
        return expelledStudents;
    }

    /**
     * Sets the number of expelled students in the study group.
     *
     * @param expelledStudents The new number of expelled students to set.
     */
    public void setExpelledStudents(long expelledStudents) {
        this.expelledStudents = expelledStudents;
    }

    /**
     * Returns the form of education of the study group.
     *
     * @return The form of education of the study group.
     */
    public FormOfEducation getFormOfEducation() {
        return formOfEducation;
    }

    /**
     * Sets the form of education of the study group.
     *
     * @param formOfEducation The new form of education to set.
     */
    public void setFormOfEducation(FormOfEducation formOfEducation) {
        this.formOfEducation = formOfEducation;
    }

    /**
     * Returns the semester of the study group.
     *
     * @return The semester of the study group.
     */
    public Semester getSemesterEnum() {
        return semesterEnum;
    }

    /**
     * Sets the semester of the study group.
     *
     * @param semesterEnum The new semester to set.
     */
    public void setSemesterEnum(Semester semesterEnum) {
        this.semesterEnum = semesterEnum;
    }

    /**
     * Returns the group admin of the study group.
     *
     * @return The group admin of the study group.
     */
    public Person getGroupAdmin() {
        return groupAdmin;
    }

    /**
     * Sets the group admin of the study group.
     *
     * @param groupAdmin The new group admin to set.
     */
    public void setGroupAdmin(Person groupAdmin) {
        this.groupAdmin = groupAdmin;
    }

    /**
     * Returns a list of string builders containing all fields of the study group.
     * This is used for saving the study group to a file.
     *
     * @return A list of string builders containing all fields of the study group.
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
        if (formOfEducation == null) {
            fields.add(new StringBuilder("null"));
        } else {
            fields.add(new StringBuilder(this.formOfEducation.toString()));
        }
        fields.add(new StringBuilder(this.semesterEnum.toString()));
        fields.add(new StringBuilder(this.groupAdmin.getName()));
        fields.add(new StringBuilder(this.groupAdmin.getBirthDay()));
        fields.add(new StringBuilder(this.groupAdmin.getWeight()));
        fields.add(new StringBuilder(this.groupAdmin.getEyeColor()));
        Location location = groupAdmin.getLocation();
        if (location == null) {
            for (int i = 0; i < 4; i++) fields.add(new StringBuilder("null"));
        } else {
            fields.add(new StringBuilder(location.getX()));
            fields.add(new StringBuilder(location.getY()));
            fields.add(new StringBuilder(location.getZ()));
            fields.add(new StringBuilder(location.getName()));
        }
        return fields;
    }

    /**
     * Compares this {@code StudyGroup} object to another object for equality.
     *
     * @param o The object to compare to.
     * @return {@code true} if the objects are equal, {@code false} otherwise.
     */
    @Override
    public boolean equals(Object o) {
        if (!(o instanceof StudyGroup that)) return false;
        return expelledStudents == that.expelledStudents && Objects.equals(id, that.id) && Objects.equals(name, that.name) && Objects.equals(coordinates, that.coordinates) && Objects.equals(creationDate, that.creationDate) && Objects.equals(studentsCount, that.studentsCount) && formOfEducation == that.formOfEducation && semesterEnum == that.semesterEnum && Objects.equals(groupAdmin, that.groupAdmin);
    }

    /**
     * Returns a hash code value for this {@code StudyGroup} object.
     *
     * @return A hash code value for this {@code StudyGroup} object.
     */
    @Override
    public int hashCode() {
        return Objects.hash(id, name, coordinates, creationDate, studentsCount, expelledStudents, formOfEducation, semesterEnum, groupAdmin);
    }
}
