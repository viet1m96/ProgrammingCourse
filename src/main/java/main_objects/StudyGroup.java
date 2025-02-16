package main_objects;

import enums.FormOfEducation;
import enums.Semester;
import io_utilities.printers.RainbowPrinter;


import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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

    public StudyGroup() {}

    public void printEverything() {
        RainbowPrinter.printCondition("*Information of the group:");
        RainbowPrinter.printResult("ID: " + this.id);
        RainbowPrinter.printResult("Name: " + this.name);
        RainbowPrinter.printResult("Coordinates: " + this.coordinates.toString());
        RainbowPrinter.printResult("Creation Date: " + this.creationDate.format(DateTimeFormatter.ofPattern("dd-MM-yyyy")));
        RainbowPrinter.printResult("Student Count: " + this.studentsCount);
        RainbowPrinter.printResult("Expelled Students: " + this.expelledStudents);
        if(formOfEducation == null) {
            RainbowPrinter.printResult("Form of Education: ");
        } else {
            RainbowPrinter.printResult("Form of Education: " + this.formOfEducation.toString());
        }
        RainbowPrinter.printResult("Semester: " + this.semesterEnum.toString());
        RainbowPrinter.printResult("Group Admin: " + this.groupAdmin.toString());
    }

    @Override
    public int compareTo(StudyGroup o) {
        return Long.compare(this.studentsCount, o.studentsCount);
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Coordinates getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(Coordinates coordinates) {
        this.coordinates = coordinates;
    }

    public LocalDate getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDate creationDate) {
        this.creationDate = creationDate;
    }

    public Long getStudentsCount() {
        return studentsCount;
    }

    public void setStudentsCount(Long studentsCount) {
        this.studentsCount = studentsCount;
    }

    public long getExpelledStudents() {
        return expelledStudents;
    }

    public void setExpelledStudents(long expelledStudents) {
        this.expelledStudents = expelledStudents;
    }

    public FormOfEducation getFormOfEducation() {
        return formOfEducation;
    }

    public void setFormOfEducation(FormOfEducation formOfEducation) {
        this.formOfEducation = formOfEducation;
    }

    public Semester getSemesterEnum() {
        return semesterEnum;
    }

    public void setSemesterEnum(Semester semesterEnum) {
        this.semesterEnum = semesterEnum;
    }

    public Person getGroupAdmin() {
        return groupAdmin;
    }

    public void setGroupAdmin(Person groupAdmin) {
        this.groupAdmin = groupAdmin;
    }

    public List<StringBuilder> getAllFields() {
        List<StringBuilder> fields = new ArrayList<>();
        fields.add(new StringBuilder(Integer.toString(this.id)));
        fields.add(new StringBuilder(this.name));
        fields.add(new StringBuilder(String.valueOf(this.coordinates.getX())));
        fields.add(new StringBuilder(String.valueOf(this.coordinates.getY())));
        fields.add(new StringBuilder(this.creationDate.format(DateTimeFormatter.ofPattern("dd-MM-yyyy"))));
        fields.add(new StringBuilder(String.valueOf(this.studentsCount)));
        fields.add(new StringBuilder(String.valueOf(this.expelledStudents)));
        if(formOfEducation == null) {
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
        if(location == null) {
            for(int i = 0; i < 4; i++) fields.add(new StringBuilder("null"));
        } else {
            fields.add(new StringBuilder(location.getX()));
            fields.add(new StringBuilder(location.getY()));
            fields.add(new StringBuilder(location.getZ()));
            fields.add(new StringBuilder(location.getName()));
        }
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