package apii.application_practice_2.controller;

import apii.application_practice_2.domain.User;
import apii.application_practice_2.domain.UserRepo;
import apii.application_practice_2.utility.GeneralResponse;
import apii.application_practice_2.utility.SessionSchema;
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

    @RequestMapping("/login.json")
    GeneralResponse handleLogin(@RequestBody Map<String, String> requestJson, HttpServletRequest request) {
        String username = requestJson.get("username");
        String password = requestJson.get("userPassword");
        try {
            User user = userRepo.findByUsername(username);
            if (user.getUserPassword().equals(DigestUtils.sha1Hex(password))) {
                HttpSession session = request.getSession();
                session.setAttribute(SessionSchema.USERNAME, username);
                session.setMaxInactiveInterval((int) TimeUnit.DAYS.toMillis(7)); // Session有效期7天
                return GeneralResponse.success();
            }
            return GeneralResponse.failure("密码不正确");
        } catch (NullPointerException e) {
            return GeneralResponse.failure("用户不存在");
        }
    }

    // 注销登录
    @RequestMapping("/logout")
    GeneralResponse handleLogout(HttpServletRequest request) {
        HttpSession session = request.getSession();
        try {
            session.removeAttribute(SessionSchema.USERNAME);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return GeneralResponse.success("已注销登录");
    }
}
