package cn.utry.psd.call.center.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

/**
 * 
 * @ClassName: DateConverter 
 * @Description: 时间参数装换
 * @author chenqinglin
 * @date 2015年3月17日 下午5:04:02
 */
@Component
public class DateConverter implements Converter<String, Date> {

	private static final DateFormat DATEFORMAT = new SimpleDateFormat("yyyy-MM-dd");
	private static final DateFormat TIMEFORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	@Override
	public Date convert(String source) {

		if (!StringUtils.hasText(source)) {
			return null;
		}
		DateFormat dateFormat;
		if (source.contains(":")) {
			dateFormat = TIMEFORMAT;
		} else {
			dateFormat = DATEFORMAT;
		}
		dateFormat.setLenient(false);
		try {
			return dateFormat.parse(source);
		} catch (ParseException ex) {
			throw new IllegalArgumentException("Could not parse date: "+ ex.getMessage(), ex);
		}
	}

}
