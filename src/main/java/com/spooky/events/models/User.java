package com.spooky.events.models;


import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.Email;
import javax.validation.constraints.Size;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name="users")
public class User {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;
    @Size(min=2, message="First name must be at least 2 characters.")
    private String firstName;
    @Size(min=2, message="Last name must be at least 2 characters.")
    private String lastName;
    @Size(min=2, max=2, message="Please enter the two character state code.. thing..")
    private String state;
    @Size(min=2, message="Location must be at least 2 characters.")
    private String location;
    @Email(message="Email must be valid")
    private String email;
    @Size(min=5, message="Password must be at least 5 characters.")
    private String password;
    @Transient
    private String passwordConfirmation;
    @Column(updatable=false)
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private Date createdAt;
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private Date updatedAt;
    @OneToMany(mappedBy="host", fetch = FetchType.LAZY)
	 private List<Event> events;
    @ManyToMany
	@JoinTable(name = "user_attending", 
		joinColumns = @JoinColumn(name = "event_id"), 
		inverseJoinColumns = @JoinColumn(name = "user_id"))
	List<Event> attending;
    @OneToMany(mappedBy = "writtenBy")
	List<Message> messages;
    
    public User() {
    }
    
   @PrePersist
    protected void onCreate(){
        this.createdAt = new Date();
    }
    @PreUpdate
    protected void onUpdate(){
        this.updatedAt = new Date();
    }
    public String getEmail() {
		return email;
	}
    public void setEmail(String email) {
    	this.email = email;
    }
    public Long getId() {
    	return id;
    }
    public String getFirstName() {
    	return firstName;
    }
    public void setFirstName(String firstName) {
    	this.firstName = firstName;
    }
    public String getLastName() {
    	return lastName;
    }
    public void setLastName(String lastName) {
    	this.lastName = lastName;
    }
    public String getLocation() {
    	return location;
    }
    public void setLocation(String location) {
    	this.location = location;
    }
    public String getState() {
    	return state;
    }
    public void setState(String state) {
    	this.state = state;
    }
    public String getPassword() {
    	return password;
    }
    public void setPassword(String password) {
    	this.password = password;
    }
    public String getPasswordConfirmation() {
    	return passwordConfirmation;
    }
    public void setPasswordConfirmation(String passwordConfirmation) {
    	this.passwordConfirmation = passwordConfirmation;
    }
    public List<Event> getAttending(){
    	return attending;
    }
    public void setAttending(List<Event> attending){
    	this.attending = attending;
    }
    public List<Event> getEvents(){
    	return events;
    }
    public void setEvents(List<Event> events) {
    	this.events = events;
    }
}