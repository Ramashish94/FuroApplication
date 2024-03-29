package com.app.furoapp.retrofit;


import com.app.furoapp.activity.newFeature.ContentEngagementModule.activityDetailsNew.ActivityDetailsResponse;
import com.app.furoapp.activity.newFeature.ContentEngagementModule.addComments.AddCommentRequest;
import com.app.furoapp.activity.newFeature.ContentEngagementModule.addComments.AddCommentResponse;
import com.app.furoapp.activity.newFeature.ContentEngagementModule.feedHomeFragment_ListingNew.ActivitiesListing;
import com.app.furoapp.activity.newFeature.ContentEngagementModule.like.LikeRequest;
import com.app.furoapp.activity.newFeature.ContentEngagementModule.like.LikeResponse;
import com.app.furoapp.activity.newFeature.ContentEngagementModule.saveBookmark.SavedRequest;
import com.app.furoapp.activity.newFeature.ContentEngagementModule.saveBookmark.SavedResponse;
import com.app.furoapp.activity.newFeature.ContentEngagementModule.userView.ViewsRequest;
import com.app.furoapp.activity.newFeature.ContentEngagementModule.userView.ViewsResponse;
import com.app.furoapp.activity.newFeature.StepsTracker.addNewSlot.AddNewSlotResponse;
import com.app.furoapp.activity.newFeature.StepsTracker.addNewSlot.AddNewSlotTimeRequest;
import com.app.furoapp.activity.newFeature.StepsTracker.addNewSlot.deleteSlot.DeleteSlotResponse;
import com.app.furoapp.activity.newFeature.StepsTracker.fetchAllSlot.FetchAllSlotResponse;
import com.app.furoapp.activity.newFeature.StepsTracker.historyModel.HistoryResponse;
import com.app.furoapp.activity.newFeature.StepsTracker.historyOfStepsTracker.weekMonthYearModel.WeeklyMonthlyYearlyRequest;
import com.app.furoapp.activity.newFeature.StepsTracker.historyOfStepsTracker.weekMonthYearModel.monthlyResponse.MonthlyResponse;
import com.app.furoapp.activity.newFeature.StepsTracker.historyOfStepsTracker.weekMonthYearModel.weeklyResponse.WeeklyResponse;
import com.app.furoapp.activity.newFeature.StepsTracker.historyOfStepsTracker.weekMonthYearModel.yearResponse.YearlyResponse;
import com.app.furoapp.activity.newFeature.StepsTracker.leaderBoard.model.LeaderBoardResponse;
import com.app.furoapp.activity.newFeature.StepsTracker.modifiedSavedData.ModifiedSavedDataRequest;
import com.app.furoapp.activity.newFeature.StepsTracker.modifiedSavedData.ModifiedSavedDataResponse;
import com.app.furoapp.activity.newFeature.StepsTracker.userStepsGoalModel.UserStepsGoalRequest;
import com.app.furoapp.activity.newFeature.StepsTracker.userStepsGoalModel.UserStepsGoalResponse;
import com.app.furoapp.activity.newFeature.bmiCalculator.fetchBmiDataModel.FetchUserWiseDataResponse;
import com.app.furoapp.activity.newFeature.bmiCalculator.storeBmiModel.BmiStoreDataRequest;
import com.app.furoapp.activity.newFeature.bmiCalculator.storeBmiModel.BmiStoreDataResponse;
import com.app.furoapp.activity.newFeature.fqTips.TipsResponse;
import com.app.furoapp.activity.newFeature.healthCare.healthCentermodel.HealthCenterResponse;
import com.app.furoapp.activity.newFeature.likeAndSaved.SavedList.saveOnPost.SavedListResponse;
import com.app.furoapp.activity.newFeature.likeAndSaved.likedList.likeOnPost.LikeListResponse;
import com.app.furoapp.activity.newFeature.notification.allNotificationModal.NotificationResponses;
import com.app.furoapp.activity.newFeature.waterIntakeCalculator.WaterIntakeExistsUser.WaterIntakeExistsUserResponse;
import com.app.furoapp.activity.newFeature.waterIntakeCalculator.changeGlassSize.ChangeGlassSizeRequest;
import com.app.furoapp.activity.newFeature.waterIntakeCalculator.changeGlassSize.UserChangeGlassSizeResponse;
import com.app.furoapp.activity.newFeature.waterIntakeCalculator.cupCreate.CupCreateResponse;
import com.app.furoapp.activity.newFeature.waterIntakeCalculator.dailyWaterIntake.DailyWaterIntakeRequest;
import com.app.furoapp.activity.newFeature.waterIntakeCalculator.dailyWaterIntake.DailyWaterIntakeResponse;
import com.app.furoapp.activity.newFeature.waterIntakeCalculator.fetchAllPlan.FetchAllPlanResponse;
import com.app.furoapp.activity.newFeature.waterIntakeCalculator.fetchGlass.GlassFetchResponse;
import com.app.furoapp.activity.newFeature.waterIntakeCalculator.planCreate.PlaneCreateRequest;
import com.app.furoapp.activity.newFeature.waterIntakeCalculator.planCreate.PlaneCreateResponse;
import com.app.furoapp.activity.newFeature.waterIntakeCalculator.restorePlanModel.RestorePlanResponse;
import com.app.furoapp.activity.newFeature.waterIntakeCalculator.selectCustomSizeGlass.SelectCustomGlassSizeRequest;
import com.app.furoapp.activity.newFeature.waterIntakeCalculator.selectCustomSizeGlass.SelectCustomSizeGlassResponse;
import com.app.furoapp.activity.newFeature.waterIntakeCalculator.waterIntakeCounter.WaterIntakeUpdatePlanRequest;
import com.app.furoapp.activity.newFeature.waterIntakeCalculator.waterIntakeCounter.WaterIntakeUpdatePlanResponse;
import com.app.furoapp.model.Bannersecond.BannerSecondResponse;
import com.app.furoapp.model.FriendModel.AddFriend;
import com.app.furoapp.model.FriendModel.FriendInviteModel;
import com.app.furoapp.model.FriendModel.FriendListModel;
import com.app.furoapp.model.FriendModel.FriendPendingModel;
import com.app.furoapp.model.FriendModel.FriendRequest;
import com.app.furoapp.model.FriendModel.Friends;
import com.app.furoapp.model.LeaderBoard.LeaderModel;
import com.app.furoapp.model.Leaderboard;
import com.app.furoapp.model.Settings.UpdateUserModel;
import com.app.furoapp.model.Settings.UserDetailsModel;
import com.app.furoapp.model.Settings.UserUpdate;
import com.app.furoapp.model.allCommunity.AllCommunitiesResponse;
import com.app.furoapp.model.bannerresponse.BannerResponse;
import com.app.furoapp.model.challangeNotification.ChallangeNotificationRequest;
import com.app.furoapp.model.challangeNotification.ChallangeNotificationResponse;
import com.app.furoapp.model.challengeAccepted.ChallengeAcceptedRequest;
import com.app.furoapp.model.challengeAccepted.ChallengeAcceptedResponse;
import com.app.furoapp.model.challengeByUser.ChallengeByUser;
import com.app.furoapp.model.challengeByUser.ChallengeByUserResponse;
import com.app.furoapp.model.challengeByYouDetail.ChallenegeByYouDetailRequest;
import com.app.furoapp.model.challengeByYouDetail.ChallenegeByYouDetailResponse;
import com.app.furoapp.model.challengeReject.ChallangeRejectRequest;
import com.app.furoapp.model.challengeReject.ChallangeRejectResponse;
import com.app.furoapp.model.challengedetail.ChallangeDetailRequest;
import com.app.furoapp.model.challengedetail.ChallangeDetailResponse;
import com.app.furoapp.model.challengeforyouRecieve.ChallenegeForYouRecieve;
import com.app.furoapp.model.challengeforyouRecieve.ChallenegeForYouRecieveRequest;
import com.app.furoapp.model.challengeforyoudetail.ChallengeForYouDetailRequest;
import com.app.furoapp.model.challengeforyoudetail.ChallengeForYouDetailResponse;
import com.app.furoapp.model.challengemap.ChallenegeMapResponse;
import com.app.furoapp.model.chooseChallange.SelectChallangeResponse;
import com.app.furoapp.model.clubChallenge.ClubChallengeResponse;
import com.app.furoapp.model.clubDetails.ClubDetailRequest;
import com.app.furoapp.model.clubDetails.ClubDetailResponse;
import com.app.furoapp.model.communityChallange.CommunityChallangeRequest;
import com.app.furoapp.model.communityChallange.CommunityChallangeResponse;
import com.app.furoapp.model.communityJoin.CommunitiesJoinRequest;
import com.app.furoapp.model.communityJoin.CommunitiesJoinResponse;
import com.app.furoapp.model.communitydetail.CommunityDetailRequest;
import com.app.furoapp.model.communitydetail.CommunityDetailResponse;
import com.app.furoapp.model.communitymembers.CommunityMembersRequest;
import com.app.furoapp.model.communitymembers.CommunityMembersResponse;
import com.app.furoapp.model.contentFeedDetail.ContentFeedDetailRequest;
import com.app.furoapp.model.contentFeedDetail.ContentFeedDetailResponse;
import com.app.furoapp.model.content_feed.ContentFeedModel;
import com.app.furoapp.model.createVideoChallenge.CreateVideoChallangeResponse;
import com.app.furoapp.model.draft.DraftRequest;
import com.app.furoapp.model.draft.DraftResponse;
import com.app.furoapp.model.emailVerified.EmailVerifiedRequest;
import com.app.furoapp.model.emailVerified.EmailVerifiedResponse;
import com.app.furoapp.model.existsContact.ExistsContactResponse;
import com.app.furoapp.model.feedback.FeedbackRequest;
import com.app.furoapp.model.feedback.FeedbackResponse;
import com.app.furoapp.model.getImage.UserImageRequest;
import com.app.furoapp.model.getImage.UserImageResponse;
import com.app.furoapp.model.imageupdate.UpdateResponse;
import com.app.furoapp.model.loginUser.LoginRequest;
import com.app.furoapp.model.loginUser.LoginResponse;
import com.app.furoapp.model.loginWithFb.LoginWithFbRequest;
import com.app.furoapp.model.loginWithFb.LoginWithFbResponse;
import com.app.furoapp.model.loginwithgmail.LoginwithGmailResponse;
import com.app.furoapp.model.mapchallenge.MapChallengeRecieveRequest;
import com.app.furoapp.model.mapchallenge.MapChallengeRecieveResponse;
import com.app.furoapp.model.myCommunities.MyCommunitiesRequest;
import com.app.furoapp.model.myCommunities.MyCommunitiesResponse;
import com.app.furoapp.model.notificationResponse.GetNotificationRequest;
import com.app.furoapp.model.notificationResponse.GetNotificationResponse;
import com.app.furoapp.model.passwordChanged.PasswordChangedRequest;
import com.app.furoapp.model.passwordChanged.PasswordChangedResponse;
import com.app.furoapp.model.profile.AddProfile;
import com.app.furoapp.model.profile.ProfileModel;
import com.app.furoapp.model.report.ReportIssueRequest;
import com.app.furoapp.model.report.ReportIssueResponse;
import com.app.furoapp.model.signUp.SignupResponse;
import com.app.furoapp.model.uniqueusername.UniqueUserNameRequest;
import com.app.furoapp.model.uniqueusername.UniqueUserNameResponse;
import com.app.furoapp.model.updateToken.UdateTokenResponse;
import com.app.furoapp.model.updatefcmtoken.UpdateFcmTokenRequest;
import com.app.furoapp.model.updatefcmtoken.UpdateFcmTokenResponse;
import com.app.furoapp.model.updateimage.UpdateImageResponse;
import com.app.furoapp.model.versionStore.VersionStoreRequest;
import com.app.furoapp.model.versionStore.VersionStoreResponse;
import com.app.furoapp.model.versionStore.versionget.VersiongetResponse;
import com.app.furoapp.model.whatBringsYoutoFuro.WhatBringsYouToFuroRequest;
import com.app.furoapp.model.whatBringsYoutoFuro.WhatBringsYouToFuroResponse;
import com.app.furoapp.model.winnerApi.WinnerRequest;
import com.app.furoapp.model.winnerApi.WinnerResponse;

