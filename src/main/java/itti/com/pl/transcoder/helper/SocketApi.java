package itti.com.pl.transcoder.helper;

import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class SocketApi {

	private static final Log LOG = LogFactory.getLog(SocketApi.class);

	private String ip;
	private int port;

	public SocketApi(String ip, int port) {
		this.ip = ip;
		this.port = port;
	}

	public void writeToSocket(String data){
		LOG.info(String.format("Writing data to the socket: %s", data));
		writeToSocket(data.getBytes());
	}

	public void writeToSocket(byte[] data){
		
		try(Socket socket = new Socket(ip, port)){
			socket.getOutputStream().write(data);
			byte[] response = new byte[4096];
			InputStream is = socket.getInputStream();
			if(is.available() > 0){
				is.read(response, 0, is.available());
				LOG.info(String.format("Read data from socket: %s", new String(response)));
			}
		}catch (IOException e) {
		}
	}
}
