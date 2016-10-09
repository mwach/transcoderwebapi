package itti.com.pl.transcoder.service;

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import itti.com.pl.transcoder.config.TestConfig;
import itti.com.pl.transcoder.dto.Bitrate;
import itti.com.pl.transcoder.dto.Configuration;
import itti.com.pl.transcoder.dto.Size;
import itti.com.pl.transcoder.helper.SocketApi;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfig.class, 
loader = AnnotationConfigContextLoader.class)
@ActiveProfiles(profiles = "dev")
public class TranscoderFacadeTest {

	@Autowired
	private TranscoderFacade transcoderFacade;

	@Autowired
	private Environment environment;
	private SocketApi socketApi = null;

	@Test
	public void beforeClass() throws Exception{

		Assert.assertNotNull(environment);
		Assert.assertNotNull(environment.getProperty("size"));
		socketApi = Mockito.mock(SocketApi.class);		
		transcoderFacade.setSocketAPI(socketApi);
		transcoderFacade.start();
	}

	private Configuration createConfiguration(Bitrate bitrate, int fps, Size size){
		Configuration configuration = new Configuration();
		configuration.setBitrate(bitrate);
		configuration.setFps(fps);
		configuration.setSize(size);
		return configuration;
	}

}