import java.util.HashMap;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;

public interface ApiInterface {


    String TOKEN_KEY = "Authorization";

    // api calling for Activity Content feed
    @GET("all-activities")
    Call<ContentFeedModel> getActivities();

    // api calling for AllActivity Content feed  landing page
//    @GET("all-activity-listings")
//    Call<ActivitiesListing> getAllActivities();

    // api calling  for login with email
    @POST("login")
    Call<LoginResponse> loginUser(@Body LoginRequest loginRequest);

    //api calling for contentFeddDetail
    @POST("activity-details")
    Call<ContentFeedDetailResponse> contentDetail(@Body ContentFeedDetailRequest contentFeedDetailRequest);

    //loginwithfb
    @POST("login-with-fb-new")
    Call<LoginWithFbResponse> loginWthFb(@Body LoginWithFbRequest loginWithFbRequest);

    //emailVerified
    @POST("email_verified")
    Call<EmailVerifiedResponse> emailVerified(@Body EmailVerifiedRequest emailVerifiedRequest);

    // passwordReset
    @POST("password_reset")
    Call<PasswordChangedResponse> passwordReset(@Body PasswordChangedRequest passwordChangedRequest);

    //allComminity
    @GET("all-communities")
    Call<AllCommunitiesResponse> allCommunities();

