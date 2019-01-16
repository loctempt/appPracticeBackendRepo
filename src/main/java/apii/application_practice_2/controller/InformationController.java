package apii.application_practice_2.controller;

import apii.application_practice_2.domain.Doctor;
import apii.application_practice_2.domain.DoctorRepo;
import apii.application_practice_2.domain.DoctorSchedule;
import apii.application_practice_2.domain.DoctorScheduleRepo;
import apii.application_practice_2.request.DoctorInformationRequest;
import apii.application_practice_2.request.DoctorScheduleRequest;
import apii.application_practice_2.request.DoctorsOfDepartmentRequest;
import apii.application_practice_2.response.*;
import apii.application_practice_2.utility.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;

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

    // 获取医生一个月内的坐诊时间表
    @RequestMapping("/doctor_schedule")
    GeneralResponse getDoctorSchedule(@RequestBody DoctorScheduleRequest doctorScheduleRequest) {
        int doctorId = doctorScheduleRequest.getDoctorId();
        Date beginDate, endDate;
        // 获取今日Date
        beginDate = DateUtil.dateAfter(0);
        // 获取30日后Date
        endDate = DateUtil.dateAfter(30);
        System.out.println("begin date: " + beginDate.toString());
        System.out.println("end date: " + endDate.toString());
        List<DoctorSchedule> schedules = doctorScheduleRepo.
                findByDoctorIdAndDoctorOnDutyDateBetweenOrderByDoctorOnDutyDateAscDoctorOnDutyTimeAsc(
                        doctorId, beginDate, endDate);
        return DoctorScheduleResponse.getDoctorScheduleResponse(schedules, "已获得医生日程表");
    }

    // 获取科室医生列表
    @RequestMapping("/doctor_list")
    GeneralResponse getDoctorList(@RequestBody DoctorsOfDepartmentRequest doctorsOfDepartmentRequest) {
//        return doctorRepo.findAll();
        String doctorDepartment = doctorsOfDepartmentRequest.getDoctorDepartment();
        System.out.println("doctor department: " + doctorDepartment);
        return new DoctorListResponse().getResponse(doctorRepo.findByDoctorDepartment(doctorDepartment), "已获得医生列表");
    }

    // 获取科室列表
    @RequestMapping("/department_list")
    GeneralResponse getDepartmentList() {
        return new DepartmentListResponse().getResponse(doctorRepo.getDepartmentList(), "已获得科室列表");
    }

    // 获取医生详细信息
    @RequestMapping("/doctor_information")
    GeneralResponse getDoctorInformation(@RequestBody DoctorInformationRequest informationRequest) {
        int doctorId = informationRequest.getDoctorId();
        Doctor doctor = doctorRepo.findByDoctorId(doctorId);
        return new DoctorInformationResponse().getResponse(doctor, "已获得医生详细信息");
    }

    // 获取医生简略信息
    @RequestMapping("/doctor_information_brief")
    GeneralResponse getDoctorInformationBrief(@RequestBody DoctorInformationRequest informationRequest) {
        int doctorId = informationRequest.getDoctorId();
        Doctor doctor = doctorRepo.findByDoctorId(doctorId);
        return new DoctorInformationBriefResponse().getResponse(doctor, "已获得医生简略信息");
    }
}
