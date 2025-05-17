package com.yyjy.web;

import cn.hutool.core.util.ReUtil;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpUtil;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.util.Strings;
import org.junit.jupiter.api.Test;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
public class HttpTests {

	@Test
	public void testOkHttp() {
//		HashMap<String, Object> map = new HashMap<>();
//		map.put("query", "112.82.189.6");
//		map.put("resource_id", 6006);
//		map.put("oe", "utf8");


//
//		String s = HttpUtil.get("https://opendata.baidu.com/api.php", query);
//		System.out.println(s);

//		String t = """
//				{"status":"0","t":"","set_cache_time":"","data":[{"ExtendedLocation":"","OriginQuery":"20.205.243.166","SchemaVer":"","appinfo":"","disp_type":0,"fetchkey":"20.205.243.166","location":"北京市北京市 移动","origip":"20.205.243.166","origipquery":"20.205.243.166","resourceid":"6006","role_id":0,"schemaID":"","shareImage":1,"showLikeShare":1,"showlamp":"1","strategyData":{},"titlecont":"IP地址查询","tplt":"ip"}]}""";
//		String group1 = ReUtil.getGroup1("\"location\":\"(.*?)\"", t);
//
//		String regexp = "(.*?)(?=省|市)";
//
//		String one = ReUtil.getGroup0(regexp, group1);
//
//		log.info(one == null ? group1 : one);

		String regexp1 = "\"location\":\"(.*?)\"";
		String regexp2 = "(.*?)(?=省|市)";

		Map<String, Object> query = Map.of("query", "119.75.217.109", "resource_id", 6006, "oe", "utf8");
		String s = HttpUtil.get("https://opendata.baidu.com/api.php", query);

//		String s = HttpRequest.get("https://opendata.baidu.com/api.php").form(query).execute().body();

		String location = ReUtil.getGroup1(regexp1, s);

		String address = ReUtil.getGroup0(regexp2, location);


		if (address == null)
			log.info("location: {}", location);
		else
			log.info("address: {}", address);

//		List<String> list = List.of("北京市海淀区 百度", "江苏省南京市 移动", "台湾省", "浙江省杭州市 阿里云", "北京市北京市 移动", "上海市松江区", "日本", "广东省深圳市 世纪互");
//		for (String s : list) {
//			String one = ReUtil.getGroup0(regexp, s);
//			if (one == null)
//				one = s;
//			log.info(one);
//		}

	}
}
