package apii.application_practice_2.controller;

import apii.application_practice_2.domain.User;
import apii.application_practice_2.domain.UserRepo;
import apii.application_practice_2.request.RegisterRequest;
import apii.application_practice_2.response.GeneralResponse;
import apii.application_practice_2.utility.SessionSchema;
import apii.application_practice_2.utility.ValidationCodeUtil;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@RestController
public class RegisterController {

    private UserRepo userRepo;

    @Autowired
    public RegisterController(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    // 创建新账号
    @RequestMapping("/account_register")
    GeneralResponse handleAccountRegister(@RequestBody RegisterRequest registerRequest, HttpServletRequest request) {
        String userValidationCode = registerRequest.getValidationCode();
        String sessionValidationCode;
        long sessionTimestamp;
        HttpSession session = request.getSession();
        sessionValidationCode = (String) session.getAttribute(SessionSchema.VALIDATION_CODE);
        // 判断Session存在是否存在相关字段，同时完成赋值
        if (sessionValidationCode != null) {
            // 获取验证码创建时间戳
            sessionTimestamp = (long) session.getAttribute(SessionSchema.VALIDATION_TIMESTAMP);
            // 检查验证码是否正确，是否过期
            int validationCodeFlag = ValidationCodeUtil.isValidationCodeCorrect(userValidationCode, sessionValidationCode, sessionTimestamp);
            // 根据检查结果进行响应
            switch (validationCodeFlag) {
                case ValidationCodeUtil.VALIDATION_CODE_OK:
                    String username = registerRequest.getUsername();
                    // 检查用户是否已经存在
                    if (userRepo.usersHavingUsername(username) == 0) {
                        User user = new User();
                        user.setUsername(registerRequest.getUsername());
                        user.setUserPassword(DigestUtils.sha1Hex(registerRequest.getUserPassword()));
                        user.setUserTel(registerRequest.getTel());
                        userRepo.save(user);
                        return GeneralResponse.success("用户" + registerRequest.getUsername() + "已创建");
                    }
                    return GeneralResponse.failure("用户已存在");
                case ValidationCodeUtil.VALIDATION_CODE_INCORRECT:
                    return GeneralResponse.failure("验证码不正确");
                case ValidationCodeUtil.VALIDATION_CODE_NOT_VALID:
                    return GeneralResponse.failure("验证码已过期");
            }
        }
        return GeneralResponse.failure("发生异常");
    }
}
