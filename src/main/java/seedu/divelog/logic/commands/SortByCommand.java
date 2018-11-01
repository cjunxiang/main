package seedu.divelog.logic.commands;

import seedu.divelog.commons.core.ApplicationState;
import seedu.divelog.commons.enums.SortCategory;
import seedu.divelog.logic.CommandHistory;
import seedu.divelog.model.Model;

/**
 * CLI command to change how dive sessions are sorted
 */
public class SortByCommand extends Command {
    public static final String COMMAND_WORD = "sortby";

    public static final String MESSAGE_SUCCESS = "You have successfully changed the order of your Dive Sessions";
    public static final String MESSAGE_USAGE = COMMAND_WORD + " time|location|duration : "
            + "Sets the order of which the data is sorted.\n "
            + "Example:\t" + COMMAND_WORD + " time: sorts the data according to time.";

    public final SortCategory sortCategory;

    public SortByCommand(SortCategory sortCategory) {
        this.sortCategory = sortCategory;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) {
        ApplicationState.getInstance().setSortByCategory(sortCategory);
        if (model.getPlanningMode()) {
            model.plannerCountPlus();
        }
        model.updateFilteredDiveList(Model.PREDICATE_SHOW_ALL_DIVES);
        model.commitDiveLog();
        return new CommandResult(MESSAGE_SUCCESS);
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof SortByCommand)) {
            return false;
        }
        return ((SortByCommand) obj).sortCategory == sortCategory;
    }
}
