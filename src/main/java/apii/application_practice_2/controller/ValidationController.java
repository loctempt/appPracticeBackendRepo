package apii.application_practice_2.controller;

import apii.application_practice_2.request.RequireValidationRequest;
import apii.application_practice_2.response.GeneralResponse;
import apii.application_practice_2.utility.ValidationCodeUtil;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@RestController
public class ValidationController {
    @RequestMapping("/require_validation")
    GeneralResponse handleRequireValidationCode(@RequestBody RequireValidationRequest validationRequest, HttpServletRequest request) throws IOException {
        HttpSession session = request.getSession();
        if(ValidationCodeUtil.sendSMS(validationRequest.getUserTel(), session)){
            return GeneralResponse.success("验证码已发送");
        }
        return GeneralResponse.failure("验证码发送失败");
    }
}