    //myComminity
    @POST("my-communities")
    Call<MyCommunitiesResponse> myCommunities(@Body MyCommunitiesRequest myCommunitiesRequest);

    //community join
    @POST("join-community")
    Call<CommunitiesJoinResponse> communitiesJoin(@Body CommunitiesJoinRequest communitiesJoinRequest);


    @POST("suggest-username")
    Call<UniqueUserNameResponse> suggestUserName(@Body UniqueUserNameRequest uniqueUserNameRequest);

    //community Deatil api
    @POST("community-details")
    Call<CommunityDetailResponse> communityDetail(@Body CommunityDetailRequest communityDetailRequest);

    //choose challange Api
    @GET("challenges")
    Call<SelectChallangeResponse> selectChallange();

    //what brings you to furo
    @POST("user-reasons")
///////////////////////////
    Call<WhatBringsYouToFuroResponse> chooseOneReason(@Body WhatBringsYouToFuroRequest whatBringsYouToFuroRequest);


    //community Challange
    @POST("community-challenges")
    Call<CommunityChallangeResponse> communityChallange(@Body CommunityChallangeRequest communityChallangeRequest);

    // send video to server
    @Multipart
    //  @POST("https://furoapi.ml/api/create-challenge-video")
    @POST("create-challenge-video")
    Call<CreateVideoChallangeResponse> videouploadChallange(

            @Part("user_id") RequestBody user_id,
            @Part("acitivity_duration") RequestBody acitivity_duration,
            @Part("activity_count") RequestBody acitivity_count,
            @Part("challenge_activity") RequestBody challenge_activity,
            @Part("challenge_name") RequestBody challenge_name,
            @Part("accept_challenge_id") RequestBody accept_challenge_id,
            @Part MultipartBody.Part video_file);


