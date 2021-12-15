package com.helloWorldTech.funQuest.util;


import android.app.Activity;
import android.content.Intent;
import android.net.Uri;

import com.helloWorldTech.funQuest.Constants;
import com.helloWorldTech.funQuest.data.remote.modelResponse.FagApiResponse;
import com.helloWorldTech.funQuest.ui.challenge.AddChallengeActivity;
import com.helloWorldTech.funQuest.ui.challenge.GameDetailsActivity;
import com.helloWorldTech.funQuest.ui.challenge.GameListActivity;
import com.helloWorldTech.funQuest.ui.chat.ChatActivity;
import com.helloWorldTech.funQuest.ui.cities.CitiesActivity;
import com.helloWorldTech.funQuest.ui.fag.FagDetailsActivity;
import com.helloWorldTech.funQuest.ui.fag.HelpQuestActivity;
import com.helloWorldTech.funQuest.ui.main.MainActivity;
import com.helloWorldTech.funQuest.ui.auth.forgetPassword.ForgetPasswordActivity;
import com.helloWorldTech.funQuest.ui.auth.forgetPassword.ResetPasswordActivity;
import com.helloWorldTech.funQuest.ui.auth.login.LoginActivity;
import com.helloWorldTech.funQuest.ui.auth.register.OtpActivity;
import com.helloWorldTech.funQuest.ui.auth.register.RegisterActivity;
import com.helloWorldTech.funQuest.ui.places.PlacesActivity;
import com.helloWorldTech.funQuest.ui.splashes.AfterSplashActivity;
import com.helloWorldTech.funQuest.ui.splashes.SplashActivity;

import javax.inject.Inject;

/**
 * Created by Panacea-Soft on 11/17/17.
 * Contact Email : teamps.is.cool@gmail.com
 */

public class NavigationController {

    //region Variables

    //private final int containerId;
    public Uri photoURI;

    //endregion


    //region Constructor
    @Inject
    public NavigationController() {

        // This setup is for MainActivity
        //this.containerId = R.id.content_frame;
    }

    //endregion

    public void navigateToSplashActivity(Activity activity) {
        Intent intent = new Intent(activity, SplashActivity.class);
        activity.startActivity(intent);
    }

    public void navigateToAfterSplashActivity(Activity activity) {
        Intent intent = new Intent(activity, AfterSplashActivity.class);
        activity.startActivity(intent);
    }

    public void navigateToMainActivity(Activity activity) {
        Intent intent = new Intent(activity, MainActivity.class);
        activity.startActivity(intent);
    }

    public void navigateToLoginActivity(Activity activity) {
        Intent intent = new Intent(activity, LoginActivity.class);
        activity.startActivity(intent);
    }

    public void navigateToRegisterActivity(Activity activity) {
        Intent intent = new Intent(activity, RegisterActivity.class);
        activity.startActivity(intent);
    }
    public void navigateToForgetPasswordActivity(Activity activity) {
        Intent intent = new Intent(activity, ForgetPasswordActivity.class);
        activity.startActivity(intent);
    }

    public void navigateToResetPasswordActivity(Activity activity, String mobile) {
        Intent intent = new Intent(activity, ResetPasswordActivity.class);
        intent.putExtra(Constants.USER_MOBILE, mobile);
        activity.startActivity(intent);
    }
    public void navigateToOtpActivity(Activity activity, String mobile) {
        Intent intent = new Intent(activity, OtpActivity.class);
        intent.putExtra(Constants.USER_MOBILE, mobile);
        activity.startActivity(intent);
    }

    public void navigateToCitiesActivity(Activity activity) {
        Intent intent = new Intent(activity, CitiesActivity.class);
        activity.startActivity(intent);
    }

    public void navigateToPlacesActivity(Activity activity, int cityId) {
        Intent intent = new Intent(activity, PlacesActivity.class);
        intent.putExtra(Constants.IN_CITY_ID, cityId);
        activity.startActivity(intent);
    }

    public void navigateToChallengesListActivity(Activity activity, int placeId) {
        Intent intent = new Intent(activity, GameListActivity.class);
        intent.putExtra(Constants.IN_PLACE_ID, placeId);
        activity.startActivity(intent);
    }

    public void navigateToGameDetailsActivity(Activity activity, int gameId) {
        Intent intent = new Intent(activity, GameDetailsActivity.class);
        intent.putExtra(Constants.IN_GAME_ID, gameId);
        activity.startActivity(intent);
    }

    public void navigateToFAGActivity(Activity activity) {
        Intent intent = new Intent(activity, HelpQuestActivity.class);
        activity.startActivity(intent);
    }

    public void navigateToFAGActivity(Activity activity, FagApiResponse.Faq faq) {
        Intent intent = new Intent(activity, FagDetailsActivity.class);
        intent.putExtra(Constants.IN_FAG_OBJ, faq);
        activity.startActivity(intent);
    }

    public void navigateToAddChallengeActivity(Activity activity) {
        Intent intent = new Intent(activity, AddChallengeActivity.class);
        activity.startActivity(intent);
    }

    public void navigateToChatActivity(Activity activity) {
        Intent intent = new Intent(activity, ChatActivity.class);
        activity.startActivity(intent);
    }



}
