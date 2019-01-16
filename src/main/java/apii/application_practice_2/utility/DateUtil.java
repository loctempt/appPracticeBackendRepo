package apii.application_practice_2.utility;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtil {
    /**
     * 返回从当前时间起，days天后的Date对象
     * @param days 天数偏移量
     * @return Date对象
     */
    public static Date dateAfter(int days) {
        Calendar dateVar = Calendar.getInstance();
        dateVar.add(Calendar.DATE, days);
        return dateVar.getTime();
    }

    public static String parseDateObject(Date day) {
        SimpleDateFormat format = new SimpleDateFormat("MM月dd日");
        return format.format(day);
    }
}
