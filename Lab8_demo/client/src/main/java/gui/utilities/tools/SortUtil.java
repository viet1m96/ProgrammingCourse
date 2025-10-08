package gui.utilities.tools;

import enums.SortType;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;
import main_objects.StdGroupUltimate;

import java.lang.reflect.Field;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class SortUtil {
    private final Map<TableColumn<StdGroupUltimate, ?>, SortType> activeSorts = new HashMap<>();
    private TableColumn<StdGroupUltimate, ?> currSortColumn;
    private SortType currSortType;

    public SortUtil() {}
    private <T>Comparator<StdGroupUltimate> createComparator(TableColumn<StdGroupUltimate, T> col, SortType sortType) {
        if(sortType == SortType.NONE) {
            currSortColumn = null;
            currSortType = SortType.NONE;
            return null;
        }
        String propertyName = (String)col.getUserData();

        return (group1, group2) -> {
            try {
                Field field = StdGroupUltimate.class.getDeclaredField(propertyName);
                field.setAccessible(true);
                T value1 = (T)field.get(group1);
                T value2 = (T)field.get(group2);
                if(value1 == null && value2 == null)  {
                    return 0;
                } else if (value1 == null) {
                    return -1;
                } else if(value2 == null) {
                    return 1;
                }

                if(value1 instanceof Comparable && value2 instanceof Comparable) {
                    int result = ((Comparable)value1).compareTo((Comparable)value2);
                    return sortType == SortType.ASC ? result : -result;
                } else {
                    return 0;
                }
            } catch (NoSuchFieldException | IllegalAccessException e) {
                AlertUtil.showErrorAlert("Sort Failed", e.getMessage(), (Stage)null);
                return 0;
            }
        };
    }

    public void handleSort(ObservableList<StdGroupUltimate> groups,TableView<StdGroupUltimate> tableView, TableColumn<StdGroupUltimate, ?> sortColumn) {
        SortType currentSortType = activeSorts.getOrDefault(sortColumn, SortType.NONE);
        SortType newSortType = currentSortType.next();
        activeSorts.put(sortColumn, newSortType);

        Comparator<StdGroupUltimate> comparator = createComparator(sortColumn, newSortType);
        if(comparator == null) {
            tableView.setItems(groups);
            return;
        }

        List<StdGroupUltimate> sortedList = groups.stream().sorted(comparator).toList();

        groups.clear();
        groups.addAll(sortedList);
        currSortColumn = sortColumn;
        currSortType = newSortType;
        tableView.setItems(groups);
    }

    public void applyCurSort(ObservableList<StdGroupUltimate> groups, TableView<StdGroupUltimate> tableView) {
        if(currSortColumn != null && currSortType != null) {
            Comparator<StdGroupUltimate> comparator = createComparator(currSortColumn, currSortType);
            List<StdGroupUltimate> sortedList = groups.stream().sorted(comparator).toList();
            groups.clear();
            groups.addAll(sortedList);
            tableView.setItems(groups);
        }
    }
}
