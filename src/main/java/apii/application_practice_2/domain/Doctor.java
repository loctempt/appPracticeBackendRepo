package apii.application_practice_2.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Doctor {
    @Id
    @GeneratedValue
    private int doctorId;
    private String doctorName;
    private String doctorDepartment;
    private String doctorJob;
    private String doctorPositionalTitle;
    private String doctorTel;
    private String doctorIntroduction;

    public String getDoctorJob() {
        return doctorJob;
    }

    public void setDoctorJob(String doctorJob) {
        this.doctorJob = doctorJob;
    }

    public String getDoctorName() {
        return doctorName;
    }

    public void setDoctorName(String doctorName) {
        this.doctorName = doctorName;
    }

    public int getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(int doctorId) {
        this.doctorId = doctorId;
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

    public String getDoctorTel() {
        return doctorTel;
    }

    public void setDoctorTel(String doctorTel) {
        this.doctorTel = doctorTel;
    }

    public String getDoctorIntroduction() {
        return doctorIntroduction;
    }

    public void setDoctorIntroduction(String doctorIntroduction) {
        this.doctorIntroduction = doctorIntroduction;
    }
}
