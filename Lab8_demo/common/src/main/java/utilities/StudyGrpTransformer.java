package utilities;

import main_objects.*;

import java.time.LocalDate;

public class StudyGrpTransformer {

    public static StdGroupUltimate transformToUltimate(StudyGroup group) {
        StdGroupUltimate groupUltimate = new StdGroupUltimate();
        groupUltimate.setSearch_key(group.getSearchKey());
        groupUltimate.setGroup_name(group.getName());
        groupUltimate.setCreation_date(group.getCreationDate());
        groupUltimate.setStudent_count(group.getStudentsCount());
        groupUltimate.setExpelled_students(group.getExpelledStudents());
        groupUltimate.setEduForm(group.getFormOfEducation());
        groupUltimate.setSemester(group.getSemesterEnum());
        groupUltimate.setCreator(group.getCreator());
        groupUltimate.setGroup_x(group.getCoordinates().getX());
        groupUltimate.setGroup_y(group.getCoordinates().getY());
        groupUltimate.setAdmin_name(group.getGroupAdmin().getName());
        groupUltimate.setBirthday(group.getGroupAdmin().getBirthDay());
        groupUltimate.setWeight(group.getGroupAdmin().getWeight());
        groupUltimate.setEye_color(group.getGroupAdmin().getEyeColor());
        if(group.getGroupAdmin().getLocation() != null) {
            groupUltimate.setX(group.getGroupAdmin().getLocation().getX());
            groupUltimate.setY(group.getGroupAdmin().getLocation().getY());
            groupUltimate.setZ(group.getGroupAdmin().getLocation().getZ());
            groupUltimate.setPlace(group.getGroupAdmin().getLocation().getName());
        }
        return groupUltimate;
    }

    public static StudyGroup transformToNormal(StdGroupUltimate groupUltimate) {
        StudyGroup group = new StudyGroup();
        group.setSearchKey(groupUltimate.getSearch_key());
        group.setName(groupUltimate.getGroup_name());
        group.setCreationDate(groupUltimate.getCreation_date());
        group.setStudentsCount(groupUltimate.getStudent_count());
        group.setExpelledStudents(groupUltimate.getExpelled_students());
        group.setFormOfEducation(groupUltimate.getForm_of_education());
        group.setSemesterEnum(groupUltimate.getSemester());
        group.setCreator(groupUltimate.getCreator());
        Coordinates coordinates = new Coordinates(groupUltimate.getGroup_x(), groupUltimate.getGroup_y());
        group.setCoordinates(coordinates);
        Person admin = getAdmin(groupUltimate);
        group.setGroupAdmin(admin);
        return group;
    }

    public static Person getAdmin(StdGroupUltimate groupUltimate) {
        Person admin = new Person();
        admin.setName(groupUltimate.getAdmin_name());
        admin.setBirthDay(groupUltimate.getBirthday());
        admin.setWeight(groupUltimate.getWeight());
        admin.setEyeColor(groupUltimate.getEye_color());
        if(groupUltimate.getX() == null && groupUltimate.getY() == null && groupUltimate.getZ() == null && groupUltimate.getPlace() == null) {
            admin.setLocation(null);
        } else {
            Location location = new Location();
            location.setX(groupUltimate.getX());
            location.setY(groupUltimate.getY());
            location.setZ(groupUltimate.getZ());
            location.setName(groupUltimate.getPlace());
            admin.setLocation(location);
        }
        return admin;
    }

}
