package cn.utry.psd.call.center.monitor.controller;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import utry.remote.client.IRemoteService;

@Controller
@RequestMapping(value = "/bigScreenController")
public class BigScreenController {

	@Autowired
	@Qualifier("remoteServiceHession")
	private IRemoteService remoteService;
	@RequestMapping(value = "/bigScreenInit")
	public String generalOverviewInit(ModelMap model) {
		model.put("newDate", getDateString());
		return "bigscreen/bigScreen";
	}
	@RequestMapping(value = "/test")
	public String test() {
		return "bigscreen/test";
	}
	public String getDateString() {
		Date today = new Date();
		Calendar resultDate = Calendar.getInstance();
		resultDate.setTime(today);
		resultDate.add(Calendar.YEAR, -0);
		resultDate.add(Calendar.MONTH, 0);
		resultDate.add(Calendar.DAY_OF_YEAR, 0);
		resultDate.add(Calendar.WEEK_OF_MONTH, 0);
		Date dt1 = resultDate.getTime();
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd E HH:mm");
		return format.format(dt1);
	}
}
