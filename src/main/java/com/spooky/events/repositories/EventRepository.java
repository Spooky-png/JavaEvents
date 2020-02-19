package com.spooky.events.repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.spooky.events.models.Event;

public interface EventRepository extends CrudRepository<Event, Long>{
	public List<Event> findByState(String state);
	public List<Event> findByStateNot(String state);
}
