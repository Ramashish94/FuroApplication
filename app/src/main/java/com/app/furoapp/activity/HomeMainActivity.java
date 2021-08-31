package com.app.furoapp.activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.app.furoapp.R;
import com.app.furoapp.activity.newFeature.StepsTracker.TrackYourStepsStartActivity;
import com.app.furoapp.activity.newFeature.healthCare.HealthCenterDashboardActivity;
import com.app.furoapp.activity.newFeature.bmiCalculator.FindYourBmiActivity;
import com.app.furoapp.activity.newFeature.waterIntakeCalculator.CreateAHydrationPlaneActivity;
import com.app.furoapp.activity.newFeature.waterIntakeCalculator.WaterIntakeExistsUser.WaterIntakeExistsUserResponse;
import com.app.furoapp.activity.newFeature.waterIntakeCalculator.WaterIntakeStartActivity;
import com.app.furoapp.databinding.ActivityMainFramelayoutBinding;
import com.app.furoapp.enums.EnumConstants;
import com.app.furoapp.fragment.YoutubePlayerFragment;
import com.app.furoapp.fragment.challenges.ChooseChallengeFragment;
import com.app.furoapp.fragment.challenges.ClubChallengeDetailsFragment;
import com.app.furoapp.fragment.challenges.ClubChallengeFragment;
import com.app.furoapp.fragment.challenges.ClubChallengeFriendLeaderBoardFragment;
import com.app.furoapp.fragment.challenges.CreateChallangeEnduranceFragment;
import com.app.furoapp.fragment.challenges.CreateChallengeFragment;
import com.app.furoapp.fragment.challenges.HomeChallengeLeaderBoard;
import com.app.furoapp.fragment.challenges.HomeChallengesFragment;
import com.app.furoapp.fragment.challenges.LeaderBoardFriendFragment;
import com.app.furoapp.fragment.challenges.LeaderBoardInviteFragment;
import com.app.furoapp.fragment.challenges.OpenAndCloseChallengeFragment;
import com.app.furoapp.fragment.challenges.RecordYourChallengeVideoFragment;
import com.app.furoapp.fragment.community.AllCommunitiesFragment;
import com.app.furoapp.fragment.community.MyCommunityFragment;
import com.app.furoapp.fragment.community.CommunitiesHomeFragment;
import com.app.furoapp.fragment.community.CommunityChallengesDetailFragment;
import com.app.furoapp.fragment.community.CommunityChallengesJoinFragment;
import com.app.furoapp.fragment.community.CommunityChallengesSelectChallangeListFragmen;
import com.app.furoapp.fragment.content_feed.ContentActivityFragment;
import com.app.furoapp.fragment.content_feed.ContentFeedDetailFood;
import com.app.furoapp.fragment.content_feed.ContentFeedHomeFragment;
import com.app.furoapp.fragment.content_feed.ContentFeedMainFragment;
import com.app.furoapp.fragment.content_feed.ContentFoodFragment;
import com.app.furoapp.fragment.content_feed.ContentHealthFragment;
import com.app.furoapp.fragment.content_feed.ContentMotivationFragment;
import com.app.furoapp.fragment.enduranceMapFragments.EnduranceMapFragmentStart;
import com.app.furoapp.fragment.profileSection.ProfileEnduranceFragment;
import com.app.furoapp.fragment.profileSection.ProfileFlexibilityChallengeFragment;
import com.app.furoapp.fragment.profileSection.ProfileStrengthFragment;
import com.app.furoapp.fragment.profileSection.ProfilesHomeNewFragment;
import com.app.furoapp.fragment.settings.Settings;
import com.app.furoapp.fragment.socialFragment.Social;
import com.app.furoapp.model.updatefcmtoken.UpdateFcmTokenRequest;
import com.app.furoapp.model.updatefcmtoken.UpdateFcmTokenResponse;
import com.app.furoapp.retrofit.RestClient;
import com.app.furoapp.utils.Constants;
import com.app.furoapp.utils.FuroPrefs;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.firebase.analytics.FirebaseAnalytics;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeMainActivity extends AppCompatActivity {
    private String getAccessToke;

    public static final int REQUEST_CODE = 301;
    public static final int REQUEST_CODE_1 = 302;
    public static final int REQUEST_CODE_2 = 303;
    ActivityMainFramelayoutBinding binding;
    String onClickFeed, onClickChallenge, onClickCommmunity, onclickfriend;
    BottomSheetDialog mBottomSheetDialog;
    Fragment contentFeedMainFragment;
    String contest;
    public LinearLayout llHealthCanterDashboard, llDailyStepTracker, llWaterIntakeCalculator, llTrackBMI, llCalculateCalories;

    public GoogleSignInClient mGoogleSignInClient;
    public AlertDialog.Builder dialogBuilder;
    private AlertDialog dialog;

    private FirebaseAnalytics mFirebaseAnalytics;
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {

            binding.navigation.getSelectedItemId();

            switch (item.getItemId()) {

                case R.id.navigation_home:
                    onClickChallenge = FuroPrefs.getString(getApplicationContext(), "tutorialchallenge");
                    if (onClickChallenge.equalsIgnoreCase("newchallenge")) {
                        replaceFragmentWithStack(R.id.container_main, HomeChallengesFragment.newInstance(null), HomeChallengesFragment.class.getSimpleName());
                    } else {
                        Intent intent = new Intent(HomeMainActivity.this, WelcomeActivityChallenges.class);
                        startActivityForResult(intent, REQUEST_CODE);
                    }
                    return true;
                case R.id.navigation_feeds:
                    onClickFeed = FuroPrefs.getString(getApplicationContext(), "tutorial");
                    if (onClickFeed.equalsIgnoreCase("contentfeed")) {
                        replaceFragmentWithStack(R.id.container_main, ContentFeedMainFragment.newInstance(null), ContentFeedMainFragment.class.getSimpleName());

                    } else {
                        Intent intent = new Intent(HomeMainActivity.this, WelcomeActivityContentFeed.class);
                        startActivity(intent);
                    }
                    return true;


                case R.id.navigation_community:

                    onClickCommmunity = FuroPrefs.getString(getApplicationContext(), "tutorialcommunity");
                    if (onClickCommmunity.equalsIgnoreCase("community")) {
                        replaceFragmentWithStack(R.id.container_main, CommunitiesHomeFragment.newInstance(null), CommunitiesHomeFragment.class.getSimpleName());

                    } else {
                        Intent intent = new Intent(HomeMainActivity.this, WelcomeActivityCommunity.class);
                        startActivityForResult(intent, REQUEST_CODE_1);
                    }
                    return true;

                case R.id.navigation_setting:

                    replaceFragmentWithStack(R.id.container_main, Settings.newInstance(null), Settings.class.getSimpleName());

                    return true;

                case R.id.navigation_more:

                    mBottomSheetDialog = new BottomSheetDialog(HomeMainActivity.this);
                    View sheetView = getLayoutInflater().inflate(R.layout.dialog_bottomsheet, null);
                    mBottomSheetDialog.setContentView(sheetView);
                    mBottomSheetDialog.setCancelable(false);

                    LinearLayout ll_leaderboard = sheetView.findViewById(R.id.ll_leaderboard);
                    LinearLayout ll_profile = sheetView.findViewById(R.id.ll_profile);
                    ImageView imageView = sheetView.findViewById(R.id.cancelbutton);
                    LinearLayout ll_friends = sheetView.findViewById(R.id.ll_friends);
                    LinearLayout ll_aboutus = sheetView.findViewById(R.id.ll_aboutus);
                    LinearLayout ll_Feedback = sheetView.findViewById(R.id.ll_Feedback);
                    LinearLayout ll_invitesfriends = sheetView.findViewById(R.id.ll_invitefriends);
                    LinearLayout ll_privacy = sheetView.findViewById(R.id.ll_privacypolicy);
                    mBottomSheetDialog.show();

                    imageView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            mBottomSheetDialog.dismiss();
                        }
                    });

                    ll_leaderboard.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            mBottomSheetDialog.dismiss();
                            onclickfriend = FuroPrefs.getString(getApplicationContext(), "tutorialleaderboard");
                            Intent intent;
                            if (onclickfriend.equalsIgnoreCase("leaderboard")) {
                                intent = new Intent(HomeMainActivity.this, LeaderBoardActivity.class);
                            } else {
                                intent = new Intent(HomeMainActivity.this, WelcomeLeaderBoardActivity.class);
                            }
                            startActivity(intent);
                        }
                    });

                    ll_profile.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            mBottomSheetDialog.dismiss();
                            replaceFragmentWithStack(R.id.container_main, ProfilesHomeNewFragment.newInstance(null), ProfilesHomeNewFragment.class.getSimpleName());
                        }
                    });

                    ll_friends.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            mBottomSheetDialog.dismiss();

                            onclickfriend = FuroPrefs.getString(getApplicationContext(), "tutorialFriends");
                            Intent intent;
                            if (onclickfriend.equalsIgnoreCase("friends")) {
                                intent = new Intent(HomeMainActivity.this, FriendsActivity.class);
                            } else {
                                intent = new Intent(HomeMainActivity.this, WewlcomeFriendsActivity.class);

                            }
                            startActivity(intent);

                        }
                    });

                    ll_invitesfriends.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            mBottomSheetDialog.dismiss();
                            Intent intent = new Intent(HomeMainActivity.this, InviteFriendsActivity.class);
                            startActivity(intent);
                        }
                    });
                    ll_Feedback.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            mBottomSheetDialog.dismiss();
                            Intent intent = new Intent(HomeMainActivity.this, FeedbackActivity.class);
                            startActivity(intent);
                        }
                    });
                    ll_privacy.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            mBottomSheetDialog.dismiss();
                            Intent intent = new Intent(HomeMainActivity.this, PrivacyPolicyActivity.class);
                            startActivity(intent);
                        }
                    });

                    ll_aboutus.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            mBottomSheetDialog.dismiss();
                            Intent intent = new Intent(HomeMainActivity.this, AboutUsActivity.class);
                            startActivity(intent);
                        }
                    });


                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main_framelayout);
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);

        /*getToken....added*/
        // getAccessToke = FuroPrefs.getString(getApplicationContext(), Constants.Get_ACCESS_TOKEN);

        Intent intent = getIntent();
        contest = intent.getStringExtra("contestpage");

        Bundle bundle = new Bundle();

        bundle.putString(FirebaseAnalytics.Param.CONTENT_TYPE, "image");
        mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.SELECT_CONTENT, bundle);

        //isNetworkConnectionAvailable();


        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN); // add on 20-11-2019 by pankaj

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        /*added*/
        llHealthCanterDashboard = findViewById(R.id.llHealthCenterDashBoard);
        llDailyStepTracker = findViewById(R.id.llDailyStepTracker);
        llWaterIntakeCalculator = findViewById(R.id.llWaterIntakeCalculator);
        llTrackBMI = findViewById(R.id.llTrackBMI);


        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        navigation.getMenu().findItem(R.id.navigation_feeds).setChecked(true);

        onClickFeed = FuroPrefs.getString(getApplicationContext(), "tutorial");


        if (onClickFeed.equalsIgnoreCase("contentfeed")) {
            replaceFragmentWithStack(R.id.container_main, ContentFeedMainFragment.newInstance(null), ContentFeedMainFragment.class.getSimpleName());

        } else {
            Intent intent1 = new Intent(HomeMainActivity.this, WelcomeActivityContentFeed.class);

            startActivity(intent1);


        }


        fcmTokenUpdate();
        clickEvetntOnHealthItem();


        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(getApplicationContext(), gso);


    }


    public void setDisplayFragment(int id, Bundle bundle) {
        switch (id) {
            case EnumConstants.HOME_CHALLENGE_CHOOSE:


                Fragment fragmentHomeChallengeChoose = ChooseChallengeFragment.newInstance(ChooseChallengeFragment.class.getSimpleName());
                replaceFragmentWithStack(R.id.fl_inside_home, fragmentHomeChallengeChoose, ChooseChallengeFragment.class.getSimpleName());

                break;
            case EnumConstants.HOME_CHALLENGE_CREATE:
                Fragment fragmentHomeChallengeCreate = CreateChallengeFragment.newInstance(CreateChallengeFragment.class.getSimpleName());
                replaceFragmentWithStack(R.id.fl_inside_home, fragmentHomeChallengeCreate, CreateChallengeFragment.class.getSimpleName());

                break;
            case EnumConstants.HOME_OPEN_CLOSE_CHALLENGE:
                Fragment fragmentHomeOpenCloseChallenge = OpenAndCloseChallengeFragment.newInstance(OpenAndCloseChallengeFragment.class.getSimpleName());
                replaceFragmentWithStack(R.id.container_main, fragmentHomeOpenCloseChallenge, OpenAndCloseChallengeFragment.class.getSimpleName());

                break;
            case EnumConstants.HOME_CLUB_CHALLENGES:
                Fragment fragmentClubChallengesFragment = ClubChallengeFragment.newInstance(ClubChallengeFragment.class.getSimpleName());
                replaceFragmentWithStack(R.id.container_main, fragmentClubChallengesFragment, ClubChallengeFragment.class.getSimpleName());

                break;
            case EnumConstants.HOME_CHALLENGE_DETAILS_FRAGMENT:
                Fragment fragmentclubChallengeDetail = ClubChallengeDetailsFragment.newInstance(ClubChallengeDetailsFragment.class.getSimpleName());
                replaceFragmentWithStack(R.id.container_main, fragmentclubChallengeDetail, ClubChallengeDetailsFragment.class.getSimpleName());

                break;

            case EnumConstants.HOME_CHALLENGES_LEADERBOARD_FRAGMENT:
                Fragment fragmentHomeChallengeLeaderBoard = HomeChallengeLeaderBoard.newInstance(HomeChallengeLeaderBoard.class.getSimpleName());
                replaceFragmentWithStack(R.id.container_main, fragmentHomeChallengeLeaderBoard, HomeChallengeLeaderBoard.class.getSimpleName());

                break;
            case EnumConstants.COMMUNITIES_HOME_FRAGMENTS:
                Fragment fragmentHomeCommunities = CommunitiesHomeFragment.newInstance(CommunitiesHomeFragment.class.getSimpleName());
                addFragmentWithStack(R.id.container_main, fragmentHomeCommunities, CommunitiesHomeFragment.class.getSimpleName());

                break;

            case EnumConstants.HOME_COMMUNITIES_CHALLENGE_DETAILS:
                Fragment fragmentCommunityChallengeDetailFragment = CommunityChallengesDetailFragment.newInstance(CommunityChallengesDetailFragment.class.getSimpleName());
                addFragmentWithStack(R.id.container_main, fragmentCommunityChallengeDetailFragment, CommunityChallengesDetailFragment.class.getSimpleName());

                break;


            case EnumConstants.HOME_COMMUNITIES_CHALLENGE_DETAILS_WITH_STATUS:
                Fragment fragmentCommunityChallengeJoinFragment = CommunityChallengesJoinFragment.newInstance(CommunityChallengesJoinFragment.class.getSimpleName());
                addFragmentWithStack(R.id.container_main, fragmentCommunityChallengeJoinFragment, CommunityChallengesJoinFragment.class.getSimpleName());

                break;
            case EnumConstants.COMMUNITIES_SELECT_CHALLENGES_LIST:
                Fragment fragmentCommunitySelectedChallengesList = CommunityChallengesSelectChallangeListFragmen.newInstance(CommunityChallengesSelectChallangeListFragmen.class.getSimpleName());
                addFragmentWithStack(R.id.fl_inside_home, fragmentCommunitySelectedChallengesList, CommunityChallengesSelectChallangeListFragmen.class.getSimpleName());

                break;

            case EnumConstants.CONTENTFEEDDETAILCHALLENGE:
                Fragment fragmentcontentfeeddetailchallenge = ContentFeedHomeFragment.newInstance(ContentFeedHomeFragment.class.getSimpleName());
                replaceFragmentWithStack(R.id.container_main, fragmentcontentfeeddetailchallenge, ContentFeedHomeFragment.class.getSimpleName());

                break;
            case EnumConstants.PROFILE_ENDURANCEFRAGMENT:
                Fragment fragmentProfileEndurancment = ProfileEnduranceFragment.newInstance(ProfileEnduranceFragment.class.getSimpleName());
                replaceFragmentWithStack(R.id.container_main, fragmentProfileEndurancment, ProfileEnduranceFragment.class.getSimpleName());

                break;
            case EnumConstants.PROFILE_FLEXIBILITY_CHALLENGE_FRAGMENT:
                Fragment fragmentFlexibilityChallengeFragment = ProfileFlexibilityChallengeFragment.newInstance(ProfileFlexibilityChallengeFragment.class.getSimpleName());
                replaceFragmentWithStack(R.id.container_main, fragmentFlexibilityChallengeFragment, ProfileFlexibilityChallengeFragment.class.getSimpleName());

                break;
            case EnumConstants.PROFILE_HOME_FRAGMENT:
                Fragment fragmentHomeFragment = ProfilesHomeNewFragment.newInstance(ProfilesHomeNewFragment.class.getSimpleName());
                replaceFragmentWithStack(R.id.container_main, fragmentHomeFragment, ProfilesHomeNewFragment.class.getSimpleName());

                break;
            case EnumConstants.PROFILE_STRENGTH_FRAGMENT:
                Fragment fragmentStrengthFragment = ProfileStrengthFragment.newInstance(ProfileStrengthFragment.class.getSimpleName());
                replaceFragmentWithStack(R.id.container_main, fragmentStrengthFragment, ProfileStrengthFragment.class.getSimpleName());

                break;

            case EnumConstants.ALL_COMMUNITIES_FRAGMENT:
                Fragment allCommunitiesFragment = AllCommunitiesFragment.newInstance(AllCommunitiesFragment.class.getSimpleName());
                replaceFragmentWithStack(R.id.fl_inside_home, allCommunitiesFragment, AllCommunitiesFragment.class.getSimpleName());

                break;
            case EnumConstants.ALL_COMMUNITY_FRAGMENT_TWO:
                Fragment allCommunitiesFragmenttwo = MyCommunityFragment.newInstance(MyCommunityFragment.class.getSimpleName());
                replaceFragmentWithStack(R.id.fl_inside_home, allCommunitiesFragmenttwo, MyCommunityFragment.class.getSimpleName());

                break;
            case EnumConstants.COMMUNITY_CHALLENGE_JOIN_FRAGMENT:
                Fragment communityjoinfragment = CommunityChallengesJoinFragment.newInstance(CommunityChallengesJoinFragment.class.getSimpleName());
                replaceFragmentWithStack(R.id.container_main, communityjoinfragment, CommunityChallengesJoinFragment.class.getSimpleName());

                break;
            case EnumConstants.COMMUNITY_CHALLENGES_SELECT_CHALLENGES_LIST_FRAGMENT:
                Fragment communitychallengeselectlist = CommunityChallengesSelectChallangeListFragmen.newInstance(CommunityChallengesSelectChallangeListFragmen.class.getSimpleName());
                replaceFragmentWithStack(R.id.container_main, communitychallengeselectlist, CommunityChallengesSelectChallangeListFragmen.class.getSimpleName());

                break;

            case EnumConstants.LEADER_BOARD_FRIEND_FRAGMENT:
                Fragment leaderBoardFriend = LeaderBoardFriendFragment.newInstance(LeaderBoardFriendFragment.class.getSimpleName());
                replaceFragmentWithStack(R.id.fl_inside_home, leaderBoardFriend, LeaderBoardFriendFragment.class.getSimpleName());

                break;

            case EnumConstants.CLUB_CHALLENGE_LEADER_BOARD_FRIEND_FRAGMENT:
                Fragment clubChallengeFriendLeaderBoardFragment = ClubChallengeFriendLeaderBoardFragment.newInstance(ClubChallengeFriendLeaderBoardFragment.class.getSimpleName());
                replaceFragmentWithStack(R.id.container_main, clubChallengeFriendLeaderBoardFragment, ClubChallengeFriendLeaderBoardFragment.class.getSimpleName());

                break;
            case EnumConstants.LEADER_BOARD_INVITE_FRAGMENT:
                Fragment leaderBoardInvite = LeaderBoardInviteFragment.newInstance(LeaderBoardInviteFragment.class.getSimpleName());
                replaceFragmentWithStack(R.id.fl_inside_home, leaderBoardInvite, LeaderBoardInviteFragment.class.getSimpleName());

                break;
            case EnumConstants.CONTENT_FEED_MAIN_FRAGMENT:
                contentFeedMainFragment = ContentFeedMainFragment.newInstance(ContentFeedMainFragment.class.getSimpleName());
                replaceFragmentWithStack(R.id.container_main, contentFeedMainFragment, ContentFeedMainFragment.class.getSimpleName());

                break;
            case EnumConstants.ALL_FEED_CATEGORY:

                Fragment contentFeedHomeFragment = ContentFeedHomeFragment.newInstance(ContentFeedHomeFragment.class.getSimpleName());
                replaceFragmentWithStack(R.id.container_feed, contentFeedHomeFragment, ContentFeedHomeFragment.class.getSimpleName());

                break;
            case EnumConstants.ACTVITIES_CATEGORY:
                Fragment contentActivityFragment = ContentActivityFragment.newInstance(ContentActivityFragment.class.getSimpleName());
                replaceFragmentWithStack(R.id.container_feed, contentActivityFragment, ContentActivityFragment.class.getSimpleName());

                break;
            case EnumConstants.FOOD_CATEGORY:
                Fragment contentFoodFragment = ContentFoodFragment.newInstance(ContentFoodFragment.class.getSimpleName());
                addFragmentWithStack(R.id.container_feed, contentFoodFragment, ContentFoodFragment.class.getSimpleName());

                break;
            case EnumConstants.HEALTH_CATEGORY:
                Fragment contentHealthFragment = ContentHealthFragment.newInstance(ContentHealthFragment.class.getSimpleName());
                addFragmentWithStack(R.id.container_feed, contentHealthFragment, ContentHealthFragment.class.getSimpleName());

                break;
            case EnumConstants.MOTIVATION_CATEGORY:
                Fragment contentMotivationFragment = ContentMotivationFragment.newInstance(ContentMotivationFragment.class.getSimpleName());
                addFragmentWithStack(R.id.container_feed, contentMotivationFragment, ContentMotivationFragment.class.getSimpleName());

                break;
            case EnumConstants.RECORD_YOUR_VIDEO_FRGMENT:
                Fragment recordYourChallengeVideoFragment = RecordYourChallengeVideoFragment.newInstance(RecordYourChallengeVideoFragment.class.getSimpleName());
                addFragmentWithStack(R.id.container_main, recordYourChallengeVideoFragment, RecordYourChallengeVideoFragment.class.getSimpleName());

                break;


            case EnumConstants.FRAGMENT_ENDURANCE_MAP:
                Fragment enduranceMapFragmentStart = EnduranceMapFragmentStart.newInstance(EnduranceMapFragmentStart.class.getSimpleName());
                replaceFragmentWithStack(R.id.container_main, enduranceMapFragmentStart, EnduranceMapFragmentStart.class.getSimpleName());

                break;

            case EnumConstants.CREATE_CHALLANGE_ENDURANCE_FRAGMENT:

                Fragment createChallangeEnduranceFragment = CreateChallangeEnduranceFragment.newInstance(CreateChallangeEnduranceFragment.class.getSimpleName());
                replaceFragmentWithStack(R.id.fl_inside_home, createChallangeEnduranceFragment, CreateChallangeEnduranceFragment.class.getSimpleName());

                break;
            /*case EnumConstants.CREATE_FEED_DETAIL_FRAGMENT:

                Fragment contentFeedDetailFragment = ContentFeedDetailFragment.newInstance(ContentFeedDetailFragment.class.getSimpleName());
                replaceFragmentWithStack(R.id.container_main, contentFeedDetailFragment, ContentFeedDetailFragment.class.getSimpleName());

                break;*/
            case EnumConstants.YOUTUBE_PLAYER_FRAGMENT:

                Fragment youtubePlayerFragment = YoutubePlayerFragment.newInstance(YoutubePlayerFragment.class.getSimpleName());
                replaceFragmentWithStack(R.id.container_main, youtubePlayerFragment, YoutubePlayerFragment.class.getSimpleName());

                break;

            case EnumConstants.CONTENT_FEED_DETAIL_FOOD:

                Fragment contentFeedDetailFood = ContentFeedDetailFood.newInstance(ContentFeedDetailFood.class.getSimpleName());
                replaceFragmentWithStack(R.id.container_main, contentFeedDetailFood, ContentFeedDetailFood.class.getSimpleName());

                break;

            case EnumConstants.SETTINGS:

                Fragment settings = Settings.newInstance(Settings.class.getSimpleName());
                replaceFragmentWithStack(R.id.container_main, settings, Settings.class.getSimpleName());

                break;

            case EnumConstants.SOCIAL:

                Fragment social = Social.newInstance(Social.class.getSimpleName());
                replaceFragmentWithStack(R.id.container_main, social, Social.class.getSimpleName());

                break;


        }
    }

    public void addFragmentWithStack(int containerId, Fragment fragment, String name) {
        try {

            FragmentManager fragmentManager = getSupportFragmentManager();

            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.add(containerId, fragment, name);
            if (!fragment.isAdded()) {
                fragmentTransaction.addToBackStack(name);
                fragmentTransaction.commitAllowingStateLoss();

            }

        } catch (IllegalStateException e) {
            e.printStackTrace();
        }
    }

    public void replaceFragmentWithStack(int containerId, Fragment fragment, String name) {
        try {
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(containerId, fragment, name);

            if (!fragment.isAdded()) {
                fragmentTransaction.addToBackStack(name);
                fragmentTransaction.commitAllowingStateLoss();

            }
        } catch (IllegalStateException e) {
            e.printStackTrace();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        return id == R.id.container_main || super.onOptionsItemSelected(item);

    }

    /* @Override
     public void onBackPressed() {

         int count = getSupportFragmentManager().getBackStackEntryCount();
         binding.navigation.getMenu().getItem(0).setChecked(true);
         if (count == 1) {
             new AlertDialog.Builder(this)
                     .setMessage("Are you sure you want to exit?")
                     .setCancelable(false)
                     .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                         public void onClick(DialogInterface dialog, int id) {
                             HomeMainActivity.super.onBackPressed();
                             finish();
                         }
                     })
                     .setNegativeButton("No", null)
                     .show();


         } else {

         }


     }

 */
    @Override
    public void onBackPressed() {

        int count = getSupportFragmentManager().getBackStackEntryCount();
        binding.navigation.getMenu().getItem(0).setChecked(true);


        if (count == 1) {
            final AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
            LayoutInflater inflater = this.getLayoutInflater();
            View dialogView = inflater.inflate(R.layout.profile_alertdialog_logout_new, null);


            dialogBuilder.setView(dialogView);

            final AlertDialog dialog = dialogBuilder.create();


            ImageView btn_Cancel = dialogView.findViewById(R.id.btn_cancel);
            TextView noiwanttocontinue = dialogView.findViewById(R.id.noiwanttocontinuee);
            TextView text_logout = dialogView.findViewById(R.id.text_logout);
            btn_Cancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();

                }
            });
            noiwanttocontinue.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();

                }
            });
            text_logout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    HomeMainActivity.super.onBackPressed();
                    finishAffinity();
                    System.exit(0);


                }
            });

            dialog.show();


        } else {
            getSupportFragmentManager().popBackStack();
        }


    }

    public void fcmTokenUpdate() {
        String userId = FuroPrefs.getString(this, "loginUserId");
        String tokenFcm = FuroPrefs.getString(this, "token");


        UpdateFcmTokenRequest updateFcmTokenRequest = new UpdateFcmTokenRequest();
        updateFcmTokenRequest.setFcmToken(tokenFcm);
        updateFcmTokenRequest.setUserId(userId);

        RestClient.userFcmTokenUpdate(updateFcmTokenRequest, new Callback<UpdateFcmTokenResponse>() {
            @Override
            public void onResponse(Call<UpdateFcmTokenResponse> call, Response<UpdateFcmTokenResponse> response) {
                if (response != null) {


                } else {

                }

            }

            @Override
            public void onFailure(Call<UpdateFcmTokenResponse> call, Throwable t) {
                Toast.makeText(HomeMainActivity.this, "Something went wrong !!", Toast.LENGTH_SHORT).show();

            }
        });


    }


    public boolean isNetworkConnectionAvailable() {
        ConnectivityManager cm =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null &&
                activeNetwork.isConnected();
        if (isConnected) {
            Log.d("Network", "Connected");
            return true;
        } else {
            checkNetworkConnection();
            Log.d("Network", "Not Connected");
            return false;
        }
    }

    public void checkNetworkConnection() {
        Toast.makeText(this, "No Internet connection !", Toast.LENGTH_SHORT).show();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE) {
            replaceFragmentWithStack(R.id.container_main, HomeChallengesFragment.newInstance(null), HomeChallengesFragment.class.getSimpleName());

        } else if (requestCode == REQUEST_CODE_1) {
            replaceFragmentWithStack(R.id.container_main, CommunitiesHomeFragment.newInstance(null), CommunitiesHomeFragment.class.getSimpleName());
        }


    }

    @Override
    protected void onResume() {

        if (contest.equalsIgnoreCase("contestpageee")) {
            replaceFragmentWithStack(R.id.container_main, HomeChallengesFragment.newInstance(null), HomeChallengesFragment.class.getSimpleName());
        } else if (contest.equalsIgnoreCase("")) {

        }
        super.onResume();
    }

    private void clickEvetntOnHealthItem() {
        llHealthCanterDashboard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), HealthCenterDashboardActivity.class);
                startActivity(intent);

            }
        });

        llDailyStepTracker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), TrackYourStepsStartActivity.class);
                startActivity(intent);

            }
        });

        llWaterIntakeCalculator.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callWaterIntakeExistsUserApi();

            }
        });

        llTrackBMI.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(HomeMainActivity.this, "Working on this", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getApplicationContext(), FindYourBmiActivity.class);
                startActivity(intent);

            }
        });

    }

    private void callWaterIntakeExistsUserApi() {

        RestClient.getWaterIntakeExistsUser(FuroPrefs.getString(getApplicationContext(), Constants.Get_ACCESS_TOKEN), new Callback<WaterIntakeExistsUserResponse>() {
            @Override
            public void onResponse(Call<WaterIntakeExistsUserResponse> call, Response<WaterIntakeExistsUserResponse> response) {
                if (response.code() == 200) {
                    if (response.body() != null) {
                        if (response.body().getIsWaterIntakeDataRequired() == 1) {
                            Intent intent = new Intent(getApplicationContext(), CreateAHydrationPlaneActivity.class);
                            startActivity(intent);
                        } else {
                            if (response.body().getIsWaterIntakeDataRequired() == 0) {
                                Intent intent = new Intent(getApplicationContext(), WaterIntakeStartActivity.class);
                                startActivity(intent);
                            }
                        }
                    }
                } else if (response.code() == 500) {
                    Toast.makeText(getApplicationContext(), response.code() + "Internal server error!", Toast.LENGTH_SHORT).show();
                } else if (response.code() == 403) {
                    getAlertTokenDialog();
                }

            }

            @Override
            public void onFailure(Call<WaterIntakeExistsUserResponse> call, Throwable t) {
                Toast.makeText(HomeMainActivity.this, "Something went wrong in water intake!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void getAlertTokenDialog() {
        if (FuroPrefs.getString(getApplicationContext(), Constants.Get_ACCESS_TOKEN) != null) {
            dialogBuilder = new AlertDialog.Builder(this);
            LayoutInflater inflater = this.getLayoutInflater();
            View dialogView = inflater.inflate(R.layout.session_expired_layout, null);
            dialogBuilder.setView(dialogView);
            dialog = dialogBuilder.create();
            ImageView btn_Cancel = dialogView.findViewById(R.id.btn_cancel);
            TextView text_logout = dialogView.findViewById(R.id.text_logout);
            TextView noiwanttocontinue = dialogView.findViewById(R.id.noiwanttocontinuee);
            LinearLayout llLogOut = dialogView.findViewById(R.id.llLogOut);

            btn_Cancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                }
            });

            noiwanttocontinue.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                }
            });

            text_logout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    FuroPrefs.clear(getApplicationContext());
                    googleSignOut();
                    Intent intent = new Intent(getApplicationContext(), LoginTutorialScreen.class);
                    startActivity(intent);
                    finishAffinity();
                }
            });
            dialog.show();
        } else {

        }
    }

    public void googleSignOut() {

        mGoogleSignInClient.signOut()
                .addOnCompleteListener(this, new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(Task<Void> task) {
                        // Toast.makeText(ActivityMain.this, "Google Sign Out done.", Toast.LENGTH_SHORT).show();
                        revokeAccess();
                    }
                });
    }

    private void revokeAccess() {
        mGoogleSignInClient.revokeAccess()
                .addOnCompleteListener(this, new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(Task<Void> task) {
                        // Toast.makeText(ActivityMain.this, "Google access revoked.", Toast.LENGTH_SHORT).show();
                    }
                });
    }

}



