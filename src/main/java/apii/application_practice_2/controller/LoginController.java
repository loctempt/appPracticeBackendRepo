package apii.application_practice_2.controller;

import apii.application_practice_2.domain.User;
import apii.application_practice_2.domain.UserRepo;
import apii.application_practice_2.domain.UserTel;
import apii.application_practice_2.request.LoginTelRequest;
import apii.application_practice_2.response.GeneralResponse;
import apii.application_practice_2.utility.SessionSchema;
import apii.application_practice_2.utility.ValidationCodeUtil;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

//@SpringBootApplication
@RestController
public class LoginController {

    private final UserRepo userRepo;

    @Autowired
    public LoginController(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    @RequestMapping("/login.get")
    Map<String, String> getUserByGet(@RequestParam(value = "userName", required = false) String userName) {
        System.out.println("hello: " + userName);
        HashMap<String, String> ret = new HashMap<>();
        ret.put("name", userName);
        ret.put("digest", DigestUtils.sha1Hex(userName));
        System.out.println(DigestUtils.sha1Hex(userName));
        return ret;
    }

    // 账号密码登录
    @RequestMapping("/login.json")
    GeneralResponse handleLogin(@RequestBody Map<String, String> requestJson, HttpServletRequest request) {
        String username = requestJson.get("username");
        String password = requestJson.get("userPassword");
        System.out.println(username+" 尝试登录");
        try {
            User user = userRepo.findByUsername(username);
            if (user.getUserPassword().equals(DigestUtils.sha1Hex(password))) {
                HttpSession session = request.getSession();
                // Session记录用户名
                session.setAttribute(SessionSchema.USERNAME, username);
                session.setMaxInactiveInterval((int) TimeUnit.DAYS.toMillis(7)); // Session有效期7天
                // Session记录用户ID
                session.setAttribute(SessionSchema.USER_ID, user.getUserId());
                System.out.println(username + " 登录成功");
                return GeneralResponse.success("已登录");
            }
            return GeneralResponse.failure("密码不正确");
        } catch (NullPointerException e) {
            return GeneralResponse.failure("用户不存在");
        }
    }

    @RequestMapping("/login.tel")
    GeneralResponse handleLoginTel(@RequestBody LoginTelRequest loginTelRequest, HttpServletRequest request) {
        String userValidationCode = loginTelRequest.getValidationCode();
        String userTel = loginTelRequest.getUserTel();
        HttpSession session = request.getSession();
        String sessionValidationCode = (String) session.getAttribute(SessionSchema.VALIDATION_CODE);
        long sessionValidationTimestamp = (long) session.getAttribute(SessionSchema.VALIDATION_TIMESTAMP);
        if (sessionValidationCode != null) {
            int status = ValidationCodeUtil.
                    isValidationCodeCorrect(userValidationCode, sessionValidationCode, sessionValidationTimestamp);
            switch (status) {
                case ValidationCodeUtil.VALIDATION_CODE_OK:
                    session.setAttribute(SessionSchema.USER_ID, userTel);
                    User user = new User();
                    user.setUserId(Integer.valueOf(userTel));
                    userRepo.save(user);
                    return GeneralResponse.success("登录成功");
                case ValidationCodeUtil.VALIDATION_CODE_INCORRECT:
                    return GeneralResponse.failure("验证码输入有误");
                case ValidationCodeUtil.VALIDATION_CODE_NOT_VALID:
                    return GeneralResponse.failure("验证码已过期");
            }
        }
        return GeneralResponse.failure("发生异常");
    }

    // 注销登录
    @RequestMapping("/logout")
    GeneralResponse handleLogout(HttpServletRequest request) {
        HttpSession session = request.getSession();
        try {
            session.removeAttribute(SessionSchema.USER_ID);
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("已注销");
        return GeneralResponse.success("已注销登录");
    }

    // 登录状态
    @RequestMapping("/ping")
    GeneralResponse handlePing(HttpServletRequest request) {
        HttpSession session = request.getSession();
        if (session != null && session.getAttribute(SessionSchema.USER_ID) != null)
            return GeneralResponse.success("登录信息未过期");
        return GeneralResponse.failure("登录信息已过期");
    }
}
