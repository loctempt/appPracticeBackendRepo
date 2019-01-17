package apii.application_practice_2.response;

import apii.application_practice_2.domain.ReservationListItem;
import apii.application_practice_2.utility.DateUtil;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class UserReservationResponse {

    public GeneralResponse getResponse(List<Object[]> future, List<Object[]> history, String message) {
        ArrayList<ReservationListItem> responseList = new ArrayList<>();
        for (Object[] i : future) {
            appendListItem(responseList, i, false);
        }
        for (Object[] i : history) {
            appendListItem(responseList, i, true);
        }
        return GeneralResponse.success(message).setExtra(responseList);
    }

    private void appendListItem(ArrayList<ReservationListItem> arrayList, Object[] i, boolean overdue) {
        ReservationListItem item = new ReservationListItem(
                (String) i[0],
                (String) i[1],
                DateUtil.parseDateObject((Date) i[2]),
                (Integer) i[3],
                (String) i[4],
                overdue
        );
        arrayList.add(item);
    }
}
