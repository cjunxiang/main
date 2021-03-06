package seedu.divelog.logic.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;
import static seedu.divelog.logic.commands.CommandTestUtil.DESC_DAY_BALI;
import static seedu.divelog.logic.commands.CommandTestUtil.DESC_DAY_TIOMAN;
import static seedu.divelog.logic.commands.CommandTestUtil.VALID_LOCATION_BALI;
import static seedu.divelog.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.divelog.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.divelog.logic.commands.CommandTestUtil.showDiveAtIndex;
import static seedu.divelog.testutil.TypicalIndexes.INDEX_FIRST_DIVE;
import static seedu.divelog.testutil.TypicalIndexes.INDEX_SECOND_DIVE;
import static seedu.divelog.testutil.TypicalDiveSessions.getTypicalDiveLog;

import org.junit.Test;

import seedu.divelog.commons.core.Messages;
import seedu.divelog.commons.core.index.Index;
import seedu.divelog.logic.CommandHistory;
import seedu.divelog.logic.commands.EditCommand.EditDiveDescriptor;
import seedu.divelog.model.DiveLog;
import seedu.divelog.model.Model;
import seedu.divelog.model.ModelManager;
import seedu.divelog.model.UserPrefs;
import seedu.divelog.model.dive.DiveSession;
import seedu.divelog.testutil.EditDiveDescriptorBuilder;
import seedu.divelog.testutil.DiveSessionBuilder;

/**
 * Contains integration tests (interaction with the Model, UndoCommand and RedoCommand) and unit tests for EditCommand.
 */
public class EditCommandTest {

