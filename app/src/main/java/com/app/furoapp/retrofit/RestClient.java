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
import com.app.furoapp.activity.newFeature.StepsTracker.fqsteps.TipsResponse;
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
import com.app.furoapp.model.loginwithgmail.LoginwithGmailRequest;
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
import retrofit2.Callback;

public class RestClient {


    public static void myContentfeedActivity(String token, Callback<ContentFeedModel> callback) {
        RetrofitClient.getClient().getActivities(token).enqueue(callback);
    }

    public static void signUpNewUser(RequestBody name, RequestBody email, RequestBody password, RequestBody dob, RequestBody gender, RequestBody country, RequestBody state, RequestBody city, RequestBody platform, RequestBody fbid, RequestBody google_id, RequestBody username, RequestBody mobile, MultipartBody.Part image, Callback<SignupResponse> callback) {
        RetrofitClient.getClient().signUpUser(name, email, password, dob, gender, country, state, city, platform, fbid, google_id, username, mobile, image).enqueue(callback);
    }

    public static void loginNewUser(LoginRequest loginRequest, Callback<LoginResponse> callback) {
        RetrofitClient.getClientnew().loginUser(loginRequest).enqueue(callback);
    }

    public static void activityDetail(String token, ContentFeedDetailRequest contentFeedDetailRequest, Callback<ContentFeedDetailResponse> callback) {
        RetrofitClient.getClient().contentDetail(token, contentFeedDetailRequest).enqueue(callback);
    }

    public static void loginFb(LoginWithFbRequest loginWithFbRequest, Callback<LoginWithFbResponse> callback) {
        RetrofitClient.getClient().loginWthFb(loginWithFbRequest).enqueue(callback);
    }

    public static void userEmailVerified(EmailVerifiedRequest emailVerifiedRequest, Callback<EmailVerifiedResponse> callback) {
        RetrofitClient.getClient().emailVerified(emailVerifiedRequest).enqueue(callback);
    }

    public static void userPasswordChanged(PasswordChangedRequest passwordChangedRequest, Callback<PasswordChangedResponse> callback) {
        RetrofitClient.getClient().passwordReset(passwordChangedRequest).enqueue(callback);
    }

    public static void userAllCommunities(String token, Callback<AllCommunitiesResponse> callback) {
        RetrofitClient.getClient().allCommunities(token).enqueue(callback);
    }

    public static void userMyCommunities(String token, MyCommunitiesRequest myCommunitiesRequest, Callback<MyCommunitiesResponse> callback) {
        RetrofitClient.getClient().myCommunities(token, myCommunitiesRequest).enqueue(callback);
    }

    public static void userCommunitiesJoin(String token, CommunitiesJoinRequest communitiesJoinRequest, Callback<CommunitiesJoinResponse> callback) {
        RetrofitClient.getClient().communitiesJoin(token, communitiesJoinRequest).enqueue(callback);
    }

    public static void userUniqueName(String token, UniqueUserNameRequest uniqueUserNameRequest, Callback<UniqueUserNameResponse> callback) {
        RetrofitClient.getClient().suggestUserName(token, uniqueUserNameRequest).enqueue(callback);
    }

    public static void userCommunityDetail(String token, CommunityDetailRequest communityDetailRequest, Callback<CommunityDetailResponse> callback) {
        RetrofitClient.getClient().communityDetail(token, communityDetailRequest).enqueue(callback);
    }

    public static void userSelectChallange(String token, Callback<SelectChallangeResponse> callback) {
        RetrofitClient.getClient().selectChallange(token).enqueue(callback);
    }

    public static void userLeaderData(String token, Leaderboard leaderboard, Callback<LeaderModel> callback) {
        RetrofitClient.getClient().getLeaderData(token, leaderboard).enqueue(callback);
    }

    public static void userChooseOneReason(String token, WhatBringsYouToFuroRequest whatBringsYouToFuroRequest, Callback<WhatBringsYouToFuroResponse> callback) {
        RetrofitClient.getClient().chooseOneReason(token, whatBringsYouToFuroRequest).enqueue(callback);
    }

    public static void userCommunityChallange(String token, CommunityChallangeRequest communityChallangeRequest, Callback<CommunityChallangeResponse> callback) {
        RetrofitClient.getClient().communityChallange(token, communityChallangeRequest).enqueue(callback);
    }

