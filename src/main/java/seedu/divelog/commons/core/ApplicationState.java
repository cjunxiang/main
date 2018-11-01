package seedu.divelog.commons.core;

import seedu.divelog.commons.enums.SortCategory;
import seedu.divelog.commons.enums.Units;
import seedu.divelog.commons.events.SortCategoryChanged;
import seedu.divelog.commons.events.UnitsChangedEvent;

/**
 * Stores the application state. Can be useful for implementing modes.
 * Currently only stores units.
 */
public class ApplicationState {
    private static ApplicationState applicationState = new ApplicationState();

    private Units commonUnits;

    private SortCategory sortByCategory;

    private ApplicationState() {
        this.commonUnits = Units.METERS;
        this.sortByCategory = SortCategory.TIME;
    }


    public static ApplicationState getInstance() {
        return applicationState;
    }

    /**
     * Sets the units of the application
     * @param unit
     */
    public void setUnits(Units unit) {
        if (unit != this.commonUnits) {
            EventsCenter ev = EventsCenter.getInstance();
            ev.post(new UnitsChangedEvent(unit));
            commonUnits = unit;
        }
    }

    /**
     * Sets the sorting order of the application
     * @param newCategory
     */
    public void setSortByCategory(SortCategory newCategory) {
        if (!newCategory.equals(this.sortByCategory)) {
            EventsCenter ev = EventsCenter.getInstance();
            ev.post(new SortCategoryChanged(newCategory));
            sortByCategory = newCategory;
        }
    }

    /**
     * Gets the units of the application
     * @return
     */
    public Units getUnit() {
        return this.commonUnits;
    }

    /**
     * Gets the sorting order of the application
     * @return
     */
    public SortCategory getSortByCategory() {
        return this.sortByCategory;
    }

}
