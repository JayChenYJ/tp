package seedu.storemando.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.storemando.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.storemando.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.storemando.model.Model.PREDICATE_SHOW_ALL_ITEMS;
import static seedu.storemando.testutil.TypicalItems.getTypicalStoreMando;

import org.junit.jupiter.api.Test;

import seedu.storemando.commons.core.Messages;
import seedu.storemando.model.Model;
import seedu.storemando.model.ModelManager;
import seedu.storemando.model.UserPrefs;
import seedu.storemando.model.item.LocationContainsPredicate;

public class ClearLocationCommandTest {

    @Test
    public void execute_emptyStoreMando_failure() {
        Model model = new ModelManager();

        assertCommandFailure(new ClearAllCommand(), model, Messages.MESSAGE_NO_ITEM_IN_LIST);
    }

    @Test
    public void execute_nonEmptyStoreMando_clearSpecificLocationSuccess() {
        Model model = new ModelManager(getTypicalStoreMando(), new UserPrefs());
        Model expectedModel = new ModelManager(getTypicalStoreMando(), new UserPrefs());
        LocationContainsPredicate predicate = new LocationContainsPredicate("Kitchen Basket");
        expectedModel.clearLocation(predicate);
        expectedModel.updateFilteredItemList(PREDICATE_SHOW_ALL_ITEMS);

        assertCommandSuccess(new ClearLocationCommand(predicate), model,
            ClearLocationCommand.CLEAR_LOCATION_MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void equals() {
        final ClearCommand standardCommand = new ClearLocationCommand(new LocationContainsPredicate("Kitchen"));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different types -> returns false
        assertFalse(standardCommand.equals(new SortAscendingQuantityCommand()));

        // same type but diff predicate -> returns false
        assertFalse(standardCommand.equals(new ClearAllCommand()));
        assertFalse(standardCommand.equals(new ClearLocationCommand(new LocationContainsPredicate("Toilet"))));
    }
}
