package cn.utry.psd.call.center.monitor.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = "/bigScreenTwoController")
public class BigScreenTwoController {
	@RequestMapping(value = "/bigScreenTwoInit")
	public String generalOverviewInit() {
		return "bigscreen/bigScreentwo";
	}
}