    //api calling For Signup
    @Multipart
    @POST("signup")
    Call<SignupResponse> signUpUser(@Part("name") RequestBody name,
                                    @Part("email") RequestBody email,
                                    @Part("password") RequestBody password,
                                    @Part("dob") RequestBody dob,
                                    @Part("gender") RequestBody gender,
                                    @Part("country") RequestBody country,
                                    @Part("state") RequestBody state,
                                    @Part("city") RequestBody city,
                                    @Part("platform") RequestBody platform,
                                    @Part("fb_id") RequestBody fbid,
                                    @Part("google_id") RequestBody google_id,
                                    @Part("username") RequestBody username,
                                    @Part("mobile") RequestBody mobile,
                                    @Part MultipartBody.Part image);

    //pushNotification
    @POST("get-notification")
    Call<GetNotificationResponse> pushNotification(@Body GetNotificationRequest notificationRequest);

    /* //pushNotification
     @POST("check-contacts")
     Call<ExistsContactResponse> checkExistsContact(@com.app.furoapp.activity.newFeature.ContentEngagementModule.activityDetailsNew.Body ExistsContactRequest existsContactRequest);
 */
    @Multipart
    @POST("check-contacts-seperate")
    Call<ExistsContactResponse> checkExistsContact(@Part("contacts") RequestBody contacts,

                                                   @Part("user_id") RequestBody user_id);

