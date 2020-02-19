package com.spooky.events.repositories;
import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import com.spooky.events.models.User;
@Repository
public interface UserRepository extends CrudRepository<User, Long> {
	List<User> findAll();
    User findByEmail(String email);
}