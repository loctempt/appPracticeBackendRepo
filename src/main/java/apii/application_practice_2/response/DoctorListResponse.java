package apii.application_practice_2.response;

import apii.application_practice_2.domain.Doctor;

import java.util.ArrayList;
import java.util.List;

public class DoctorListResponse extends ResponseBase {

    @Override
    public GeneralResponse getResponse(Object obj, String message) {
        return GeneralResponse.success(message).setExtra(obj);
    }
}
