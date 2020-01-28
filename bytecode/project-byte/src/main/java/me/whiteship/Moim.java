package me.whiteship;

public class Moim {
    public int maxNumberOfAttendees;
    public int numberOfEnrollment;

    public Boolean isEnrollmentFull() {
        if (maxNumberOfAttendees == 0) {
            return false;
        }
        if (numberOfEnrollment < maxNumberOfAttendees) {
            return false;
        }
        return true;
    }
}
