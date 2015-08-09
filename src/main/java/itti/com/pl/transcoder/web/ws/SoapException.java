package itti.com.pl.transcoder.web.ws;

import org.springframework.ws.soap.server.endpoint.annotation.FaultCode;
import org.springframework.ws.soap.server.endpoint.annotation.SoapFault;

@SoapFault(faultCode=FaultCode.SERVER)
public class SoapException extends Exception{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public SoapException(String msg){
		super(msg);
	}
}
