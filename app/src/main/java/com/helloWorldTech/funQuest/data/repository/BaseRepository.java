package com.helloWorldTech.funQuest.data.repository;


import com.helloWorldTech.funQuest.AppExecutors;
import com.helloWorldTech.funQuest.data.AppDatabase;
import com.helloWorldTech.funQuest.data.remote.api.ApiService;
import com.helloWorldTech.funQuest.util.Connectivity;
import com.helloWorldTech.funQuest.util.Utils;

import javax.inject.Inject;

/**
 * Parent Class of All Repository Class in this project
 /**
 * @author Ahmed Eid Hefny
 * @date 26/11/2021
 * <p>
 * ahmedeid2026@gmail.com
 **/

public abstract class BaseRepository {


    //region Variables

    protected final ApiService apiService;
    protected final AppExecutors appExecutors;
    protected final AppDatabase db;

    @Inject
    protected Connectivity connectivity;

    //endregion

    //region Constructor

    /**
     * Constructor of PSRepository
     * @param apiService  API Service Instance
     * @param appExecutors Executors Instance
     * @param db Panacea-Soft DB
     */
    protected BaseRepository(ApiService apiService, AppExecutors appExecutors, AppDatabase db) {
        Utils.log("Inside Base repository");

        this.apiService = apiService;
        this.appExecutors = appExecutors;
        this.db = db;
    }

    //endregion


}
