package apii.application_practice_2.controller;

import apii.application_practice_2.domain.*;
import apii.application_practice_2.utility.DateUtil;
import apii.application_practice_2.response.GeneralResponse;
import apii.application_practice_2.utility.SessionSchema;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@RestController
@RequestMapping("/data_alter")
public class DataAlterController {

    private DoctorRepo doctorRepo;
    private DoctorScheduleRepo scheduleRepo;
    private ReservationRepo reservationRepo;

    @Autowired
    DataAlterController(DoctorRepo doctorRepo, DoctorScheduleRepo scheduleRepo, ReservationRepo reservationRepo) {
        this.doctorRepo = doctorRepo;
        this.scheduleRepo = scheduleRepo;
        this.reservationRepo = reservationRepo;
    }

    // 添加新的预约记录
    @RequestMapping("/new_reservation")
    GeneralResponse handleNewReservation(@RequestBody Reservation reservation, HttpServletRequest request){
        try {
            HttpSession session = request.getSession();
            int userId = (Integer) session.getAttribute(SessionSchema.USER_ID);
            // 记录userId
            reservation.setUserId(userId);
            // 按scheduleId查到日程，将其patientCount字段加一保存
            int scheduleId = reservation.getScheduleId();
            DoctorSchedule schedule = scheduleRepo.findByScheduleId(scheduleId);
            if(schedule.getPatientCount() >= 10)    // 如果已经预约满员，返回失败状态
                return GeneralResponse.failure("此时段人数已满");
            schedule.setPatientCount(schedule.getPatientCount() + 1);
            scheduleRepo.save(schedule);
            // 保存挂号信息
            reservationRepo.save(reservation);
            return GeneralResponse.success("预约成功");
        } catch (Exception e){
            return GeneralResponse.failure("出现异常");
        }
    }

    // 记录新的医生日程
    @RequestMapping("/new_schedule")
    GeneralResponse handleCreateSchedule(@RequestBody DoctorSchedule schedule) {
        try {
            scheduleRepo.save(schedule);
            int doctorId = schedule.getDoctorId();
            Doctor doctor = doctorRepo.findByDoctorId(doctorId);
            return GeneralResponse.success(String.format("已创建新日程：%s 医生， %s %s",
                    doctor.getDoctorName(),
                    DateUtil.parseDateObject(schedule.getDoctorOnDutyDate()),
                    DoctorSchedule.parseOnDutyTime(schedule.getDoctorOnDutyTime())
            ));
        } catch (Exception e) {
            return GeneralResponse.failure("创建新日程失败");
        }
    }

    // 记录新医生
    @RequestMapping("/new_doctor")
    GeneralResponse handleCreateDoctor(@RequestBody Doctor doctor) {
        try {
            doctorRepo.save(doctor);
            return GeneralResponse.success("已记录新医生：" + doctor.getDoctorName());
        } catch (Exception e) {
            return GeneralResponse.failure("记录新医生失败");
        }
    }

}
