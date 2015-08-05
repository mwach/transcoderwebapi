package itti.com.pl.transcoder.controller;

import java.util.UUID;

import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

@Endpoint
public class TranscoderEndpoint {

	private static final String NAMESPACE_URI  = "itti.com.pl.transcoderwebapi";

	@PayloadRoot(namespace = NAMESPACE_URI, localPart="getConfiguration")
	@ResponsePayload
	public String getConfiguration(){
		return UUID.randomUUID().toString();
	}

}
