package com.mindfiresolutions.resourcemanager.utility;


import com.mindfiresolutions.resourcemanager.admin.SharedResourceSummaryActivity;
import com.mindfiresolutions.resourcemanager.model.AddHardwareSetter;
import com.mindfiresolutions.resourcemanager.model.AddSoftwareSetter;
import com.mindfiresolutions.resourcemanager.model.AdminHomeSummaryCountResponse;
import com.mindfiresolutions.resourcemanager.model.AdminListResponse;
import com.mindfiresolutions.resourcemanager.model.AllResourceRequestSummary;
import com.mindfiresolutions.resourcemanager.model.AllResourceSummary;
import com.mindfiresolutions.resourcemanager.model.AssignHardwareSetter;
import com.mindfiresolutions.resourcemanager.model.AssignSoftwareSetter;
import com.mindfiresolutions.resourcemanager.model.AvailableHardwareByTypeIdResponse;
import com.mindfiresolutions.resourcemanager.model.ChangePasswordSetter;
import com.mindfiresolutions.resourcemanager.model.ContactSetter;
import com.mindfiresolutions.resourcemanager.model.DeleteUserRequestSetter;
import com.mindfiresolutions.resourcemanager.model.ForgotPasswordSetter;
import com.mindfiresolutions.resourcemanager.model.HardwareBrandResponse;
import com.mindfiresolutions.resourcemanager.model.HardwareByIdResponse;
import com.mindfiresolutions.resourcemanager.model.HardwareByTypeResponse;
import com.mindfiresolutions.resourcemanager.model.HardwareCountResponse;
import com.mindfiresolutions.resourcemanager.model.HardwareIdSetter;
import com.mindfiresolutions.resourcemanager.model.HardwareImageListResponse;
import com.mindfiresolutions.resourcemanager.model.HardwareListForUserResponse;
import com.mindfiresolutions.resourcemanager.model.HardwareTypeResponse;
import com.mindfiresolutions.resourcemanager.model.IdSetter;
import com.mindfiresolutions.resourcemanager.model.KeyDetailsResponse;
import com.mindfiresolutions.resourcemanager.model.LicenseIdSetter;
import com.mindfiresolutions.resourcemanager.model.LoginSetter;
import com.mindfiresolutions.resourcemanager.model.NewBrandSetter;
import com.mindfiresolutions.resourcemanager.model.NewLicenseSetter;
import com.mindfiresolutions.resourcemanager.model.NewUserRequestSetter;
import com.mindfiresolutions.resourcemanager.model.AssignPendingHardwareSetter;
import com.mindfiresolutions.resourcemanager.model.ResponseGetterBase;
import com.mindfiresolutions.resourcemanager.model.ResponseGetterWithToken;
import com.mindfiresolutions.resourcemanager.model.SharedResourceSummaryWithResponse;
import com.mindfiresolutions.resourcemanager.model.SignUpSetter;
import com.mindfiresolutions.resourcemanager.model.SoftwareBrandIdSetter;
import com.mindfiresolutions.resourcemanager.model.SoftwareBrandListResponse;
import com.mindfiresolutions.resourcemanager.model.SoftwareDetailsResponse;
import com.mindfiresolutions.resourcemanager.model.SoftwareDetailsSetter;
import com.mindfiresolutions.resourcemanager.model.SoftwareIdSetter;
import com.mindfiresolutions.resourcemanager.model.SoftwareLicenseListResponse;
import com.mindfiresolutions.resourcemanager.model.SoftwareSummaryWithResponse;
import com.mindfiresolutions.resourcemanager.model.TypeSetter;
import com.mindfiresolutions.resourcemanager.model.UpdateSoftwareSetter;
import com.mindfiresolutions.resourcemanager.model.UserIdSetter;
import com.mindfiresolutions.resourcemanager.model.UserMyRequestsResponse;
import com.mindfiresolutions.resourcemanager.model.UserMyRessourceResponse;
import com.mindfiresolutions.resourcemanager.model.UserNameSetter;
import com.mindfiresolutions.resourcemanager.model.UserResponse;
import com.mindfiresolutions.resourcemanager.model.ViewAllUsersResponse;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;

