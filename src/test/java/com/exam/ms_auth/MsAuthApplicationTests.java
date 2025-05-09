package com.exam.ms_auth;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootTest(properties = {
		"spring.cloud.vault.enabled=false",
		"spring.cloud.config.enabled=false",
		"eureka.client.enabled=false"
})
class MsAuthApplicationTests {

	@Test
	void contextLoads() {
	}

}