package com.helloWorldTech.funQuest.ui.base;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.helloWorldTech.funQuest.Constants;
import com.helloWorldTech.funQuest.R;
import com.helloWorldTech.funQuest.data.local.shardPref.PreferencesHelper;
import com.helloWorldTech.funQuest.util.Connectivity;
import com.helloWorldTech.funQuest.util.NavigationController;
import com.helloWorldTech.funQuest.util.Utils;

import javax.inject.Inject;

import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.HasAndroidInjector;

public abstract class BaseActivity extends AppCompatActivity implements HasAndroidInjector {

    //region Variables

    @Inject
    DispatchingAndroidInjector<Object> dispatchingAndroidInjector;

    @Inject
    protected ViewModelProvider.Factory viewModelFactory;

    @Inject
    protected NavigationController navigationController;

    @Inject
    protected Connectivity connectivity;

    private Dialog progressDialog;

    @Inject
    protected PreferencesHelper pref;

    public String language;


    //endregion

    //region Override Methods

    @Override
    public AndroidInjector<Object> androidInjector() {
        return dispatchingAndroidInjector;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (pref.getLang().equalsIgnoreCase(Constants.ARABIC_VALUE)) {
            language = Constants.ARABIC_VALUE;
        } else {
            language = Constants.ENGLISH_VALUE;
        }

        // hide Status Bar
//        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
//                WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }

    protected void observerErrorStatus(){}
    protected void observeLoadStatus(){}

    //region Toolbar Init

    protected Toolbar initToolbar(Toolbar toolbar, String title, int color) {

        if (toolbar != null) {

            toolbar.setTitle(title);

            if (color != 0) {
                try {
                    toolbar.setTitleTextColor(getResources().getColor(color));
                } catch (Exception e) {
                    Utils.errorLog("Can't set color.", e);
                }
            } else {
                try {
                    toolbar.setTitleTextColor(getResources().getColor(R.color.white));
                } catch (Exception e) {
                    Utils.errorLog("Can't set color.", e);
                }
            }

            try {
                setSupportActionBar(toolbar);
            } catch (Exception e) {
                Utils.errorLog("Error in set support action bar.", e);
            }

            try {
                if (getSupportActionBar() != null) {
                    getSupportActionBar().setDisplayHomeAsUpEnabled(true);
                }
            } catch (Exception e) {
                Utils.errorLog("Error in set display home as up enabled.", e);
            }

            /*
               Warning :

               Set Spannable text must call after set Support Action Bar

               The problem is actually that you have a non-String title set on the Toolbar
               at the time you're calling setSupportActionBar. It will end up in ToolbarWidgetWrapper,
               where it will be used when you click the navigation menu. You can use any CharSequence (e.g. Spannable)
               after calling setSuppportActionBar.

               https://stackoverflow.com/questions/27023802/clicking-toolbar-navigation-icon-crashes-the-app/27044868

             */
            if (!title.equals("")) {
                setToolbarText(toolbar, title);
            }

        } else {
            Utils.log("Toolbar is null");
        }

        return toolbar;
    }

    protected Toolbar initToolbar(Toolbar toolbar, String title) {

        if (toolbar != null) {

            toolbar.setTitle(title);

            try {
                toolbar.setTitleTextColor(getResources().getColor(R.color.white));
            } catch (Exception e) {
                Utils.errorLog("Can't set color.", e);
            }

            try {
                setSupportActionBar(toolbar);
            } catch (Exception e) {
                Utils.errorLog("Error in set support action bar.", e);
            }

            try {
                if (getSupportActionBar() != null) {
                    getSupportActionBar().setDisplayHomeAsUpEnabled(true);
                }
            } catch (Exception e) {
                Utils.errorLog("Error in set display home as up enabled.", e);
            }

            /*
               Warning :

               Set Spannable text must call after set Support Action Bar

               The problem is actually that you have a non-String title set on the Toolbar
               at the time you're calling setSupportActionBar. It will end up in ToolbarWidgetWrapper,
               where it will be used when you click the navigation menu. You can use any CharSequence (e.g. Spannable)
               after calling setSuppportActionBar.

               https://stackoverflow.com/questions/27023802/clicking-toolbar-navigation-icon-crashes-the-app/27044868

             */

            if (!title.equals("")) {
                setToolbarText(toolbar, title);
            }

        } else {
            Utils.log("Toolbar is null");
        }
        return toolbar;
    }

    public void setToolbarText(Toolbar toolbar, String text) {
        Utils.log("Set Toolbar Text : " + text);
        toolbar.setTitle(text);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item != null) {
            Utils.log("Clicked " + item.getItemId());
            switch (item.getItemId()) {
                case android.R.id.home:
                    onBackPressed();
                    return true;
            }
        }
        return super.onOptionsItemSelected(item);
    }

    //endregion

    //region Setup Fragment

    protected void setupFragment(Fragment fragment) {
        try {
            this.getSupportFragmentManager().beginTransaction()
                    .replace(R.id.content_frame, fragment)
                    .addToBackStack(null)
                    .commitAllowingStateLoss();
        } catch (Exception e) {
            Utils.errorLog("Error! Can't replace fragment.", e);
        }
    }

    protected void setupFragment(Fragment fragment, int frameId) {
        try {
            this.getSupportFragmentManager().beginTransaction()
                    .replace(frameId, fragment)
                    .commitAllowingStateLoss();
        } catch (Exception e) {
            Utils.errorLog("Error! Can't replace fragment.", e);
        }
    }

    //endregion

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(newBase);
    }

    public void hideKeyboard() {
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager)
                    getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    public void showMessage(String message) {
        if (message != null) {
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, getString(R.string.some_error), Toast.LENGTH_SHORT).show();
        }

    }

    public void showMessage(int resId) {
        showMessage(getString(resId));
    }

}
