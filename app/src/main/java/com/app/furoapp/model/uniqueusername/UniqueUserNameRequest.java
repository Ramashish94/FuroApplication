package com.app.furoapp.model.uniqueusername;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UniqueUserNameRequest {

@SerializedName("username")
@Expose
private String username;

public String getUsername() {
return username;
}

public void setUsername(String username) {
this.username = username;
}

}