    public static void videoChallange(String token, RequestBody user_id, RequestBody acitivity_duration, RequestBody acitivity_count, RequestBody challenge_activity, RequestBody challenge_name, RequestBody accept_challenge_id, MultipartBody.Part video_file, Callback<CreateVideoChallangeResponse> callback) {
        RetrofitClient.getClient().videouploadChallange(token, user_id, acitivity_duration, acitivity_count, challenge_activity, challenge_name, accept_challenge_id, video_file).enqueue(callback);
    }

    public static void userGetNotification(String token, GetNotificationRequest notificationRequest, Callback<GetNotificationResponse> callback) {
        RetrofitClient.getClient().pushNotification(token, notificationRequest).enqueue(callback);
    }

    public static void userExistsContact(String token, RequestBody contacts, RequestBody user_id, Callback<ExistsContactResponse> callback) {
        RetrofitClient.getClient().checkExistsContact(token, contacts, user_id).enqueue(callback);
    }

    public static void userFcmTokenUpdate(String token, UpdateFcmTokenRequest updateFcmTokenRequest, Callback<UpdateFcmTokenResponse> callback) {
        RetrofitClient.getClient().updateFcmToken(token, updateFcmTokenRequest).enqueue(callback);
    }

    public static void userChallangeNotification(String token, ChallangeNotificationRequest challangeNotificationRequest, Callback<ChallangeNotificationResponse> callback) {
        RetrofitClient.getClient().challangetoUserNotification(token, challangeNotificationRequest).enqueue(callback);
    }

    public static void userChallangeDetail(String token, ChallangeDetailRequest challangeDetailRequest, Callback<ChallangeDetailResponse> callback) {
        RetrofitClient.getClient().challengeDetail(token, challangeDetailRequest).enqueue(callback);
    }

    public static void userChallangeByUser(String token, ChallengeByUser challengeByUser, Callback<ChallengeByUserResponse> callback) {
        RetrofitClient.getClient().challngeByUser(token, challengeByUser).enqueue(callback);
    }

    public static void ChallangeAcceptByUser(String token, ChallengeAcceptedRequest challengeAcceptedRequest, Callback<ChallengeAcceptedResponse> callback) {
        RetrofitClient.getClient().challngeAccept(token, challengeAcceptedRequest).enqueue(callback);
    }

    public static void ChallangeRejectByUser(String token, ChallangeRejectRequest challangeRejectRequest, Callback<ChallangeRejectResponse> callback) {
        RetrofitClient.getClient().challngeReject(token, challangeRejectRequest).enqueue(callback);
    }


    public static void myClubChallenges(String token, Callback<ClubChallengeResponse> callback) {
        RetrofitClient.getClient().clubChallenges(token).enqueue(callback);
    }

    public static void myClubDetails(String token, ClubDetailRequest clubDetailRequest, Callback<ClubDetailResponse> callback) {
        RetrofitClient.getClient().clubDetailsChallenges(token, clubDetailRequest).enqueue(callback);
    }

    public static void userFriendsData(String token, FriendRequest friendRequest, Callback<Friends> callback) {
        RetrofitClient.getClient().getFriendsData(token, friendRequest).enqueue(callback);
    }

    public static void AddFriends(String token, AddFriend addFriend, Callback<FriendInviteModel> callback) {
        RetrofitClient.getClient().AddFriendsData(token, addFriend).enqueue(callback);
    }

    public static void PendingFriends(String token, AddFriend addFriend, Callback<FriendPendingModel> callback) {
        RetrofitClient.getClient().FriendsPendingData(token, addFriend).enqueue(callback);
    }

    public static void FriendsList(String token, AddFriend addFriend, Callback<FriendListModel> callback) {
        RetrofitClient.getClient().getFriendList(token, addFriend).enqueue(callback);
    }

    public static void RemoveFriends(String token, AddFriend addFriend, Callback<FriendInviteModel> callback) {
        RetrofitClient.getClient().removeFriends(token, addFriend).enqueue(callback);
    }

    public static void AcceptFriend(String token, AddFriend addFriend, Callback<FriendInviteModel> callback) {
        RetrofitClient.getClient().AcceptFriend(token, addFriend).enqueue(callback);
    }

    public static void RejectFriends(String token, AddFriend addFriend, Callback<FriendInviteModel> callback) {
        RetrofitClient.getClient().RejectFriend(token, addFriend).enqueue(callback);
    }

    public static void winnerUserResponse(String token, WinnerRequest winnerRequest, Callback<WinnerResponse> callback) {
        RetrofitClient.getClient().winner(token, winnerRequest).enqueue(callback);
    }

    public static void getUserDetails(String token, AddFriend addFriend, Callback<UserDetailsModel> callback) {
        RetrofitClient.getClient().getUser(token, addFriend).enqueue(callback);
    }

