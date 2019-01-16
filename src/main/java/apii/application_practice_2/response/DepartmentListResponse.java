package apii.application_practice_2.response;

public class DepartmentListResponse extends ResponseBase {
    @Override
    public GeneralResponse getResponse(Object obj, String message) {
        return GeneralResponse.success(message).setExtra(obj);
    }
}
