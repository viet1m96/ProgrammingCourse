package main_objects;

import enums.FormOfEducation;
import enums.Semester;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class StudyGroup implements Comparable<StudyGroup>, Serializable {
    @Serial
    private final static long serialVersionUID = 3L;
    private String search_key;
    private Integer id;
    private String name;
    private Coordinates coordinates;
    private LocalDate creationDate;
    private Long studentsCount;
    private long expelledStudents;
    private FormOfEducation formOfEducation;
    private Semester semesterEnum;
    private Person groupAdmin;
    private String creator;
    public StudyGroup() {
    }

    public StringBuilder getTheGroup() {
        StringBuilder theGroup = new StringBuilder();
        theGroup.append("*Information of the group:");
        theGroup.append("\n");
        theGroup.append("Search Key: ").append(search_key);
        theGroup.append("\n");
        theGroup.append("ID: ").append(this.id);
        theGroup.append("\n");
        theGroup.append("Name: ").append(this.name);
        theGroup.append("\n");
        theGroup.append("Coordinates: ").append(this.coordinates.toString());
        theGroup.append("\n");
        theGroup.append("Creation Date: ").append(this.creationDate.toString());
        theGroup.append("\n");
        theGroup.append("Student Count: ").append(this.studentsCount);
        theGroup.append("\n");
        theGroup.append("Expelled Students: ").append(this.expelledStudents);
        theGroup.append("\n");
        if (formOfEducation == null) {
            theGroup.append("Form of Education: ");
        } else {
            theGroup.append("Form of Education: ").append(this.formOfEducation.toString());
        }
        theGroup.append("\n");
        theGroup.append("Semester: ").append(this.semesterEnum.toString());
        theGroup.append("\n");
        theGroup.append("Group Admin: ").append(this.groupAdmin.toString());
        theGroup.append("\n");
        theGroup.append("Created by: ").append(this.creator);
        return theGroup;
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

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public String getCreator() {
        return creator;
    }

    public void setSearchKey(String search_key) {
        this.search_key = search_key;
    }
    public String getSearchKey() {
        return search_key;
    }

    public List<StringBuilder> getAllFields() {
        List<StringBuilder> fields = new ArrayList<>();
        fields.add(new StringBuilder(Integer.toString(this.id)));
        fields.add(new StringBuilder(this.name));
        fields.add(new StringBuilder(String.valueOf(this.coordinates.getX())));
        fields.add(new StringBuilder(String.valueOf(this.coordinates.getY())));
        fields.add(new StringBuilder(this.creationDate.toString()));
        fields.add(new StringBuilder(String.valueOf(this.studentsCount)));
        fields.add(new StringBuilder(String.valueOf(this.expelledStudents)));
        if (formOfEducation == null) {
            fields.add(new StringBuilder("null"));
        } else {
            fields.add(new StringBuilder(this.formOfEducation.toString()));
        }
        fields.add(new StringBuilder(this.semesterEnum.toString()));
        fields.add(new StringBuilder(this.groupAdmin.getName()));
        fields.add(new StringBuilder(this.groupAdmin.getBirthDay().toString()));
        fields.add(new StringBuilder(this.groupAdmin.getWeight()));
        fields.add(new StringBuilder(this.groupAdmin.getEyeColor().toString()));
        Location location = groupAdmin.getLocation();
        if (location == null) {
            for (int i = 0; i < 4; i++) fields.add(new StringBuilder("null"));
        } else {
            fields.add(new StringBuilder(Long.toString(location.getX())));
            fields.add(new StringBuilder(Integer.toString(location.getY())));
            fields.add(new StringBuilder(Integer.toString(location.getZ())));
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
