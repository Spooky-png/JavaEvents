package com.spooky.events.validator;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import com.spooky.events.models.User;
import com.spooky.events.services.EventService;
@Component
public class UserValidator implements Validator {
	private final EventService eventService;
	
	public UserValidator(EventService eventService) {
		this.eventService = eventService;
	}
    
    @Override
    public boolean supports(Class<?> clazz) {
        return User.class.equals(clazz);
    }
    
    @Override
    public void validate(Object target, Errors errors) {
        User user = (User) target;
        
        if (!user.getPasswordConfirmation().equals(user.getPassword())) {
            errors.rejectValue("passwordConfirmation", "Match");
        }
        if(!(eventService.findByEmail(user.getEmail()) == null)) {
        	errors.rejectValue("duplicate", "Duplicate");
        }
    }
}
