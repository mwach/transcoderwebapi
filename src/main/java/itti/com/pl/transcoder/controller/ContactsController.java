package itti.com.pl.transcoder.controller;

import java.util.UUID;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class ContactsController {

	@RequestMapping("/uuid")
	@ResponseBody
	public String getUUID() {
		return UUID.randomUUID().toString();
	}

	@RequestMapping("/ws/uuidname")
	@ResponseBody
	public String getUUIDWithName(
			@RequestParam(name = "name", defaultValue = "") String name) {
		return name + UUID.randomUUID().toString();
	}

	@RequestMapping("/uuid/{id}")
	@ResponseBody
	public String getUUIDWithId(@PathVariable("id") String id) {
		return id + UUID.randomUUID().toString();
	}

}