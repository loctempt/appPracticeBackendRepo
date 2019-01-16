package apii.application_practice_2.controller;

import apii.application_practice_2.domain.*;
import apii.application_practice_2.request.RegisterRequest;
import apii.application_practice_2.utility.DateUtil;
import apii.application_practice_2.utility.GeneralResponse;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.print.Doc;

@RestController
@RequestMapping("/data_alter")
public class DataAlterController {

    private DoctorRepo doctorRepo;
    private UserRepo userRepo;
    private DoctorScheduleRepo scheduleRepo;

    @Autowired
    DataAlterController(DoctorRepo doctorRepo, UserRepo userRepo, DoctorScheduleRepo scheduleRepo) {
        this.doctorRepo = doctorRepo;
        this.userRepo = userRepo;
        this.scheduleRepo = scheduleRepo;
    }

    // 记录新的医生日程
    @RequestMapping("/new_schedule")
    GeneralResponse handleCreateSchedule(@RequestBody DoctorSchedule schedule) {
        try {
            scheduleRepo.save(schedule);
            int doctorId = schedule.getDoctorId();
            Doctor doctor = doctorRepo.findByDoctorId(doctorId);
            return GeneralResponse.success(String.format("已创建新日程：%s医生， %s %s",
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

    // 创建新账号
    @RequestMapping("/account_register")
    GeneralResponse handleAccountRegister(@RequestBody RegisterRequest registerRequest) {
        String username = registerRequest.getUsername();
        if (userRepo.usersHavingUsername(username) == 0) {
            User user = new User();
            user.setUsername(registerRequest.getUsername());
            user.setUserPassword(DigestUtils.sha1Hex(registerRequest.getUserPassword()));
            user.setUserTel(registerRequest.getTel());
            userRepo.save(user);
            return GeneralResponse.success("用户" + registerRequest.getUsername() + "已创建");
        }
        return GeneralResponse.failure("用户已存在");
    }
}
