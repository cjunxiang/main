package seedu.divelog.model;

import static java.util.Objects.requireNonNull;

import java.util.function.Predicate;
import java.util.logging.Logger;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import seedu.divelog.commons.core.ComponentManager;
import seedu.divelog.commons.core.LogsCenter;
import seedu.divelog.commons.events.model.DiveLogChangedEvent;
import seedu.divelog.commons.util.CollectionUtil;
import seedu.divelog.model.dive.DiveSession;
import seedu.divelog.model.dive.exceptions.DiveNotFoundException;

/**
 * Represents the in-memory model of the divelog book data.
 */
public class ModelManager extends ComponentManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private final VersionedDiveLog versionedDiveLog;
    private final FilteredList<DiveSession> filteredDives;

    private boolean planningMode = false;
    private int plannerCount = 0;

    /**
     * Initializes a ModelManager with the given addressBook and userPrefs.
     */
    public ModelManager(ReadOnlyDiveLog addressBook, UserPrefs userPrefs) {
        super();
        CollectionUtil.requireAllNonNull(addressBook, userPrefs);

        logger.fine("Initializing with divelog book: " + addressBook + " and user prefs " + userPrefs);

        versionedDiveLog = new VersionedDiveLog(addressBook);
        filteredDives = new FilteredList<>(versionedDiveLog.getDiveList());
    }

    public ModelManager() {
        this(new DiveLog(), new UserPrefs());
    }

    //@@author Cjunx
    @Override
    public void setPlanningMode() {
        this.planningMode = !this.planningMode;
    }

    @Override
    public boolean getPlanningMode() {
        return this.planningMode;
    }

    @Override
    public void plannerCountPlus() {
        this.plannerCount++;
    }

    @Override
    public int getPlannerCount() {
        return this.plannerCount;
    }

    @Override
    public void plannerCountMinus() {
        this.plannerCount--;
    }
    //@@author

    @Override
    public void resetData(ReadOnlyDiveLog newData) {
        versionedDiveLog.resetData(newData);
        indicateAddressBookChanged();
    }

    @Override
    public ReadOnlyDiveLog getDiveLog() {
        return versionedDiveLog;
    }

    /** Raises an event to indicate the model has changed */
    private void indicateAddressBookChanged() {
        raise(new DiveLogChangedEvent(versionedDiveLog));
    }


    @Override
    public void deleteDiveSession(DiveSession target) throws DiveNotFoundException {
        versionedDiveLog.removeDive(target);
        indicateAddressBookChanged();
    }

    @Override
    public void addDiveSession(DiveSession diveSession) {
        versionedDiveLog.addDive(diveSession);
        updateFilteredDiveList(PREDICATE_SHOW_ALL_DIVES);
        indicateAddressBookChanged();
    }

    @Override
    public void updateDiveSession(DiveSession target, DiveSession editedDiveSession) throws DiveNotFoundException {
        CollectionUtil.requireAllNonNull(target, editedDiveSession);
        versionedDiveLog.updateDive(target, editedDiveSession);
        indicateAddressBookChanged();
    }

    //=========== Filtered DiveSession List Accessors =============================================================

    /**
     * Returns an unmodifiable view of the list of {@code Dive} backed by the internal list of
     * {@code versionedDiveLog}
     */
    @Override
    public ObservableList<DiveSession> getFilteredDiveList() {
        return FXCollections.unmodifiableObservableList(filteredDives);
    }

    @Override
    public void updateFilteredDiveList(Predicate<DiveSession> predicate) {
        requireNonNull(predicate);
        filteredDives.setPredicate(predicate);
    }

    //=========== Undo/Redo =================================================================================

    @Override
    public boolean canUndoDiveLog() {
        return versionedDiveLog.canUndo();
    }

    @Override
    public boolean canRedoDiveLog() {
        return versionedDiveLog.canRedo();
    }

    @Override
    public void undoDiveLog() {
        versionedDiveLog.undo();
        indicateAddressBookChanged();
    }

    @Override
    public void redoDiveLog() {
        versionedDiveLog.redo();
        indicateAddressBookChanged();
    }

    @Override
    public void commitDiveLog() {
        versionedDiveLog.commit();
    }

    @Override
    public boolean equals(Object obj) {
        // short circuit if same object
        if (obj == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(obj instanceof ModelManager)) {
            return false;
        }

        // state check
        ModelManager other = (ModelManager) obj;
        return versionedDiveLog.equals(other.versionedDiveLog)
                && filteredDives.equals(other.filteredDives);
    }

}