    //updatefcm token
    @POST("update-fcm-token")
    Call<UpdateFcmTokenResponse> updateFcmToken(@Body UpdateFcmTokenRequest updateFcmTokenRequest);


    //challangetofriend
    @POST("challenge-notification")
    Call<ChallangeNotificationResponse> challangetoUserNotification(@Body ChallangeNotificationRequest challangeNotificationRequest);


    //challangedetail
    @POST("challenge-details")
    Call<ChallangeDetailResponse> challengeDetail(@Body ChallangeDetailRequest challangeDetailRequest);


    //openand close challenge
    @POST("challenges_by_user")
    Call<ChallengeByUserResponse> challngeByUser(@Body ChallengeByUser challengeByUser);


    //acceptChallange
    @POST("accept-challenge")
    Call<ChallengeAcceptedResponse> challngeAccept(@Body ChallengeAcceptedRequest challengeAcceptedRequest);


    //rejectChallange
    @POST("reject-challenge")
    Call<ChallangeRejectResponse> challngeReject(@Body ChallangeRejectRequest challangeRejectRequest);


    // add by pankaj 0n 08-11-2019
    @Headers({"Content-Type: application/json"})
    @POST("leadership-data")
    Call<LeaderModel> getLeaderData(@Body Leaderboard leaderboard);


    //club challange Api
    @POST("club-challenges")
    Call<ClubChallengeResponse> clubChallenges();

    //club challange  detail Api
    @POST("club-details")
    Call<ClubDetailResponse> clubDetailsChallenges(@Body ClubDetailRequest clubDetailRequest);

    // add by pankaj 0n 13-11-2019
    @POST("all-users")
    Call<Friends> getFriendsData(@Body FriendRequest friendRequest);

    // add by pankaj 0n 13-11-2019
    @POST("add-friend")
    Call<FriendInviteModel> AddFriendsData(@Body AddFriend addFriend);

    // add by pankaj 0n 13-11-2019
    @POST("pending-friend-list")
    Call<FriendPendingModel> FriendsPendingData(@Body AddFriend addFriend);

    // add by pankaj 0n 13-11-2019
    @POST("accept-friend")
    Call<FriendInviteModel> AcceptFriend(@Body AddFriend addFriend);

    // add by pankaj 0n 13-11-2019
    @POST("decline-friend")
    Call<FriendInviteModel> RejectFriend(@Body AddFriend addFriend);

    // add by pankaj 0n 14-11-2019
    @POST("friend-list")
    Call<FriendListModel> getFriendList(@Body AddFriend addFriend);

    // add by pankaj 0n 14-11-2019
    @POST("remove-friend")
    Call<FriendInviteModel> removeFriends(@Body AddFriend addFriend);

