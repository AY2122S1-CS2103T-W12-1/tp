package seedu.address.model.facility;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

import seedu.address.model.member.Member;

/**
 * Represents a Facility in SportsPA.
 */
public class Facility {
    // Identity fields
    private final FacilityName name;
    private final Location location;

    // Data fields
    private final Time time;
    private final Capacity capacity;
    private final AllocationMap allocationMap;

    /**
     * Creates a Facility object with the specified name,
     * location, time, capacity and allocation map.
     *
     * @param name Name of facility.
     * @param location Location of facility.
     * @param time Time of slot booked for facility.
     * @param capacity Capacity of facility booked.
     * @param allocationMap Allocation map of members to facility.
     */
    public Facility(FacilityName name, Location location, Time time, Capacity capacity, AllocationMap allocationMap) {
        requireAllNonNull(name, location, time, capacity, allocationMap);
        this.name = name;
        this.location = location;
        this.time = time;
        this.capacity = capacity;
        this.allocationMap = allocationMap;
    }

    public FacilityName getName() {
        return name;
    }

    public Location getLocation() {
        return location;
    }

    public Time getTime() {
        return time;
    }

    public Capacity getCapacity() {
        return capacity;
    }

    public int getCapacityAsOnDay(DayOfWeek day) {
        return allocationMap.getCapacityOnDay(day);
    }

    public int getMaxCapacityOnDay(DayOfWeek day) {
        // all days have the same capacity for now
        return capacity.getCapacityAsInt();
    }

    public AllocationMap getAllocationMap() {
        return allocationMap;
    }

    /**
     * Makes a deep copy of the allocation map.
     */
    public AllocationMap getAllocationMapClone() {
        Map<DayOfWeek, List<Member>> map = new EnumMap<>(DayOfWeek.class);
        for (DayOfWeek day : DayOfWeek.values()) {
            map.put(day, new ArrayList<>());
            for (Member member : allocationMap.getMembersAllocatedOnDay(day)) {
                map.get(day).add(member);
            }
        }
        return new AllocationMap(map);
    }

    public boolean isMaxCapacityOnDay(DayOfWeek day) {
        return capacity.isMaxCapacity(allocationMap.getCapacityOnDay(day));
    }

    public void clearAllocationMapOnDay(DayOfWeek day) {
        allocationMap.clearAllocationOnDay(day);
    }

    public boolean isMemberAllocatedOnDay(Member member, DayOfWeek day) {
        return allocationMap.isMemberAllocatedOnDay(member, day);
    }

    public void addMemberToFacilityOnDay(Member member, DayOfWeek day) {
        allocationMap.addMemberOnDay(member, day);
    }

    public void removeMemberFromFacilityOnDay(Member member, DayOfWeek day) {
        allocationMap.removeMemberOnDay(member, day);
    }

    public void removeMemberFromFacilityOnAllDays(Member member) {
        allocationMap.removeMemberOnAllDays(member);
    }

    /**
     * Returns true if both name and location of the facilities are the same.
     * This defines a weaker notion of equality between two facilities.
     */
    public boolean isSameFacility(Facility otherFacility) {
        if (otherFacility == this) {
            return true;
        }

        return otherFacility != null
                && otherFacility.getName().equals(getName())
                && otherFacility.getLocation().equals(getLocation());
    }

    /**
     * Returns true if both facilities have same identity
     * and field values.
     *
     * @param obj Object being compared with facility.
     * @return Boolean value of equality between two facilities.
     */
    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }

        if (!(obj instanceof Facility)) {
            return false;
        }

        Facility facility = (Facility) obj;
        return name.equals(facility.name)
                && location.equals(facility.location)
                && time.equals(facility.time)
                && capacity.equals(facility.capacity)
                && allocationMap.equals(facility.allocationMap);
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append(name)
                .append("; Location: ")
                .append(location)
                .append("; Time: ")
                .append(time)
                .append("; Capacity: ")
                .append(capacity);

        return builder.toString();
    }
}
