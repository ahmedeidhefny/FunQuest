package com.helloWorldTech.funQuest.data.remote.api;

import androidx.lifecycle.LiveData;

import com.helloWorldTech.funQuest.data.remote.modelResponse.AwardsApiResponse;
import com.helloWorldTech.funQuest.data.remote.modelResponse.CreateChallengeApiResponse;
import com.helloWorldTech.funQuest.data.remote.modelResponse.EditProfileApiResponse;
import com.helloWorldTech.funQuest.data.remote.modelResponse.FagApiResponse;
import com.helloWorldTech.funQuest.data.remote.modelResponse.GameDetailsApiResponse;
import com.helloWorldTech.funQuest.data.remote.modelResponse.GamesApiResponse;
import com.helloWorldTech.funQuest.data.remote.modelResponse.HistoryApiResponse;
import com.helloWorldTech.funQuest.data.remote.modelResponse.HomeApiResponse;
import com.helloWorldTech.funQuest.data.remote.modelResponse.MissionsApiResponse;
import com.helloWorldTech.funQuest.data.remote.modelResponse.NotificationsApiResponse;
import com.helloWorldTech.funQuest.data.remote.modelResponse.PlacesApiResponse;
import com.google.gson.JsonElement;
import com.helloWorldTech.funQuest.data.local.entity.AppDataResponse;
import com.helloWorldTech.funQuest.data.local.entity.UserEntity;
import com.helloWorldTech.funQuest.data.remote.modelResponse.ProfileApiResponse;

import io.reactivex.Observable;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;


/**
 * @author Ahmed Eid Hefny
 * @date 26/11/2021
 * <p>
 * ahmedeid2026@gmail.com
 **/
public interface ApiService {

    //region Auth

    //region login
    @FormUrlEncoded
    @POST(ApiUrls.LOGIN)
    LiveData<ApiResponse<UserEntity>> postUserLogin(
            @Query("api_key") String authorization,
            @Query("lang") String lang,
            @Field("mobile") String mobile,
            @Field("password") String userPassword);


    //endregion

    //region register
    @FormUrlEncoded
    @POST(ApiUrls.REGISTER)
    Observable<JsonElement> postUserRegister(@Query("api_key") String authorization,
                                             @Field("mobile") String mobile,
                                             @Field("name") String userName,
                                             @Field("password") String userPassword,
                                             @Field("password_confirmation") String passwordConfirmation,
                                             @Query("age_range") String ageRange,
                                             @Query("lang") String lang);

    @FormUrlEncoded
    @POST(ApiUrls.VERIFY_ACCOUNT)
    Observable<JsonElement> postVerifyAccount(@Query("api_key") String authorization,
                                              @Query("lang") String lang,
                                              @Field("mobile") String mobile,
                                              @Field("code") String code);

    @FormUrlEncoded
    @POST(ApiUrls.RESEND_ACTIVATION_CODE)
    Observable<JsonElement> postResendActivationCode(@Query("api_key") String authorization,
                                                     @Query("lang") String lang,
                                                     @Field("mobile") String mobile);
    //endregion

    //region profile
    @GET(ApiUrls.PROFILE)
    Observable<ProfileApiResponse> getProfile(
            @Header("Authorization") String authorization,
            @Query("api_key") String apiKey,
            @Query("lang") String lang);


    @Multipart
    @POST(ApiUrls.EDIT_PROFILE)
    Observable<EditProfileApiResponse> editProfile(
            @Header("Authorization") String authorization,
            @Query("api_key") String apiKey,
            @Part("name") RequestBody name,
            @Part("email") RequestBody email,
            @Part MultipartBody.Part image,
            @Part("birth_date") RequestBody birth_date,
            @Query("lang") String lang);
    //endregion

    // region forget password
    @FormUrlEncoded
    @POST(ApiUrls.FORGET_PASSWORD)
    Observable<JsonElement> postForgetPassword(@Query("api_key") String authorization,
                                               @Query("lang") String lang,
                                               @Field("mobile") String mobile);

