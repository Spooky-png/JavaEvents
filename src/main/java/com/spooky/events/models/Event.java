package com.spooky.events.models;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import javax.validation.constraints.Size;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name="events")
public class Event {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	@Size(min=5, message="Event name must be at least 5 characters.")
	private String name;
	@DateTimeFormat(pattern="yyyy-MM-dd")
	private Date date;
	@Size(min=2, message="Location must be at least 2 characters.")
	private String location;
	@Size(min=2, max=2, message="Please enter the two character state code.. thing..")
	private String state;
	@Column(updatable=false)
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private Date createdAt;
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private Date updatedAt;
    @ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="user_id")
	private User host;
    @ManyToMany
	@JoinTable(name = "user_attending",
		joinColumns = @JoinColumn(name = "user_id"),
		inverseJoinColumns = @JoinColumn(name = "event_id")
	)
	private List<User> attendees;
    @OneToMany(mappedBy = "event")
    private List<Message> messages;
    
    public Event() {
    }
    
    public Event(String name, User host, String location,String state, List<User> attendees) {
		this.name = name;
		this.state = state;
		this.location = location;
		this.host = host;
		this.attendees = attendees;
	}

	@PrePersist
    protected void onCreate(){
        this.createdAt = new Date();
    }
    @PreUpdate
    protected void onUpdate(){
        this.updatedAt = new Date();
    }
    public Long getId() {
    	return id;
    }
    public void setId(Long id) {
    	this.id = id;
    }
    public String getName() {
    	return name;
    }
    public void setName(String name) {
    	this.name = name;
    }
    public Date getDate() {
    	return date;
    }
    public void setDate(Date date) {
    	this.date = date;
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
    public User getHost() {
    	return host;
    }
    public void setHost(User host) {
    	this.host = host;
    }
    public List<User> getAttendees(){
    	return attendees;
    }
    public void setAttendees(List<User> attendees){
    	this.attendees = attendees;
    }
    public List<Message> getMessages(){
    	return messages;
    }
    public void setMessages(List<Message> messages) {
    	this.messages = messages;
    }
}
