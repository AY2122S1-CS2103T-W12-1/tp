package seedu.address.testutil;


import static seedu.address.logic.commands.CommandTestUtil.VALID_AVAILABILITY_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_AVAILABILITY_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_AVAILABILITY_CHARLIE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_CHARLIE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_CHARLIE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_EXCO;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_Y2;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.model.member.Member;


/**
 * A utility class containing a list of {@code Member} objects to be used in tests.
 */
public class TypicalMembers {

    public static final Member ALICE = new MemberBuilder().withName("Alice Pauline")
            .withPhone("94351253").build();
    public static final Member ALICE_TAN = new MemberBuilder().withName("Alice Tan").build();
    public static final Member BENSON = new MemberBuilder().withName("Benson Meier")
            .withPhone("98765432").build();
    public static final Member CARL = new MemberBuilder().withName("Carl Kurz")
            .withPhone("95352563").build();
    public static final Member DANIEL = new MemberBuilder().withName("Daniel Meier")
            .withPhone("87652533").build();
    public static final Member ELLE = new MemberBuilder().withName("Elle Meyer")
            .withPhone("9482224").build();
    public static final Member FIONA = new MemberBuilder().withName("Fiona Kunz")
            .withPhone("9482427").build();
    public static final Member GEORGE = new MemberBuilder().withName("George Best")
            .withPhone("9482442").build();

    // Manually added
    public static final Member HOON = new MemberBuilder().withName("Hoon Meier")
            .withPhone("8482424").build();
    public static final Member IDA = new MemberBuilder().withName("Ida Mueller")
            .withPhone("8482131").withTodayAttendance(false).withTotalAttendance(0).build();
    public static final Member ALICE_DIFFERENT_PHONE = new MemberBuilder().withName("Alice Pauline")
            .withPhone("94839542").build();
    public static final Member ALICE_DIFFERENT_NAME = new MemberBuilder().withName("Alice Smith")
            .withPhone("94351253").build();
    public static final Member CHOO = new MemberBuilder().withName("Choo")
            .withPhone("90909090").build();
    public static final Member DOO = new MemberBuilder().withName("Doo")
            .withPhone("99889898").withTags("y3").build();

    // Manually added - Member's details found in {@code CommandTestUtil}
    public static final Member AMY = new MemberBuilder().withName(VALID_NAME_AMY)
            .withPhone(VALID_PHONE_AMY).withAvailability(VALID_AVAILABILITY_AMY).withTags(VALID_TAG_Y2).build();
    public static final Member BOB = new MemberBuilder().withName(VALID_NAME_BOB)
            .withPhone(VALID_PHONE_BOB).withAvailability(VALID_AVAILABILITY_BOB)
            .withTags(VALID_TAG_EXCO, VALID_TAG_Y2).build();
    public static final Member CHARLIE = new MemberBuilder().withName(VALID_NAME_CHARLIE)
            .withPhone(VALID_PHONE_CHARLIE).withAvailability(VALID_AVAILABILITY_CHARLIE).build();

    public static final String KEYWORD_MATCHING_MEIER = "Meier"; // A keyword that matches MEIER

    private TypicalMembers() {} // prevents instantiation

    public static List<Member> getTypicalMembers() {
        return new ArrayList<>(Arrays.asList(ALICE, BENSON, CARL, DANIEL, ELLE, FIONA, GEORGE));
    }

    public static List<Member> getTypicalMembersToFind() {
        return new ArrayList<>(Arrays.asList(ALICE, ALICE_TAN, BENSON));
    }

    public static List<Member> getTypicalMembersUnsortedName() {
        return new ArrayList<>(Arrays.asList(GEORGE, FIONA, ELLE, DANIEL, CARL, BENSON, ALICE));
    }

    public static List<Member> getTypicalMembersUnsortedTag() {
        return new ArrayList<>(Arrays.asList(AMY, BOB, CHOO, DOO));
    }
}
