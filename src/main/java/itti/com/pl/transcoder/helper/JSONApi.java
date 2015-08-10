package itti.com.pl.transcoder.helper;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JSONApi {

	private static ObjectMapper mapper = new ObjectMapper();

	public static String objectToJson(Object object){
		try {
			return mapper.writeValueAsString(object);
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
}
