package com.josesa.websocket.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import com.josesa.websocket.entity.AuthToken;

public interface AuthTokenDAO extends JpaRepository<AuthToken, Integer>{

	AuthToken findByUserName(String userName);
	
}
