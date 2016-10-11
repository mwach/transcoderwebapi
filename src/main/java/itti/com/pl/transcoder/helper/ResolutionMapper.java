package itti.com.pl.transcoder.helper;

import itti.com.pl.transcoder.dto.Resolution;

public class ResolutionMapper {

	public static Resolution getResolution(String resolutionStr) {
		Resolution resolution = new Resolution();
		resolution.setWidth(Integer.parseInt(resolutionStr.split("x")[0]));
		resolution.setHeight(Integer.parseInt(resolutionStr.split("x")[1]));
		return resolution;
	}

}
