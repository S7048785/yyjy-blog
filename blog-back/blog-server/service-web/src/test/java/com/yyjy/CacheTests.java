package com.yyjy;

import cn.hutool.core.util.ReUtil;
import cn.hutool.http.HttpUtil;
import com.yyjy.utils.CacheUtil;
import com.yyjy.utils.IpUtils;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@SpringBootTest
public class CacheTests {


	@Autowired
	private CacheUtil cacheUtil;
	String regexp1 = "\"location\":\"(.*?)\"";
	String regexp2 = "(.*?)(?=省|市 )";
	@Test
	public void test() {

		String ipAddress = getIpAddress("0:0:0:0:0:0:0:1");
		log.info("ipAddress: {}", ipAddress);
//		HashMap<String, String> stringStringHashMap = new HashMap<>();
//		Map<Object, Object> hashAll = cacheUtil.getHashAll("article:detail:4");
//		hashAll.forEach((k, v) -> {
//			stringStringHashMap.put(k.toString(), v.toString());
//		});
//		cacheUtil.setHashAll("article:detail:5", stringStringHashMap);

//		cacheUtil.setHash("article:detail:5", "viewCount", "1");
	}

	/**
	 * 获取ip属地
	 * @param ip
	 * @return 国内返回省份或地级市 国外返回国家
	 */
	public String getIpAddress(String ip) {
		String cityInfo = IpUtils.getCityInfo(ip);
		String[] split = cityInfo.split("\\|");
		if (!split[0].equals("中国"))
			return split[0];
		for (int i = 2; i > 0; i--) {
			if (!"0".equals(split[i])) {
				return split[i].replaceFirst("省|市", "");
			}
		}
		return "未知";
	}

	@Test
	void iptest(){
		// 获取属地信息
		String cityInfo = IpUtils.getCityInfo("223.104.150.223");
		System.out.println(cityInfo);

		// 测试城市名
		List<String> list = List.of("202.181.202.140", "218.60.56.12", "112.82.189.6", "106.52.62.38", "202.175.3.3", "168.95.192.1", "208.67.220.220", "202.216.228.18", "202.96.0.133");
		for (String string : list) {
			System.out.println(IpUtils.getIpAddress(string));
		}
	}


}
