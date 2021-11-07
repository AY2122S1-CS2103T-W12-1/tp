package seedu.address.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_MEMBERS;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST;
import static seedu.address.testutil.TypicalMembers.ALICE;
import static seedu.address.testutil.TypicalMembers.ALICE_DIFFERENT_PHONE;
import static seedu.address.testutil.TypicalMembers.AMY;
import static seedu.address.testutil.TypicalMembers.BENSON;
import static seedu.address.testutil.TypicalMembers.BOB;
import static seedu.address.testutil.TypicalMembers.CARL;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.GuiSettings;
import seedu.address.commons.core.index.Index;
import seedu.address.model.member.Member;
import seedu.address.model.member.NameContainsKeywordsPredicate;
import seedu.address.testutil.MemberBuilder;
import seedu.address.testutil.SportsPaBuilder;

public class ModelManagerTest {

    private ModelManager modelManager = new ModelManager();

    @Test
    public void constructor() {
        assertEquals(new UserPrefs(), modelManager.getUserPrefs());
        assertEquals(new GuiSettings(), modelManager.getGuiSettings());
        assertEquals(new SportsPa(), new SportsPa(modelManager.getSportsPa()));
    }

    @Test
    public void setUserPrefs_nullUserPrefs_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.setUserPrefs(null));
    }

    @Test
    public void setUserPrefs_validUserPrefs_copiesUserPrefs() {
        UserPrefs userPrefs = new UserPrefs();
        userPrefs.setSportsPaFilePath(Paths.get("address/book/file/path"));
        userPrefs.setGuiSettings(new GuiSettings(1, 2, 3, 4));
        modelManager.setUserPrefs(userPrefs);
        assertEquals(userPrefs, modelManager.getUserPrefs());

        // Modifying userPrefs should not modify modelManager's userPrefs
        UserPrefs oldUserPrefs = new UserPrefs(userPrefs);
        userPrefs.setSportsPaFilePath(Paths.get("new/address/book/file/path"));
        assertEquals(oldUserPrefs, modelManager.getUserPrefs());
    }

    @Test
    public void setGuiSettings_nullGuiSettings_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.setGuiSettings(null));
    }

    @Test
    public void setGuiSettings_validGuiSettings_setsGuiSettings() {
        GuiSettings guiSettings = new GuiSettings(1, 2, 3, 4);
        modelManager.setGuiSettings(guiSettings);
        assertEquals(guiSettings, modelManager.getGuiSettings());
    }

    @Test
    public void setAddressBookFilePath_nullPath_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.setSportsPaFilePath(null));
    }

    @Test
    public void setAddressBookFilePath_validPath_setsAddressBookFilePath() {
        Path path = Paths.get("address/book/file/path");
        modelManager.setSportsPaFilePath(path);
        assertEquals(path, modelManager.getSportsPaFilePath());
    }

    @Test
    public void hasPerson_nullPerson_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.hasMember(null));
    }

    @Test
    public void hasPerson_personNotInAddressBook_returnsFalse() {
        assertFalse(modelManager.hasMember(ALICE));
    }

    @Test
    public void hasPerson_personInAddressBook_returnsTrue() {
        modelManager.addMember(ALICE);
        assertTrue(modelManager.hasMember(ALICE));
    }

    @Test
    public void getSamePerson_personWithSameNameInAddressBook_returnsPersonWithSameName() {
        modelManager.addMember(ALICE);
        assertEquals(modelManager.getSameMember(ALICE_DIFFERENT_PHONE), ALICE);
    }

    @Test
    public void getSamePerson_personWithSameNameNotInAddressBook_returnsNull() {
        assertNull(modelManager.getSameMember(BOB));
    }

    @Test
    public void isValidImport_invalidImport_returnsFalse() {
        modelManager.addMember(AMY);
        modelManager.addMember(BOB);
        Member toTest = new MemberBuilder().withName(VALID_NAME_AMY).withPhone(VALID_PHONE_BOB).build();
        assertFalse(modelManager.isValidImport(toTest));
    }

    @Test
    public void isValidImport_validImport_returnsTrue() {
        modelManager.addMember(BOB);
        modelManager.addMember(CARL);
        Member firstToTest = new MemberBuilder().withName(VALID_NAME_AMY).build();
        Member secondToTest = new MemberBuilder().withPhone(VALID_PHONE_BOB).build();
        Member thirdToTest = new MemberBuilder().build();

        assertTrue(modelManager.isValidImport(firstToTest));
        assertTrue(modelManager.isValidImport(secondToTest));
        assertTrue(modelManager.isValidImport(thirdToTest));
    }

    @Test
    public void getFilteredPersonList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> modelManager.getFilteredMemberList().remove(0));
    }

    @Test
    public void getFilteredFacilityList_modifyList_throwsUnsupportedOperationsException() {
        assertThrows(UnsupportedOperationException.class, () -> modelManager.getFilteredFacilityList().remove(0));
    }

    @Test
    public void isWithinListIndex_validIndicies_returnsFalse() {
        Member toAdd = ALICE;
        modelManager.addMember(toAdd);
        List<Index> indices = Arrays.asList(INDEX_FIRST);
        assertTrue(modelManager.isWithinListIndex(indices));
    }

    @Test
    public void isWithinListIndex_invalidIndices_returnsTrue() {
        Index outOfBoundsIndex = Index.fromOneBased(modelManager.getFilteredMemberList().size() + 1);
        List<Index> indices = Arrays.asList(outOfBoundsIndex);
        assertFalse(modelManager.isWithinListIndex(indices));
    }
    @Test
    public void equals() {
        SportsPa sportsPa = new SportsPaBuilder().withMember(ALICE).withMember(BENSON).build();
        SportsPa differentSportsPa = new SportsPa();
        UserPrefs userPrefs = new UserPrefs();

        // same values -> returns true
        modelManager = new ModelManager(sportsPa, userPrefs);
        ModelManager modelManagerCopy = new ModelManager(sportsPa, userPrefs);
        assertTrue(modelManager.equals(modelManagerCopy));

        // same object -> returns true
        assertTrue(modelManager.equals(modelManager));

        // null -> returns false
        assertFalse(modelManager.equals(null));

        // different types -> returns false
        assertFalse(modelManager.equals(5));

        // different sportsPa -> returns false
        assertFalse(modelManager.equals(new ModelManager(differentSportsPa, userPrefs)));

        // different filteredList -> returns false
        String[] keywords = ALICE.getName().fullName.split("\\s+");
        modelManager.updateFilteredMemberList(new NameContainsKeywordsPredicate(Arrays.asList(keywords)));
        assertFalse(modelManager.equals(new ModelManager(sportsPa, userPrefs)));

        // resets modelManager to initial state for upcoming tests
        modelManager.updateFilteredMemberList(PREDICATE_SHOW_ALL_MEMBERS);

        // different userPrefs -> returns false
        UserPrefs differentUserPrefs = new UserPrefs();
        differentUserPrefs.setSportsPaFilePath(Paths.get("differentFilePath"));
        assertFalse(modelManager.equals(new ModelManager(sportsPa, differentUserPrefs)));
    }
}
