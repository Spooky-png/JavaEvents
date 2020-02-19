package com.spooky.events.services;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.mindrot.jbcrypt.BCrypt;
import org.springframework.stereotype.Service;

import com.spooky.events.models.Event;
import com.spooky.events.models.Message;
import com.spooky.events.models.User;
import com.spooky.events.repositories.EventRepository;
import com.spooky.events.repositories.MessageRepository;
import com.spooky.events.repositories.UserRepository;

@Service
public class EventService {
	private final UserRepository userRepository;
	private final MessageRepository messageRepository;
	private final EventRepository eventRepository;
	
	public EventService(UserRepository userRepository,MessageRepository messageRepository, EventRepository eventRepository) {
		this.eventRepository = eventRepository;
		this.messageRepository = messageRepository;
		this.userRepository = userRepository;
	}
	public List<User> allUsers(){
		return userRepository.findAll();
	}
	public List<Message> allMessages(){
		return messageRepository.findAll();
	}
	public User findUser(Long id) {
		Optional<User> optionalUser = userRepository.findById(id);
		if(optionalUser.isPresent()) {
			return optionalUser.get();
		} else {
			return null;
		}
	}
	public Event findEvent(Long id) {
		Optional<Event> optionalEvent = eventRepository.findById(id);
		if(optionalEvent.isPresent()) {
			return optionalEvent.get();
		} else {
			return null;
		}
	}
	public Message findMessage(Long id) {
		Optional<Message> optionalMessage = messageRepository.findById(id);
		if(optionalMessage.isPresent()) {
			return optionalMessage.get();
		} else {
			return null;
		}
	}
	public User registerUser(User user) {
        String hashed = BCrypt.hashpw(user.getPassword(), BCrypt.gensalt());
        user.setPassword(hashed);
        return userRepository.save(user);
    }
	public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }
	public boolean authenticateUser(String email, String password) {
        User user = userRepository.findByEmail(email);
        if(user == null) {
            return false;
        } else {
            if(BCrypt.checkpw(password, user.getPassword())) {
                return true;
            } else {
                return false;
            }
        }
    }
	public List<Event> getEventsInState(String state) {
		return eventRepository.findByState(state);
	}

	public List<Event> getEventsOutOfState(String state) {
		return eventRepository.findByStateNot(state);
	}

	public Event getEventById(Long id) {
		Optional<Event> e = eventRepository.findById(id);
    	
    	if(e.isPresent()) {
            return e.get();
    	} else {
    	    return null;
    	}
	}
	public User findUserById(Long id) {
    	Optional<User> u = userRepository.findById(id);
    	
    	if(u.isPresent()) {
            return u.get();
    	} else {
    	    return null;
    	}
    }
	public void deleteEvent(Event e) {
		eventRepository.delete(e);
	}
	public void createEvent(Event e) {
			eventRepository.save(e);
	}
	public void saveUser(User u) {
		userRepository.save(u);
	}
}
