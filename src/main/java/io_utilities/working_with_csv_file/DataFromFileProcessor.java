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

/**
 * The {@code DataFromFileProcessor} class is responsible for validating and processing data read from a CSV file.
 * It checks the validity of the data based on predefined types and formats, throwing a {@link WrongUploadingDataException}
 * if any validation fails.
 */
public class DataFromFileProcessor {

    /**
     * Constructs a new {@code DataFromFileProcessor}.
     */
    public DataFromFileProcessor() {
    }

    /**
     * Checks the validity of the study group information extracted from a CSV string.
     * This method validates individual fields against expected types and formats using {@link ObjInputChecker}.
     *
     * @param str The CSV string containing study group information.
     * @return A list of strings representing the validated study group information.
     * @throws WrongUploadingDataException If the input string does not contain the expected number of tokens,
     *                                     or if any of the individual fields fail validation.
     */
    public List<String> checkGroupInfo(String str) throws WrongUploadingDataException {
        List<String> tokens = Arrays.asList(str.split(","));
        if (tokens.size() != 17) {
            throw new WrongUploadingDataException();
        }
        List<Boolean> boolList = new ArrayList<>();
        boolList.add(ObjInputChecker.checkInputForGroup(tokens.get(0), TypeOfGrp.ID));
        boolList.add(ObjInputChecker.checkInputForGroup(tokens.get(1), TypeOfGrp.STRING));
        boolList.add(ObjInputChecker.checkInputForGroup(tokens.get(2), TypeOfGrp.X));
        boolList.add(ObjInputChecker.checkInputForGroup(tokens.get(3), TypeOfGrp.Y));
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        try {
            LocalDate.parse(tokens.get(4), formatter);
        } catch (DateTimeParseException e) {
            e.printStackTrace(); // Consider logging this instead of printing to the console
            throw new WrongUploadingDataException();
        }
        boolList.add(ObjInputChecker.checkInputForGroup(tokens.get(5), TypeOfGrp.COUNT));
        boolList.add(ObjInputChecker.checkInputForGroup(tokens.get(6), TypeOfGrp.COUNT));
        boolList.add(ObjInputChecker.checkInputForGroup(tokens.get(7), TypeOfGrp.EDU));
        boolList.add(ObjInputChecker.checkInputForGroup(tokens.get(8), TypeOfGrp.SEMESTER));

        for (Boolean bool : boolList) {
            if (!bool) {
                throw new WrongUploadingDataException();
            }
        }
        return tokens;
    }

    /**
     * Checks the validity of the person (administrator) information extracted from a CSV string.
     * This method validates individual fields against expected types and formats using {@link ObjInputChecker}.
     *
     * @param str The CSV string containing person information.
     * @return A list of strings representing the validated person information.
     * @throws WrongUploadingDataException If any of the individual fields fail validation.
     */
    public List<String> checkPerInfo(String str) throws WrongUploadingDataException {
        List<String> tokens = Arrays.asList(str.split(","));
        List<Boolean> boolList = new ArrayList<>();
        boolList.add(ObjInputChecker.checkInputForPerson(tokens.get(9), TypeOfPer.STRING));
        boolList.add(ObjInputChecker.checkInputForPerson(tokens.get(10), TypeOfPer.BIRTHDAY));
        boolList.add(ObjInputChecker.checkInputForPerson(tokens.get(11), TypeOfPer.WEIGHT));
        boolList.add(ObjInputChecker.checkInputForPerson(tokens.get(12), TypeOfPer.COLOR));
        boolList.add(ObjInputChecker.checkInputForPerson(tokens.get(13), TypeOfPer.X));
        boolList.add(ObjInputChecker.checkInputForPerson(tokens.get(14), TypeOfPer.Y));
        boolList.add(ObjInputChecker.checkInputForPerson(tokens.get(15), TypeOfPer.Z));
        boolList.add(ObjInputChecker.checkInputForPerson(tokens.get(16), TypeOfPer.STRING));
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