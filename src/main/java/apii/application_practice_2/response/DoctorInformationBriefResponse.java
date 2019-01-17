package apii.application_practice_2.response;

import apii.application_practice_2.domain.Doctor;

import javax.print.Doc;
import java.util.ArrayList;
import java.util.List;

public class DoctorInformationBriefResponse extends ResponseBase {
    private int doctorId;
    private String doctorName;
    private String doctorJob;
    private String doctorPositionalTitle;

    public DoctorInformationBriefResponse() {
    }

    private DoctorInformationBriefResponse(int doctorId, String doctorName, String doctorJob, String doctorPositionalTitle) {
        this.doctorId = doctorId;
        this.doctorName = doctorName;
        this.doctorJob = doctorJob;
        this.doctorPositionalTitle = doctorPositionalTitle;
    }

    public int getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(int doctorId) {
        this.doctorId = doctorId;
    }

    public String getDoctorName() {
        return doctorName;
    }

    public void setDoctorName(String doctorName) {
        this.doctorName = doctorName;
    }

    public void setDoctorJob(String doctorJob) {
        this.doctorJob = doctorJob;
    }

    public String getDoctorJob() {
        return doctorJob;
    }

    public String getDoctorPositionalTitle() {
        return doctorPositionalTitle;
    }

    public void setDoctorPositionalTitle(String doctorPositionalTitle) {
        this.doctorPositionalTitle = doctorPositionalTitle;
    }

    @Override
    public GeneralResponse getResponse(Object obj, String message) {
        List<Doctor> doctors = (List<Doctor>) obj;
        ArrayList<DoctorInformationBriefResponse> doctorInformationBriefResponses = new ArrayList<>();
        for (Doctor i : doctors) {
            doctorInformationBriefResponses.add(new DoctorInformationBriefResponse(
                    i.getDoctorId(),
                    i.getDoctorName(),
                    i.getDoctorJob(),
                    i.getDoctorPositionalTitle()
            ));
        }
        return GeneralResponse.success(message).setExtra(doctorInformationBriefResponses);
    }
}
