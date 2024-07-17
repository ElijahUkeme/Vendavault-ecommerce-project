package com.vendavaultecommerceproject.admin.report.utils;

import org.threeten.bp.LocalDate;
import org.threeten.bp.YearMonth;

public class MonthlyReportUtil {
    public static boolean isLastDayOfTheMonth(){
        LocalDate localDate = YearMonth.now().atEndOfMonth();
        if (LocalDate.now().equals(localDate)){
            return true;
        }
        return false;
    }
}
