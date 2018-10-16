package seedu.divelog.model;

import java.util.function.Predicate;

import javafx.collections.ObservableList;
import seedu.divelog.model.dive.DiveSession;
import seedu.divelog.model.dive.exceptions.DiveNotFoundException;


/**
 * The API of the Model component.
 */
public interface Model {
    /** {@code Predicate} that always evaluate to true */
    Predicate<DiveSession> PREDICATE_SHOW_ALL_PERSONS = unused -> true;

    /** Clears existing backing model and replaces with the provided new data. */
    void resetData(ReadOnlyDiveLog newData);

    /** Returns the DiveLog */
    ReadOnlyDiveLog getDiveLog();

    /**
     * Deletes the given dive.
     * The person must exist in the divelog book.
     * @param target
     */
    void deleteDiveSession(DiveSession target) throws DiveNotFoundException;

    /**
     * Adds the given dive.
     * {@code person} must not already exist in the divelog book.
     * @param diveSession
     */
    void addDiveSession(DiveSession diveSession);

    /**
     * Replaces the given dive {@code target} with {@code editedDiveSession}.
     * {@code target} must exist in the divelog book.
     * The person identity of {@code editedPerson} must not be the same as another existing person in the divelog book.
     * @param target
     * @param editedDiveSession
     */
    void updateDiveSession(DiveSession target, DiveSession editedDiveSession) throws DiveNotFoundException;

    /** Returns an unmodifiable view of the filtered person list */
    ObservableList<DiveSession> getFilteredDiveList();

    /**
     * Updates the filter of the filtered person list to filter by the given {@code predicate}.
     * @param predicate
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredDiveList(Predicate<DiveSession> predicate);

    /**
     * Returns true if the model has previous divelog book states to restore.
     */
    boolean canUndoDiveLog();

    /**
     * Returns true if the model has undone divelog book states to restore.
     */
    boolean canRedoDiveLog();

    /**
     * Restores the model's divelog book to its previous state.
     */
    void undoDiveLog();

    /**
     * Restores the model's divelog book to its previously undone state.
     */
    void redoDiveLog();

    /**
     * Saves the current divelog book state for undo/redo.
     */
    void commitDiveLog();
}
