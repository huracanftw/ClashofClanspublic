package me.crolemol.coc.utils;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class PanelUtils {
	public static Long timeBetweenDates(Long earliestDate,Long latestDate){
		Long difference = latestDate - earliestDate;
		return difference;
	}
	public static String LongtoSimpleString(long time){
		String simpledate;
		if(time>1440){
			int day = (int) round(Math.ceil(time/1440),0);
			simpledate = day+" Days";
			return simpledate;
		}else if(time>60){
			int hour = (int) round(Math.ceil(time/60),0);
			simpledate = hour+" Hours";
			return simpledate;
		}else{
			int minute = (int) round(Math.ceil(time),0);
			simpledate = minute+" Minutes";
			return simpledate;
		}
	}
	public static double round(double value, int places) {
	    if (places < 0) throw new IllegalArgumentException();

	    BigDecimal bd = new BigDecimal(value);
	    bd = bd.setScale(places, RoundingMode.UP);
	    return bd.doubleValue();
	}
}
