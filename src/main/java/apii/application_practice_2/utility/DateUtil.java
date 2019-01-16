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
        dateVar.set(Calendar.HOUR_OF_DAY, 0);
        dateVar.set(Calendar.MINUTE, 0);
        dateVar.set(Calendar.SECOND, 0);
        dateVar.add(Calendar.DATE, days);
        return dateVar.getTime();
    }

    /**
     * 格式化Date对象
     * @param day Date实例
     * @return 格式化的Date对象
     */
    public static String parseDateObject(Date day) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy年MM月dd日");
        return format.format(day);
    }

}
