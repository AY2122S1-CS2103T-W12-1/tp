package seedu.address.testutil;

import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

import seedu.address.model.facility.AllocationMap;
import seedu.address.model.facility.Capacity;
import seedu.address.model.facility.Facility;
import seedu.address.model.facility.FacilityName;
import seedu.address.model.facility.Location;
import seedu.address.model.facility.Time;
import seedu.address.model.member.Member;

/**
 * A utility class to help with building Facility objects.
 */
public class FacilityBuilder {

    public static final String DEFAULT_FACILITY_NAME = "Court 1";
    public static final String DEFAULT_LOCATION = "Tampines Hub Badminton Hall";
    public static final String DEFAULT_TIME = "1700";
    public static final String DEFAULT_CAPACITY = "5";
    private Map<DayOfWeek, List<Member>> defaultAllocations = new EnumMap<>(DayOfWeek.class);

    private FacilityName facilityName;
    private Location location;
    private Time time;
    private Capacity capacity;
    private AllocationMap allocationMap;

    /**
     * Creates a {@code FacilityBuilder} with the default details.
     */
    public FacilityBuilder() {
        this.facilityName = new FacilityName(DEFAULT_FACILITY_NAME);
        this.location = new Location(DEFAULT_LOCATION);
        this.time = new Time(DEFAULT_TIME);
        this.capacity = new Capacity(DEFAULT_CAPACITY);
        for (DayOfWeek day : DayOfWeek.values()) {
            defaultAllocations.put(day, new ArrayList<>());
        }
        this.allocationMap = new AllocationMap(defaultAllocations);
    }

    /**
     * Initializes the FacilityBuilder with the data of {@code facilityToCopy}.
     */
    public FacilityBuilder(Facility facilityToCopy) {
        this.facilityName = facilityToCopy.getName();
        this.location = facilityToCopy.getLocation();
        this.time = facilityToCopy.getTime();
        this.capacity = facilityToCopy.getCapacity();
        this.allocationMap = facilityToCopy.getAllocationMapClone();
    }

    /**
     * Sets the {@code FacilityName} of the {@code Facility} that we are building.
     */
    public FacilityBuilder withFacilityName(String facilityName) {
        this.facilityName = new FacilityName(facilityName);
        return this;
    }

    /**
     * Sets the {@code Location} of the {@code Facility} that we are building.
     */
    public FacilityBuilder withLocation(String location) {
        this.location = new Location(location);
        return this;
    }

    /**
     * Sets the {@code Time} of the {@code Facility} that we are building.
     */
    public FacilityBuilder withTime(String time) {
        this.time = new Time(time);
        return this;
    }

    /**
     * Sets the {@code Capacity} of the {@code Facility} that we are building.
     */
    public FacilityBuilder withCapacity(String capacity) {
        this.capacity = new Capacity(capacity);
        return this;
    }

    /**
     * Sets the {@code AllocationMap} of the {@code Facility} that we are building.
     */
    public FacilityBuilder withAllocationMap(Map<DayOfWeek, List<Member>> allocationMap) {
        this.allocationMap = new AllocationMap(allocationMap);
        return this;
    }

    public Facility build() {
        return new Facility(facilityName, location, time, capacity, allocationMap);
    }
}
