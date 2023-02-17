package com.sicredi.votesms;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
class VotesMsApplicationTests {

	@Test
	void contextLoads() {

	}

	@Test
	public void initApplication() {
		VotesMsApplication.main(new String[]{});
	}

}
