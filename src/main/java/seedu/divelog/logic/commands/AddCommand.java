package seedu.divelog.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.divelog.logic.CommandHistory;
import seedu.divelog.logic.parser.CliSyntax;
import seedu.divelog.model.Model;
import seedu.divelog.model.dive.DiveSession;

/**
 * Adds a dive to the divelog book.
 */
public class AddCommand extends Command {

    public static final String COMMAND_WORD = "add";
    public static final String MESSAGE_ERROR = "Dive is too deep and too long!!";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a dive to the divelog book. "
            + "Parameters: "
            + CliSyntax.PREFIX_DATE_START + "DATE_START (DDMMYYYY Format) "
            + CliSyntax.PREFIX_TIME_START + "START_TIME (24Hr Format) "
            + CliSyntax.PREFIX_DATE_END + "DATE_END (DDMMYYYY Format) "
            + CliSyntax.PREFIX_TIME_END + "END_TIME (24Hr Format) "
            + CliSyntax.PREFIX_SAFETY_STOP + "SAFETY_STOP_TIME (24Hr Format) \n\t"
            + CliSyntax.PREFIX_DEPTH + "DEPTH "
            + CliSyntax.PREFIX_PRESSURE_GROUP_START + "PRESSURE_GROUP_START "
            + CliSyntax.PREFIX_LOCATION + "LOCATION"
            + CliSyntax.PREFIX_TIMEZONE + "TIMEZONE\n"
            + "Example: " + COMMAND_WORD + " "
            + CliSyntax.PREFIX_DATE_START + "04082018 "
            + CliSyntax.PREFIX_TIME_START + "0700 "
            + CliSyntax.PREFIX_DATE_END + "04082018 "
            + CliSyntax.PREFIX_TIME_END + "0800 "
            + CliSyntax.PREFIX_SAFETY_STOP + "0745 "
            + CliSyntax.PREFIX_DEPTH + "16 "
            + CliSyntax.PREFIX_PRESSURE_GROUP_START + "A "
            + CliSyntax.PREFIX_LOCATION + "Sentosa "
            + CliSyntax.PREFIX_TIMEZONE + "+8";

    public static final String MESSAGE_SUCCESS = "New dive session added!";

    private final DiveSession toAdd;

    /**
     * Creates an AddCommand to add the specified {@code Person}
     */
    public AddCommand(DiveSession dive) {
        requireNonNull(dive);
        toAdd = dive;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) {
        if (model.getPlanningMode()) {
            model.plannerCountPlus();
        }
        requireNonNull(model);
        model.addDiveSession(toAdd);
        model.commitDiveLog();
        return new CommandResult(MESSAGE_SUCCESS);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddCommand // instanceof handles nulls
                && toAdd.equals(((AddCommand) other).toAdd));
    }
}
