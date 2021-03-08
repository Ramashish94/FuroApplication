package com.app.furoapp.base;


/**
 * Base class that implements the Presenter interface and provides a base implementation for
 * onAttach() and onDetach(). It also handles keeping a reference to the mvpView that
 * can be accessed from the children classes by calling getmView().
 */
public class BasePresenter<V extends MainBaseView> implements MvpPresenter<V> {


    public V getmView() {
        return mView;
    }

    private V mView;

    @Override
    public void onAttach(V mvpView) {
        mView = mvpView;
    }

    @Override
    public void onDetach() {

        mView = null;

    }

    @Override
    public void handleApiError(String error) {

    }

    @Override
    public void setUserAsLoggedOut() {

    }
}