package main_objects;

import enums.FormOfEducation;
import enums.Semester;
import io_utilities.printers.RainbowPrinter;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

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

    public Semester getSemesterEnum() {
        return semesterEnum;
    }

    public List<StringBuilder> getAllFields() {
        List<StringBuilder> fields = new ArrayList<>();
        fields.add(new StringBuilder(Integer.toString(this.id)));
        fields.add(new StringBuilder(this.name));
        fields.add(new StringBuilder(this.coordinates.getX()));
        fields.add(new StringBuilder(this.coordinates.getY()));
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

}
