package itti.com.pl.transcoder.web.ws;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import itti.com.pl.transcoder.dto.GetConfigurationResponse;
import itti.com.pl.transcoder.dto.GetEncoderStateResponse;
import itti.com.pl.transcoder.dto.SetConfigurationRequest;
import itti.com.pl.transcoder.service.TranscoderFacade;

@Endpoint
public class TranscoderEndpoint {

	private static final String NAMESPACE_URI  = "http://dto.transcoder.pl.com.itti";

	@Autowired
	private TranscoderFacade transcoderFacade;

	@PayloadRoot(namespace = NAMESPACE_URI, localPart="startEncoderRequest")
	public void start() throws SoapException{

		transcoderFacade.start();
	}

	@PayloadRoot(namespace = NAMESPACE_URI, localPart="stopEncoderRequest")
	public void stop() throws SoapException{
		
		transcoderFacade.stop();
	}

	@PayloadRoot(namespace = NAMESPACE_URI, localPart="getEncoderStateRequest")
	@ResponsePayload
	public GetEncoderStateResponse status() throws SoapException{
		
		GetEncoderStateResponse response = new GetEncoderStateResponse();
		response.setState(transcoderFacade.getState());
		return response;
	}

	@PayloadRoot(namespace = NAMESPACE_URI, localPart="getConfigurationRequest")
	@ResponsePayload
	public GetConfigurationResponse getConfiguration(){
		GetConfigurationResponse response = new GetConfigurationResponse();
		response.setConfiguration(transcoderFacade.getConfiguration());
		return response;
	}

	@PayloadRoot(namespace = NAMESPACE_URI, localPart="setConfigurationRequest")
	public void setConfiguration(@RequestPayload SetConfigurationRequest request) throws SoapException{

		transcoderFacade.setConfiguration(request.getConfiguration());
	}

}
