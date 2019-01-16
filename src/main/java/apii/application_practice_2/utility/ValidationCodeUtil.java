package apii.application_practice_2.utility;

import com.cloopen.rest.sdk.CCPRestSmsSDK;

import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class ValidationCodeUtil {
    public static final int VALIDATION_CODE_NOT_VALID = -1;
    public static final int VALIDATION_CODE_INCORRECT = -2;
    public static final int VALIDATION_CODE_OK = 0;

    private static final int KEEP_ALIVE_TIME = 10;

    private static final String SID = "8a216da8681259360168450b86be1797";
    private static final String APPID = "8a216da8681259360168450b870d179d";
    private static final String AUTH_CODE = "2412a26901bb44a5b1837d7da45fa820";

    /**
     * 判断验证码是否正确且未过期
     *
     * @param userValidationCode    用户输入的验证码
     * @param sessionValidationCode Session保存的验证码
     * @param timestamp             Session字段创建时的时间戳
     * @return 验证码是否正确且未过期
     */
    public static int isValidationCodeCorrect(String userValidationCode, String sessionValidationCode, long timestamp) {
        Date currentDate = new Date(), sessionDate = new Date(timestamp);
        long diff = TimeUnit.MINUTES.toMillis(KEEP_ALIVE_TIME);
        long currentTime = currentDate.getTime();
        long sessionTime = sessionDate.getTime();
        boolean isValid = currentTime - sessionTime <= diff;
        boolean isCorrect = userValidationCode.equals(sessionValidationCode);
        if(!isValid)
            return VALIDATION_CODE_NOT_VALID;
        if(!isCorrect)
            return VALIDATION_CODE_INCORRECT;
        return VALIDATION_CODE_OK;
    }

    /**
     * 生成四位数验证码
     *
     * @return 四位数验证码
     */
    public static String generateValidationCode() {
        Date date = new Date();
        Random rand = new Random(date.getTime());
        int validationCode = rand.nextInt(9999);
        return String.format("%04d", validationCode);
    }

    /**
     * 发送验证码给指定手机号
     *
     * @param target 指定手机号
     * @return 发送成功与否，true表示成功，false表示失败
     * @throws IOException IO异常
     */
    public static boolean sendSMS(String target, HttpSession session) throws IOException {
        CCPRestSmsSDK restAPI = new CCPRestSmsSDK();
        restAPI.init("app.cloopen.com", "8883");
        restAPI.setAccount(SID, AUTH_CODE);
        restAPI.setAppId(APPID);
        String validationCode = generateValidationCode();
        HashMap<String, Object> resultMap = restAPI.sendTemplateSMS(
                target,
                "1",
                new String[]{validationCode, String.valueOf(KEEP_ALIVE_TIME)}
        );
        if (resultMap.get("statusCode").equals("000000")) {
            session.setAttribute(SessionSchema.VALIDATION_CODE, validationCode);
            session.setAttribute(SessionSchema.VALIDATION_TIMESTAMP, new Date().getTime());
            System.out.println("验证码已发出："+validationCode);
            return true;
        } else {
            System.out.println("错误码=" + resultMap.get("statusCode") + " 错误信息= " + resultMap.get("statusMsg"));
            return false;
        }
    }
}