    public static void UpdateUserDetails(String token, UpdateUserModel updateUserModel, Callback<UserUpdate> callback) {
        RetrofitClient.getClient().UpdateUserInform(token, updateUserModel).enqueue(callback);
    }

    public static void updateUpNewUser(String token, RequestBody user_id, MultipartBody.Part image, Callback<UpdateResponse> callback) {
        RetrofitClient.getClient().updateResponse(token, user_id, image).enqueue(callback);
    }

    // add on 21-11-2019 by pankaj
    public static void addProfile(String token, AddProfile add_Profile, Callback<ProfileModel> callback) {
        RetrofitClient.getClient().GetProfileData(token, add_Profile).enqueue(callback);
    }

    public static void challenegeForYouRecieve(String token, ChallenegeForYouRecieveRequest challenegeForYouRecieveRequest, Callback<ChallenegeForYouRecieve> callback) {
        RetrofitClient.getClient().challengeForYou(token, challenegeForYouRecieveRequest).enqueue(callback);
    }

    public static void userloginwithgmail(HashMap<String, String> hashMap, Callback<LoginwithGmailResponse> callback) {
        RetrofitClient.getClient().loginwithgmail(hashMap).enqueue(callback);
    }

    public static void saveMapChallenge(String token, RequestBody user_id, RequestBody acitivity_duration, RequestBody distance, RequestBody challenge_activity, RequestBody accept_challenge_id, MultipartBody.Part image, Callback<ChallenegeMapResponse> callback) {
        RetrofitClient.getClient().mapChallenge(token, user_id, acitivity_duration, distance, challenge_activity, accept_challenge_id, image).enqueue(callback);
    }

    public static void userChallengeByDetail(String token, ChallenegeByYouDetailRequest challenegeByYouDetailRequest, Callback<ChallenegeByYouDetailResponse> callback) {
        RetrofitClient.getClient().challenegeByDetail(token, challenegeByYouDetailRequest).enqueue(callback);
    }

    public static void userChallengeForDetail(String token, ChallengeForYouDetailRequest challengeForYouDetailRequest, Callback<ChallengeForYouDetailResponse> callback) {
        RetrofitClient.getClient().challenegeForDetail(token, challengeForYouDetailRequest).enqueue(callback);
    }

    public static void userFeedBack(String token, FeedbackRequest feedbackRequest, Callback<FeedbackResponse> callback) {
        RetrofitClient.getClient().feedBack(token, feedbackRequest).enqueue(callback);
    }


    public static void userReportIssue(String token, ReportIssueRequest reportIssueRequest, Callback<ReportIssueResponse> callback) {
        RetrofitClient.getClient().reportIssue(token, reportIssueRequest).enqueue(callback);
    }

    public static void appVersionGet(Callback<VersiongetResponse> callback) {
        RetrofitClient.getClient().versionget().enqueue(callback);
    }

    public static void sendAppVersion(String token, VersionStoreRequest versionStoreRequest, Callback<VersionStoreResponse> callback) {
        RetrofitClient.getClient().submitVersion(token, versionStoreRequest).enqueue(callback);
    }

    public static void userMapReciveChallenge(String token, MapChallengeRecieveRequest mapChallengeRecieveRequest, Callback<MapChallengeRecieveResponse> callback) {
        RetrofitClient.getClient().mapChallengeRecieve(token, mapChallengeRecieveRequest).enqueue(callback);
    }

    public static void userCommunityMembers(String token, CommunityMembersRequest communityMembersRequest, Callback<CommunityMembersResponse> callback) {
        RetrofitClient.getClient().commuintymembers(token, communityMembersRequest).enqueue(callback);
    }

    public static void userdraft(String token, DraftRequest draftRequest, Callback<DraftResponse> callback) {
        RetrofitClient.getClient().draft(token, draftRequest).enqueue(callback);
    }

    public static void userImageUpdate(String token, RequestBody user_id, MultipartBody.Part image, Callback<UpdateImageResponse> callback) {
        RetrofitClient.getClient().updateImage(token, user_id, image).enqueue(callback);
    }

    public static void bannerImage(String token, Callback<BannerResponse> callback) {
        RetrofitClient.getClient().bannerImage(token).enqueue(callback);
    }

    public static void bannerSecondImage(String token, Callback<BannerSecondResponse> callback) {
        RetrofitClient.getClient().bannerSecondImage(token).enqueue(callback);
    }

