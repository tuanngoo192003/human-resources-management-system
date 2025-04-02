package com.lab.server.payload.user;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class UserRequest {

    private String username;	
    private String password;
    private String email;
    private int roleId; 
    private String firstName;
    private String lastName;
    private LocalDate dateOfBirth;
    private int positionId;
    private int departmentId;
    
    @Data
    @Builder
    public static class UpdateUserRequest{
    	 private String username;	
    	    private String password;
    	    private String email;
    }
}