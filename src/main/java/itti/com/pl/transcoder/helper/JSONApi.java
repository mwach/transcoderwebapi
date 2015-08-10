package itti.com.pl.transcoder.helper;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public final class JSONApi {

	private static ObjectMapper mapper = new ObjectMapper();

	private JSONApi(){}

	public static String objectToJson(Object object){
		try {
			return mapper.writeValueAsString(object);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		return null;
	}
}
