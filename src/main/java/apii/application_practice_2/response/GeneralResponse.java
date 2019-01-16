package apii.application_practice_2.response;

import java.util.HashMap;
import java.util.Map;

public class GeneralResponse {
    private String status;
    private String message;
    private Object extra;

    GeneralResponse(String status, String message, Object extra) {
        this.status = status;
        this.message = message;
        this.extra = extra;
    }

    GeneralResponse(GeneralResponse generalResponse) {
        this.status = generalResponse.status;
        this.message = generalResponse.message;
        this.extra = generalResponse.extra;
    }

    public static GeneralResponse success(String message) {
        return new GeneralResponse("ok", message, new HashMap<>());
    }

    public static GeneralResponse failure(String message) {
        return new GeneralResponse("failed", message, new HashMap<>());
    }

    static GeneralResponse success() {
        return new GeneralResponse("ok", "", new HashMap<>());
    }

    static GeneralResponse failure() {
        return new GeneralResponse("failed", "", new HashMap<>());
    }


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

    public Object getExtra() {
        return extra;
    }

    public GeneralResponse setExtra(Object extra) {
        this.extra = extra;
        return this;
    }
}
