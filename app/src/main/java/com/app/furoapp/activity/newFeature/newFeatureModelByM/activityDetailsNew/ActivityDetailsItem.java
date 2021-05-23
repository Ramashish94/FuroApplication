package com.app.furoapp.activity.newFeature.newFeatureModelByM.activityDetailsNew;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ActivityDetailsItem{

	@SerializedName("image")
	private String image;

	@SerializedName("total_comments")
	private int totalComments;

	@SerializedName("comments")
	private List<CommentsItem> comments;

	@SerializedName("user_view")
	private String userView;

	@SerializedName("link")
	private String link;

	@SerializedName("description")
	private String description;

	@SerializedName("created_at")
	private String createdAt;

	@SerializedName("total_views")
	private int totalViews;

	@SerializedName("video")
	private String video;

	@SerializedName("type")
	private String type;

	@SerializedName("title")
	private String title;

	@SerializedName("body")
	private List<BodyItem> body;

	@SerializedName("total_likes")
	private int totalLikes;

	@SerializedName("user_save")
	private String userSave;

	@SerializedName("video_thumbnail")
	private String videoThumbnail;

	@SerializedName("user_like")
	private String userLike;

	@SerializedName("updated_at")
	private String updatedAt;

	@SerializedName("activity_id")
	private String activityId;

	@SerializedName("id")
	private int id;

	public void setImage(String image){
		this.image = image;
	}

	public String getImage(){
		return image;
	}

	public void setTotalComments(int totalComments){
		this.totalComments = totalComments;
	}

	public int getTotalComments(){
		return totalComments;
	}

	public void setComments(List<CommentsItem> comments){
		this.comments = comments;
	}

	public List<CommentsItem> getComments(){
		return comments;
	}

	public void setUserView(String userView){
		this.userView = userView;
	}

	public String getUserView(){
		return userView;
	}



	public void setDescription(String description){
		this.description = description;
	}

	public String getDescription(){
		return description;
	}

	public void setCreatedAt(String createdAt){
		this.createdAt = createdAt;
	}

	public String getCreatedAt(){
		return createdAt;
	}

	public void setTotalViews(int totalViews){
		this.totalViews = totalViews;
	}

	public int getTotalViews(){
		return totalViews;
	}


	public void setType(String type){
		this.type = type;
	}

	public String getType(){
		return type;
	}



	public void setBody(List<BodyItem> body){
		this.body = body;
	}

	public List<BodyItem> getBody(){
		return body;
	}

	public void setTotalLikes(int totalLikes){
		this.totalLikes = totalLikes;
	}

	public int getTotalLikes(){
		return totalLikes;
	}

	public void setUserSave(String userSave){
		this.userSave = userSave;
	}

	public String getUserSave(){
		return userSave;
	}


	public void setUserLike(String userLike){
		this.userLike = userLike;
	}

	public String getUserLike(){
		return userLike;
	}

	public void setUpdatedAt(String updatedAt){
		this.updatedAt = updatedAt;
	}

	public String getUpdatedAt(){
		return updatedAt;
	}

	public void setId(int id){
		this.id = id;
	}

	public int getId(){
		return id;
	}

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}

	public String getVideo() {
		return video;
	}

	public void setVideo(String video) {
		this.video = video;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getVideoThumbnail() {
		return videoThumbnail;
	}

	public void setVideoThumbnail(String videoThumbnail) {
		this.videoThumbnail = videoThumbnail;
	}

	public String getActivityId() {
		return activityId;
	}

	public void setActivityId(String activityId) {
		this.activityId = activityId;
	}
}