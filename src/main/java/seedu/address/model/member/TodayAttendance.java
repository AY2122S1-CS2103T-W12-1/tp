package seedu.address.model.member;


/**
 * Represents a Member's today attendance in SportsPA.
 */
public class TodayAttendance {
    private boolean isPresent;

    public TodayAttendance(boolean isPresent) {
        this.isPresent = isPresent;
    }

    /**
     * Returns icon of attendance status.
     *
     * @return String representation of attendance status.
     */
    public String getAttendanceStatusIcon() {
        return isPresent ? "✓" : "X";
    }

    /**
     * Returns true if today's attendance has been set as present.
     * Otherwise, false is returned.
     *
     * @return Boolean value if today's attendance is marked as present.
     */
    public boolean isPresentToday() {
        return isPresent;
    }

    /**
     * Sets today's attendance as present.
     */
    public void setPresent() {
        this.isPresent = true;
    }

    /**
     * Sets today's attendance as not present.
     */
    public void setNotPresent() {
        this.isPresent = false;
    }

    /**
     * Returns string of today's attendance.
     *
     * @return String represention of today's attendance.
     */
    public String toString() {
        return "Today's attendance: " + getAttendanceStatusIcon();
    }

    @Override
    public boolean equals(Object obj) {
        return obj == this
                || (obj instanceof TodayAttendance
                && isPresent == ((TodayAttendance) obj).isPresent);
    }
}
