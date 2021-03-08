package com.app.furoapp.utils;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;


public class Twitter {
    public static Intent getTwitterIntent(Context ctx, String shareText)
    {
        Intent shareIntent;


            shareIntent = new Intent(Intent.ACTION_SEND);
            shareIntent.setClassName("com.twitter.android",
                    "com.twitter.android.PostActivity");
            shareIntent.setType("text/*");
            shareIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareText);
            return shareIntent;
        }

    }
