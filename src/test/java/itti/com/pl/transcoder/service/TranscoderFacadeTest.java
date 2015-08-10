package itti.com.pl.transcoder.service;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import itti.com.pl.transcoder.dto.Bitrate;
import itti.com.pl.transcoder.dto.Configuration;
import itti.com.pl.transcoder.dto.Size;
import itti.com.pl.transcoder.helper.SocketApi;

public class TranscoderFacadeTest {

	private TranscoderFacade transcoderFacade = null;
	private SocketApi socketApi = null;

	@Before
	public void beforeTest(){
		
		socketApi = Mockito.mock(SocketApi.class);		
		transcoderFacade = new TranscoderFacade();
		transcoderFacade.setSocketAPI(socketApi);
		transcoderFacade.setConfiguration(new Configuration());
	}

	@Test
	public void setNewConfiguration(){
		Configuration configuration = createConfiguration(Bitrate.MB2, 34, Size.FHD);
		transcoderFacade.setConfiguration(configuration);
	}


	private Configuration createConfiguration(Bitrate bitrate, int fps, Size size){
		Configuration configuration = new Configuration();
		configuration.setBitrate(bitrate);
		configuration.setFps(fps);
		configuration.setSize(size);
		return configuration;
	}

}
