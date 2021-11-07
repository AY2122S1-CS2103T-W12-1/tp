package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalFacilities.KENT_RIDGE_OUTDOOR_TENNIS_COURTS_COURT_1;
import static seedu.address.testutil.TypicalFacilities.KENT_RIDGE_OUTDOOR_TENNIS_COURTS_COURT_10;
import static seedu.address.testutil.TypicalFacilities.UTOWN_FIELD_SECTION_A;
import static seedu.address.testutil.TypicalSportsPa.getTypicalSportsPa;
import static seedu.address.testutil.TypicalSportsPa.getTypicalSportsPaEmptyFacilityList;

import java.util.Arrays;
import java.util.Collections;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.Messages;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.facility.LocationContainsKeywordsPredicate;

/**
 * Contains integration tests for {@code FindFacilityCommand}.
 */
public class FindFacilityCommandTest {
    private Model model = new ModelManager(getTypicalSportsPa(), new UserPrefs());
    private Model expectedModel = new ModelManager(getTypicalSportsPa(), new UserPrefs());

    @Test
    public void equals() {
        LocationContainsKeywordsPredicate firstPred =
                new LocationContainsKeywordsPredicate(Collections.singletonList("first"));
        LocationContainsKeywordsPredicate secondPred =
                new LocationContainsKeywordsPredicate(Collections.singletonList("second"));

        FindFacilityCommand findFacilFirstCommand = new FindFacilityCommand(firstPred);
        FindFacilityCommand findFacilSecondCommand = new FindFacilityCommand(secondPred);

        // same object -> returns true
        assertTrue(findFacilFirstCommand.equals(findFacilFirstCommand));

        // same values -> returns true
        FindFacilityCommand findFacilFirstCommandCopy = new FindFacilityCommand(firstPred);
        assertTrue(findFacilFirstCommand.equals(findFacilFirstCommandCopy));

        // different types -> returns false
        assertFalse(findFacilFirstCommand.equals(1));

        // null -> returns false
        assertFalse(findFacilFirstCommand.equals(null));

        // different facility -> returns false
        assertFalse(findFacilFirstCommand.equals(findFacilSecondCommand));
    }

    @Test
    public void execute_zeroKeywords_noFacilityFound() {
        String expectedMessage = String.format(Messages.MESSAGE_FACILITIES_LISTED_OVERVIEW, 0);
        LocationContainsKeywordsPredicate predicate = preparePredicate("  ");
        FindFacilityCommand command = new FindFacilityCommand(predicate);
        expectedModel.updateFilteredFacilityList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Collections.emptyList(), model.getFilteredFacilityList());
    }

    @Test
    public void execute_multipleKeywords_multipleFacilitiesFound() {
        String expectedMessage = String.format(Messages.MESSAGE_FACILITIES_LISTED_OVERVIEW, 3);
        LocationContainsKeywordsPredicate predicate = preparePredicate("Tennis Utown");
        FindFacilityCommand command = new FindFacilityCommand(predicate);
        expectedModel.updateFilteredFacilityList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(
                        KENT_RIDGE_OUTDOOR_TENNIS_COURTS_COURT_1,
                        KENT_RIDGE_OUTDOOR_TENNIS_COURTS_COURT_10,
                        UTOWN_FIELD_SECTION_A),
                model.getFilteredFacilityList());
    }

    @Test
    public void execute_emptyFacilityList_failure() {
        Model model = new ModelManager(getTypicalSportsPaEmptyFacilityList(), new UserPrefs());
        LocationContainsKeywordsPredicate predicate = preparePredicate("Tennis Utown");
        FindFacilityCommand command = new FindFacilityCommand(predicate);
        assertCommandFailure(command, model, String.format(Messages.MESSAGE_EMPTY_LIST, Messages.MESSAGE_FACILITY));
    }

    /**
     * Parses {@code userInput} into a {@code LocationContainsKeywordsPredicate}.
     */
    private LocationContainsKeywordsPredicate preparePredicate(String userInput) {
        return new LocationContainsKeywordsPredicate(Arrays.asList(userInput.split("\\s+")));
    }
}
