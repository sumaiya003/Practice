package com.example.Facebooksecurityproject.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
@Table(name="USERS_AUTHENTICATION_TABLE")
public class User {

    @Id
    @GeneratedValue
    private int id;

    private String userName;
    private String password;

    private boolean active;
    private String roles;


}
