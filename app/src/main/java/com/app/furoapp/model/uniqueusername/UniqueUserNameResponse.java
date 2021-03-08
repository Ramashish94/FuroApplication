package com.app.furoapp.model.uniqueusername;


import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UniqueUserNameResponse {

@SerializedName("status")
@Expose
private Integer status;
@SerializedName("new_username")
@Expose
private List<String> newUsername = null;

public Integer getStatus() {
return status;
}

public void setStatus(Integer status) {
this.status = status;
}

public List<String> getNewUsername() {
return newUsername;
}

public void setNewUsername(List<String> newUsername) {
this.newUsername = newUsername;
}

}