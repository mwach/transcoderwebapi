package itti.com.pl.transcoder.web.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import itti.com.pl.transcoder.dto.Bitrate;
import itti.com.pl.transcoder.dto.Configuration;
import itti.com.pl.transcoder.dto.Size;
import itti.com.pl.transcoder.service.TranscoderFacade;

@Controller
public class TranscoderRestController {

	@Autowired
	private TranscoderFacade transcoderFacade;

	@RequestMapping("/start")
	@ResponseStatus(value=HttpStatus.OK)
	public void start() {
		transcoderFacade.start();
	}

	@RequestMapping("/stop")
	@ResponseStatus(value=HttpStatus.OK)
	public void stop() {
		transcoderFacade.stop();
	}

	@RequestMapping("/state")
	@ResponseBody
	public String state() {
		return String.valueOf(transcoderFacade.getState());
	}

	@RequestMapping(value="/configuration", method=RequestMethod.GET, produces={"application/json"})
	@ResponseBody
	public ResponseEntity<Configuration> getConfiguration() {
		return new ResponseEntity<Configuration>(
				transcoderFacade.getConfiguration(), HttpStatus.OK);
	}

	@RequestMapping(value="/configuration", method=RequestMethod.PUT)
	@ResponseStatus(value=HttpStatus.OK)
	public void setConfiguration(
			@RequestParam Bitrate bitrate,
			@RequestParam int fps,
			@RequestParam Size size) {
		Configuration configuration = new Configuration();
		configuration.setBitrate(bitrate);
		configuration.setFps(fps);
		configuration.setSize(size);
		transcoderFacade.setConfiguration(configuration);
	}

}