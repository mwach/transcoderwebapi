package itti.com.pl.transcoder.service;

import java.io.Closeable;
import java.io.IOException;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import itti.com.pl.transcoder.dto.Bitrate;
import itti.com.pl.transcoder.dto.Configuration;
import itti.com.pl.transcoder.dto.Size;
import itti.com.pl.transcoder.dto.State;
import itti.com.pl.transcoder.helper.JSONApi;
import itti.com.pl.transcoder.helper.SocketApi;
import itti.com.pl.transcoder.service.Event.EventBuilder;

@Service
public class TranscoderFacade {

	private static final String ACTION = "configure";

	@Autowired
	private Environment environment;

	private Process process = null;
	private Configuration configuration = null;

	private SocketApi socketApi;

	@PostConstruct
	private void postConstruct(){
		configuration = new Configuration();
		configuration.setFps(environment.getProperty("fps", Integer.class));
		configuration.setBitrate(environment.getProperty("bitrate", Bitrate.class));
		configuration.setSize(environment.getProperty("size", Size.class));
		setConfiguration(configuration);

		setSocketAPI(new SocketApi(
				environment.getProperty("transcoder_admin_ip", String.class),
				environment.getProperty("transcoder_admin_port", Integer.class)));
	}

	void setSocketAPI(SocketApi socketApi) {
		this.socketApi = socketApi;
	}

	public void start() {

		if (getState() == State.STOPPED) {
			String startCmd = environment.getProperty("start_cmd");
			ProcessBuilder builder = new ProcessBuilder(startCmd.split(" "));
			try {
				process = builder.start();
			} catch (IOException e) {
				destroyProcess();
				throw new RuntimeException(e);
			}
		}
	}

	public void stop() {

		destroyProcess();
	}

	private void destroyProcess() {
		if (process != null) {
			closeStream(process.getErrorStream());
			closeStream(process.getInputStream());
			closeStream(process.getOutputStream());
			process.destroy();
			process = null;
		}
	}

	private void closeStream(Closeable stream) {
		try {
			stream.close();
		} catch (IOException e) {

		}
	}

	public void setConfiguration(Configuration configuration) {
		
		if(this.configuration == null){
			this.configuration = configuration;
		}
		if(isEqual(configuration.getFps(), this.configuration.getFps())){
			setFps(configuration.getFps());
		}
		if(isEqual(configuration.getBitrate(), this.configuration.getBitrate())){
			setBitrate(configuration.getBitrate());
		}
		if(isEqual(configuration.getSize(), this.configuration.getSize())){
			setSize(configuration.getSize());
		}
		this.configuration = configuration;
	}

	private boolean isEqual(Object first, Object second){
		if(first == null && second == null){
			return true;
		}else if(first == null || second == null){
			return false;
		}
		return first.equals(second);
	}

	private void setFps(int fps) {
		socketApi.writeToSocket(JSONApi.objectToJson(new EventBuilder().withAction(ACTION).withParam("fps", fps).withFilterId(1002).build()));
		socketApi.writeToSocket(JSONApi.objectToJson(new EventBuilder().withAction(ACTION).withParam("fps", fps).withFilterId(1001).build()));
		socketApi.writeToSocket(JSONApi.objectToJson(new EventBuilder().withAction(ACTION).withParam("fps", fps).withFilterId(1000).build()));
	}

	private void setBitrate(Bitrate bitrate) {
		socketApi.writeToSocket(JSONApi.objectToJson(new EventBuilder().withAction(ACTION).withParam("bitrate", bitrate.getBitrate()/4).withFilterId(1002).build()));
		socketApi.writeToSocket(JSONApi.objectToJson(new EventBuilder().withAction(ACTION).withParam("bitrate", bitrate.getBitrate()/2).withFilterId(1001).build()));
		socketApi.writeToSocket(JSONApi.objectToJson(new EventBuilder().withAction(ACTION).withParam("bitrate", bitrate.getBitrate()).withFilterId(1000).build()));
	}

	private void setSize(Size size) {
		socketApi.writeToSocket(JSONApi.objectToJson(new EventBuilder().withAction(ACTION).withParam("width", size.getWidth()/2).withParam("height", size.getHeight()/2).withFilterId(2002).build()));
		socketApi.writeToSocket(JSONApi.objectToJson(new EventBuilder().withAction(ACTION).withParam("width", size.getWidth()).withParam("height", size.getHeight()).withFilterId(2001).build()));
		socketApi.writeToSocket(JSONApi.objectToJson(new EventBuilder().withAction(ACTION).withParam("width", size.getWidth()).withParam("height", size.getHeight()).withFilterId(2000).build()));
	}

	public Configuration getConfiguration() {
		Configuration cfg = new Configuration();
		cfg.setBitrate(configuration.getBitrate());
		cfg.setFps(configuration.getFps());
		cfg.setSize(configuration.getSize());
		return cfg;
	}

	public State getState() {
		return process != null ? State.RUNNING : State.STOPPED;
	}

}
