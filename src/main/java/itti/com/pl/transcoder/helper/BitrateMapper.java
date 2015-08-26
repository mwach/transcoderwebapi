package itti.com.pl.transcoder.helper;

import itti.com.pl.transcoder.dto.Bitrate;

public class BitrateMapper {

	public static final int getBitrate(Bitrate bitrate){
		switch (bitrate) {
		case MB_05:
			return 500;
		case MB_1:
			return 1000;
		case MB_2:
			return 2000;
		case MB_4:
			return 4000;
		}
		return 0;
	}
}
