package com.app.furoapp.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.app.furoapp.R;
import com.app.furoapp.model.feedback.FeedbackRequest;
import com.app.furoapp.model.feedback.FeedbackResponse;
import com.app.furoapp.retrofit.RestClient;
import com.app.furoapp.utils.FuroPrefs;
import com.app.furoapp.utils.Util;
import com.app.furoapp.utils.Utils;
import com.hsalf.smilerating.SmileRating;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FeedbackActivity extends AppCompatActivity implements SmileRating.OnSmileySelectionListener, SmileRating.OnRatingSelectedListener {
    private static final String TAG = "MainActivity";
    String feedback;
    TextView buttonFeedBack;
    EditText editTextFeedBack;
    String userIdFeedback;
    ImageView imageView;

    private SmileRating mSmileRating;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);
        buttonFeedBack = findViewById(R.id.submitfeedbackbutton);
        imageView = findViewById(R.id.ivBackBtnnew);
        mSmileRating = (SmileRating) findViewById(R.id.ratingView);
        editTextFeedBack = findViewById(R.id.otherFeedback);

        mSmileRating.setOnSmileySelectionListener(this);
        mSmileRating.setOnRatingSelectedListener(this);

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });


        buttonFeedBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (editTextFeedBack.getText().toString().isEmpty()) {
                    Toast.makeText(FeedbackActivity.this, "Please give your Feedback, Your Feedback is valuable for us.", Toast.LENGTH_LONG).show();

                } else {
                    feedbackNew();

                }

            }
        });
    }

    @Override
    public void onRatingSelected(int level, boolean reselected) {
        Log.i(TAG, "Rated as: " + level + " - " + reselected);
    }

    @Override
    public void onSmileySelected(int smiley, boolean reselected) {
        switch (smiley) {
            case SmileRating.BAD:
                buttonFeedBack.setVisibility(View.VISIBLE);
                Log.i(TAG, "Bad");
                feedback = "Bad";
                break;
            case SmileRating.GOOD:
                buttonFeedBack.setVisibility(View.VISIBLE);
                Log.i(TAG, "Good");
                feedback = "Good";
                break;
            case SmileRating.GREAT:
                buttonFeedBack.setVisibility(View.VISIBLE);
                Log.i(TAG, "Great");
                feedback = "Great";
                break;
            case SmileRating.OKAY:
                buttonFeedBack.setVisibility(View.VISIBLE);
                Log.i(TAG, "Okay");
                feedback = "Okay";
                break;
            case SmileRating.TERRIBLE:
                buttonFeedBack.setVisibility(View.VISIBLE);
                Log.i(TAG, "Terrible");
                feedback = "Terrible";
                break;
            case SmileRating.NONE:

                break;
        }


    }

    public void feedbackNew() {

        if (feedback != null) {

            userIdFeedback = FuroPrefs.getString(getApplicationContext(), "loginUserId");
            FeedbackRequest feedbackRequest = new FeedbackRequest();
            feedbackRequest.setFeedback(feedback);
            feedbackRequest.setUserId(userIdFeedback);
            feedbackRequest.setMessage(editTextFeedBack.getText().toString());



            Util.showProgressDialog(this);
            RestClient.userFeedBack(feedbackRequest, new Callback<FeedbackResponse>() {

                @Override
                public void onResponse(Call<FeedbackResponse> call, Response<FeedbackResponse> response) {
                    Util.dismissProgressDialog();

                    try {
                        if (response != null) {
                            if (response.body().getStatus().equalsIgnoreCase("200")) {
                                buttonFeedBack.setText("Feedback Submitted");
                                buttonFeedBack.setBackgroundResource(R.drawable.buttonbackgroungdarkgraycolor);
                                Toast.makeText(FeedbackActivity.this, "Thankyou for your Feedback", Toast.LENGTH_SHORT).show();
                                buttonFeedBack.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        Toast.makeText(FeedbackActivity.this, "Feedback already submitted   ", Toast.LENGTH_SHORT).show();
                                    }
                                });

                            } else {
                                Toast.makeText(FeedbackActivity.this, "Feedback submitted failed ", Toast.LENGTH_SHORT).show();

                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }


                }

                @Override
                public void onFailure(Call<FeedbackResponse> call, Throwable t) {
                    Toast.makeText(FeedbackActivity.this, " Something Went Wrong !!", Toast.LENGTH_SHORT).show();

                }
            });


        }

    }


}
