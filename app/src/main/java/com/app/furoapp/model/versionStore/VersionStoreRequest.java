package com.app.furoapp.model.versionStore;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class VersionStoreRequest {

@SerializedName("version")
@Expose
private String version;

public String getVersion() {
return version;
}

public void setVersion(String version) {
this.version = version;
}

}