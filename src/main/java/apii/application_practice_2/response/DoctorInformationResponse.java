package apii.application_practice_2.response;

import apii.application_practice_2.domain.Doctor;

public class DoctorInformationResponse extends ResponseBase {
    public DoctorInformationResponse() {
    }

    @Override
    public GeneralResponse getResponse(Object obj, String message) {
        Doctor doctor = (Doctor) obj;
        return GeneralResponse.success(message).setExtra(doctor);
    }
}