    //winner  detail Api
    @POST("get-winner")
    Call<WinnerResponse> winner(@Body WinnerRequest winnerRequest);

    // add by pankaj 0n 15-11-2019
    @POST("user-details")
    Call<UserDetailsModel> getUser(@Body AddFriend addFriend);

    // add by pankaj 0n 15-11-2019
    @POST("user-update")
    Call<UserUpdate> UpdateUserInform(@Body UpdateUserModel updateUserModel);

    @Multipart
    @POST("update-image")
    Call<UpdateResponse> updateResponse(@Part("user_id") RequestBody user_id,
                                        @Part MultipartBody.Part image);

    // add by pankaj on 21-11-2019
    @POST("user-profile")
    Call<ProfileModel> GetProfileData(@Body AddProfile addProfile);

    @POST("challenges_for_user")
    Call<ChallenegeForYouRecieve> challengeForYou(@Body ChallenegeForYouRecieveRequest challenegeForYouRecieveRequest);

    @POST("login-with-google-new")
    @FormUrlEncoded
    Call<LoginwithGmailResponse> loginwithgmail(@FieldMap HashMap<String, String> hashMap);/*LoginwithGmailRequest loginwithGmailRequest*/


    @Multipart
    @POST("create-challenge-video")
    Call<ChallenegeMapResponse> mapChallenge(@Part("user_id") RequestBody user_id,
                                             @Part("activity_duration") RequestBody activity_duration,
                                             @Part("distance") RequestBody distance,
                                             @Part("challenge_activity") RequestBody challenge_activity,
                                             @Part("accept_challenge_id") RequestBody accept_challenge_id,
                                             @Part MultipartBody.Part image);

    @POST("challenge-by-you-details")
    Call<ChallenegeByYouDetailResponse> challenegeByDetail(@Body ChallenegeByYouDetailRequest challenegeByYouDetailRequest);

    @POST("challenge-for-you-details")
    Call<ChallengeForYouDetailResponse> challenegeForDetail(@Body ChallengeForYouDetailRequest challengeForYouDetailRequest);

    @POST("feedback")
    Call<FeedbackResponse> feedBack(@Body FeedbackRequest feedbackRequest);

    @POST("report-issue")
    Call<ReportIssueResponse> reportIssue(@Body ReportIssueRequest reportIssueRequest);

    @GET("get-app-version")
    Call<VersiongetResponse> versionget();

    @POST("post-app-version")
    Call<VersionStoreResponse> submitVersion(@Body VersionStoreRequest versionStoreRequest);


    @POST("map-details")
    Call<MapChallengeRecieveResponse> mapChallengeRecieve(@Body MapChallengeRecieveRequest mapChallengeRecieveRequest);

    @POST("community-members")
    Call<CommunityMembersResponse> commuintymembers(@Body CommunityMembersRequest communityMembersRequest);

    @POST("draft-challenges")
    Call<DraftResponse> draft(@Body DraftRequest draftRequest);

    @Multipart
    @POST("update-image")
    Call<UpdateImageResponse> updateImage(@Part("user_id") RequestBody user_id,
                                          @Part MultipartBody.Part image);


    @GET("banner-image")
    Call<BannerResponse> bannerImage();

    @GET("banner-second-image")
    Call<BannerSecondResponse> bannerSecondImage();

    @Multipart
    @POST("update-token-details")
    Call<UdateTokenResponse> updateToken(@Part("user_id") RequestBody user_id,
                                         @Part("device_id") RequestBody device_id,
                                         @Part("fcm_token") RequestBody fcm_token);

    @POST("user-image")
    Call<UserImageResponse> user_image(@Body UserImageRequest userImageRequest);

    /*added new feature in Fq*/

