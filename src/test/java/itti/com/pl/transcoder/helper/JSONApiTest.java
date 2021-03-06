package itti.com.pl.transcoder.helper;

import static org.junit.Assert.*;

import org.junit.Ignore;
import org.junit.Test;

import itti.com.pl.transcoder.dto.Bitrate;
import itti.com.pl.transcoder.dto.Configuration;
import itti.com.pl.transcoder.dto.Size;

public class JSONApiTest {

	@Test
	public void objectToJsonNull(){
		assertEquals("null", JSONApi.objectToJson(null));
	}

	@Test
	public void objectToJsonString(){
		assertEquals("\"string\"", JSONApi.objectToJson("string"));
	}

	@Test
	@Ignore
	public void objectToJsonConfiguration(){
		Configuration configuration = new Configuration();
		configuration.setBitrate(Bitrate.MB_05);
		configuration.setFps(30);
		configuration.setSize(Size.CIF);
		assertEquals("{\"bitrate\":\"MB_05\",\"fps\":30,\"size\":\"CIF\"}", JSONApi.objectToJson(configuration));
	}


}
