package com.helloWorldTech.funQuest.di.component;

import android.app.Application;

import com.helloWorldTech.funQuest.App;
import com.helloWorldTech.funQuest.di.module.ActivityModule;
import com.helloWorldTech.funQuest.di.module.ApiModule;
import com.helloWorldTech.funQuest.di.module.AppModule;
import com.helloWorldTech.funQuest.di.module.DbModule;
import com.helloWorldTech.funQuest.di.module.ViewModelModule;

import javax.inject.Singleton;

import dagger.BindsInstance;
import dagger.Component;
import dagger.android.support.AndroidSupportInjectionModule;


/**
 * Any class with the annotation @Component defines the connection between the
 * modules and the classes which need the dependency.
 * We define a @Component.Builder interface which will be called from
 * our custom Application class. This will set our application object to
 * the AppComponent. So inside the AppComponent the application instance is available.
 * So this application instance can be accessed by our modules such as ApiModule when
 * needed.
 *
 * We mark this interface with the @Component annotation.
 * And we define all the modules that can be injected.
 * Note that we provide AndroidSupportInjectionModule.class
 * here. This class was not created by us.
 * It is an internal class in Dagger 2.10.
 * Provides our activities and fragments with given module.
 * */
@Component(modules = {
                ApiModule.class,
                DbModule.class,
                AppModule.class,
                ViewModelModule.class,
                ActivityModule.class,
                AndroidSupportInjectionModule.class})
@Singleton
public interface AppComponent {


    /* We will call this builder interface from our custom Application class.
     * This will set our application object to the AppComponent.
     * So inside the AppComponent the application instance is available.
     * So this application instance can be accessed by our modules
     * such as ApiModule when needed
     * */
    @Component.Builder
    interface Builder {

        @BindsInstance
        Builder application(Application application);

        @BindsInstance
        Builder apiModule(ApiModule apiModule);

        @BindsInstance
        Builder dbModule(DbModule dbModule);

        AppComponent build();
    }


    /*
     * This is our custom Application class
     * */
    void inject(App app);
}
