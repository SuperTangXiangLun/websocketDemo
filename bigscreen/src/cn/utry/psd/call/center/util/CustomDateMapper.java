package cn.utry.psd.call.center.util;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;







import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.JsonSerializer;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.SerializerProvider;
import org.codehaus.jackson.map.ser.CustomSerializerFactory;
import org.springframework.stereotype.Component;

/**
 * 
 * @ClassName: CustomDateMapper 
 * @Description: 返回json 时间装换
 * @author chenqinglin
 * @date 2015年3月17日 下午5:03:41
 */
@Component("customDateMapper")
public class CustomDateMapper extends ObjectMapper{
	
	public CustomDateMapper() {
		CustomSerializerFactory factory = new CustomSerializerFactory();
		factory.addGenericMapping(Date.class, new JsonSerializer<Date>() {
			@Override
			public void serialize(Date value, JsonGenerator jsonGenerator,SerializerProvider provider) throws IOException,
					JsonProcessingException {
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				jsonGenerator.writeString(sdf.format(value));
			}
		});
		this.setSerializerFactory(factory);
	}
}
