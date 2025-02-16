package io_utilities.working_with_csv_file;

import enums.TypeOfGrp;
import enums.TypeOfPer;
import exceptions.user_exceptions.WrongUploadingDataException;
import io_utilities.working_with_input.ObjInputChecker;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DataFromFileProcessor {

    public DataFromFileProcessor() {
    }

    public List<String> checkGroupInfo(String str) throws WrongUploadingDataException {
        List<String> tokens = Arrays.asList(str.split(","));
        if (tokens.size() != 17) {
            throw new WrongUploadingDataException();
        }
        List<Boolean> boolList = new ArrayList<>();
        boolList.add(ObjInputChecker.checkInputForGroupFile(tokens.get(0), TypeOfGrp.ID));
        boolList.add(ObjInputChecker.checkInputForGroupFile(tokens.get(1), TypeOfGrp.STRING));
        boolList.add(ObjInputChecker.checkInputForGroupFile(tokens.get(2), TypeOfGrp.X));
        boolList.add(ObjInputChecker.checkInputForGroupFile(tokens.get(3), TypeOfGrp.Y));
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        try {
            LocalDate.parse(tokens.get(4), formatter);
        } catch (DateTimeParseException e) {
            e.printStackTrace();
            throw new WrongUploadingDataException();
        }
        boolList.add(ObjInputChecker.checkInputForGroupFile(tokens.get(5), TypeOfGrp.COUNT));
        boolList.add(ObjInputChecker.checkInputForGroupFile(tokens.get(6), TypeOfGrp.COUNT));
        boolList.add(ObjInputChecker.checkInputForGroupFile(tokens.get(7), TypeOfGrp.EDU));
        boolList.add(ObjInputChecker.checkInputForGroupFile(tokens.get(8), TypeOfGrp.SEMESTER));

        for (Boolean bool : boolList) {
            if (!bool) {
                throw new WrongUploadingDataException();
            }
        }
        return tokens;
    }

    public List<String> checkPerInfo(String str) throws WrongUploadingDataException {
        List<String> tokens = Arrays.asList(str.split(","));
        List<Boolean> boolList = new ArrayList<>();
        boolList.add(ObjInputChecker.checkInputForPersonFile(tokens.get(9), TypeOfPer.NAME));
        boolList.add(ObjInputChecker.checkInputForPersonFile(tokens.get(10), TypeOfPer.BIRTHDAY));
        boolList.add(ObjInputChecker.checkInputForPersonFile(tokens.get(11), TypeOfPer.WEIGHT));
        boolList.add(ObjInputChecker.checkInputForPersonFile(tokens.get(12), TypeOfPer.COLOR));
        boolList.add(ObjInputChecker.checkInputForPersonFile(tokens.get(13), TypeOfPer.X));
        boolList.add(ObjInputChecker.checkInputForPersonFile(tokens.get(14), TypeOfPer.Y));
        boolList.add(ObjInputChecker.checkInputForPersonFile(tokens.get(15), TypeOfPer.Z));
        boolList.add(ObjInputChecker.checkInputForPersonFile(tokens.get(16), TypeOfPer.LOCATION));
        for (boolean bool : boolList) {
            if (!bool) {
                throw new WrongUploadingDataException();
            }
        }
        List<String> res = new ArrayList<>();
        for (int i = 9; i < tokens.size(); i++) {
            res.add(tokens.get(i));
        }
        return res;
    }
}