import static com.mindfiresolutions.resourcemanager.utility.APIurls.ADD_NEW_HARDWARE_REQUEST_URL;
import static com.mindfiresolutions.resourcemanager.utility.APIurls.ADMIN_HOME_PAGE_SUMMARY;
import static com.mindfiresolutions.resourcemanager.utility.APIurls.ASSIGN_HARDWARE;
import static com.mindfiresolutions.resourcemanager.utility.APIurls.ASSIGN_REQUESTED_HARDWARE;
import static com.mindfiresolutions.resourcemanager.utility.APIurls.AVAILABLE_HARDWARE_DETAILS_BY_TYPE_ID;
import static com.mindfiresolutions.resourcemanager.utility.APIurls.CHANGE_PASSWORD_DETAIL_URL;
import static com.mindfiresolutions.resourcemanager.utility.APIurls.CREATE_NEW_SOFTWARE;
import static com.mindfiresolutions.resourcemanager.utility.APIurls.FORGOT_PASSWORD_REQUEST_URL;
import static com.mindfiresolutions.resourcemanager.utility.APIurls.GET_ALL_RESOURCE_SUMMARY_URL;
import static com.mindfiresolutions.resourcemanager.utility.APIurls.GET_ALL_USERS;
import static com.mindfiresolutions.resourcemanager.utility.APIurls.HARDWARE_COUNT_SUMMARY_URL;
import static com.mindfiresolutions.resourcemanager.utility.APIurls.HARDWARE_DETAILS_BY_ID;
import static com.mindfiresolutions.resourcemanager.utility.APIurls.HARDWARE_DETAIL_BY_TYPE;
import static com.mindfiresolutions.resourcemanager.utility.APIurls.HARDWARE_LIST_BY_USER;
import static com.mindfiresolutions.resourcemanager.utility.APIurls.LOGIN_REQUEST_DETAIL_URL;
import static com.mindfiresolutions.resourcemanager.utility.APIurls.LOGIN_REQUEST_HEADER_KEY;
import static com.mindfiresolutions.resourcemanager.utility.APIurls.MASTER_DELETE_HARDWARE;
import static com.mindfiresolutions.resourcemanager.utility.APIurls.MASTER_DELETE_SOFTWARE;
import static com.mindfiresolutions.resourcemanager.utility.APIurls.MASTER_HARDWARE_BRAND_DETAIL_URL;
import static com.mindfiresolutions.resourcemanager.utility.APIurls.MASTER_HARDWARE_TYPE_DETAIL_URL;
import static com.mindfiresolutions.resourcemanager.utility.APIurls.MASTER_SOFTWARE_BRAND_DETAIL_URL;
import static com.mindfiresolutions.resourcemanager.utility.APIurls.MASTER_SOFTWARE_CREATE_NEW_BRAND;
import static com.mindfiresolutions.resourcemanager.utility.APIurls.MASTER_SOFTWARE_CREATE_NEW_LICENSE_TYPE;
import static com.mindfiresolutions.resourcemanager.utility.APIurls.MASTER_SOFTWARE_LICENCE_TYPE_URL;
import static com.mindfiresolutions.resourcemanager.utility.APIurls.NEW_USER_REQUEST;
import static com.mindfiresolutions.resourcemanager.utility.APIurls.SHOW_HARDWARE_IMAGE;
import static com.mindfiresolutions.resourcemanager.utility.APIurls.SIGNUP_REQUEST_DETAIL_URL;
import static com.mindfiresolutions.resourcemanager.utility.APIurls.SOFTWARE_DETAILS_BY_ID_URL;
import static com.mindfiresolutions.resourcemanager.utility.APIurls.SOFTWARE_DETAIL_URL;
import static com.mindfiresolutions.resourcemanager.utility.APIurls.SOFTWARE_SUMMARY_COUNT;
import static com.mindfiresolutions.resourcemanager.utility.APIurls.TOKEN_HEADER_KEY;
import static com.mindfiresolutions.resourcemanager.utility.APIurls.UPDATE_PROFILE_REQUEST_URL;
import static com.mindfiresolutions.resourcemanager.utility.APIurls.UPDATE_SOFTWARE;
import static com.mindfiresolutions.resourcemanager.utility.APIurls.UPDATE_SOFTWARE_REQUEST;
import static com.mindfiresolutions.resourcemanager.utility.APIurls.USERNAME;
import static com.mindfiresolutions.resourcemanager.utility.APIurls.USER_DELETE_MY_REQUEST;
import static com.mindfiresolutions.resourcemanager.utility.APIurls.USER_IMAGE_UPLOAD_MULTIPART;
import static com.mindfiresolutions.resourcemanager.utility.APIurls.USER_MY_REQUESTS;
import static com.mindfiresolutions.resourcemanager.utility.APIurls.USER_MY_RESOURCE;
import static com.mindfiresolutions.resourcemanager.utility.APIurls.VIEW_ALL_ADMINS;
import static com.mindfiresolutions.resourcemanager.utility.APIurls.VIEW_ALL_PENDING_REQUESTS;
import static com.mindfiresolutions.resourcemanager.utility.APIurls.VIEW_PROFILE_REQUEST_URL;
import static com.mindfiresolutions.resourcemanager.utility.APIurls.VIEW_SOFTWARE_KEY;


