package itti.com.pl.transcoder.service;

import java.util.HashMap;
import java.util.Map;

final class Event {

	private String action;
	private Map<String, Object> params = new HashMap<>();
	int filterId;

	private Event(){}

	public String getAction(){
		return action;
	}
	public int getFilterId(){
		return filterId;
	}
	public Map<String, Object> getParams(){
		return new HashMap<>(params);
	}
	public static class EventBuilder{

		private Event event = new Event();

		public EventBuilder(){
		}
		public EventBuilder withAction(String action){
			event.action = action;
			return this;
		}
		public EventBuilder withParam(String key, Object value){
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

		public Map<String, Event[]> buildOneItemList(){
			Map<String, Event[]> map = new HashMap<>();
			map.put("events", new Event[]{build()});
			return map;
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
