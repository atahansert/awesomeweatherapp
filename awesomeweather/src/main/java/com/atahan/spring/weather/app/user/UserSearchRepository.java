package com.atahan.spring.weather.app.user;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;



@Repository
public interface UserSearchRepository extends JpaRepository<UserSearch, Long> {

}
