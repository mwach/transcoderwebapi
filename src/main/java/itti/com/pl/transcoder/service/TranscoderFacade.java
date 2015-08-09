package itti.com.pl.transcoder.service;

import org.springframework.stereotype.Service;

import itti.com.pl.transcoder.dto.Bitrate;
import itti.com.pl.transcoder.dto.Configuration;
import itti.com.pl.transcoder.dto.Size;
import itti.com.pl.transcoder.dto.State;

@Service
public class TranscoderFacade {

	public void start() {

	}

	public void stop() {

	}

	public void setConfiguration(Configuration configuration){
		
	}

	public Configuration getConfiguration(){
		Configuration c = new Configuration();
		c.setBitrate(Bitrate.EUR);
		c.setFps(34);
		c.setSize(Size.HD);
		return c;
	}

	public State getState() {
		return State.RUNNING;
	}

}
