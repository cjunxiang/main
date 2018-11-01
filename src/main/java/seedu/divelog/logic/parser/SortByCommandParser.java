package seedu.divelog.logic.parser;

import seedu.divelog.commons.core.Messages;
import seedu.divelog.commons.enums.SortCategory;
import seedu.divelog.logic.commands.SortByCommand;
import seedu.divelog.logic.parser.exceptions.ParseException;



//@@author Cjunx
/**
 * parses string for sort by function. :)
 */
public class SortByCommandParser implements Parser<SortByCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the SortByCommand
     * and returns an SortByCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format.
     */

    public SortByCommand parse(String args) throws ParseException {
        String trimmed = args.trim();
        if (trimmed.isEmpty()) {
            throw new ParseException(
                    String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, SortByCommand.MESSAGE_USAGE));
        }
        switch(trimmed) {
        case "time":
            return new SortByCommand(SortCategory.TIME);
        case "location":
            return new SortByCommand(SortCategory.LOCATION);
        case "duration":
            return new SortByCommand(SortCategory.DURATION);
        default:
            throw new ParseException(String.format(Messages.MESSAGE_WRONG_SORT, SortByCommand.MESSAGE_USAGE));
        }

    }
}
