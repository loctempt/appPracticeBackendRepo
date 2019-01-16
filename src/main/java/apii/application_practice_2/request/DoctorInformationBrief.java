package apii.application_practice_2.request;

public class DoctorInformationBrief {
    private String doctorName;
    private String doctorDepartment;
    private String doctorPositionalTitle;

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
}
