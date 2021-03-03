package com.wczx.api.response;

import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author Administrator
 */
@Controller
public class CommonReturnController {

	private Logger logger = LoggerFactory.getLogger(getClass());


	/**
	 *
	 * @param response
	 */
	protected void commonResponse(HttpServletResponse response, WorkResponse workResponse) {
		try {
			response.setContentType("application/json;charset=UTF-8");
			response.setHeader("Access-Control-Allow-Origin", "*");
			JSONObject jsonObject = (JSONObject) JSONObject.toJSON(workResponse);
			String json = jsonObject.toString();
			logger.info("response data : " + json);
			response.getWriter().println(json);
		} catch (IOException e) {
			logger.error(String.valueOf(e));
			return;
		}
	}


}
