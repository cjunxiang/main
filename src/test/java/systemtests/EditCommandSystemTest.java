package systemtests;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;
import static seedu.divelog.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.divelog.model.Model.PREDICATE_SHOW_ALL_DIVES;

import org.junit.Test;

import seedu.divelog.commons.core.Messages;
import seedu.divelog.commons.core.index.Index;
import seedu.divelog.logic.commands.EditCommand;
import seedu.divelog.model.Model;
import seedu.divelog.model.dive.DiveSession;

public class EditCommandSystemTest extends DiveLogSystemTest {

    private static final String MESSAGE_INVALID_FORMAT =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE);
    @Test
    public void edit() {
        Model model = getModel();

        /* ----------------- Performing edit operation while an unfiltered list is being shown ---------------------- */

        /* Case: edit all fields, command with leading spaces, trailing spaces and multiple spaces between each field
         * -> edited
         */


        /* Case: undo editing the last person in the list -> last person restored */

        /* Case: redo editing the last person in the list -> last person edited again */


        /* Case: edit a person with new values same as existing values -> edited */


        /* Case: edit a person with new values same as another person's values but with different name -> edited */


        /* Case: edit a person with new values same as another person's values but with different phone and email
         * -> edited
         */

        /* Case: clear tags -> cleared */


        /* ------------------ Performing edit operation while a filtered list is being shown ------------------------ */

        /* Case: filtered person list, edit index within bounds of divelog book and person list -> edited */


        /* Case: filtered person list, edit index within bounds of divelog book but out of bounds of person list
         * -> rejected
         */


        /* --------------------- Performing edit operation while a person card is selected -------------------------- */

        /* Case: selects first card in the person list, edit a person -> edited, card selection remains unchanged but
         * browser url changes
         */

        // this can be misleading: card selection actually remains unchanged but the
        // browser's url is updated to reflect the new person's name


        /* --------------------------------- Performing invalid edit operation -------------------------------------- */


        /* Check that no index is */
        /* Case: invalid index (0) -> rejected */
        /*String command = "0 ds/081219 ts/ de/DATE_END te/091219 ss/SAFETY_STOP_TIME" +
                " d/DEPTH pg/PG_AT_START pge/PG_AT_END l/LOCATION"*/
        assertCommandFailure("edit 0 ts/1210", MESSAGE_INVALID_FORMAT);

        /* Case: invalid index (-1) -> rejected */
        assertCommandFailure("edit -1 ts/1210", MESSAGE_INVALID_FORMAT);

        /* Case: invalid index (size + 1) -> rejected */
        int index = model.getFilteredDiveList().size() + 1;
        assertCommandFailure("edit " + index + " ts/1210", Messages.MESSAGE_INVALID_DIVE_DISPLAYED_INDEX);

        /* Case: missing index -> rejected */
        assertCommandFailure("edit ts/1210", MESSAGE_INVALID_FORMAT);

        /* Case: missing all fields -> rejected */
        assertCommandFailure("edit", MESSAGE_INVALID_FORMAT);


    }

    /**
     * Performs the same verification as {@code assertCommandSuccess(String, Index, Person, Index)} except that
     * the browser url and selected card remain unchanged.
     * @param toEdit the index of the current model's filtered list
     * @see EditCommandSystemTest#assertCommandSuccess(String, Index, DiveSession, Index)
     */
    private void assertCommandSuccess(String command, Index toEdit, DiveSession editedDive) {
        assertCommandSuccess(command, toEdit, editedDive, null);
    }

    /**
     * Performs the same verification as {@code assertCommandSuccess(String, Model, String, Index)} and in addition,<br>
     * 1. Asserts that result display box displays the success message of executing {@code EditCommand}.<br>
     * 2. Asserts that the model related components are updated to reflect the person at index {@code toEdit} being
     * updated to values specified {@code editedPerson}.<br>
     * @param toEdit the index of the current model's filtered list.
     * @param editedDive
     * @see EditCommandSystemTest#assertCommandSuccess(String, Model, String, Index)
     */
    private void assertCommandSuccess(String command, Index toEdit, DiveSession editedDive,
                                      Index expectedSelectedCardIndex) {
        Model expectedModel = getModel();
        try {
            expectedModel.updateDiveSession(expectedModel.getFilteredDiveList().get(toEdit.getZeroBased()), editedDive);
        } catch (seedu.divelog.model.dive.exceptions.DiveNotFoundException e) {
            e.printStackTrace();
        }
        expectedModel.updateFilteredDiveList(PREDICATE_SHOW_ALL_DIVES);

        assertCommandSuccess(command, expectedModel,
                String.format(EditCommand.MESSAGE_EDIT_DIVE_SUCCESS, editedDive), expectedSelectedCardIndex);
    }

    /**
     * Performs the same verification as {@code assertCommandSuccess(String, Model, String, Index)} except that the
     * browser url and selected card remain unchanged.
     * @see EditCommandSystemTest#assertCommandSuccess(String, Model, String, Index)
     */
    private void assertCommandSuccess(String command, Model expectedModel, String expectedResultMessage) {
        assertCommandSuccess(command, expectedModel, expectedResultMessage, null);
    }

    /**
     * Executes {@code command} and in addition,<br>
     * 1. Asserts that the command box displays an empty string.<br>
     * 2. Asserts that the result display box displays {@code expectedResultMessage}.<br>
     * 3. Asserts that the browser url and selected card update accordingly depending on the card at
     * {@code expectedSelectedCardIndex}.<br>
     * 4. Asserts that the status bar's sync status changes.<br>
     * 5. Asserts that the command box has the default style class.<br>
     * Verifications 1 and 2 are performed by
     * {@code DiveLogSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
     * @see DiveLogSystemTest#assertApplicationDisplaysExpected(String, String, Model)
     * @see DiveLogSystemTest#assertSelectedCardChanged(Index)
     */
    private void assertCommandSuccess(String command, Model expectedModel, String expectedResultMessage,
            Index expectedSelectedCardIndex) {
        executeCommand(command);
        expectedModel.updateFilteredDiveList(PREDICATE_SHOW_ALL_DIVES);
        assertApplicationDisplaysExpected("", expectedResultMessage, expectedModel);
        assertCommandBoxShowsDefaultStyle();
        if (expectedSelectedCardIndex != null) {
            assertSelectedCardChanged(expectedSelectedCardIndex);
        } else {
            assertSelectedCardUnchanged();
        }
        assertStatusBarUnchangedExceptSyncStatus();
    }

    /**
     * Executes {@code command} and in addition,<br>
     * 1. Asserts that the command box displays {@code command}.<br>
     * 2. Asserts that result display box displays {@code expectedResultMessage}.<br>
     * 3. Asserts that the browser url, selected card and status bar remain unchanged.<br>
     * 4. Asserts that the command box has the error style.<br>
     * Verifications 1 and 2 are performed by
     * {@code DiveLogSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
     * @see DiveLogSystemTest#assertApplicationDisplaysExpected(String, String, Model)
     */
    private void assertCommandFailure(String command, String expectedResultMessage) {
        Model expectedModel = getModel();
        executeCommand(command);
        assertApplicationDisplaysExpected(command, expectedResultMessage, expectedModel);
        assertSelectedCardUnchanged();
        assertCommandBoxShowsErrorStyle();
        assertStatusBarUnchanged();
    }
}