/**
 * This interface has declaration of all the methods which are being used for api calls
 * Created by Shivangi Singh on 3/28/2017
 * modified on 7th April
 */

public interface CallAPIInterface {

    @POST(LOGIN_REQUEST_DETAIL_URL)
    Call<ResponseGetterWithToken> getResponse(@Header(LOGIN_REQUEST_HEADER_KEY) String header,
                                              @Body LoginSetter body);

    @POST(FORGOT_PASSWORD_REQUEST_URL)
    Call<ResponseGetterBase> forgotPasswordRequest(@Header(LOGIN_REQUEST_HEADER_KEY) String header,
                                                   @Body ForgotPasswordSetter body);

    @POST(SIGNUP_REQUEST_DETAIL_URL)
    Call<ResponseGetterBase> signupResponse(@Header(LOGIN_REQUEST_HEADER_KEY) String header,
                                            @Body SignUpSetter body);

    @PUT(CHANGE_PASSWORD_DETAIL_URL)
    Call<ResponseGetterBase> getResponse(@Header(LOGIN_REQUEST_HEADER_KEY) String header,
                                         @Header(TOKEN_HEADER_KEY) String token,
                                         @Body ChangePasswordSetter body);

    @POST(VIEW_PROFILE_REQUEST_URL)
    Call<UserResponse> getProfile(@Header(LOGIN_REQUEST_HEADER_KEY) String header,
                                  @Header(TOKEN_HEADER_KEY) String token,
                                  @Body UserNameSetter userNameSetter);

    @PUT(UPDATE_PROFILE_REQUEST_URL)
    Call<ResponseGetterBase> upateProfile(@Header(LOGIN_REQUEST_HEADER_KEY) String header,
                                          @Header(TOKEN_HEADER_KEY) String token,
                                          @Query(USERNAME) String string,
                                          @Body ContactSetter contactSetter);

    @GET(GET_ALL_RESOURCE_SUMMARY_URL)
    Call<AllResourceSummary> getResourceSummary(@Header(LOGIN_REQUEST_HEADER_KEY) String header,
                                                @Header(TOKEN_HEADER_KEY) String token);

    @GET(HARDWARE_COUNT_SUMMARY_URL)
    Call<HardwareCountResponse> getHardwareSummaryCount(@Header(LOGIN_REQUEST_HEADER_KEY) String header,
                                                        @Header(TOKEN_HEADER_KEY) String token);

    @POST(MASTER_HARDWARE_BRAND_DETAIL_URL)
    Call<HardwareBrandResponse> getMasterHardwareBrands(@Header(LOGIN_REQUEST_HEADER_KEY) String header,
                                                        @Header(TOKEN_HEADER_KEY) String token);

    @POST(MASTER_HARDWARE_TYPE_DETAIL_URL)
//TODO addnewHwdRsrcActivity
    Call<HardwareTypeResponse> getMasterHardwareTypes(@Header(LOGIN_REQUEST_HEADER_KEY) String header,
                                                      @Header(TOKEN_HEADER_KEY) String token);

    @POST(MASTER_SOFTWARE_LICENCE_TYPE_URL)
    Call<SoftwareLicenseListResponse> getMasterSoftwareLicenseType(@Header(LOGIN_REQUEST_HEADER_KEY) String header,
                                                                   @Header(TOKEN_HEADER_KEY) String token);

    @POST(MASTER_SOFTWARE_LICENCE_TYPE_URL)
    Call<SoftwareLicenseListResponse> getMasterSoftwareLicenseType(@Header(LOGIN_REQUEST_HEADER_KEY) String header,
                                                                   @Header(TOKEN_HEADER_KEY) String token, LicenseIdSetter licenseIdSetter);

    @POST(MASTER_SOFTWARE_BRAND_DETAIL_URL)
    Call<SoftwareBrandListResponse> getMasterSoftwareBrandDetail(@Header(LOGIN_REQUEST_HEADER_KEY) String header,
                                                                 @Header(TOKEN_HEADER_KEY) String token,
                                                                 @Body SoftwareBrandIdSetter softwareBrandIdSetter);

    @POST(MASTER_SOFTWARE_BRAND_DETAIL_URL)
    Call<SoftwareBrandListResponse> getMasterSoftwareBrandDetail(@Header(LOGIN_REQUEST_HEADER_KEY) String header,
                                                                 @Header(TOKEN_HEADER_KEY) String token);

