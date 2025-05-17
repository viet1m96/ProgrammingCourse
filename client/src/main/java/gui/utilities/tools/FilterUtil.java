package gui.utilities.tools;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.stage.Stage;
import main_objects.StdGroupUltimate;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

public class FilterUtil {

    private final List<Predicate<StdGroupUltimate>> filters = new ArrayList<>();

    public void addFilter(String fieldName, String filterValue) {
        Predicate<StdGroupUltimate> newFilter = createFilter(fieldName, filterValue);
        filters.add(newFilter);
    }

    private Predicate<StdGroupUltimate> createFilter(String fieldName, String filterValue) {
        return new FieldPredicate(fieldName, filterValue);
    }

    public void applyFilters(ObservableList<StdGroupUltimate> tempList, ObservableList<StdGroupUltimate> sourceList) {
        tempList.forEach(item -> {
            boolean passAllFilters = true;
            for (Predicate<StdGroupUltimate> filter : filters) {
                if (!filter.test(item)) {
                    passAllFilters = false;
                    break;
                }
            }
            if (passAllFilters) {
                sourceList.add(item);
            }
        });
    }

    public void applyCurFilters(ObservableList<StdGroupUltimate> groups) {
        if (filters.isEmpty()) return;
        ObservableList<StdGroupUltimate> tempList = FXCollections.observableArrayList();
        for (StdGroupUltimate group : groups) {
            StdGroupUltimate temp = new StdGroupUltimate(group);
            tempList.add(temp);
        }
        groups.clear();
        applyFilters(tempList, groups);
    }

    public void clearFilters() {
        filters.clear();
    }

    private record FieldPredicate(String fieldName, String filterValue) implements Predicate<StdGroupUltimate> {

        @Override
        public boolean test(StdGroupUltimate group) {
            try {
                Method method = StdGroupUltimate.class.getMethod("get" + capitalize(fieldName));
                method.setAccessible(true);
                Object value = method.invoke(group);
                if (value == null) {
                    return filterValue == null || filterValue.isEmpty();
                }
                return value.toString().contains(filterValue);
            } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
                AlertUtil.showErrorAlert("Filter Failed", e.getMessage(), (Stage)null );
                return false;
            }
        }

        private String capitalize(String input) {
            if (input == null || input.isEmpty()) {
                return input;
            }
            return input.substring(0, 1).toUpperCase() + input.substring(1);
        }
    }

}
