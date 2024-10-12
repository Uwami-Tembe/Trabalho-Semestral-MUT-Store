/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

import java.util.Date;

/**
 *
 * @author Aderito
 */
public class Usuario {
    private String name;
    private String username;
    private String password;
    private String mobileNumber;
    private String email;
    private String userType;
    private boolean userStatus;
    private Date posted;
    private int validated;
    public Usuario(){};
    
    public Usuario(String name, String username, String password, String mobileNumber, String email, String userType) {
        this.name = name;
        this.username = username;
        this.password = password;
        this.mobileNumber = mobileNumber;
        this.email = email;
        this.userType = userType;
        this.userStatus = true;
        this.validated = 3;
    }

    public Usuario(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public int getValidated() {
        return validated;
    }

    public void setValidated(int validated) {
        this.validated = validated;
    }

    

    public boolean isUserStatus() {
        return userStatus;
    }

    public void setUserStatus(boolean userStatus) {
        this.userStatus = userStatus;
    }

    public Date getPosted() {
        return posted;
    }

    public void setPosted(Date posted) {
        this.posted = posted;
    }
    

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
    

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }
   
    
    
    
    

    
     
}