    public static void updateTokenofuser(String token, RequestBody user_id, RequestBody device_id, RequestBody fcm_token, Callback<UdateTokenResponse> callback) {
        RetrofitClient.getClient().updateToken(token, user_id, device_id, fcm_token).enqueue(callback);
    }

    public static void userimageGet(String token, UserImageRequest userImageRequest, Callback<UserImageResponse> callback) {
        RetrofitClient.getClient().user_image(token, userImageRequest).enqueue(callback);
    }

    /*added new feature in FQ*/
    /*notification*/
    public static void getNotificationData(String token, Callback<NotificationResponses> callback) {
        RetrofitClient.getClient().getNotification(token).enqueue(callback);
    }
   /* public static void getChallangeNotificationData(String token, Callback<ChallengeNotificationResp> callback) {
        RetrofitClient.getClient().CHALLANGE_NOTIFICATION_RESPONSE_CALL(token).enqueue(callback);
    }

    public static void getDailyNotificationData(String token, Callback<DailyFeedNotificationResponse> callback) {
        RetrofitClient.getClient().DAILY_NOTIFICATION_RESPONSE_CALL(token).enqueue(callback);
    }*/

    /*modify*/
    public static void myContentfeedAllActivity(String token, String type, int page, Callback<ActivitiesListing> callback) {
        RetrofitClient.getClient().ActivitiesListing(token, type, page).enqueue(callback);
    }

    public static void activityDetailNew(String token, ContentFeedDetailRequest contentFeedDetailRequest, Callback<ActivityDetailsResponse> callback) {
        RetrofitClient.getClient().contentDetailNew(token, contentFeedDetailRequest).enqueue(callback);
    }

    public static void userPostLike(String token, LikeRequest request, Callback<LikeResponse> callback) {
        RetrofitClient.getClientnew().userPostLike(token, request).enqueue(callback);
    }

    public static void userCommentOnPost(String token, AddCommentRequest request, Callback<AddCommentResponse> callback) {
        RetrofitClient.getClient().userCommentOnPost(token, request).enqueue(callback);
    }

    public static void userPostSaved(String token, SavedRequest request, Callback<SavedResponse> callback) {
        RetrofitClient.getClient().userPostSaved(token, request).enqueue(callback);
    }

    public static void userPostView(String token, ViewsRequest request, Callback<ViewsResponse> callback) {
        RetrofitClient.getClient().userPostView(token, request).enqueue(callback);
    }

    public static void saveList(String token, Callback<SavedListResponse> callback) {
        RetrofitClient.getClient().saveOnPost(token).enqueue(callback);
    }

    public static void likeList(String token, Callback<LikeListResponse> callback) {
        RetrofitClient.getClient().likeList(token).enqueue(callback);
    }

    public static void getBmiStoreData(String token, BmiStoreDataRequest bmiStoreDataRequest, Callback<BmiStoreDataResponse> callback) {
        RetrofitClient.getClient().bmiStoreData(token, bmiStoreDataRequest).enqueue(callback);
    }

    public static void getFetchUserWiseData(String token, Callback<FetchUserWiseDataResponse> callback) {
        RetrofitClient.getClient().fetchuserWiseData(token).enqueue(callback);
    }

    public static void getUserGlassFetch(String token, Callback<GlassFetchResponse> callback) {
        RetrofitClient.getClient().userFetchGlass(token).enqueue(callback);
    }

    public static void getDailyWaterIntake(String token, DailyWaterIntakeRequest dailyWaterIntakeRequest, Callback<DailyWaterIntakeResponse> callback) {
        RetrofitClient.getClient().dailyWaterIntake(token, dailyWaterIntakeRequest).enqueue(callback);
    }

    public static void getFetchAllPlan(String token, Callback<FetchAllPlanResponse> callback) {
        RetrofitClient.getClient().fetchAllPlan(token).enqueue(callback);
    }

    public static void getPlaneCreate(String token, PlaneCreateRequest planeCreateRequest, Callback<PlaneCreateResponse> callback) {
        RetrofitClient.getClient().planeCreate(token, planeCreateRequest).enqueue(callback);
    }

    public static void getCupCreate(String token, Callback<CupCreateResponse> callback) {
        RetrofitClient.getClient().cupCreate(token).enqueue(callback);
    }

    public static void getCustomSizeGlass(String token, SelectCustomGlassSizeRequest selectCustomGlassSizeRequest, Callback<SelectCustomSizeGlassResponse> callback) {
        RetrofitClient.getClient().customGlassSize(token, selectCustomGlassSizeRequest).enqueue(callback);
    }

