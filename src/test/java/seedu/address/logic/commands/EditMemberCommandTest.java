package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_EXCO;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showPersonAtIndex;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND;
import static seedu.address.testutil.TypicalSportsPa.getTypicalSportsPa;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.SportsPa;
import seedu.address.model.UserPrefs;
import seedu.address.model.member.Member;
import seedu.address.testutil.EditMemberDescriptorBuilder;
import seedu.address.testutil.MemberBuilder;

/**
 * Contains integration tests (interaction with the Model) and unit tests for EditMemberCommand.
 */
public class EditMemberCommandTest {

    private Model model = new ModelManager(getTypicalSportsPa(), new UserPrefs());

    @Test
    public void execute_allFieldsSpecifiedUnfilteredList_success() {
        Member editedMember = new MemberBuilder().build();

        EditMemberCommand.EditMemberDescriptor descriptor = new EditMemberDescriptorBuilder(editedMember).build();
        EditMemberCommand editCommand = new EditMemberCommand(INDEX_FIRST, descriptor);

        String expectedMessage = String.format(EditMemberCommand.MESSAGE_EDIT_MEMBER_SUCCESS, editedMember);

        Model expectedModel = new ModelManager(new SportsPa(model.getSportsPa()), new UserPrefs());
        expectedModel.setMember(model.getFilteredMemberList().get(0), editedMember);

        assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_onlyPhoneSpecifiedUnfilteredList_success() {
        Index indexLastPerson = Index.fromOneBased(model.getFilteredMemberList().size());
        Member lastMember = model.getFilteredMemberList().get(indexLastPerson.getZeroBased());

        MemberBuilder personInList = new MemberBuilder(lastMember);
        Member editedMember = personInList.withPhone(VALID_PHONE_BOB).build();

        EditMemberCommand.EditMemberDescriptor descriptor = new EditMemberDescriptorBuilder()
                .withPhone(VALID_PHONE_BOB).build();
        EditMemberCommand editCommand = new EditMemberCommand(indexLastPerson, descriptor);

        String expectedMessage = String.format(EditMemberCommand.MESSAGE_EDIT_MEMBER_SUCCESS, editedMember);

        Model expectedModel = new ModelManager(new SportsPa(model.getSportsPa()), new UserPrefs());
        expectedModel.setMember(lastMember, editedMember);

        assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_onlyTagsSpecifiedUnfilteredList_success() {
        Index indexLastPerson = Index.fromOneBased(model.getFilteredMemberList().size());
        Member lastMember = model.getFilteredMemberList().get(indexLastPerson.getZeroBased());

        MemberBuilder personInList = new MemberBuilder(lastMember);
        Member editedMember = personInList.withTags(VALID_TAG_EXCO).build();

        EditMemberCommand.EditMemberDescriptor descriptor = new EditMemberDescriptorBuilder()
                .withTags(VALID_TAG_EXCO).build();
        EditMemberCommand editCommand = new EditMemberCommand(indexLastPerson, descriptor);

        String expectedMessage = String.format(EditMemberCommand.MESSAGE_EDIT_MEMBER_SUCCESS, editedMember);

        Model expectedModel = new ModelManager(new SportsPa(model.getSportsPa()), new UserPrefs());
        expectedModel.setMember(lastMember, editedMember);

        assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_onlyNameSpecifiedUnfilteredList_success() {
        Index indexLastPerson = Index.fromOneBased(model.getFilteredMemberList().size());
        Member lastMember = model.getFilteredMemberList().get(indexLastPerson.getZeroBased());

        MemberBuilder personInList = new MemberBuilder(lastMember);
        Member editedMember = personInList.withName(VALID_NAME_BOB).build();

        EditMemberCommand.EditMemberDescriptor descriptor = new EditMemberDescriptorBuilder()
                .withName(VALID_NAME_BOB).build();
        EditMemberCommand editCommand = new EditMemberCommand(indexLastPerson, descriptor);

        String expectedMessage = String.format(EditMemberCommand.MESSAGE_EDIT_MEMBER_SUCCESS, editedMember);

        Model expectedModel = new ModelManager(new SportsPa(model.getSportsPa()), new UserPrefs());
        expectedModel.setMember(lastMember, editedMember);

        assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_noFieldSpecifiedUnfilteredList_success() {

        EditMemberCommand editCommand =
                new EditMemberCommand(INDEX_FIRST, new EditMemberCommand.EditMemberDescriptor());
        Member editedMember = model.getFilteredMemberList().get(INDEX_FIRST.getZeroBased());

        String expectedMessage = String.format(EditMemberCommand.MESSAGE_EDIT_MEMBER_SUCCESS, editedMember);

        Model expectedModel = new ModelManager(new SportsPa(model.getSportsPa()), new UserPrefs());

        assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_filteredList_success() {
        showPersonAtIndex(model, INDEX_FIRST);

        Member memberInFilteredList = model.getFilteredMemberList().get(INDEX_FIRST.getZeroBased());
        Member editedMember = new MemberBuilder(memberInFilteredList).withName(VALID_NAME_BOB).build();

        EditMemberCommand editCommand = new EditMemberCommand(INDEX_FIRST,
                new EditMemberDescriptorBuilder().withName(VALID_NAME_BOB).build());

        String expectedMessage = String.format(EditMemberCommand.MESSAGE_EDIT_MEMBER_SUCCESS, editedMember);

        Model expectedModel = new ModelManager(new SportsPa(model.getSportsPa()), new UserPrefs());
        expectedModel.setMember(model.getFilteredMemberList().get(0), editedMember);

        assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_duplicateNameFilteredList_failure() {

        // edit member in filtered list into a duplicate in address book
        Member firstMember = model.getFilteredMemberList().get(INDEX_FIRST.getZeroBased());
        EditMemberCommand.EditMemberDescriptor descriptor = new EditMemberDescriptorBuilder(firstMember)
                .withPhone(VALID_PHONE_AMY).build();
        EditMemberCommand editCommand = new EditMemberCommand(INDEX_SECOND, descriptor);

        assertCommandFailure(editCommand, model, EditMemberCommand.MESSAGE_DUPLICATE_MEMBER);
    }

    @Test
    public void execute_duplicatePhoneFilteredList_failure() {

        // edit member in filtered list into a duplicate in address book
        Member firstMember = model.getFilteredMemberList().get(INDEX_FIRST.getZeroBased());
        EditMemberCommand.EditMemberDescriptor descriptor = new EditMemberDescriptorBuilder(firstMember)
                .withName(VALID_NAME_AMY).build();
        EditMemberCommand editCommand = new EditMemberCommand(INDEX_SECOND, descriptor);

        assertCommandFailure(editCommand, model, EditMemberCommand.MESSAGE_DUPLICATE_MEMBER);
    }

    @Test
    public void execute_duplicateNameUnFilteredList_failure() {
        showPersonAtIndex(model, INDEX_FIRST);

        Member memberInList = model.getSportsPa().getMemberList().get(INDEX_SECOND.getZeroBased());
        EditMemberCommand editCommand = new EditMemberCommand(INDEX_FIRST,
                new EditMemberDescriptorBuilder(memberInList).withPhone(VALID_PHONE_BOB).build());

        assertCommandFailure(editCommand, model, EditMemberCommand.MESSAGE_DUPLICATE_MEMBER);
    }

    @Test
    public void execute_duplicatePhoneUnFilteredList_failure() {
        showPersonAtIndex(model, INDEX_FIRST);

        Member memberInList = model.getSportsPa().getMemberList().get(INDEX_SECOND.getZeroBased());
        EditMemberCommand editCommand = new EditMemberCommand(INDEX_FIRST,
                new EditMemberDescriptorBuilder(memberInList).withName(VALID_NAME_BOB).build());

        assertCommandFailure(editCommand, model, EditMemberCommand.MESSAGE_DUPLICATE_MEMBER);
    }

    @Test
    public void execute_invalidPersonIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredMemberList().size() + 1);
        EditMemberCommand.EditMemberDescriptor descriptor =
                new EditMemberDescriptorBuilder().withName(VALID_NAME_BOB).build();
        EditMemberCommand editCommand = new EditMemberCommand(outOfBoundIndex, descriptor);

        assertCommandFailure(editCommand, model, Messages.MESSAGE_INVALID_MEMBER_DISPLAYED_INDEX);
    }

    /**
     * Edit filtered list where index is larger than size of filtered list,
     * but smaller than size of address book
     */
    @Test
    public void execute_invalidPersonIndexFilteredList_failure() {
        showPersonAtIndex(model, INDEX_FIRST);
        Index outOfBoundIndex = INDEX_SECOND;
        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getSportsPa().getMemberList().size());

        EditMemberCommand editCommand = new EditMemberCommand(outOfBoundIndex,
                new EditMemberDescriptorBuilder().withName(VALID_NAME_BOB).build());

        assertCommandFailure(editCommand, model, Messages.MESSAGE_INVALID_MEMBER_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {

        final EditMemberCommand standardCommand = new EditMemberCommand(INDEX_FIRST, DESC_AMY);

        // same values -> returns true
        EditMemberCommand.EditMemberDescriptor copyDescriptor = new EditMemberCommand.EditMemberDescriptor(DESC_AMY);
        EditMemberCommand commandWithSameValues = new EditMemberCommand(INDEX_FIRST, copyDescriptor);

        assertTrue(standardCommand.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different types -> returns false
        assertFalse(standardCommand.equals(new ClearMembersCommand()));

        // different index -> returns false
        assertFalse(standardCommand.equals(new EditMemberCommand(INDEX_SECOND, DESC_AMY)));

        // different descriptor -> returns false
        assertFalse(standardCommand.equals(new EditMemberCommand(INDEX_FIRST, DESC_BOB)));
    }

    @Test
    public void execute_emptyMemberList_failure() {
        Member editedMember = new MemberBuilder().build();
        EditMemberCommand.EditMemberDescriptor descriptor = new EditMemberDescriptorBuilder(editedMember).build();
        EditMemberCommand editCommand = new EditMemberCommand(INDEX_FIRST, descriptor);
        model.updateFilteredMemberList(Model.PREDICATE_SHOW_NO_MEMBERS);
        assertCommandFailure(editCommand, model, String.format(Messages.MESSAGE_EMPTY_LIST, Messages.MESSAGE_MEMBER));
    }

}
