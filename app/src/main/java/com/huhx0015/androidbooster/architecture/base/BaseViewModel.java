package com.huhx0015.androidbooster.architecture.base;

import android.app.Application;
import android.content.Context;
import androidx.annotation.CallSuper;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.Observable;
import androidx.databinding.PropertyChangeRegistry;
import androidx.databinding.library.baseAdapters.BR;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.OnLifecycleEvent;
import com.huhx0015.androidbooster.application.AndroidApplication;
import com.huhx0015.androidbooster.injections.components.ActivityComponent;
import com.huhx0015.androidbooster.injections.components.DaggerActivityComponent;
import com.huhx0015.androidbooster.injections.modules.ActivityModule;
import com.huhx0015.androidbooster.injections.modules.ViewModelModule;
import javax.inject.Inject;
import io.reactivex.disposables.CompositeDisposable;

public class BaseViewModel extends AndroidViewModel implements LifecycleOwner, Observable {

    /** CLASS VARIABLES ________________________________________________________________________ **/

    @Inject
    CompositeDisposable mDisposable;
    @Inject
    Lifecycle mLifecycle;
    @Inject
    PropertyChangeRegistry mRegistry;

    /** CONSTRUCTOR ____________________________________________________________________________ **/

    public BaseViewModel(@NonNull Application application) {
        super(application);
        getComponent(application).inject(this); // DEPENDENCY INJECTION
    }

    /** LIFECYCLE METHODS ______________________________________________________________________ **/

    @CallSuper
    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    public void onCreate() {}

    @CallSuper
    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    public void onResume() {}

    @CallSuper
    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    public void onPause() {}

    @CallSuper
    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    public void onDestroy() {
        mDisposable.dispose();
        removeOnPropertyChangedCallback(null);
    }

    @NonNull
    @Override
    public Lifecycle getLifecycle() {
        return mLifecycle;
    }

    /** DATABINDING METHODS ____________________________________________________________________ **/

    @Override
    public void addOnPropertyChangedCallback(@NonNull OnPropertyChangedCallback callback) {
        mRegistry.add(callback);
    }

    @Override
    public void removeOnPropertyChangedCallback(@Nullable OnPropertyChangedCallback callback) {
        mRegistry.remove(callback);
    }

    protected void notifyChange(int propertyId) {
        mRegistry.notifyChange(this, propertyId);
    }

    protected void notifyChangeAll() {
        mRegistry.notifyChange(this, BR._all);
    }

    /** DEPENDENCY INJECTION METHODS ___________________________________________________________ **/

    @NonNull
    private ActivityComponent getComponent(@NonNull Context context) {
        return DaggerActivityComponent.builder()
                .applicationComponent(AndroidApplication.get(context).getComponent())
                .activityModule(new ActivityModule((AppCompatActivity) context))
                .viewModelModule(new ViewModelModule())
                .build();
    }
}