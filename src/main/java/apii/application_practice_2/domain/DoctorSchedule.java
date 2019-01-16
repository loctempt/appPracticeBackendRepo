package apii.application_practice_2.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Date;

@Entity
public class DoctorSchedule {
    @Id
    @GeneratedValue
    private int scheduleId; // 日程ID
    private int doctorId;   // 医生ID
    @Column(nullable = false)
    private Date doctorOnDutyDate;  // 坐诊日期
    private int doctorOnDutyTime;   // 坐诊时段（0-上午/1-下午）
    private int patientCount;       // 时段病人计数

    public int getScheduleId() {
        return scheduleId;
    }

    public void setScheduleId(int scheduleId) {
        this.scheduleId = scheduleId;
    }

    public int getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(int doctorId) {
        this.doctorId = doctorId;
    }

    public Date getDoctorOnDutyDate() {
        return doctorOnDutyDate;
    }

    public void setDoctorOnDutyDate(Date doctorOnDutyDate) {
        this.doctorOnDutyDate = doctorOnDutyDate;
    }

    public int getDoctorOnDutyTime() {
        return doctorOnDutyTime;
    }

    public void setDoctorOnDutyTime(int doctorOnDutyTime) {
        this.doctorOnDutyTime = doctorOnDutyTime;
    }

    public int getPatientCount() {
        return patientCount;
    }

    public void setPatientCount(int patientCount) {
        this.patientCount = patientCount;
    }

    public static final class OnDutyTime {
        public static final int FORENOON = 0;
        public static final int AFTERNOON = 1;
    }

    public static String parseOnDutyTime(int doctorOnDutyTime) {
        switch (doctorOnDutyTime) {
            case 0:
                return "上午";
            case 1:
                return "下午";
            default:
                return "上午";
        }
    }
}
