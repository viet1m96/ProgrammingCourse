package enums;

public enum SortType {
    ASC, DESC, NONE;

    public SortType next() {
        return switch (this) {
            case NONE -> ASC;
            case ASC -> DESC;
            case DESC -> NONE;
        };
    }
}