    @FormUrlEncoded
    @POST(ApiUrls.RESET_PASSWORD)
    Observable<JsonElement> postResetPassword(@Query("api_key") String authorization,
                                              @Query("lang") String lang,
                                              @Field("mobile") String mobile,
                                              @Field("code") String code,
                                              @Field("password") String userPassword,
                                              @Field("password_confirmation") String passwordConfirmation);

    @FormUrlEncoded
    @POST(ApiUrls.RESEND_FORGET_CODE)
    Observable<JsonElement> postResendForgetCode(@Query("api_key") String authorization,
                                                 @Query("lang") String lang,
                                                 @Field("mobile") String mobile);
    //endregion

    //endregion

    //region Home

    @GET(ApiUrls.DATA)
    LiveData<ApiResponse<AppDataResponse>> getAppData(@Query("api_key") String token,
                                                      @Query("lang") String lang);

    @GET(ApiUrls.HOME)
    Observable<HomeApiResponse> getHomeData(@Query("api_key") String apiKey,
                                            @Header("Authorization") String token,
                                            @Query("lang") String lang);


    @GET(ApiUrls.NOTIFICATIONS)
    Observable<NotificationsApiResponse> getNotifications(@Header("Authorization") String token,
                                                          @Query("api_key") String apiKey,
                                                          @Query("lang") String lang,
                                                          @Query("page") int page);

    @GET(ApiUrls.AWARD)
    Observable<AwardsApiResponse> getAwards(@Header("Authorization") String token,
                                            @Query("api_key") String apiKey,
                                            @Query("lang") String lang,
                                            @Query("page") int page);

    @GET(ApiUrls.FAG)
    Observable<FagApiResponse> getFAG(@Header("Authorization") String token,
                                      @Query("api_key") String apiKey,
                                      @Query("lang") String lang);


    @GET(ApiUrls.PLACES)
    Observable<PlacesApiResponse> getPlaces(@Header("Authorization") String token,
                                            @Query("api_key") String apiKey,
                                            @Query("city_id") int cityId,
                                            @Query("lang") String lang);

    //endregion

    //region Game

    @GET(ApiUrls.GAME_LIST)
    Observable<GamesApiResponse> getGameList(@Header("Authorization") String token,
                                             @Query("api_key") String apiKey,
                                             @Query("place_id") int placeId,
                                             @Query("lang") String lang,
                                             @Query("page") int page);

    @GET(ApiUrls.HISTORY)
    Observable<HistoryApiResponse> getHistory(@Header("Authorization") String token,
                                              @Query("api_key") String apiKey,
                                              @Query("lang") String lang,
                                              @Query("page") int page);


    @GET(ApiUrls.GAME_DETAILS)
    Observable<GameDetailsApiResponse> getGameDetails(@Header("Authorization") String token,
                                                      @Query("api_key") String apiKey,
                                                      @Query("game_id") int gameId,
                                                      @Query("lang") String lang);

    @GET(ApiUrls.MISSIONS)
    Observable<MissionsApiResponse> getMission(@Header("Authorization") String token,
                                               @Query("api_key") String apiKey,
                                               @Query("game_id") int gameId,
                                               @Query("lang") String lang);

    @POST(ApiUrls.CREATE_CHALLENGE)
    @FormUrlEncoded
    Observable<CreateChallengeApiResponse> createChallenge(@Header("Authorization") String token,
                                                           @Query("api_key") String apiKey,
                                                           @Field("city_id") int cityId,
                                                           @Field("place_id") int placeId,
                                                           @Field("team_count") int teamCount,
                                                           @Field("date_time") String dateTime,
                                                           @Field("email") String email,
                                                           @Field("lang") String lang);

//    @GET(ApiUrls.MISSIONS)
//    Observable<MissionsApiResponse> getMission(@Header("Authorization") String token,
//                                               @Query("api_key") String apiKey,
//                                               @Field("game_id") int gameId,
//                                               @Query("lang") String lang);


    //endregion


}
