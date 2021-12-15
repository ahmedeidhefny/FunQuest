package com.helloWorldTech.funQuest.di.module;


import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.helloWorldTech.funQuest.factory.ViewModelFactory;
import com.helloWorldTech.funQuest.viewModel.GameViewModel;
import com.helloWorldTech.funQuest.viewModel.HomeViewModel;
import com.helloWorldTech.funQuest.viewModel.UserViewModel;

import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;


/**
 * @author mac
 *
 * The ViewModelModule is used to provide a map of view models through dagger that
 * is used by the ViewModelFactory class.
 *
 *  so basically
 *  We can use the ViewModelModule to define our ViewModels.
 *  We provide a key for each ViewModel using the ViewModelKey class.
 *  Then in our Activity/Fragment, we use the ViewModelFactory class to
 *  inject the corresponding ViewModel. (We will look into more detail at
 *  this when we are creating our Activity).
 */
@Module
public abstract class ViewModelModule {

    @Binds
    abstract ViewModelProvider.Factory bindViewModelFactory(ViewModelFactory factory);


    /*
     * This method basically says
     * inject this object into a Map using the @IntoMap annotation,
     * with the  MovieListViewModel.class as key,
     * and a Provider that will build a MovieListViewModel
     * object.
     *
     * */

    @Binds
    @IntoMap
    @ViewModelKey(UserViewModel.class)
    protected abstract ViewModel userViewModel(UserViewModel userViewModel);

    @Binds
    @IntoMap
    @ViewModelKey(HomeViewModel.class)
    protected abstract ViewModel homeViewModel(HomeViewModel homeViewModel);

    @Binds
    @IntoMap
    @ViewModelKey(GameViewModel.class)
    protected abstract ViewModel gameViewModel(GameViewModel gameViewModel);




}