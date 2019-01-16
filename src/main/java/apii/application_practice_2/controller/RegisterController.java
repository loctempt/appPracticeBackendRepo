package apii.application_practice_2.controller;

import apii.application_practice_2.domain.User;
import apii.application_practice_2.domain.UserRepo;
import apii.application_practice_2.request.RegisterRequest;
import apii.application_practice_2.utility.GeneralResponse;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RegisterController {

    private UserRepo userRepo;

    @Autowired
    public RegisterController(UserRepo userRepo) {
        this.userRepo = userRepo;
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
