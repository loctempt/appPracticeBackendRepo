package apii.application_practice_2.response;

import apii.application_practice_2.domain.Doctor;

public class DoctorInformationBriefResponse extends ResponseBase{
    private int doctorId;
    private String doctorName;
    private String doctorDepartment;
    private String doctorPositionalTitle;

    public DoctorInformationBriefResponse() {
    }

    private DoctorInformationBriefResponse(int doctorId, String doctorName, String doctorDepartment, String doctorPositionalTitle) {
        this.doctorId = doctorId;
        this.doctorName = doctorName;
        this.doctorDepartment = doctorDepartment;
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

    public String getDoctorDepartment() {
        return doctorDepartment;
    }

    public void setDoctorDepartment(String doctorDepartment) {
        this.doctorDepartment = doctorDepartment;
    }

    public String getDoctorPositionalTitle() {
        return doctorPositionalTitle;
    }

    public void setDoctorPositionalTitle(String doctorPositionalTitle) {
        this.doctorPositionalTitle = doctorPositionalTitle;
    }

    @Override
    public GeneralResponse getResponse(Object obj, String message) {
        Doctor doctor = (Doctor) obj;
        DoctorInformationBriefResponse dibr = new DoctorInformationBriefResponse(
                doctor.getDoctorId(),
                doctor.getDoctorName(),
                doctor.getDoctorDepartment(),
                doctor.getDoctorPositionalTitle()
        );
        return GeneralResponse.success(message).setExtra(dibr);
    }
}
