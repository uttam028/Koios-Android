package org.mlab.research.koios;

import org.mlab.research.koios.ui.survey.SurveyResponse;

import java.util.ArrayList;
import java.util.List;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface CimonService {

    @GET("signup/register")
    Call<CimonResponse> signup(@Query("email") String email, @Query("uuid") String uuid);

    @GET("signup/verify")
    Call<CimonResponse> verifyToken(@Query("email") String email, @Query("uuid") String uuid, @Query("token") String token);


    //https://koiosplatform.com/cimoninterface/study/list/open/public?email=test@nd.edu
    @GET("study/list/open/public")
    Call<ArrayList<KoiosStudy>> getOpenStudies(@Query("email") String email);

    @GET("study/enroll")
    Call<CimonResponse> enrollToStudy(@Query("id") String studyId, @Query("email") String email, @Query("uuid") String uuid,
                                      @Query("jointime") String joinTime, @Query("jointimezone") String joinTimeZoSne);


    //https://koiosplatform.com/mcsweb/cimoninterface/study/list/enrolled/active?email=uttam028@gmail.com&uuid=732819hjkd
    @GET("study/list/enrolled/active")
    Call<ArrayList<KoiosStudy>> getEnrolledStudies(@Query("email") String email, @Query("uuid") String uuid);

    //https://koiosplatform.com/cimoninterface/study/32/sensorconfigs/published
    @GET("study/{studyId}/sensorconfigs/published")
    Call<ArrayList<StudySensorConfig>> getSensorConfigs(@Path(value = "studyId", encoded = true) int studyId);

    //https://koiosplatform.com/cimoninterface/study/{studyId}/sensorconfig/{configId}/actionlist
    @GET("study/{studyId}/sensorconfig/{configId}/actionlist")
    Call<ArrayList<StudySensorAction>> getSensorActions(@Path(value = "studyId", encoded = true) int studyId, @Path(value = "configId", encoded = true) int configId);


    //https://koiosplatform.com/cimoninterface/study/37/surveys/published
    @GET("study/{studyId}/surveys/published")
    Call<ArrayList<StudySurveyConfig>> getSurveyConfigs(@Path(value = "studyId", encoded = true) int studyId);


    //https://koiosplatform.com/cimoninterface/study/{studyId}/survey/{surveyId}/tasklist
    @GET("study/{studyId}/survey/{surveyId}/tasklist")
    Call<ArrayList<StudySurveyTask>> getSurveyTasks(@Path(value = "studyId", encoded = true) int studyId, @Path(value = "surveyId", encoded = true) int surveyId);


    //ping
    //var serviceUrl = Utils.getBaseUrl() + "device/ping?email=\(email)&uuid=\(Utils.getDeviceIdentifier())&network=\(network)&os_type=ios&os_version=\(osVersion)&app_version=\(appVersion)&data=\(data)"
    //@Headers("secret:koiosByAfzalFrommLabND@sralabDoD")
    @Headers("secret:koiosByAfzalFromNDmL@b")
    @GET("device/ping")
    Call<CimonResponse> pingToPlatform(@Query("email") String email, @Query("uuid") String uuid, @Query("network") String network, @Query("os_type") String osType,
                                       @Query("os_version") String osVersion, @Query("app_version") String appVersion, @Query("data") String data);


    //fcm token
    //@Headers("secret:koiosByAfzalFrommLabND@sralabDoD")
    @Headers("secret:koiosByAfzalFromNDmL@b")
    @GET("device/fcm")
    Call<CimonResponse> uploadToken(@Query("email") String email, @Query("uuid") String uuid, @Query("fcm_token") String fcmToken);


    @Multipart
    @POST("files/upload")
    Call<CimonResponse> uploadFile(@Part MultipartBody.Part file/*, @Part("file") RequestBody name*/);


    @Headers("secret:koiosByAfzalFromNDmL@b")
    @GET("study/labeling/history")
    Call<CimonResponse> uploadLabelData(@Query("id") int id, @Query("email") String email, @Query("uuid") String uuid, @Query("label_time") String labelTime,
                                        @Query("label") String label, @Query("label_type") String labelType);

    @Headers("secret:koiosByAfzalFromNDmL@b")
    @GET("study/object/history")
    Call<CimonResponse> uploadFileTransferStatus(@Query("id") int id, @Query("email") String email, @Query("uuid") String uuid, @Query("object_name") String objectName,
                                                 @Query("status_type") String statusType, @Query("status_time") String statusTime);


    @Headers("secret:koiosByAfzalFromNDmL@b")
    @GET("study/object/status")
    Call<CimonResponse> getFileTransferStatus(@Query("id") int id, @Query("object_name") String objectName);


//    @Headers("secret:koiosByAfzalFromNDmL@b")
//    @GET("temp/s3/credential")
//    Call<S3Credential> getS3Info();


    ///------------------------------------Survey--------------------------///
    @POST("study/survey/response")
    Call<CimonResponse> uplaodSurveyResponse(@Query("email") String email, @Query("uuid") String uuid, @Body List<SurveyResponse> responses);

}