    @GET("notification")
    Call<NotificationResponses> getNotification(@Header(TOKEN_KEY) String token);

//    @GET("all_challanges_notification")
//    Call<ChallengeNotificationResp> CHALLANGE_NOTIFICATION_RESPONSE_CALL(@Header(TOKEN_KEY) String token);

/*

    @GET("all_dailyfeed_notification")
    Call<DailyFeedNotificationResponse> DAILY_NOTIFICATION_RESPONSE_CALL(@Header(TOKEN_KEY) String token);
*/


    // New api calling for AllActivity Content feed  Content feed home fragment landing page
    @FormUrlEncoded
    @POST("all-activity-listings-new-paginate")
    Call<ActivitiesListing> ActivitiesListing(@Header(TOKEN_KEY) String token, @Field("type") String type, @Field("page") int page);

    //api calling for contentFeddDetail
    @POST("activity-details-new")
    Call<ActivityDetailsResponse> contentDetailNew(@Header(TOKEN_KEY) String token, @Body ContentFeedDetailRequest contentFeedDetailRequest);

    /*like*/
    @POST("like")
    Call<LikeResponse> userPostLike(@Header(TOKEN_KEY) String token, @Body LikeRequest request);

    /*Add comment*/
    @POST("add-comment")
    Call<AddCommentResponse> userCommentOnPost(@Header(TOKEN_KEY) String token, @Body AddCommentRequest request);

    /*Saved*/
    @POST("saved")
    Call<SavedResponse> userPostSaved(@Header(TOKEN_KEY) String token, @Body SavedRequest request);

    /*Views*/
    @POST("views")
    Call<ViewsResponse> userPostView(@Header(TOKEN_KEY) String token, @Body ViewsRequest request);

    /*Saved list*/
    @POST("saved-list")
    Call<SavedListResponse> saveOnPost(@Header(TOKEN_KEY) String token);

    /*Liked list*/
    @POST("like-list")
    Call<LikeListResponse> likeList(@Header(TOKEN_KEY) String token);

    /* Bmi calculation started */
    /*store-user-wise-data*/
    @POST("store-user-wise-data")
    Call<BmiStoreDataResponse> bmiStoreData(@Header(TOKEN_KEY) String token, @Body BmiStoreDataRequest bmiStoreDataRequest);

    /*fetch-user-wise-data*/
    @POST("fetch-user-wise-data")
    Call<FetchUserWiseDataResponse> fetchuserWiseData(@Header(TOKEN_KEY) String token);

    /*Water intake monitor started */
    /*fetch glass */
    @GET("user/glass/fetch")
    Call<GlassFetchResponse> userFetchGlass(@Header(TOKEN_KEY) String token);

    /*daily water intake monitor */
    @POST("user/glass/daily-water-intake")
    Call<DailyWaterIntakeResponse> dailyWaterIntake(@Header(TOKEN_KEY) String token, @Body DailyWaterIntakeRequest dailyWaterIntakeRequest);

    /*user/fetch-all-plan*/
    @GET("user/fetch-all-plan")
    Call<FetchAllPlanResponse> fetchAllPlan(@Header(TOKEN_KEY) String token);

    /*user/plan/create*/
    @POST("user/plan/create")
    Call<PlaneCreateResponse> planeCreate(@Header(TOKEN_KEY) String token, @Body PlaneCreateRequest planeCreateRequest);

    /*user/cup/create*/
    @POST("user/cup/create")
    Call<CupCreateResponse> cupCreate(@Header(TOKEN_KEY) String token);

    /*user/cup/select-custom-glass-size*/
    @POST("user/cup/select-custom-glass-size")
    Call<SelectCustomSizeGlassResponse> customGlassSize(@Header(TOKEN_KEY) String token, @Body SelectCustomGlassSizeRequest selectCustomGlassSizeRequest);

    /*user/water-intake-update-plan*/
    @POST("user/water-intake-update-plan")
    Call<WaterIntakeUpdatePlanResponse> WATER_INTAKE_UPDATE_PLAN_RESPONSE_CALL(@Header(TOKEN_KEY) String token, @Body WaterIntakeUpdatePlanRequest waterIntakeUpdatePlanRequest);

