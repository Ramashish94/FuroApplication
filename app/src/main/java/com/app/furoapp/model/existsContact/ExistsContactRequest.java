package com.app.furoapp.model.existsContact;


import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ExistsContactRequest {

@SerializedName("contacts")
@Expose
private List<String> contacts = null;
@SerializedName("user_id")
@Expose
private String userId;

public List<String> getContacts() {
return contacts;
}

public void setContacts(List<String> contacts) {
this.contacts = contacts;
}

public String getUserId() {
return userId;
}

public void setUserId(String userId) {
this.userId = userId;
}

}