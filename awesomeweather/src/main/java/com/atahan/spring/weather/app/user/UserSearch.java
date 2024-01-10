package com.atahan.spring.weather.app.user;


import jakarta.persistence.*;

@Entity
@Table(name = "userSearch")
public class UserSearch {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id; 
    
    @Column(name = "userId")
    private String userId;
  
    @Column(name = "text")
    private String text;

    public UserSearch(String text, String userId) {
        this.text = text;
        this.userId = userId;
      }
    

}
