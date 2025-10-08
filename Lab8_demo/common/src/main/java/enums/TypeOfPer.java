package enums;

/**
 * The {@code TypeOfPer} enum represents the different types of personal characteristics or attributes.
 * Each constant defines a specific data type used to represent a person's property, primarily for input validation
 * and data handling.
 */
public enum TypeOfPer {

    /**
     * Represents a person's name or other String-based identifier.  Used for textual input like names and titles.
     */
    NAME,

    /**
     * Represents a person's location name or description, typically a String.
     */
    LOCATION,

    /**
     * Represents a person's birthday (date of birth).  Expected to be a date or date-time object.
     */
    BIRTHDAY,

    /**
     * Represents a person's weight (numerical value, typically a Double or Integer).
     */
    WEIGHT,

    /**
     * Represents a person's favorite color or another color-related attribute.  Often represented as a String or Color object.
     */
    COLOR,

    /**
     * Represents a person's X-coordinate (numerical value, typically a Double or Integer).  May represent a location in a 2D or 3D space.
     */
    X,

    /**
     * Represents a person's Y-coordinate (numerical value, typically a Double or Integer).  May represent a location in a 2D or 3D space.
     */
    Y,

    /**
     * Represents a person's Z-coordinate (numerical value, typically a Double or Integer). May represent a location in 3D space or another dimension.
     */
    Z
}