    /*user/cup/user-change-glass-size*/
    @POST("user/cup/user-change-glass-size")
    Call<UserChangeGlassSizeResponse> CHANGE_GLASS_SIZE_RESPONSE_CALL(@Header(TOKEN_KEY) String token, @Body ChangeGlassSizeRequest changeGlassSizeRequest);

    /*user/restore-plan*/
    @GET("user/restore-plan")
    Call<RestorePlanResponse> RESTORE_PLAN_RESPONSE_CALL(@Header(TOKEN_KEY) String token);

    /*track Steps counter */

    /*user/modify-save-data*/
    @POST("user/modify-save-data")
    Call<ModifiedSavedDataResponse> MODIFIED_SAVED_DATA_RESPONSE_CALL(@Header(TOKEN_KEY) String token, @Body ModifiedSavedDataRequest modifiedSavedDataRequest);

    @POST("user/modify-save-data")
    Call<ModifiedSavedDataResponse> MODIFIED_SAVED_DATA_RESPONSE_CALL_(@Header(TOKEN_KEY) String token);


    /*user/fetch-all-slots*/
    @GET("user/fetch-all-slots")
    Call<FetchAllSlotResponse> FETCH_ALL_SLOT_RESPONSE_CALL(@Header(TOKEN_KEY) String token);

    /*user/add-new-slot*/
    @POST("user/add-new-slot")
    Call<AddNewSlotResponse> ADD_NEW_SLOT_RESPONSE_CALL(@Header(TOKEN_KEY) String token, @Body AddNewSlotTimeRequest addNewSlotTimeRequest);

    /*user/deleted slots*/
    @GET("user/deleted-slots/{id}")
    Call<DeleteSlotResponse> deleteSlot(@Header(TOKEN_KEY) String token, @Path("id") Integer id);


    /*user/step-goal*/
    @POST("user/step-goal")
    Call<UserStepsGoalResponse> USER_STEPS_GOAL_RESPONSE_CALL(@Header(TOKEN_KEY) String token, @Body UserStepsGoalRequest userStepsGoalRequest);

    /*user/history-step-counter*/
    @GET("user/history-step-counter")
    Call<HistoryResponse> HISTORY_RESPONSE_CALL(@Header(TOKEN_KEY) String token);

    /*user/health-center*/
    @GET("user/health-center")
    Call<HealthCenterResponse> HEALTH_CENTER_RESPONSE_CALL(@Header(TOKEN_KEY) String token);

    /*user/history-step-counter*/
    @POST("user/history-step-counter")
    Call<WeeklyResponse> WEEKLY_RESPONSE_CALL(@Header(TOKEN_KEY) String token, @Body WeeklyMonthlyYearlyRequest weeklyMonthlyYearlyRequest);

    @POST("user/history-step-counter")
    Call<MonthlyResponse> MONTHLY_RESPONSE_CALL(@Header(TOKEN_KEY) String token, @Body WeeklyMonthlyYearlyRequest weeklyMonthlyYearlyRequest);

    @POST("user/history-step-counter")
    Call<YearlyResponse> YEARLY_RESPONSE_CALL(@Header(TOKEN_KEY) String token, @Body WeeklyMonthlyYearlyRequest weeklyMonthlyYearlyRequest);

    @GET("user/leader-board")
    Call<LeaderBoardResponse> LEADER_BOARD_RESPONSE_CALL(@Header(TOKEN_KEY) String token);

    /*user/all-trips*/
    @GET("user/all-trips ")
    Call<TipsResponse> ALL_TIPS_RESPONSE_CALL(@Header(TOKEN_KEY) String token);

    /*user/waterIntake/exists*/
    @GET("user/waterIntake/exists ")
    Call<WaterIntakeExistsUserResponse> WATER_INTAKE_EXISTS_USER_RESP_CALL(@Header(TOKEN_KEY) String token);


}