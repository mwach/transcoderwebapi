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

@Service
public class TranscoderFacade {

	@Autowired
	private Environment environment;

	private Process process = null;
	private Configuration configuration = null;

	@PostConstruct
	private void postConstruct(){
		configuration = new Configuration();
		configuration.setFps(environment.getProperty("fps", Integer.class));
		configuration.setBitrate(environment.getProperty("bitrate", Bitrate.class));
		configuration.setSize(environment.getProperty("size", Size.class));
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
		this.configuration = configuration;
	}

	public Configuration getConfiguration() {
		return configuration;
	}

	public State getState() {
		return process != null ? State.RUNNING : State.STOPPED;
	}

}