    public static void getWaterIntakeUpdatePlan(String token, WaterIntakeUpdatePlanRequest waterIntakeUpdatePlanRequest, Callback<WaterIntakeUpdatePlanResponse> callback) {
        RetrofitClient.getClient().WATER_INTAKE_UPDATE_PLAN_RESPONSE_CALL(token, waterIntakeUpdatePlanRequest).enqueue(callback);
    }

    public static void getChangeCupSize(String token, ChangeGlassSizeRequest changeGlassSizeRequest, Callback<UserChangeGlassSizeResponse> callback) {
        RetrofitClient.getClient().CHANGE_GLASS_SIZE_RESPONSE_CALL(token, changeGlassSizeRequest).enqueue(callback);
    }

    public static void getRestorePlan(String token, Callback<RestorePlanResponse> callback) {
        RetrofitClient.getClient().RESTORE_PLAN_RESPONSE_CALL(token).enqueue(callback);
    }

    public static void getModifiedSavedData(String token, ModifiedSavedDataRequest modifiedSavedDataRequest, Callback<ModifiedSavedDataResponse> callback) {
        RetrofitClient.getClient().MODIFIED_SAVED_DATA_RESPONSE_CALL(token, modifiedSavedDataRequest).enqueue(callback);
    }

    public static void getModifiedSavedDataLandingTime(String token, Callback<ModifiedSavedDataResponse> callback) {
        RetrofitClient.getClient().MODIFIED_SAVED_DATA_RESPONSE_CALL_(token).enqueue(callback);
    }

    public static void getFetchAllSlot(String token, Callback<FetchAllSlotResponse> callback) {
        RetrofitClient.getClient().FETCH_ALL_SLOT_RESPONSE_CALL(token).enqueue(callback);
    }

    public static void getAddNewSlotTime(String token, AddNewSlotTimeRequest addNewSlotTimeRequest, Callback<AddNewSlotResponse> callback) {
        RetrofitClient.getClient().ADD_NEW_SLOT_RESPONSE_CALL(token, addNewSlotTimeRequest).enqueue(callback);
    }

    public static void deleteSlot(String token, Integer id, Callback<DeleteSlotResponse> callback) {
        RetrofitClient.getClient().deleteSlot(token, id).enqueue(callback);
    }

    public static void getUserStepsGoal(String token, UserStepsGoalRequest userStepsGoalRequest, Callback<UserStepsGoalResponse> callback) {
        RetrofitClient.getClient().USER_STEPS_GOAL_RESPONSE_CALL(token, userStepsGoalRequest).enqueue(callback);
    }

    public static void getHistoryData(String token, Callback<HistoryResponse> callback) {
        RetrofitClient.getClient().HISTORY_RESPONSE_CALL(token).enqueue(callback);
    }

    public static void getHealthCenter(String token, Callback<HealthCenterResponse> callback) {
        RetrofitClient.getClient().HEALTH_CENTER_RESPONSE_CALL(token).enqueue(callback);
    }

    public static void getWeeklyData(String token, WeeklyMonthlyYearlyRequest weeklyMonthlyYearlyRequest, Callback<WeeklyResponse> callback) {
        RetrofitClient.getClient().WEEKLY_RESPONSE_CALL(token, weeklyMonthlyYearlyRequest).enqueue(callback);
    }

    public static void getMonthlyData(String token, WeeklyMonthlyYearlyRequest weeklyMonthlyYearlyRequest, Callback<MonthlyResponse> callback) {
        RetrofitClient.getClient().MONTHLY_RESPONSE_CALL(token, weeklyMonthlyYearlyRequest).enqueue(callback);
    }

    public static void getYearlyData(String token, WeeklyMonthlyYearlyRequest weeklyMonthlyYearlyRequest, Callback<YearlyResponse> callback) {
        RetrofitClient.getClient().YEARLY_RESPONSE_CALL(token, weeklyMonthlyYearlyRequest).enqueue(callback);
    }

    public static void getLeaderBoardData(String token, Callback<LeaderBoardResponse> callback) {
        RetrofitClient.getClient().LEADER_BOARD_RESPONSE_CALL(token).enqueue(callback);
    }

    public static void getAllTipsData(String token, Callback<TipsResponse> callback) {
        RetrofitClient.getClient().ALL_TIPS_RESPONSE_CALL(token).enqueue(callback);
    }

    public static void getWaterIntakeExistsUser(String token, Callback<WaterIntakeExistsUserResponse> callback) {
        RetrofitClient.getClient().WATER_INTAKE_EXISTS_USER_RESP_CALL(token).enqueue(callback);
    }

}
