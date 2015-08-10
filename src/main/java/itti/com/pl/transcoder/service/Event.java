package itti.com.pl.transcoder.service;

import java.util.HashMap;
import java.util.Map;

final class Event {

	private String action;
	private Map<String, Integer> params = new HashMap<>();
	int filterId;

	private Event(){}

	public static class EventBuilder{

		private Event event = new Event();

		public EventBuilder(){
		}
		public EventBuilder withAction(String action){
			event.action = action;
			return this;
		}
		public EventBuilder withParam(String key, int value){
			event.params.put(key, value);
			return this;
		}
		public EventBuilder withFilterId(int filterId){
			event.filterId = filterId;
			return this;
		}
		
		public Event build(){
			validateEvent();
			return event;
		}
		private void validateEvent() {
			if(event.action == null){
				throw new EventBuilderException("action not provided");
			}
			if(event.params.isEmpty()){
				throw new EventBuilderException("parameters not provided");
			}
			if(event.filterId == 0){
				throw new EventBuilderException("filterId not provided");
			}
		}
	}

}
