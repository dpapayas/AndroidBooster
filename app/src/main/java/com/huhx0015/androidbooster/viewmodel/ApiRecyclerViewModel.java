package com.huhx0015.androidbooster.viewmodel;

import android.app.Application;
import androidx.annotation.NonNull;
import com.huhx0015.androidbooster.architecture.base.BaseViewModel;

public class ApiRecyclerViewModel extends BaseViewModel {

    /** CLASS VARIABLES ________________________________________________________________________ **/

    // TEXT VARIABLES
    private String mErrorText;

    // VISIBILITY VARIABLES
    private boolean mErrorVisibility = false;
    private boolean mProgressBarVisibility = false;
    private boolean mRecyclerViewVisibility = false;

    /** CONSTRUCTOR ____________________________________________________________________________ **/

    public ApiRecyclerViewModel(@NonNull Application application) {
        super(application);
    }

    /** GET METHODS ____________________________________________________________________________ **/

    public boolean getErrorVisible() {
        return mErrorVisibility;
    }

    public boolean getProgressBarVisible() {
        return mProgressBarVisibility;
    }

    public boolean getRecyclerViewVisible() {
        return mRecyclerViewVisibility;
    }

    @NonNull
    public String getErrorText() {
        return mErrorText;
    }

    /** SET METHODS ____________________________________________________________________________ **/

    public void setProgressBarVisible(boolean visible) {
        this.mProgressBarVisibility = visible;
        notifyChangeAll();
    }

    public void setRecyclerViewVisible(boolean visible) {
        this.mRecyclerViewVisibility = visible;
        notifyChangeAll();
    }

    public void setErrorVisible(boolean visible) {
        this.mErrorVisibility = visible;
        notifyChangeAll();
    }

    public void setErrorText(@NonNull String text) {
        this.mErrorText = text;
        notifyChangeAll();
    }
}