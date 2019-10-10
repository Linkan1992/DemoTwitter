package com.demotwitter.linkan.demotwitter.base;

import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.AnimRes;
import android.support.annotation.AnimatorRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.Toast;


import com.demotwitter.linkan.demotwitter.utils.NetworkUtils;
import com.twitter.sdk.android.core.TwitterApiException;
import com.twitter.sdk.android.core.TwitterException;

import java.net.UnknownHostException;
import java.util.List;

import javax.inject.Inject;

import dagger.android.AndroidInjection;
import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.support.HasSupportFragmentInjector;

public abstract class BaseActivity<T extends ViewDataBinding, V extends BaseViewModel>
        extends AppCompatActivity implements BaseNavigator, HasSupportFragmentInjector,
        BaseFragment.Callback {

    public final String TAG = this.getClass().getName();
    private T mViewDataBinding;
    private V mViewModel;


    @Inject
    DispatchingAndroidInjector<Fragment> mFragmentInjector;

    @Inject
    Context context;

    @Override
    public void onFragmentAttached() {

    }

    @Override
    public void onFragmentDetached(String TAG, @AnimatorRes @AnimRes int EnterAnimation, @AnimatorRes @AnimRes int ExitAnimation) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        Fragment fragment = fragmentManager.findFragmentByTag(TAG);
        if (fragment != null)
            fragmentManager
                    .beginTransaction()
                    .disallowAddToBackStack()
                    .setCustomAnimations(EnterAnimation, ExitAnimation)
                    .remove(fragment)
                    .commitNow();
    }

    protected abstract String getActivityName();

    /**
     * @return layout resource id
     */
    public abstract @LayoutRes
    int getLayoutId();

    protected abstract android.support.v7.widget.Toolbar getSupportedToolbar();

    /**
     * Override for set binding variable
     *
     * @return variable id
     */
    public abstract int getBindingVariable();

    public abstract void initOnCreate(@Nullable Bundle savedInstanceState);

    protected abstract void permissionGranted(int requestPhoneStateCode);


    @Override
    public AndroidInjector<Fragment> supportFragmentInjector() {
        return mFragmentInjector;
    }


    @TargetApi(Build.VERSION_CODES.M)
    public void requestPermissionsSafely(String[] permissions, int requestCode) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(permissions, requestCode);
        }
    }


    @TargetApi(Build.VERSION_CODES.M)
    public boolean hasPermission(String permission) {
        return (ActivityCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED);
    }


    public T getViewDataBinding() {
        return mViewDataBinding;
    }

    /**
     * Override for set view model
     *
     * @return view model instance
     */
    public abstract V getViewModel();

    public void performDependencyInjection() {
        AndroidInjection.inject(this);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        performDependencyInjection();
       /* if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }*/

        super.onCreate(savedInstanceState);
        performDataBinding();

        setSupportActionBar(getSupportedToolbar());
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
        initOnCreate(savedInstanceState);
        setTitle(getActivityName());
    }

    private void performDataBinding() {
        mViewDataBinding = DataBindingUtil.setContentView(this, getLayoutId());
        this.mViewModel = mViewModel == null ? getViewModel() : mViewModel;
        mViewDataBinding.setVariable(getBindingVariable(), mViewModel);
        mViewDataBinding.executePendingBindings();
    }


    public void showAlertDialog(String title, String message, String positiveText, DialogInterface.OnClickListener pClickListener, String negative, DialogInterface.OnClickListener nClickListener) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(title);
        builder.setMessage(message);
        if (pClickListener != null)
            builder.setPositiveButton(positiveText, pClickListener);

        if (nClickListener != null) {
            builder.setNegativeButton(negative, nClickListener);
        }
        builder.create().show();

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case android.R.id.home:
                return true;
        }

        return super.onOptionsItemSelected(item);
    }


    public boolean isNetworkConnected() {
        return NetworkUtils.isNetworkConnected(context);
    }

    @Override
    public void handleError(Throwable throwable) {
        if (throwable instanceof UnknownHostException
                || (throwable instanceof TwitterException
                && throwable.getCause() instanceof UnknownHostException)
                )
            getViewModel().getNavigator().showToast("No Internet Connection");
        else if (throwable instanceof TwitterException && ((TwitterApiException) throwable).getStatusCode() == 429)
            getViewModel().getNavigator().showToast("You exhausted your rate limit");
        else
            getViewModel().getNavigator().showToast(throwable.getMessage());

    }

    @Override
    public void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }


}
