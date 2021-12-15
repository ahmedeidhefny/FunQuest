package com.helloWorldTech.funQuest.di.module;

import com.helloWorldTech.funQuest.ui.auth.forgetPassword.ForgetPasswordActivity;
import com.helloWorldTech.funQuest.ui.auth.forgetPassword.ResetPasswordActivity;
import com.helloWorldTech.funQuest.ui.auth.login.LoginActivity;
import com.helloWorldTech.funQuest.ui.auth.register.OtpActivity;
import com.helloWorldTech.funQuest.ui.auth.register.RegisterActivity;
import com.helloWorldTech.funQuest.ui.award.AwardFragment;
import com.helloWorldTech.funQuest.ui.challenge.AddChallengeActivity;
import com.helloWorldTech.funQuest.ui.chat.ChatActivity;
import com.helloWorldTech.funQuest.ui.fag.FagDetailsActivity;
import com.helloWorldTech.funQuest.ui.fag.HelpQuestActivity;
import com.helloWorldTech.funQuest.ui.history.HistoryFragment;
import com.helloWorldTech.funQuest.ui.challenge.CreateChallengeFragment;
import com.helloWorldTech.funQuest.ui.challenge.GameDetailsActivity;
import com.helloWorldTech.funQuest.ui.challenge.JoinChallengeFragment;
import com.helloWorldTech.funQuest.ui.challenge.GameListActivity;
import com.helloWorldTech.funQuest.ui.cities.CitiesActivity;
import com.helloWorldTech.funQuest.ui.home.HomeFragment;
import com.helloWorldTech.funQuest.ui.main.MainActivity;
import com.helloWorldTech.funQuest.ui.more.MoreFragment;
import com.helloWorldTech.funQuest.ui.notifications.NotificationsFragment;
import com.helloWorldTech.funQuest.ui.places.PlacesActivity;
import com.helloWorldTech.funQuest.ui.splashes.AfterSplashActivity;
import com.helloWorldTech.funQuest.ui.splashes.SplashActivity;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

/**
 * Since we are using the dagger-android support library,
 * we can make use of Android Injection.
 * The ActivityModule generates AndroidInjector(this is the new dagger-android class which exist in dagger-android framework)
 * for Activities defined in this class.
 * This allows us to inject things into Activities using AndroidInjection.
 * inject(this) in the onCreate() method.
 */
@Module
public abstract class ActivityModule {

    @ContributesAndroidInjector()
    abstract MainActivity contributeMainActivity();

    @ContributesAndroidInjector()
    abstract SplashActivity contributeSplashActivity();

    @ContributesAndroidInjector()
    abstract AfterSplashActivity contributeAfterSplashActivity();

    @ContributesAndroidInjector()
    abstract LoginActivity contributeLoginActivity();

    @ContributesAndroidInjector()
    abstract RegisterActivity contributeRegisterActivity();

    @ContributesAndroidInjector()
    abstract OtpActivity contributeOtpActivity();

    @ContributesAndroidInjector()
    abstract ResetPasswordActivity contributeResetPasswordActivity();

    @ContributesAndroidInjector()
    abstract ForgetPasswordActivity contributeForgetPasswordActivity();

    @ContributesAndroidInjector()
    abstract HomeFragment contributeHomeFragment();

    @ContributesAndroidInjector()
    abstract AwardFragment contributeAwardFragment();

    @ContributesAndroidInjector()
    abstract MoreFragment contributeMoreFragment();

    @ContributesAndroidInjector()
    abstract NotificationsFragment contributeNotificationsFragment();

    @ContributesAndroidInjector()
    abstract HistoryFragment contributeChallengesFragment();

    @ContributesAndroidInjector()
    abstract CreateChallengeFragment contributeCreateChallengeFragment();

    @ContributesAndroidInjector()
    abstract JoinChallengeFragment contributeJoinChallengeFragment();

    @ContributesAndroidInjector()
    abstract GameListActivity contributeChallengesListActivity();

    @ContributesAndroidInjector()
    abstract CitiesActivity contributeCitiesActivity();

    @ContributesAndroidInjector()
    abstract PlacesActivity contributePlacesActivity();

    @ContributesAndroidInjector()
    abstract GameDetailsActivity contributeGameDetailsActivity();

    @ContributesAndroidInjector()
    abstract HelpQuestActivity contributeHelpQuestActivity();

    @ContributesAndroidInjector()
    abstract AddChallengeActivity contributeAddChallengeActivity();

    @ContributesAndroidInjector()
    abstract ChatActivity contributeChatActivity();

    @ContributesAndroidInjector()
    abstract FagDetailsActivity contributeFagDetailsActivity();


}