    @POST(SOFTWARE_DETAIL_URL)
    Call<SoftwareDetailsResponse> getSoftwareDetailById(@Header(LOGIN_REQUEST_HEADER_KEY) String header,
                                                        @Header(TOKEN_HEADER_KEY) String token,
                                                        @Body SoftwareIdSetter id);

    @POST(SOFTWARE_DETAIL_URL)
    Call<SoftwareDetailsSetter> getSoftwareDetails(@Header(LOGIN_REQUEST_HEADER_KEY) String header,
                                                   @Header(TOKEN_HEADER_KEY) String token);


    @POST(MASTER_SOFTWARE_CREATE_NEW_BRAND)
    Call<ResponseGetterBase> createMasterSoftwareBrandType(@Header(LOGIN_REQUEST_HEADER_KEY) String header,
                                                           @Header(TOKEN_HEADER_KEY) String token,
                                                           @Body NewBrandSetter newBrandSetter);

    @POST(MASTER_SOFTWARE_CREATE_NEW_LICENSE_TYPE)
    Call<ResponseGetterBase> createMasterSoftwareLicenseType(@Header(LOGIN_REQUEST_HEADER_KEY) String header,
                                                             @Header(TOKEN_HEADER_KEY) String token,
                                                             @Body NewLicenseSetter newLicenseSetter);

    @DELETE(MASTER_DELETE_SOFTWARE)
    Call<ResponseGetterBase> deleteSoftware(@Header(LOGIN_REQUEST_HEADER_KEY) String header,
                                            @Header(TOKEN_HEADER_KEY) String token,
                                            @Path("id") int id);

    @POST(ADD_NEW_HARDWARE_REQUEST_URL)
    Call<ResponseGetterBase> addNewHardwareRequestResponse(@Header(LOGIN_REQUEST_HEADER_KEY) String header,
                                                           @Header(TOKEN_HEADER_KEY) String token,
                                                           @Body AddHardwareSetter body);

    @POST(MASTER_DELETE_HARDWARE)
    Call<ResponseGetterBase> deleteHardware(@Header(LOGIN_REQUEST_HEADER_KEY) String header,
                                            @Header(TOKEN_HEADER_KEY) String token,
                                            @Body IdSetter id);

    @GET(VIEW_ALL_ADMINS)
    Call<AdminListResponse> getAdminList(@Header(LOGIN_REQUEST_HEADER_KEY) String header,
                                         @Header(TOKEN_HEADER_KEY) String token);

    @POST(NEW_USER_REQUEST)
    Call<ResponseGetterBase> newUserRequestResponse(@Header(LOGIN_REQUEST_HEADER_KEY) String header,
                                                    @Header(TOKEN_HEADER_KEY) String token, @Body NewUserRequestSetter body);

    @POST(CREATE_NEW_SOFTWARE)
    Call<ResponseGetterBase> createNewSoftware(@Header(LOGIN_REQUEST_HEADER_KEY) String header,
                                               @Header(TOKEN_HEADER_KEY) String token,
                                               @Body AddSoftwareSetter addSoftwareSetter);

    @POST(HARDWARE_DETAILS_BY_ID)
    Call<HardwareByIdResponse> getHardwareDetailById(@Header(LOGIN_REQUEST_HEADER_KEY) String header,
                                                     @Header(TOKEN_HEADER_KEY) String token,
                                                     @Body HardwareIdSetter id);

    @POST(HARDWARE_DETAIL_BY_TYPE)
    Call<HardwareByTypeResponse> getHardwareDetailByType(@Header(LOGIN_REQUEST_HEADER_KEY) String header,
                                                         @Header(TOKEN_HEADER_KEY) String token,
                                                         @Body TypeSetter id);

    @POST(AVAILABLE_HARDWARE_DETAILS_BY_TYPE_ID)
    Call<AvailableHardwareByTypeIdResponse> getAvailableHardwareByTypeId(@Header(LOGIN_REQUEST_HEADER_KEY) String header,
                                                                         @Header(TOKEN_HEADER_KEY) String token,
                                                                         @Body TypeSetter typeSetter);

    @POST(ASSIGN_REQUESTED_HARDWARE)
    Call<ResponseGetterBase>getAssignRequestedHardware(@Header(LOGIN_REQUEST_HEADER_KEY) String header,
                                                       @Header(TOKEN_HEADER_KEY) String token,
                                                       @Body AssignPendingHardwareSetter pendingHardwareSetter);

