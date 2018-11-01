package seedu.divelog.commons.core;

/**
 * Container for user visible messages.
 */
public class Messages {

    public static final String MESSAGE_UNKNOWN_COMMAND = "Unknown command";
    public static final String MESSAGE_INVALID_COMMAND_FORMAT = "Invalid command format! \n%1$s";
    public static final String MESSAGE_INVALID_DIVE_DISPLAYED_INDEX = "The index provided is invalid!\n"
            + "It should be based on the numbers you see in the side panel.";
    public static final String MESSAGE_DIVE_LISTED_OVERVIEW = "%1$d dives listed!";
    public static final String MESSAGE_INVALID_TIME_FORMAT = "Invalid time format! (Not 24Hr format) ";
    public static final String MESSAGE_INVALID_DATE_FORMAT = "Invalid date format! (Not DDMMYYYY) ";
    public static final String MESSAGE_INVALID_TIMEZONE_FORMAT = "Invalid timezone format! (UTC format)";
    public static final String MESSAGE_INTERNAL_ERROR = "An internal error occured";
    public static final String MESSAGE_WRONG_SORT = "The parameter input is invalid! Try time | location | duration";
}
