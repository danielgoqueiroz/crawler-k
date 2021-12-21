package Utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Utils {

	public static Date parseDate(String date, String dateformat) throws ParseException {
		SimpleDateFormat format = new SimpleDateFormat(dateformat);
		return format.parse(date);
	
	}
	
}
