package seedu.address.model.member;

/**
 * Represents a Member's total attendance in SportsPA.
 */
public class TotalAttendance {
    private int totalAttendance;

    public TotalAttendance(int totalAttendance) {
        this.totalAttendance = totalAttendance;
    }

    public Integer getAttendance() {
        return totalAttendance;
    }

    /**
     * Increases total attendance by one.
     */
    public void incrementAttendance() {
        totalAttendance++;
    }

    /**
     * Decreases attendance by one if attendance is positive.
     */
    public void decrementAttendance() {
        if (totalAttendance >= 1) {
            totalAttendance--;
        }
    }

    /**
     * Returns string of total attendance.
     *
     * @return String representation of total attendance.
     */
    public String toString() {
        return "Total attendance: " + totalAttendance;
    }

    @Override
    public boolean equals(Object obj) {
        return obj == this
                || (obj instanceof TotalAttendance
                && totalAttendance == ((TotalAttendance) obj).totalAttendance);
    }
}
