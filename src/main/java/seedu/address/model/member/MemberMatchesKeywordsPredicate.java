package seedu.address.model.member;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

import seedu.address.model.tag.Tag;

/**
 * Tests that a {@code Person} matches any of the keywords given.
 */
public class MemberMatchesKeywordsPredicate implements Predicate<Member> {
    private Name name = new Name("DEFAULT");
    private Phone phone = new Phone("00000000");
    private List<Tag> tags = new ArrayList<>();
    private Availability availability = new Availability(new ArrayList<>());
    private TodayAttendance todayAttendance = new TodayAttendance(false);
    private TotalAttendance totalAttendance = new TotalAttendance(0);
    private Predicate<Member> predicate;

    private MemberMatchesKeywordsPredicate() {
        this.predicate = x-> true;
    }

    private void setName(Name name) {
        this.name = name;
    }

    private void setPhone(Phone phone) {
        this.phone = phone;
    }

    private void setTags(List<Tag> tags) {
        this.tags = tags;
    }

    private void setAvailability(Availability availability) {
        this.availability = availability;
    }

    private void setTodayAttendance(TodayAttendance todayAttendance) {
        this.todayAttendance = todayAttendance;
    }

    private void setTotalAttendance(TotalAttendance totalAttendance) {
        this.totalAttendance = totalAttendance;
    }

    private void setPredicate(Predicate<Member> predicate) {
        this.predicate = this.predicate.and(predicate);
    }
    @Override
    public boolean test(Member member) {
        return predicate.test(member);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof MemberMatchesKeywordsPredicate // instanceof handles nulls
                && name.equals(((MemberMatchesKeywordsPredicate) other).name) // state check
                && phone.equals(((MemberMatchesKeywordsPredicate) other).phone)
                && tags.equals(((MemberMatchesKeywordsPredicate) other).tags)
                && availability.equals(((MemberMatchesKeywordsPredicate) other).availability)
                && todayAttendance.equals(((MemberMatchesKeywordsPredicate) other).todayAttendance)
                && totalAttendance.equals(((MemberMatchesKeywordsPredicate) other).totalAttendance));
    }

    public static class Builder {
        // default values

        private MemberMatchesKeywordsPredicate predicate;

        public Builder() {
            this.predicate = new MemberMatchesKeywordsPredicate();
        }


        /**
         * Sets the {@code Name} of the {@code MemberMatchesKeywordsPredicate} that we are building.
         */
        public Builder withName(Name name) {
            predicate.setName(name);
            return this;
        }

        /**
         * Sets the {@code Phone} of the {@code MemberMatchesKeywordsPredicate} that we are building.
         */
        public Builder withPhone(Phone phone) {
            predicate.setPhone(phone);
            return this;
        }

        /**
         * Sets the {@code tags} of the {@code MemberMatchesKeywordsPredicate} that we are building
         * that we are building.
         */
        public Builder withTags(List<Tag> tags) {
            predicate.setTags(tags);
            return this;
        }

        /**
         * Sets the {@code Availability} of the {@code MemberMatchesKeywordsPredicate} that we are building.
         */
        public Builder withAvailability(Availability avail) {
            predicate.setAvailability(avail);
            return this;
        }

        /**
         * Sets the {@code TodayAttendance} of the {@code MemberMatchesKeywordsPredicate} that we are building.
         */
        public Builder withTodayAttendance(TodayAttendance tda) {
            predicate.setTodayAttendance(tda);
            return this;
        }

        /**
         * Sets the {@code TotalAttendance} of the {@code MemberMatchesKeywordsPredicate} that we are building.
         */
        public Builder withTotalAttendance(TotalAttendance tta) {
            predicate.setTotalAttendance(tta);
            return this;
        }

        /**
         * Sets the {@code Predicate} of the {@code MemberMatchesKeywordsPredicate} that we are building.
         */
        public Builder withPredicate(Predicate<Member> p) {
            predicate.setPredicate(p);
            return this;
        }

        /**
         * Builds a {@code PersonMatchesKeywordsPredicate} to be used in {@code FindMemberCommand}
         */
        public MemberMatchesKeywordsPredicate build() {
            return predicate;
        }
    }
}
