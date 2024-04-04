package com.example.MC;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@SpringBootTest
class McApplicationTests {

	@Test
	void contextLoads() {
	}

	@RequestMapping("/")
	@ResponseBody
	public Object DoTest(){

		return null;

	}
}