    @GET(GET_ALL_USERS)
    Call<ViewAllUsersResponse>getAllUsers(@Header(LOGIN_REQUEST_HEADER_KEY) String header,
                                          @Header(TOKEN_HEADER_KEY) String token);
    @POST(ASSIGN_HARDWARE)
    Call<ResponseGetterBase>getAssignHardware(@Header(LOGIN_REQUEST_HEADER_KEY) String header,
                                              @Header(TOKEN_HEADER_KEY) String token,
                                              @Body AssignHardwareSetter assignHardwareSetter);
    @GET(ADMIN_HOME_PAGE_SUMMARY)
    Call<AdminHomeSummaryCountResponse> getAdminHomePageSummary(@Header(LOGIN_REQUEST_HEADER_KEY) String header,
                                                                @Header(TOKEN_HEADER_KEY) String token);

    @PUT(UPDATE_SOFTWARE)
    Call<ResponseGetterBase> getSoftwareUpdateResponse(@Header(LOGIN_REQUEST_HEADER_KEY) String header,
                                         @Header(TOKEN_HEADER_KEY) String token,
                                         @Body UpdateSoftwareSetter body);

    @POST(USER_MY_REQUESTS)
    Call<UserMyRequestsResponse>getUserRequestsById(@Header(LOGIN_REQUEST_HEADER_KEY) String header,
                                                    @Header(TOKEN_HEADER_KEY) String token,
                                                    @Body UserIdSetter userIdSetter);
    @Multipart
    @PUT(USER_IMAGE_UPLOAD_MULTIPART)
    Call<ResponseGetterBase> uploadUserImage( @Header(TOKEN_HEADER_KEY) String token,
                                             @Part MultipartBody.Part file,
                                             @Part("UserID") RequestBody id /*,@Part("Contact") RequestBody contact*/);//TODO

    @POST(USER_DELETE_MY_REQUEST)
    Call<ResponseGetterBase> deleteMyRequest(@Header(LOGIN_REQUEST_HEADER_KEY) String header,
                                             @Header(TOKEN_HEADER_KEY) String token,
                                             @Body DeleteUserRequestSetter deleteUserRequestSetter);
    @POST(VIEW_ALL_PENDING_REQUESTS)
    Call<AllResourceRequestSummary> getResourceRequestSummary(@Header(LOGIN_REQUEST_HEADER_KEY) String header,
                                                              @Header(TOKEN_HEADER_KEY) String token);

    @POST(VIEW_SOFTWARE_KEY)
    Call<KeyDetailsResponse> getSoftwareKey(@Header(LOGIN_REQUEST_HEADER_KEY) String header,
                                            @Header(TOKEN_HEADER_KEY) String token,
                                            @Body SoftwareIdSetter softwareIdSetter);
    @PUT(UPDATE_SOFTWARE_REQUEST)
    Call<ResponseGetterBase> assignSoftwareKey(@Header(LOGIN_REQUEST_HEADER_KEY) String header,
                                               @Header(TOKEN_HEADER_KEY) String token,
                                               @Body AssignSoftwareSetter assignSoftwareSetter);

    @POST(HARDWARE_LIST_BY_USER)
    Call<HardwareListForUserResponse> getHardwareForUser(@Header(LOGIN_REQUEST_HEADER_KEY) String header,
                                                         @Header(TOKEN_HEADER_KEY) String token,
                                                         @Body UserIdSetter idSetter);

    @POST(USER_MY_RESOURCE)
    Call<UserMyRessourceResponse>getUserResourcesById(@Header(LOGIN_REQUEST_HEADER_KEY) String header,
                                                               @Header(TOKEN_HEADER_KEY) String token,
                                                               @Body UserIdSetter userIdSetter);

    @GET(SOFTWARE_SUMMARY_COUNT)
    Call<SoftwareSummaryWithResponse>getSoftwareSummary(@Header(LOGIN_REQUEST_HEADER_KEY) String header,
                                                        @Header(TOKEN_HEADER_KEY) String token);

    @GET(GET_ALL_RESOURCE_SUMMARY_URL)
    Call<SharedResourceSummaryWithResponse>getSharedResourceeSummary(@Header(LOGIN_REQUEST_HEADER_KEY) String header,
                                                                     @Header(TOKEN_HEADER_KEY) String token);
    @POST(SHOW_HARDWARE_IMAGE)
    Call<HardwareImageListResponse>getHardwareImage(@Header(LOGIN_REQUEST_HEADER_KEY) String header,
                                                    @Header(TOKEN_HEADER_KEY) String token,
                                                    @Body HardwareIdSetter hardwareIdSetter);
}