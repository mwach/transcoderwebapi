package itti.com.pl.transcoder.helper;

import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class ProcessHelper {

	private static final Log LOG = LogFactory.getLog(ProcessHelper.class);

	private final String SH_COMMAND;
	private final String PS_COMMAND;
	private final String KILL_COMMAND;

	public ProcessHelper(String shCmd, String psCommand, String killCommand) {
		SH_COMMAND = shCmd;
		PS_COMMAND = psCommand;
		KILL_COMMAND = killCommand;
	}

	public Process startProcess(String processCmd) {

		LOG.info(processCmd);
		Process process = null;
		ProcessBuilder builder = new ProcessBuilder(SH_COMMAND, "-c", processCmd);
		try {
			process = builder.start();
		} catch (IOException e) {
			LOG.warn(e.getLocalizedMessage(), e);
			destroyProcess(process);
			throw new RuntimeException("Could not create process");
		}

		return process;
	}

	public boolean isProcessRunning(String name) {

		return !getPid(name).isEmpty();
	}

	public String getPid(String name) {
		LOG.info(name);
		Process process = null;
		String pidCmd = String.format(PS_COMMAND, name);
		String pid = "";
		try {
			process = startProcess(pidCmd);
			logWarnNotEmpty(readStream(process.getErrorStream()));

			pid = readStream(process.getInputStream());
			logInfoNotEmpty(pid);
		} catch (IOException e) {
			LOG.warn(e.getLocalizedMessage(), e);
		} finally {
			destroyProcess(process);
		}
		return pid;
	}

	private String readStream(InputStream errorStream) throws IOException {
		byte[] output = new byte[1024];
		errorStream.read(output);
		return new String(output).trim();
	}

	public void destroyProcess(String name) {
		Process process = null;
		String pid = getPid(name);
		if (!pid.isEmpty()) {
			String killCmd = String.format(KILL_COMMAND, pid);
			try {
				process = startProcess(killCmd);

				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
				}

				logWarnNotEmpty(readStream(process.getErrorStream()));
				logInfoNotEmpty(readStream(process.getInputStream()));
			} catch (IOException e) {
				LOG.warn(e.getLocalizedMessage(), e);
			} finally {
				destroyProcess(process);
			}
		}
	}

	private void destroyProcess(Process process) {
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

	private void logWarnNotEmpty(String message){
		if(LOG.isWarnEnabled() && !message.isEmpty()){
			LOG.warn(message);
		}
	}

	private void logInfoNotEmpty(String message){
		if(LOG.isInfoEnabled() && !message.isEmpty()){
			LOG.info(message);
		}
	}
}
