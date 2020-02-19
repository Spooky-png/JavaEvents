package com.spooky.events.controllers;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.spooky.events.models.Event;
import com.spooky.events.models.Message;
import com.spooky.events.models.User;
import com.spooky.events.repositories.EventRepository;
import com.spooky.events.repositories.MessageRepository;
import com.spooky.events.repositories.UserRepository;
import com.spooky.events.services.EventService;

@Controller
public class EventController {
	private final EventService eventService;
	private final EventRepository eventRepository;
	private final MessageRepository messageRepository;
	private final UserRepository userRepository;
	
	public EventController(EventService eventService,EventRepository eventRepository,MessageRepository messageRepository,UserRepository userRepository) {
		this.eventRepository = eventRepository;
		this.messageRepository = messageRepository;
		this.userRepository = userRepository;
		this.eventService = eventService;
	}
	
	@GetMapping("/")
	public String login(@ModelAttribute("user") User user) {
		return "/events/loginPage.jsp";
	}
	@PostMapping(value="/registration")
	 public String registerUser(@Valid @ModelAttribute("user") User user, BindingResult result, HttpSession session, RedirectAttributes redirectAttr) {
		 if (result.hasErrors()) {
			 redirectAttr.addFlashAttribute("error", result.getAllErrors().get(0));
			 return "redirect:/";
		 }else {
			 if (!user.getPassword().equals(user.getPasswordConfirmation())) {
				 redirectAttr.addFlashAttribute("error", "Passwords Don't Match");
				 return "redirect:/";
			 }
			 eventService.registerUser(user);
			 session.setAttribute("user_id", user.getId());
			 return "redirect:/events";
		 }
	 }
	@PostMapping(value="/login")
	 public String loginUser(@RequestParam("email") String email, @RequestParam("password") String password, RedirectAttributes redirectAttr, HttpSession session) {
		 User user = eventService.findByEmail(email);
		 if (user == null) {
			 redirectAttr.addFlashAttribute("error","No Such User");
		 }else if (!eventService.authenticateUser(email, password)) {
			 redirectAttr.addFlashAttribute("error","Invalid Password");
		 }else {
			 session.setAttribute("user_id", user.getId());
			 return "redirect:/events";
		 }
		 return "redirect:/";
	 }
	@GetMapping("/events")
	public String eventsPage(Model model, HttpSession session, @ModelAttribute("event") Event event) {
		Long userId = (Long) session.getAttribute("user_id");
		if (userId == null) {
			 return "redirect:/login";
		}
		User user = eventService.findUserById(userId);
		model.addAttribute("user", user);
		model.addAttribute("eventsInState", eventService.getEventsInState(user.getState()));
		model.addAttribute("otherEvents", eventService.getEventsOutOfState(user.getState()));
		model.addAttribute("now",new Date());
		return "/events/dashboard.jsp";
	}
	@PostMapping("/newevent")
	public String createEvent(@Valid @ModelAttribute("event") Event event,BindingResult result,RedirectAttributes redirectAttr, HttpSession session){
		Long userId = (Long) session.getAttribute("user_id");
		if (userId == null) {
			 return "redirect:/";
		}
		if(result.hasErrors()) {
			return "/events/dashboard.jsp";
		}
		Date d = event.getDate();
		Date current = new Date();
		if(d.before(current)) {
			redirectAttr.addFlashAttribute("error","Date must be at a future time");
			return "redirect:/events";
		}
		eventService.createEvent(event);
		return "redirect:/events";
	}
	@PostMapping("/newmessage/{id}")
	public String createMessage(@PathVariable("id") Long eventId,@Valid @ModelAttribute("message") Message m, BindingResult result, HttpSession session) {
		Long userId = (Long) session.getAttribute("user_id");
		Event event = eventService.findEvent(eventId);
		if (userId == null) {
			 return "redirect:/";
		}
		User user = eventService.findUserById(userId);
		m.setWrittenBy(user);
		eventService.createMessage(m);
		event.getMessages().add(m);
		eventService.createEvent(event);
		return "redirect:/events/event/{id}";
	}
	@GetMapping("/events/{id}/a/join")
	public String joinEvent(HttpSession session, @PathVariable("id") Long eventId) {
		Long userId = (Long) session.getAttribute("user_id");
		if (userId == null) {
			 return "redirect:/login";
		}
		User user = eventService.findUserById(userId);
		user.getAttending().add(eventService.getEventById(eventId));
		
		eventService.saveUser(user);
		
		return "redirect:/events";
		
	}
	@GetMapping("/events/{id}/a/cancel")
	public String cancelJoinEvent(HttpSession session, @PathVariable("id") Long eventId) {
		Long userId = (Long) session.getAttribute("user_id");
		if (userId == null) {
			 return "redirect:/login";
		}
		User user = eventService.findUserById(userId);
		user.getAttending().remove(eventService.getEventById(eventId));
		
		eventService.saveUser(user);
		
		return "redirect:/events";
		
	}
	@GetMapping("/events/event/{id}")
	public String eventPage(@PathVariable("id") Long eventId, Model model, @ModelAttribute("message") Message message, HttpSession session) {
		Long userId = (Long) session.getAttribute("user_id");
		if (userId == null) {
			 return "redirect:/login";
		}
		Event event = eventService.findEvent(eventId);
		List<User> numberofpeople = event.getAttendees();
		int number = numberofpeople.size();
		System.out.println(messageRepository.findAll());
		model.addAttribute("number", number);
		model.addAttribute("event", eventService.getEventById(eventId));
		return "/events/viewEvents.jsp";
	}
	@DeleteMapping("/events/event/{id}")
	public String deleteEvent(@PathVariable("id") Long eventId, Model model) {
		Event event = eventService.findEvent(eventId);
		eventService.deleteEvent(event);
		return "redirect:/events";
	}
	@GetMapping("/events/edit/{id}")
	public String edit(@PathVariable("id") Long id, Model model, HttpSession session) {
		Long userId = (Long) session.getAttribute("user_id");
		if(userId != eventService.getEventById(id).getHost().getId()) {
			return "redirect:/events";
		}
		
		Event event = eventService.findEvent(id);
		model.addAttribute("event", event);
		model.addAttribute("userID", userId);
		return "/events/editEvents.jsp";
	}
	@PutMapping("/editevent/{id}")
	public String editEvent(@PathVariable("id") Long id,@ModelAttribute("event") Event event, HttpSession session, RedirectAttributes redirectAttr){
		Long userId = (Long) session.getAttribute("user_id");
		if (userId == null) {
			 return "redirect:/login";
		}
		Date d = event.getDate();
		Date current = new Date();
		if(d.before(current)) {
			redirectAttr.addFlashAttribute("error","Date must be at a future time");
			return "redirect:/events/edit/{id}";
		}
		eventService.createEvent(event);
		return "redirect:/events";
	}
	@GetMapping("/logout")
	 public String logout(HttpSession session) {
		 session.invalidate();
		 return "redirect:/";
	 }
}
