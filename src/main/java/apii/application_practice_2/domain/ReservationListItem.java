package apii.application_practice_2.domain;

import java.io.Serializable;

public class ReservationListItem implements Serializable {
    private static final long serialVersionUID = -6347911007178390219L;
    private String doctorName;
    private String reservationTime;
    private String doctorPositionalTitle;
    private String patientName;
    private boolean overdue;

    public ReservationListItem(String patientName, String doctorName, String reservationTime, int doctorOnDutyTime, String doctorPositionalTitle, boolean overdue) {
        this.doctorName = doctorName;
        this.reservationTime = reservationTime +DoctorSchedule.parseOnDutyTime(doctorOnDutyTime);
        this.doctorPositionalTitle = doctorPositionalTitle;
        this.patientName = patientName;
        this.overdue = overdue;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getDoctorName() {
        return doctorName;
    }

    public void setDoctorName(String doctorName) {
        this.doctorName = doctorName;
    }

    public String getReservationTime() {
        return reservationTime;
    }

    public void setReservationTime(String reservationTime) {
        this.reservationTime = reservationTime;
    }

    public String getDoctorPositionalTitle() {
        return doctorPositionalTitle;
    }

    public void setDoctorPositionalTitle(String doctorPositionalTitle) {
        this.doctorPositionalTitle = doctorPositionalTitle;
    }

    public String getPatientName() {
        return patientName;
    }

    public void setPatientName(String patientName) {
        this.patientName = patientName;
    }

    public boolean isOverdue() {
        return overdue;
    }

    public void setOverdue(boolean overdue) {
        this.overdue = overdue;
    }
}
