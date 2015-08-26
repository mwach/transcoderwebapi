package itti.com.pl.transcoder.helper;

import itti.com.pl.transcoder.dto.Size;

public class SizeMapper {

	public static final int getWidth(Size size){
		switch (size) {
		case FHD:
			return 1920;
		case HD:
			return 1280;
		case SD:
			return 720;
		case CIF:
			return 352;
		}
		return 0;
	}

	public static final int getHeight(Size size){
		switch (size) {
		case FHD:
			return 1080;
		case HD:
			return 720;
		case SD:
			return 480;
		case CIF:
			return 288;
		}
		return 0;
	}
}
