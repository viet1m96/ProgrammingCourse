package enums;

/**
 * The {@code TypeOfPer} enum represents the different types of personal characteristics or attributes.
 * Each constant defines a specific data type used to represent a person's property.
 */
public enum TypeOfPer {

    /**
     * Represents a person's name or other String-based identifier.
     */
    STRING,

    /**
     * Represents a person's birthday (date of birth).
     */
    BIRTHDAY,

    /**
     * Represents a person's weight (numerical value).
     */
    WEIGHT,

    /**
     * Represents a person's favorite color or another color-related attribute.
     */
    COLOR,

    /**
     * Represents a person's X-coordinate (numerical value).  May represent location.
     */
    X,

    /**
     * Represents a person's Y-coordinate (numerical value).  May represent location.
     */
    Y,

    /**
     * Represents a person's Z-coordinate (numerical value). May represent location or other 3D data.
     */
    Z
}