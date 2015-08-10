package itti.com.pl.transcoder.helper;

import java.io.IOException;
import java.net.Socket;

public class SocketApi {

	private String ip;
	private int port;

	public SocketApi(String ip, int port) {
		this.ip = ip;
		this.port = port;
	}

	public void writeToSocket(String data){
		writeToSocket(data.getBytes());
	}

	public void writeToSocket(byte[] data){
		
		try(Socket socket = new Socket(ip, port)){
			socket.getOutputStream().write(data);
			byte[] response = new byte[4096];
			socket.getInputStream().read(response, 0, response.length);
		}catch (IOException e) {
		}
	}
}
