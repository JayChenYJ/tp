package seedu.storemando.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.storemando.model.Model.PREDICATE_SHOW_ALL_ITEMS;

import java.util.List;
import java.util.function.Predicate;

import seedu.storemando.commons.core.Messages;
import seedu.storemando.logic.commands.exceptions.CommandException;
import seedu.storemando.model.Model;
import seedu.storemando.model.item.Item;
import seedu.storemando.model.item.predicate.LocationContainsPredicate;

/**
 * Clears all items in a specified location.
 */
public class ClearLocationCommand extends ClearCommand {

    public static final String CLEAR_LOCATION_MESSAGE_SUCCESS = "All items in the specified location are cleared!";

    private final Predicate<Item> predicate;

    public ClearLocationCommand(LocationContainsPredicate predicate) {
        this.predicate = predicate;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Item> currentList = model.getFilteredItemList();
        if (currentList.isEmpty()) {
            throw new CommandException(Messages.MESSAGE_NO_ITEM_IN_LIST);
        }
        model.clearLocation(predicate);
        model.updateFilteredItemList(PREDICATE_SHOW_ALL_ITEMS);
        return new CommandResult(CLEAR_LOCATION_MESSAGE_SUCCESS);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
            || (other instanceof ClearLocationCommand // instanceof handles nulls
            && predicate.equals(((ClearLocationCommand) other).predicate)); // state check

    }

}
