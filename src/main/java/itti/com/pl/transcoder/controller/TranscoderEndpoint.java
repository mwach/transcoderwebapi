package itti.com.pl.transcoder.controller;

import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import itti.com.pl.transcoder.dto.Bitrate;
import itti.com.pl.transcoder.dto.Configuration;
import itti.com.pl.transcoder.dto.GetConfigurationResponse;
import itti.com.pl.transcoder.dto.SetConfigurationRequest;
import itti.com.pl.transcoder.dto.Size;

@Endpoint
public class TranscoderEndpoint {

	private static final String NAMESPACE_URI  = "http://dto.transcoder.pl.com.itti";

	@PayloadRoot(namespace = NAMESPACE_URI, localPart="getConfigurationRequest")
	@ResponsePayload
	public GetConfigurationResponse getConfiguration(){
		GetConfigurationResponse response = new GetConfigurationResponse();
		Configuration c = new Configuration();
		c.setBitrate(Bitrate.EUR);
		c.setFps(34);
		c.setSize(Size.HD);
		response.setConfiguration(c);
		return response;
	}

	@PayloadRoot(namespace = NAMESPACE_URI, localPart="setConfigurationRequest")
	public void setConfiguration(@RequestPayload SetConfigurationRequest request) throws SoapException{
		
		request.setConfiguration(new Configuration());
		request = null;;
		throw new SoapException("sdas");
	}

}
