package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;

import org.junit.jupiter.api.Test;

import javafx.collections.ObservableList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ReadOnlySportsPa;
import seedu.address.model.ReadOnlyUserPrefs;
import seedu.address.model.SportsPa;
import seedu.address.model.alias.Alias;
import seedu.address.model.alias.AliasMap;
import seedu.address.model.alias.CommandWord;
import seedu.address.model.alias.Shortcut;
import seedu.address.model.facility.Facility;
import seedu.address.model.member.Member;
import seedu.address.model.sort.SortOrder;
import seedu.address.testutil.MemberBuilder;

public class AddMemberCommandTest {

    @Test
    public void constructor_nullMember_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new AddMemberCommand(null));
    }

    @Test
    public void execute_memberAcceptedByModel_addSuccessful() throws Exception {
        ModelStubAcceptingPersonAdded modelStub = new ModelStubAcceptingPersonAdded();
        Member validMember = new MemberBuilder().build();

        CommandResult commandResult = new AddMemberCommand(validMember).execute(modelStub);

        assertEquals(String.format(AddMemberCommand.MESSAGE_SUCCESS, validMember), commandResult.getFeedbackToUser());
        assertEquals(Arrays.asList(validMember), modelStub.membersAdded);
    }

    @Test
    public void execute_duplicateName_throwsCommandException() {
        Member validMember = new MemberBuilder().build();
        Member validMemberSameName = new MemberBuilder().withPhone("93852042").build();
        AddMemberCommand addMemberCommand = new AddMemberCommand(validMember);
        ModelStub modelStub = new ModelStubWithPerson(validMemberSameName);

        assertThrows(CommandException.class,
                AddMemberCommand.MESSAGE_DUPLICATE_MEMBER, () -> addMemberCommand.execute(modelStub));
    }

    @Test
    public void execute_duplicatePhone_throwsCommandException() {
        Member validMember = new MemberBuilder().build();
        Member validMemberSamePhone = new MemberBuilder().withName("Sally").build();
        AddMemberCommand addMemberCommand = new AddMemberCommand(validMember);
        ModelStub modelStub = new ModelStubWithPerson(validMemberSamePhone);

        assertThrows(CommandException.class,
                AddMemberCommand.MESSAGE_DUPLICATE_MEMBER, () -> addMemberCommand.execute(modelStub));
    }

    @Test
    public void equals() {
        Member alice = new MemberBuilder().withName("Alice").build();
        Member bob = new MemberBuilder().withName("Bob").build();
        AddMemberCommand addAliceCommand = new AddMemberCommand(alice);
        AddMemberCommand addBobCommand = new AddMemberCommand(bob);

        // same object -> returns true
        assertTrue(addAliceCommand.equals(addAliceCommand));

        // same values -> returns true
        AddMemberCommand addAliceCommandCopy = new AddMemberCommand(alice);
        assertTrue(addAliceCommand.equals(addAliceCommandCopy));

        // different types -> returns false
        assertFalse(addAliceCommand.equals(1));

        // null -> returns false
        assertFalse(addAliceCommand.equals(null));

        // different member -> returns false
        assertFalse(addAliceCommand.equals(addBobCommand));
    }

    /**
     * A default model stub that have all the methods failing.
     */
    private class ModelStub implements Model {
        @Override
        public void setUserPrefs(ReadOnlyUserPrefs userPrefs) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ReadOnlyUserPrefs getUserPrefs() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public GuiSettings getGuiSettings() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setGuiSettings(GuiSettings guiSettings) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public Path getSportsPaFilePath() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setSportsPaFilePath(Path sportsPaFilePath) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public AliasMap getAliases() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void addAlias(Alias alias) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public CommandWord removeAlias(Shortcut shortcut) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void addMember(Member member) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void addFacility(Facility facility) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public int split(Predicate<Member> person, int dayNumber) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setSportsPa(ReadOnlySportsPa sportsPa) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ReadOnlySportsPa getSportsPa() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean hasMember(Member member) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean hasFacility(Facility facility) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void deleteMember(Member target) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void removeMemberFromAllocations(Member target) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void deleteFacility(Facility target) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setMember(Member target, Member editedMember) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setFacility(Facility target, Facility editedFacility) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void resetTodayAttendance() {
            throw new AssertionError("This method should not be called");
        }

        @Override
        public void markMembersAttendance(List<Index> indices) {
            throw new AssertionError("This method should not be called");
        }

        @Override
        public void markOneMemberAttendance(Member member) {
            throw new AssertionError("This method should not be called");
        }

        @Override
        public void unmarkMembersAttendance(List<Index> indices) {
            throw new AssertionError("This method should not be called");
        }

        @Override
        public void unmarkOneMemberAttendance(Member member) {
            throw new AssertionError("This method should not be called");
        }

        @Override
        public boolean isWithinListIndex(List<Index> indices) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<Member> getFilteredMemberList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<Facility> getFilteredFacilityList() {
            throw new AssertionError("This method should not be called");
        }

        @Override
        public ObservableList<Member> getInternalMemberList() {
            throw new AssertionError("This method should not be called");
        }

        @Override
        public ObservableList<Facility> getInternalFacilityList() {
            throw new AssertionError("This method should not be called");
        }

        @Override
        public Member getSameMember(Member member) {
            throw new AssertionError("This method should not be called");
        }

        @Override
        public boolean isValidImport(Member member) {
            throw new AssertionError("This method should not be called");
        }

        @Override
        public void resetFacilityList() {
            throw new AssertionError("This method should not be called");
        }

        @Override
        public void resetMemberList() {
            throw new AssertionError("This method should not be called");
        }

        @Override
        public void sortMemberList(SortOrder sortOrder) {
            throw new AssertionError("This method should not be called");
        }

        @Override
        public void updateFilteredMemberList(Predicate<Member> predicate) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateFilteredFacilityList(Predicate<Facility> predicate) {
            throw new AssertionError("This method should not be called.");
        }
    }

    /**
     * A Model stub that contains a single member.
     */
    private class ModelStubWithPerson extends ModelStub {
        private final Member member;

        ModelStubWithPerson(Member member) {
            requireNonNull(member);
            this.member = member;
        }

        @Override
        public boolean hasMember(Member member) {
            requireNonNull(member);
            return this.member.isSameMember(member);
        }
    }

    /**
     * A Model stub that always accept the member being added.
     */
    private class ModelStubAcceptingPersonAdded extends ModelStub {
        final ArrayList<Member> membersAdded = new ArrayList<>();

        @Override
        public boolean hasMember(Member member) {
            requireNonNull(member);
            return membersAdded.stream().anyMatch(member::isSameMember);
        }

        @Override
        public void addMember(Member member) {
            requireNonNull(member);
            membersAdded.add(member);
        }

        @Override
        public ReadOnlySportsPa getSportsPa() {
            return new SportsPa();
        }
    }

}
