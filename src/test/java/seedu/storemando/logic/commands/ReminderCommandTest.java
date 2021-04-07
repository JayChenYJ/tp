package seedu.storemando.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.storemando.testutil.TypicalItems.APPLE;
import static seedu.storemando.testutil.TypicalItems.BREAD;
import static seedu.storemando.testutil.TypicalItems.getTypicalStoreMando;

import java.util.Arrays;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.storemando.model.Model;
import seedu.storemando.model.ModelManager;
import seedu.storemando.model.UserPrefs;
import seedu.storemando.model.expirydate.ItemExpiringPredicate;
import seedu.storemando.model.item.ItemComparatorByExpiryDate;

public class ReminderCommandTest {
    private Model model;
    private Model expectedModel = new ModelManager(getTypicalStoreMando(), new UserPrefs());

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalStoreMando(), new UserPrefs());
        expectedModel = new ModelManager(model.getStoreMando(), new UserPrefs());
    }

    @Test
    public void equals() {
        ItemExpiringPredicate firstPredicate =
            new ItemExpiringPredicate((long) 3);
        ItemExpiringPredicate secondPredicate =
            new ItemExpiringPredicate((long) 7);

        ReminderCommand reminderFirstCommand = new ReminderCommand(firstPredicate, 3, "days");
        ReminderCommand reminderSecondCommand = new ReminderCommand(secondPredicate, 3, "days");

        // same object -> returns true
        assertTrue(reminderFirstCommand.equals(reminderFirstCommand));

        // same values -> returns true
        ReminderCommand reminderFirstCommandCopy = new ReminderCommand(firstPredicate, 3, "days");
        assertTrue(reminderFirstCommand.equals(reminderFirstCommandCopy));

        // different types -> returns false
        assertFalse(reminderFirstCommand.equals(1));

        // null -> returns false
        assertFalse(reminderFirstCommand.equals(null));

        // different Item -> returns false
        assertFalse(reminderFirstCommand.equals(reminderSecondCommand));
    }

    @Test
    public void execute_multipleItemsFound() {
        ItemExpiringPredicate predicate = new ItemExpiringPredicate((long) 30);
        expectedModel.updateFilteredItemList(predicate);
        ItemComparatorByExpiryDate comparator = new ItemComparatorByExpiryDate();
        expectedModel.updateSortedItemList(comparator);
        expectedModel.setItems(expectedModel.getSortedItemList());
        assertEquals(Arrays.asList(BREAD, APPLE), expectedModel.getFilteredItemList());
    }
}