    private Model model = new ModelManager(getTypicalDiveLog(), new UserPrefs());
    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void execute_allFieldsSpecifiedUnfilteredList_success() {
        DiveSession editedDive = new DiveSessionBuilder().build();
        EditDiveDescriptor descriptor = new EditDiveDescriptorBuilder(editedDive).build();
        EditCommand editCommand = new EditCommand(INDEX_FIRST_DIVE, descriptor);

        String expectedMessage = String.format(EditCommand.MESSAGE_EDIT_DIVE_SUCCESS, editedDive);

        Model expectedModel = new ModelManager(new DiveLog(model.getDiveLog()), new UserPrefs());
        try {
            expectedModel.updateDiveSession(model.getFilteredDiveList().get(0), editedDive);
        } catch (seedu.divelog.model.dive.exceptions.DiveNotFoundException e) {
            e.printStackTrace();
        }
        expectedModel.commitDiveLog();

        assertCommandSuccess(editCommand, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void execute_someFieldsSpecifiedUnfilteredList_success() {
        Index indexLastDive = Index.fromOneBased(model.getFilteredDiveList().size());
        DiveSession lastDive = model.getFilteredDiveList().get(indexLastDive.getZeroBased());

        DiveSessionBuilder diveInList = new DiveSessionBuilder(lastDive);
        DiveSession editedDive = diveInList.withLocation(VALID_LOCATION_BALI).build();

        EditDiveDescriptor descriptor = new EditDiveDescriptorBuilder().withLocation(VALID_LOCATION_BALI).build();
        EditCommand editCommand = new EditCommand(indexLastDive, descriptor);

        String expectedMessage = String.format(EditCommand.MESSAGE_EDIT_DIVE_SUCCESS, editedDive);

        Model expectedModel = new ModelManager(new DiveLog(model.getDiveLog()), new UserPrefs());
        try {
            expectedModel.updateDiveSession(lastDive, editedDive);
        } catch (seedu.divelog.model.dive.exceptions.DiveNotFoundException e) {
            e.printStackTrace();
        }
        expectedModel.commitDiveLog();

        assertCommandSuccess(editCommand, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void execute_noFieldSpecifiedUnfilteredList_success() {
        EditCommand editCommand = new EditCommand(INDEX_FIRST_DIVE, new EditDiveDescriptor());
        DiveSession editedDive = model.getFilteredDiveList().get(INDEX_FIRST_DIVE.getZeroBased());

        String expectedMessage = String.format(EditCommand.MESSAGE_EDIT_DIVE_SUCCESS, editedDive);

        Model expectedModel = new ModelManager(new DiveLog(model.getDiveLog()), new UserPrefs());
        expectedModel.commitDiveLog();

        assertCommandSuccess(editCommand, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void execute_filteredList_success() {
        showDiveAtIndex(model, INDEX_FIRST_DIVE);

        DiveSession personInFilteredList = model.getFilteredDiveList().get(INDEX_FIRST_DIVE.getZeroBased());
        DiveSession editedDive = new DiveSessionBuilder(personInFilteredList).withLocation(VALID_LOCATION_BALI).build();
        EditCommand editCommand = new EditCommand(INDEX_FIRST_DIVE,
                new EditDiveDescriptorBuilder().withLocation(VALID_LOCATION_BALI).build());

        String expectedMessage = String.format(EditCommand.MESSAGE_EDIT_DIVE_SUCCESS, editedDive);

        Model expectedModel = new ModelManager(new DiveLog(model.getDiveLog()), new UserPrefs());
        try {
            expectedModel.updateDiveSession(model.getFilteredDiveList().get(0), editedDive);
        } catch (seedu.divelog.model.dive.exceptions.DiveNotFoundException e) {
            e.printStackTrace();
        }
        expectedModel.commitDiveLog();

        assertCommandSuccess(editCommand, model, commandHistory, expectedMessage, expectedModel);
    }


    @Test
    public void execute_invalidDiveIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredDiveList().size() + 1);
        EditDiveDescriptor descriptor = new EditDiveDescriptorBuilder().withLocation(VALID_LOCATION_BALI).build();
        EditCommand editCommand = new EditCommand(outOfBoundIndex, descriptor);

        assertCommandFailure(editCommand, model, commandHistory, Messages.MESSAGE_INVALID_DIVE_DISPLAYED_INDEX);
    }

    /**
     * Edit filtered list where index is larger than size of filtered list,
     * but smaller than size of divelog book
     */
    @Test
    public void execute_invalidPersonIndexFilteredList_failure() {
        showDiveAtIndex(model, INDEX_FIRST_DIVE);
        Index outOfBoundIndex = INDEX_SECOND_DIVE;
        // ensures that outOfBoundIndex is still in bounds of divelog book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getDiveLog().getDiveList().size());

        EditCommand editCommand = new EditCommand(outOfBoundIndex,
                new EditDiveDescriptorBuilder().withLocation(VALID_LOCATION_BALI).build());

        assertCommandFailure(editCommand, model, commandHistory, Messages.MESSAGE_INVALID_DIVE_DISPLAYED_INDEX);
    }

    @Test
    public void executeUndoRedo_validIndexUnfilteredList_success() throws Exception {
        DiveSession editedDive = new DiveSessionBuilder().build();
        DiveSession diveToEdit = model.getFilteredDiveList().get(INDEX_FIRST_DIVE.getZeroBased());
        EditDiveDescriptor descriptor = new EditDiveDescriptorBuilder(editedDive).build();
        EditCommand editCommand = new EditCommand(INDEX_FIRST_DIVE, descriptor);
        Model expectedModel = new ModelManager(new DiveLog(model.getDiveLog()), new UserPrefs());
        expectedModel.updateDiveSession(diveToEdit, editedDive);
        expectedModel.commitDiveLog();

        // edit -> first person edited
        editCommand.execute(model, commandHistory);

        // undo -> reverts addressbook back to previous state and filtered person list to show all persons
        expectedModel.undoDiveLog();
        assertCommandSuccess(new UndoCommand(), model, commandHistory, UndoCommand.MESSAGE_SUCCESS, expectedModel);

        // redo -> same first person edited again
        expectedModel.redoDiveLog();
        assertCommandSuccess(new RedoCommand(), model, commandHistory, RedoCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void executeUndoRedo_invalidIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredDiveList().size() + 1);
        EditDiveDescriptor descriptor = new EditDiveDescriptorBuilder().withLocation(VALID_LOCATION_BALI).build();
        EditCommand editCommand = new EditCommand(outOfBoundIndex, descriptor);

        // execution failed -> divelog book state not added into model
        assertCommandFailure(editCommand, model, commandHistory, Messages.MESSAGE_INVALID_DIVE_DISPLAYED_INDEX);

        // single divelog book state in model -> undoCommand and redoCommand fail
        assertCommandFailure(new UndoCommand(), model, commandHistory, UndoCommand.MESSAGE_FAILURE);
        assertCommandFailure(new RedoCommand(), model, commandHistory, RedoCommand.MESSAGE_FAILURE);
    }

    /**
     * 1. Edits a {@code Dive} from a filtered list.
     * 2. Undo the edit.
     * 3. The unfiltered list should be shown now. Verify that the index of the previously edited dive in the
     * unfiltered list is different from the index at the filtered list.
     * 4. Redo the edit. This ensures {@code RedoCommand} edits the person object regardless of indexing.
     * TODO: This test is problematic! FIX IT!
     */
    @Test
    public void executeUndoRedo_validIndexFilteredList_sameDiveEdited() throws Exception {
       /* DiveSession editedDive = new DiveSessionBuilder().build();
        EditDiveDescriptor descriptor = new EditDiveDescriptorBuilder(editedDive).build();
        EditCommand editCommand = new EditCommand(INDEX_FIRST_DIVE, descriptor);
        Model expectedModel = new ModelManager(new DiveLog(model.getDiveLog()), new UserPrefs());

        showDiveAtIndex(model, INDEX_SECOND_DIVE);
        DiveSession diveToEdit = model.getFilteredDiveList().get(INDEX_FIRST_DIVE.getZeroBased());
        expectedModel.updateDiveSession(diveToEdit, editedDive);
        expectedModel.commitDiveLog();

        // edit -> edits second person in unfiltered person list / first person in filtered person list
        editCommand.execute(model, commandHistory);

        // undo -> reverts divelog back to previous state and filtered person list to show all persons
        expectedModel.undoDiveLog();
        assertCommandSuccess(new UndoCommand(), model, commandHistory, UndoCommand.MESSAGE_SUCCESS, expectedModel);

        assertNotEquals(model.getFilteredDiveList().get(INDEX_FIRST_DIVE.getZeroBased()), diveToEdit);
        // redo -> edits same second person in unfiltered person list
        expectedModel.redoDiveLog();
        assertCommandSuccess(new RedoCommand(), model, commandHistory, RedoCommand.MESSAGE_SUCCESS, expectedModel);*/
    }

    @Test
    public void equals() {
        final EditCommand standardCommand = new EditCommand(INDEX_FIRST_DIVE, DESC_DAY_BALI);

        // same values -> returns true
        EditDiveDescriptor copyDescriptor = new EditDiveDescriptor(DESC_DAY_BALI);
        EditCommand commandWithSameValues = new EditCommand(INDEX_FIRST_DIVE, copyDescriptor);
        assertTrue(standardCommand.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different types -> returns false
        assertFalse(standardCommand.equals(new ClearCommand()));

        // different index -> returns false
        assertFalse(standardCommand.equals(new EditCommand(INDEX_SECOND_DIVE, DESC_DAY_BALI)));

        // different descriptor -> returns false
        assertFalse(standardCommand.equals(new EditCommand(INDEX_FIRST_DIVE, DESC_DAY_TIOMAN)));
    }
}
