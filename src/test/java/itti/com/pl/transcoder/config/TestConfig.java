package itti.com.pl.transcoder.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;

@Configuration
@Profile("dev")
@PropertySource("classpath:transcoder.test.properties")
@ComponentScan({"itti.com.pl.transcoder.service"})
public class TestConfig {

//	@Autowired
//	private Environment environment;
}
