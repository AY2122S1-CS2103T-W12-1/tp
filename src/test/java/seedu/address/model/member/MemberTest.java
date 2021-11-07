package seedu.address.model.member;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_EXCO;
import static seedu.address.model.util.SampleDataUtil.getTagSet;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalMembers.ALICE;
import static seedu.address.testutil.TypicalMembers.BOB;

import java.time.DayOfWeek;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.testutil.MemberBuilder;

public class MemberTest {

    @Test
    public void asObservableList_modifyList_throwsUnsupportedOperationException() {
        Member member = new MemberBuilder().build();
        assertThrows(UnsupportedOperationException.class, () -> member.getTags().remove(0));
    }

    @Test
    public void constructor_null_throwsException() {
        List<DayOfWeek> validAvailability = Arrays.asList(DayOfWeek.MONDAY);
        assertThrows(NullPointerException.class, () ->
                new Member(new Name(null), new Phone("92929292"), new Availability(validAvailability),
                        getTagSet("y1")));
        assertThrows(NullPointerException.class, () ->
                new Member(new Name("Alice"), new Phone(null), new Availability(validAvailability),
                        getTagSet("exco")));
        assertThrows(NullPointerException.class, () ->
                new Member(new Name("Alice"), new Phone("92929292"), new Availability(null), null));
    }

    @Test
    public void isSamePerson() {
        // same object -> returns true
        assertTrue(ALICE.isSameMember(ALICE));

        // null -> returns false
        assertFalse(ALICE.isSameMember(null));

        // same name, all other attributes different -> returns true
        Member editedPhoneAlice = new MemberBuilder(ALICE).withPhone(VALID_PHONE_BOB).build();
        assertTrue(ALICE.isSameMember(editedPhoneAlice));

        // same phone, all other attributes different -> returns true
        Member editedNameAlice = new MemberBuilder(ALICE).withName(VALID_NAME_BOB).build();
        assertTrue(ALICE.isSameMember(editedNameAlice));

        // different name, different phone, all other attributes same -> returns false
        Member editedPhoneAndNameAlice = new MemberBuilder(ALICE).withName(VALID_NAME_BOB)
                .withPhone(VALID_PHONE_BOB).build();
        assertFalse(ALICE.isSameMember(editedPhoneAndNameAlice));

        // name differs in case, all other attributes same -> returns true
        Member editedBob = new MemberBuilder(BOB).withName(VALID_NAME_BOB.toLowerCase()).build();
        assertTrue(BOB.isSameMember(editedBob));

        // name has trailing spaces, different phone, all other attributes same -> returns false
        String nameWithTrailingSpaces = VALID_NAME_BOB + " ";
        editedBob = new MemberBuilder(BOB).withName(nameWithTrailingSpaces).withPhone(VALID_PHONE_AMY).build();
        assertFalse(BOB.isSameMember(editedBob));
    }

    @Test
    public void isAvailableOnDay_success() {
        Member member = new MemberBuilder().build();
        assertFalse(member.isAvailableOnDay(1));
        member = new MemberBuilder()
                .withAvailability("1 2 4").build();
        assertTrue(member.isAvailableOnDay(1));
        assertTrue(member.isAvailableOnDay(2));
        assertFalse(member.isAvailableOnDay(5));
    }

    @Test
    public void clearTodayAttendance_success() {
        Member member = new MemberBuilder().build();
        member.setPresent();
        member.clearTodayAttendance();
        Member expectedMember = new MemberBuilder().build();
        expectedMember.getTotalAttendance().incrementAttendance();
        assertEquals(member, expectedMember);
    }

    @Test
    public void equals() {
        // same values -> returns true
        Member aliceCopy = new MemberBuilder(ALICE).build();
        assertTrue(ALICE.equals(aliceCopy));

        // same object -> returns true
        assertTrue(ALICE.equals(ALICE));

        // null -> returns false
        assertFalse(ALICE.equals(null));

        // different type -> returns false
        assertFalse(ALICE.equals(5));

        // different member -> returns false
        assertFalse(ALICE.equals(BOB));

        // different name -> returns false
        Member editedAlice = new MemberBuilder(ALICE).withName(VALID_NAME_BOB).build();
        assertFalse(ALICE.equals(editedAlice));

        // different phone -> returns false
        editedAlice = new MemberBuilder(ALICE).withPhone(VALID_PHONE_BOB).build();
        assertFalse(ALICE.equals(editedAlice));

        // different tags -> returns false
        editedAlice = new MemberBuilder(ALICE).withTags(VALID_TAG_EXCO).build();
        assertFalse(ALICE.equals(editedAlice));
    }


}
