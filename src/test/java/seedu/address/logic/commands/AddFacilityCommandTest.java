package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalFacilities.FIELD;
import static seedu.address.testutil.TypicalFacilities.KENT_RIDGE_OUTDOOR_TENNIS_COURTS_COURT_1;
import static seedu.address.testutil.TypicalFacilities.TAMPINES_HUB_FIELD_SECTION_B;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.facility.Facility;
import seedu.address.testutil.FacilityBuilder;

public class AddFacilityCommandTest {
    @Test
    public void constructor_null_exceptionThrown() {
        assertThrows(NullPointerException.class, () -> new AddFacilityCommand(null));
    }

    @Test
    public void execute_facilityAcceptedByModel_addSuccessful() {
        ModelManager model = new ModelManager();
        Facility facility = new FacilityBuilder(KENT_RIDGE_OUTDOOR_TENNIS_COURTS_COURT_1).build();
        Model expectedModel = new ModelManager(model.getSportsPa(), new UserPrefs());
        expectedModel.addFacility(facility);
        AddFacilityCommand command = new AddFacilityCommand(facility);
        String expectedMessage = String.format(AddFacilityCommand.MESSAGE_SUCCESS, facility);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_duplicateFacility_throwsCommandException() {
        Facility facility = new FacilityBuilder().build();
        Model model = new ModelManager();
        model.addFacility(facility);
        AddFacilityCommand addFacilityCommand = new AddFacilityCommand(facility);

        assertThrows(CommandException.class,
                AddFacilityCommand.MESSAGE_DUPLICATE_FACILITY, () -> addFacilityCommand.execute(model));
    }

    @Test
    public void equals() {
        Facility court = new FacilityBuilder(TAMPINES_HUB_FIELD_SECTION_B).build();
        Facility field = new FacilityBuilder(FIELD).build();

        AddFacilityCommand addCourtCommand = new AddFacilityCommand(court);
        AddFacilityCommand addFieldCommand = new AddFacilityCommand(field);

        //same objects -> returns true
        assertTrue(addCourtCommand.equals(addCourtCommand));

        //same values -> returns true
        AddFacilityCommand addCourtCommandCopy = new AddFacilityCommand(court);
        assertTrue(addCourtCommand.equals(addCourtCommandCopy));

        //different facilities -> returns false
        assertFalse(addCourtCommand.equals(addFieldCommand));

        //null -> returns false
        assertFalse(addCourtCommand.equals(null));

        //different types -> returns false
        assertFalse(addCourtCommand.equals("10"));
    }
}
