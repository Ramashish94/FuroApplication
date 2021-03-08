package com.app.furoapp.base;

/**
 * Every presenter in the app must either implement this interface or extend BasePresenter
 * indicating the MvpView type that wants to be attached with.
 */
public interface MvpPresenter<V extends MainBaseView> {

    void onAttach(V mvpView);

    void onDetach();

    /*TODO change  String to  AN error  and check as per corresponding error */

    void handleApiError(String error);

    void setUserAsLoggedOut();
}
