package apii.application_practice_2.controller;

import apii.application_practice_2.domain.Doctor;
import apii.application_practice_2.domain.DoctorRepo;
import apii.application_practice_2.domain.DoctorScheduleRepo;
import apii.application_practice_2.request.DoctorInformationBrief;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/information")
public class InformationController {

    private DoctorRepo doctorRepo;
    private DoctorScheduleRepo doctorScheduleRepo;

    @Autowired
    InformationController(DoctorRepo doctorRepo, DoctorScheduleRepo doctorScheduleRepo) {
        this.doctorRepo = doctorRepo;
        this.doctorScheduleRepo = doctorScheduleRepo;
    }

    @RequestMapping("/all_doctors")
    List<Doctor> getAllDoctors(){
        return  doctorRepo.findAll();
    }

    @RequestMapping("/doctor_information")
    Doctor getDoctorInformation(@RequestBody Map<String, String> requestMap) {
        int doctorId = Integer.valueOf(requestMap.get("doctorId"));
        return doctorRepo.findByDoctorId(doctorId);
    }

    @RequestMapping("/doctor_information_brief")
    DoctorInformationBrief getDoctorInformationBrief(@RequestBody Map<String, String> requestMap) {
        int doctorId = Integer.valueOf(requestMap.get("doctorId"));
        Doctor doctor = doctorRepo.findByDoctorId(doctorId);
        DoctorInformationBrief doctorInformationBrief = new DoctorInformationBrief();
        doctorInformationBrief.setDoctorDepartment(doctor.getDoctorDepartment());
        doctorInformationBrief.setDoctorName(doctor.getDoctorName());
        doctorInformationBrief.setDoctorPositionalTitle(doctor.getDoctorPositionalTitle());
        return doctorInformationBrief;
    }
}
