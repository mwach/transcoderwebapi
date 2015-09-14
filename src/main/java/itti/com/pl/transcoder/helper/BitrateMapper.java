package itti.com.pl.transcoder.helper;

import itti.com.pl.transcoder.dto.Bitrate;

public class BitrateMapper {

	public static final int getBitrate(Bitrate bitrate){
		switch (bitrate) {
		case MB_0125:
			return 128;
		case MB_025:
			return 256;
		case MB_05:
			return 512;
		case MB_1:
			return 1024;
		case MB_2:
			return 2048;
		case MB_4:
			return 4096;
		}
		return 0;
	}
}
