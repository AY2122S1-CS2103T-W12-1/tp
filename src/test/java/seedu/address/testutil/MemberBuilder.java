package seedu.address.testutil;

import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import seedu.address.model.member.Availability;
import seedu.address.model.member.Member;
import seedu.address.model.member.Name;
import seedu.address.model.member.Phone;
import seedu.address.model.member.TodayAttendance;
import seedu.address.model.member.TotalAttendance;
import seedu.address.model.tag.Tag;
import seedu.address.model.util.SampleDataUtil;

/**
 * A utility class to help with building Member objects.
 */
public class MemberBuilder {

    public static final String DEFAULT_NAME = "Amy Bee";
    public static final String DEFAULT_PHONE = "85355255";
    public static final Boolean DEFAULT_TODAY_ATTENDANCE = false;
    public static final Integer DEFAULT_TOTAL_ATTENDANCE = 0;
    public static final List<DayOfWeek> DEFAULT_AVAILABILITY = new ArrayList<>();

    private Name name;
    private Phone phone;
    private Availability availability;
    private TotalAttendance totalAttendance;
    private TodayAttendance todayAttendance;
    private Set<Tag> tags;

    /**
     * Creates a {@code MemberBuilder} with the default details.
     */
    public MemberBuilder() {
        name = new Name(DEFAULT_NAME);
        phone = new Phone(DEFAULT_PHONE);
        availability = new Availability(DEFAULT_AVAILABILITY);
        totalAttendance = new TotalAttendance(DEFAULT_TOTAL_ATTENDANCE);
        todayAttendance = new TodayAttendance(DEFAULT_TODAY_ATTENDANCE);
        tags = new HashSet<>();
    }

    /**
     * Initializes the MemberBuilder with the data of {@code memberToCopy}.
     */
    public MemberBuilder(Member memberToCopy) {
        name = memberToCopy.getName();
        phone = memberToCopy.getPhone();
        availability = memberToCopy.getAvailability();
        todayAttendance = memberToCopy.getTodayAttendance();
        totalAttendance = memberToCopy.getTotalAttendance();
        tags = new HashSet<>(memberToCopy.getTags());
    }

    /**
     * Sets the {@code Name} of the {@code Member} that we are building.
     */
    public MemberBuilder withName(String name) {
        this.name = new Name(name);
        return this;
    }

    /**
     * Sets the {@code Phone} of the {@code Member} that we are building.
     */
    public MemberBuilder withPhone(String phone) {
        this.phone = new Phone(phone);
        return this;
    }

    /**
     * Sets the {@code Availability} of the {@code Member} that we are building.
     */
    public MemberBuilder withAvailability(List<DayOfWeek> availability) {
        this.availability = new Availability(availability);
        return this;
    }

    /**
     * Sets the {@code Availability} of the {@code Member} that we are building.
     * Used with string input.
     */
    public MemberBuilder withAvailability(String availabilityString) {
        List<DayOfWeek> availability = new ArrayList<>();
        for (String dayNumber : availabilityString.split(" ")) {
            availability.add(DayOfWeek.of(Integer.parseInt(dayNumber)));
        }
        this.availability = new Availability(availability);
        return this;
    }

    /**
     * Sets {@code TodayAttendance} of the {@code Member} that we are building.
     */
    public MemberBuilder withTodayAttendance(Boolean attendance) {
        this.todayAttendance = new TodayAttendance(attendance);
        return this;
    }

    /**
     * Sets {@code TotalAttendance} of the {@code Member} that we are building.
     */
    public MemberBuilder withTotalAttendance(Integer attendance) {
        this.totalAttendance = new TotalAttendance(attendance);
        return this;
    }

    /**
     * Parses the {@code tags} into a {@code Set<Tag>} and set it to the {@code Member} that we are building.
     */
    public MemberBuilder withTags(String ... tags) {
        this.tags = SampleDataUtil.getTagSet(tags);
        return this;
    }

    public Member build() {
        return new Member(name, phone, availability, todayAttendance, totalAttendance, tags);
    }

}
