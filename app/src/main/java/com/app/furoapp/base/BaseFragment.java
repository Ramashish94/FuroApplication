
package com.app.furoapp.base;

import android.content.Context;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.fragment.app.Fragment;


/**
 * Created by Avdhesh on 27/01/17.
 */

public abstract class BaseFragment extends Fragment implements MainBaseView {

    private BaseActivity mActivity;
//    private MyCustomProgressDialog mProgressDialog;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setUp(view);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof BaseActivity) {
            BaseActivity activity = (BaseActivity) context;
            this.mActivity = activity;
            activity.onFragmentAttached();
        }
    }

    @Override
    public void showLoading(boolean withText) {
/*
        mProgressDialog = MyCustomProgressDialog.newInstance(withText);
        mProgressDialog.show(getChildFragmentManager(), "TAT");
*/

    }


    @Override
    public void hideLoading() {
/*
        if (mProgressDialog != null) {
            mProgressDialog.dismiss();
        }
*/
    }

    @Override
    public void onError(String message) {
        if (mActivity != null) {
            mActivity.onError(message);
        }
    }

    @Override
    public void onError(@StringRes int resId) {
        if (mActivity != null) {
            mActivity.onError(resId);
        }
    }

    @Override
    public void showMessage(String message) {
        if (mActivity != null) {
            mActivity.showMessage(message);
        }
    }

    @Override
    public void showMessage(@StringRes int resId) {
        if (mActivity != null) {
            mActivity.showMessage(resId);
        }
    }

    @Override
    public boolean isNetworkConnected() {
        return mActivity != null && mActivity.isNetworkConnected();
    }

    @Override
    public void onDetach() {
        mActivity = null;
        super.onDetach();
    }

    @Override
    public void hideKeyboard() {
        if (mActivity != null) {
            mActivity.hideKeyboard();
        }
    }

    @Override
    public void openActivityOnTokenExpire() {
        if (mActivity != null) {
            mActivity.openActivityOnTokenExpire();
        }
    }


    public BaseActivity getBaseActivity() {
        return mActivity;
    }

//    public void setUnBinder(Unbinder unBinder) {
//        mUnBinder = unBinder;
//    }

    protected abstract void setUp(View view);

    @Override
    public void onDestroy() {
/*
        if (mUnBinder != null) {
            mUnBinder.unbind();
        }
*/
        super.onDestroy();
    }

    public interface Callback {

        void onFragmentAttached();

        void onFragmentDetached(String tag);
    }
    public  void  onBackPressed(){


    }


}
