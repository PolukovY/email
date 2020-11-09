package com.levik.mailsender;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

@ActiveProfiles("test")
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class MailSenderApplicationTests extends BaseIntegrationTests {

	private static final String HOST = "http://localhost:%d%s";

	@LocalServerPort
	private int port;

	private TestRestTemplate restTemplate;


	@BeforeEach
	void setUp() {
		this.restTemplate = new TestRestTemplate();
	}

	@Test
	void shouldGetActuatorInfo() {
		//given
		String url = getHost("/actuator/info");

		//when
		ResponseEntity<Object> forEntity = restTemplate.getForEntity(url, Object.class);

		//then
		assertThat(forEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
	}

	public String getHost(String path) {
		return String.format(HOST, port, path);
	}

}
