package com.app.furoapp.activity.newFeature.likeAndSaved.likedList;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LikedListResponse {

@SerializedName("Saved")
@Expose
private List<Object> saved = null;
@SerializedName("status")
@Expose
private String status;

public List<Object> getSaved() {
return saved;
}

public void setSaved(List<Object> saved) {
this.saved = saved;
}

public String getStatus() {
return status;
}

public void setStatus(String status) {
this.status = status;
}

}