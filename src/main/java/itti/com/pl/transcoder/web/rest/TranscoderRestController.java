package itti.com.pl.transcoder.web.rest;

import org.springframework.beans.factory.annotation.Autowired;
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
import itti.com.pl.transcoder.helper.ResolutionMapper;
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
			@RequestParam(required=false) Bitrate bitrate,
			@RequestParam(required=false) Integer fps,
			@RequestParam(required=false) Size size,
			@RequestParam(required=false) String resolution,
			@RequestParam(required=false) Integer gop
			) {
		if(resolution != null && size != null){
			throw new RuntimeException("Both resolution and size specified.");
		}
		Configuration configuration = new Configuration();
		configuration.setBitrate(bitrate);
		configuration.setFps(fps);
		configuration.setGop(gop);
		configuration.setSize(size);
		configuration.setResolution(ResolutionMapper.getResolution(resolution));
		transcoderFacade.setConfiguration(configuration);
	}

}