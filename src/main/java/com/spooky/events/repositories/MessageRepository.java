package com.spooky.events.repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.spooky.events.models.Message;

public interface MessageRepository extends CrudRepository<Message, Long>{
	List<Message> findAll();

}
