package apii.application_practice_2.utility;

import java.util.HashMap;
import java.util.Map;

public class GeneralResponse {
    public GeneralResponse(String status, String message, HashMap<String, String> extra){
        this.status = status;
        this.message = message;
        this.extra = extra;
    }
    public static GeneralResponse success(String message){
        return new GeneralResponse("ok", message, new HashMap<>());
    }
    public static GeneralResponse failure(String message){
        return new GeneralResponse("failed", message, new HashMap<>());
    }
    public static GeneralResponse success(){
        return new GeneralResponse("ok", "", new HashMap<>());
    }
    public static GeneralResponse failure(){
        return new GeneralResponse("failed", "", new HashMap<>());
    }
    private String status;
    private String message;
    private Map<String, String> extra;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Map<String, String> getExtra() {
        return extra;
    }

    public void setExtra(Map<String, String> extra) {
        this.extra = extra;
    }
}
