package apii.application_practice_2.response;

import apii.application_practice_2.domain.DoctorSchedule;
import apii.application_practice_2.utility.DateUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class DoctorScheduleResponse {

    private String scheduleId;
    private String doctorId;
    private String doctorOnDutyDate;
    private String doctorOnDutyTime;
    private String available;

    public DoctorScheduleResponse(String scheduleId, String doctorId, String doctorOnDutyDate, String doctorOnDutyTime, String available) {
        this.scheduleId = scheduleId;
        this.doctorId = doctorId;
        this.doctorOnDutyDate = doctorOnDutyDate;
        this.doctorOnDutyTime = doctorOnDutyTime;
        this.available = available;
    }

    public String getScheduleId() {
        return scheduleId;
    }

    public void setScheduleId(String scheduleId) {
        this.scheduleId = scheduleId;
    }

    public String getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(String doctorId) {
        this.doctorId = doctorId;
    }

    public String getDoctorOnDutyDate() {
        return doctorOnDutyDate;
    }

    public void setDoctorOnDutyDate(String doctorOnDutyDate) {
        this.doctorOnDutyDate = doctorOnDutyDate;
    }

    public String getDoctorOnDutyTime() {
        return doctorOnDutyTime;
    }

    public void setDoctorOnDutyTime(String doctorOnDutyTime) {
        this.doctorOnDutyTime = doctorOnDutyTime;
    }

    public String getAvailable() {
        return available;
    }

    public void setAvailable(String available) {
        this.available = available;
    }

    public static GeneralResponse getDoctorScheduleResponse(List<DoctorSchedule> schedules, String message) {
        ArrayList<DoctorScheduleResponse> list = new ArrayList<>();
        for (DoctorSchedule i : schedules) {
            list.add(new DoctorScheduleResponse(
                    String.valueOf(i.getScheduleId()),
                    String.valueOf(i.getDoctorId()),
                    DateUtil.parseDateObject(i.getDoctorOnDutyDate()),
                    DoctorSchedule.parseOnDutyTime(i.getDoctorOnDutyTime()),
                    String.valueOf(i.getPatientCount() < 10)
            ));
        }
        GeneralResponse response = GeneralResponse.success();
        response.setExtra(list);
        response.setMessage(message);
        return response;
    }
}
