package seedu.address.model.member;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.time.DayOfWeek;
import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import seedu.address.model.tag.Tag;


/**
 * Represents a Member in SportsPA.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Member {

    // Identity fields
    private final Name name;
    private final Phone phone;


    // Data fields
    private final Availability availability;
    private final TodayAttendance todayAttendance;
    private final TotalAttendance totalAttendance;
    private final Set<Tag> tags = new HashSet<>();

    /**
     * Constructor that creates member objected with no attendance.
     * Every field must be present and not null.
     */
    public Member(Name name, Phone phone, Availability availability, Set<Tag> tags) {
        requireAllNonNull(name, phone, availability, tags);
        this.name = name;
        this.phone = phone;
        this.availability = availability;
        this.todayAttendance = new TodayAttendance(false);
        this.totalAttendance = new TotalAttendance(0);
        this.tags.addAll(tags);
    }

    /**
     * Constructor with all fields of a member.
     * @param name Name of member.
     * @param phone Phone number of member.
     * @param availability Availability of member.
     * @param todayAttendance Today's attendance of member.
     * @param totalAttendance Total attendance of member.
     * @param tags Tags associated with member.
     */
    public Member(Name name, Phone phone, Availability availability,
                  TodayAttendance todayAttendance, TotalAttendance totalAttendance, Set<Tag> tags) {
        requireAllNonNull(name, phone, availability, todayAttendance, totalAttendance, tags);
        this.name = name;
        this.phone = phone;
        this.availability = availability;
        this.totalAttendance = totalAttendance;
        this.todayAttendance = todayAttendance;
        this.tags.addAll(tags);
    }

    /**
     * Constructor that creates member object with no tags.
     *
     * @param name Name of member
     * @param phone Phone number of member
     * @param availability availability of member
     * @param todayAttendance Today's attendance of member.
     * @param totalAttendance Total attendance of member.
     */
    public Member(Name name, Phone phone, Availability availability,
                  TodayAttendance todayAttendance, TotalAttendance totalAttendance) {
        requireAllNonNull(name, phone, availability, todayAttendance, totalAttendance);
        this.name = name;
        this.phone = phone;
        this.availability = availability;
        this.totalAttendance = totalAttendance;
        this.todayAttendance = todayAttendance;
    }

    public Name getName() {
        return name;
    }

    public Phone getPhone() {
        return phone;
    }

    public Availability getAvailability() {
        return availability;
    }

    public TotalAttendance getTotalAttendance() {
        return totalAttendance;
    }

    public TodayAttendance getTodayAttendance() {
        return todayAttendance;
    }

    /**
     * Returns an immutable tag set, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    public Set<Tag> getTags() {
        return Collections.unmodifiableSet(tags);
    }

    /**
     * Returns true if both persons have the same name or same phone number.
     * This defines a weaker notion of equality between two persons.
     */
    public boolean isSameMember(Member otherMember) {
        if (otherMember == this) {
            return true;
        }

        return otherMember != null
                && (otherMember.getName().equals(getName())
                    || otherMember.getPhone().equals(getPhone()));
    }

    /**
     * Returns true if member is available on specified day. Otherwise,
     * false is returned.
     *
     * @param dayNumber Day to be checked if member is available.
     * @return Boolean value if member is available on day.
     */
    public boolean isAvailableOnDay(int dayNumber) {
        return availability.contains(DayOfWeek.of(dayNumber));
    }


    /**
     * Sets the member as present today.
     */
    public void setPresent() {
        if (!isMarkedPresent()) {
            todayAttendance.setPresent();
            totalAttendance.incrementAttendance();
        }
    }

    /**
     * Sets the member as not present today.
     */
    public void setNotPresent() {
        if (isMarkedPresent()) {
            todayAttendance.setNotPresent();
            totalAttendance.decrementAttendance();
        }
    }

    /**
     * Clears today's attendance.
     */
    public void clearTodayAttendance() {
        if (isMarkedPresent()) {
            todayAttendance.setNotPresent();
        }
    }

    /**
     * Returns true if the member has been marked present. Otherwise,
     * false is returned.
     *
     * @return Boolean value if the member is marked present.
     */
    public boolean isMarkedPresent() {
        return todayAttendance.isPresentToday();
    }

    /**
     * Returns true if both members have the same identity and data fields.
     * This defines a stronger notion of equality between two members.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Member)) {
            return false;
        }

        Member otherMember = (Member) other;
        return otherMember.getName().equals(getName())
                && otherMember.getPhone().equals(getPhone())
                && otherMember.getAvailability().equals(getAvailability())
                && otherMember.getTodayAttendance().equals(getTodayAttendance())
                && otherMember.getTotalAttendance().equals(getTotalAttendance())
                && otherMember.getTags().equals(getTags());
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(name, phone, availability, tags);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getName())
                .append("; Phone: ")
                .append(getPhone());

        Availability availability = getAvailability();
        if (!availability.isEmpty()) {
            builder.append("; Availability: ");
            builder.append(availability);
        }
        Set<Tag> tags = getTags();
        if (!tags.isEmpty()) {
            builder.append("; Tags: ");
            tags.forEach(builder::append);
        }
        return builder.toString();
    }
}
