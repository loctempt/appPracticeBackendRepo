package apii.application_practice_2.controller;

import apii.application_practice_2.domain.*;
import apii.application_practice_2.request.DoctorInformationRequest;
import apii.application_practice_2.request.DoctorScheduleRequest;
import apii.application_practice_2.request.DoctorsOfDepartmentRequest;
import apii.application_practice_2.request.UserReservationRequest;
import apii.application_practice_2.response.*;
import apii.application_practice_2.utility.DateUtil;
import apii.application_practice_2.utility.SessionSchema;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.print.Doc;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/information")
public class InformationController {

    private DoctorRepo doctorRepo;
    private DoctorScheduleRepo doctorScheduleRepo;
    private ReservationRepo reservationRepo;

    @Autowired
    InformationController(DoctorRepo doctorRepo, DoctorScheduleRepo doctorScheduleRepo, ReservationRepo reservationRepo) {
        this.doctorRepo = doctorRepo;
        this.doctorScheduleRepo = doctorScheduleRepo;
        this.reservationRepo = reservationRepo;
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
//        int doctorId = informationRequest.getDoctorId();
        String doctorDepartment = informationRequest.getDoctorDepartment();
//        Doctor doctor = doctorRepo.findByDoctorId(doctorId);
        List<Doctor> doctors = doctorRepo.findByDoctorDepartment(doctorDepartment);
        return new DoctorInformationBriefResponse().getResponse(doctors, "已获得医生简略信息");
    }

    @RequestMapping("/user_reservation")
    GeneralResponse getUserReservation(HttpServletRequest request){
        HttpSession session = request.getSession();
        int userId = (Integer) session.getAttribute(SessionSchema.USER_ID);
        List<Object[]> reservationsFuture = reservationRepo.getFutureReservationList(userId, DateUtil.dateAfter(0));
        List<Object[]> reservationsHistory = reservationRepo.getHistoryReservationList(userId, DateUtil.dateAfter(0));
        return new UserReservationResponse().getResponse(reservationsFuture, reservationsHistory, "已获得预约列表");
    }
}
