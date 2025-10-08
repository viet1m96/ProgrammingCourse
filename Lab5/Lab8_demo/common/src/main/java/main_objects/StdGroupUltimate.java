package main_objects;

import enums.Color;
import enums.FormOfEducation;
import enums.Semester;

import java.time.LocalDate;

public class StdGroupUltimate {

    private String search_key;
    private String group_name;
    private LocalDate creation_date;
    private Long student_count;
    private Long expelled_students;
    private FormOfEducation form_of_education;
    private Semester semester;
    private String creator;
    private Integer group_x;
    private Integer group_y;
    private String admin_name;
    private LocalDate birthday;
    private Integer weight;
    private Color eye_color;
    private Long x;
    private Integer y;
    private Integer z;
    private String place;

    public FormOfEducation getForm_of_education() {
        return form_of_education;
    }

    public void setEduForm(FormOfEducation eduForm) {
        this.form_of_education = eduForm;
    }

    public String getSearch_key() {
        return search_key;
    }

    public void setSearch_key(String search_key) {
        this.search_key = search_key;
    }

    public String getGroup_name() {
        return group_name;
    }

    public void setGroup_name(String group_name) {
        this.group_name = group_name;
    }

    public LocalDate getCreation_date() {
        return creation_date;
    }

    public void setCreation_date(LocalDate creation_date) {
        this.creation_date = creation_date;
    }

    public Long getStudent_count() {
        return student_count;
    }

    public void setStudent_count(Long student_count) {
        this.student_count = student_count;
    }

    public Long getExpelled_students() {
        return expelled_students;
    }

    public void setExpelled_students(Long expelled_students) {
        this.expelled_students = expelled_students;
    }

    public Semester getSemester() {
        return semester;
    }

    public void setSemester(Semester semester) {
        this.semester = semester;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public Integer getGroup_x() {
        return group_x;
    }

    public void setGroup_x(Integer group_x) {
        this.group_x = group_x;
    }

    public Integer getGroup_y() {
        return group_y;
    }

    public void setGroup_y(Integer group_y) {
        this.group_y = group_y;
    }

    public String getAdmin_name() {
        return admin_name;
    }

    public void setAdmin_name(String admin_name) {
        this.admin_name = admin_name;
    }

    public LocalDate getBirthday() {
        return birthday;
    }

    public void setBirthday(LocalDate birthday) {
        this.birthday = birthday;
    }

    public Integer getWeight() {
        return weight;
    }

    public void setWeight(Integer weight) {
        this.weight = weight;
    }

    public Color getEye_color() {
        return eye_color;
    }

    public void setEye_color(Color eye_color) {
        this.eye_color = eye_color;
    }

    public Long getX() {
        return x;
    }

    public void setX(Long x) {
        this.x = x;
    }

    public Integer getY() {
        return y;
    }

    public void setY(Integer y) {
        this.y = y;
    }

    public Integer getZ() {
        return z;
    }

    public void setZ(Integer z) {
        this.z = z;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }



    public StdGroupUltimate() {}
    public StdGroupUltimate(StdGroupUltimate other) {
        this.search_key = other.search_key;
        this.group_name = other.group_name;
        this.creation_date = other.creation_date;
        this.student_count = other.student_count;
        this.expelled_students = other.expelled_students;
        this.form_of_education = other.form_of_education;
        this.semester = other.semester;
        this.creator = other.creator;
        this.group_x = other.group_x;
        this.group_y = other.group_y;
        this.admin_name = other.admin_name;
        this.birthday = other.birthday;
        this.weight = other.weight;
        this.eye_color = other.eye_color;
        this.x = other.x;
        this.y = other.y;
        this.z = other.z;
        this.place = other.place;
    }

}
