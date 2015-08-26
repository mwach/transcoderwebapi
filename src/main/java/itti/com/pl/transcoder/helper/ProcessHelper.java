package itti.com.pl.transcoder.helper;

import java.io.Closeable;
import java.io.IOException;

public class ProcessHelper {

	private final String SH_COMMAND;
	private final String PS_COMMAND;
	private final String KILL_COMMAND;

	public ProcessHelper(String shCmd, String psCommand, String killCommand) {
		SH_COMMAND = shCmd;
		PS_COMMAND = psCommand;
		KILL_COMMAND = killCommand;
	}

	public Process startProcess(String processCmd) {

		Process process = null;
		ProcessBuilder builder = new ProcessBuilder(SH_COMMAND, "-c", processCmd);
		try {
			process = builder.start();
		} catch (IOException e) {
			destroyProcess(process);
			throw new RuntimeException("Could not create process");
		}

		return process;
	}

	public boolean isProcessRunning(String name) {

		return !getPid(name).isEmpty();
	}

	public String getPid(String name) {
		Process process = null;
		String pidCmd = String.format(PS_COMMAND, name);
		String pid = "";
		try {
			process = startProcess(pidCmd);
			byte[] output = new byte[1024];
			byte[] err = new byte[1024];
			process.getInputStream().read(output);
			process.getErrorStream().read(err);
			String errStr = new String(err).trim();
			if(errStr.isEmpty()){
			}
			pid = new String(output).trim();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			destroyProcess(process);
		}
		return pid;
	}

	public void destroyProcess(String name) {
		Process process = null;
		String pid = getPid(name);
		if (!pid.isEmpty()) {
			String killCmd = String.format(KILL_COMMAND, pid);
			try {
				process = startProcess(killCmd);
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

}
