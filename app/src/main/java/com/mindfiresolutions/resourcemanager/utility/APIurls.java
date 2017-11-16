package com.mindfiresolutions.resourcemanager.utility;

/**This class contains all the urls which is used in api calls
 * Created by Shivangi Singh on 3/30/2017
 */

public interface APIurls {
    String LOGIN_REQUEST_HEADER_VALUE = "application/json";
    String BASE_URL ="http://203.92.41.80:9093/Api/";   //old url "http://192.168.11.71:5001/Api/";
    String BASE_URL_IMAGE = "http://203.92.41.80:9093/";
    String VIEW_PROFILE_REQUEST_URL = "Accounts/ViewUserProfile";
    String LOGIN_REQUEST_DETAIL_URL = "Accounts/UserLogIn";
    String SIGNUP_REQUEST_DETAIL_URL = "Accounts/UserRegistration";
    String LOGIN_REQUEST_HEADER_KEY = "Content-Type";
    String CHANGE_PASSWORD_DETAIL_URL = "Accounts/ChangeUserPassword";
    String UPDATE_PROFILE_REQUEST_URL = " Accounts//UpdateUser";
    String USERNAME= "userName";
    String FORGOT_PASSWORD_REQUEST_URL = "Accounts/RetrieveUserPassword";
    String GET_ALL_RESOURCE_SUMMARY_URL = "Summary/Summary";
    String HARDWARE_COUNT_SUMMARY_URL = "HardwareSummary/ViewHardwareCountSummary";
    String MASTER_HARDWARE_BRAND_DETAIL_URL = "Master/ViewHardwareBrands";
    String MASTER_HARDWARE_TYPE_DETAIL_URL = "Master/ViewHardwareType";
    String MASTER_SOFTWARE_BRAND_DETAIL_URL = "Master/GetSoftwareBrandDetails";
    String MASTER_SOFTWARE_LICENCE_TYPE_URL = "Master/GetLicenseTypes";
    String SOFTWARE_DETAIL_URL = "Software/GetSoftwareDetails";
    String ADD_NEW_HARDWARE_REQUEST_URL = "Hardware/AddHardwareDetails";
    String ID = "id";
    String MASTER_SOFTWARE_CREATE_NEW_BRAND = "Master/CreateSoftwareBrandDetail";
    String MASTER_DELETE_SOFTWARE ="Software/RemoveSoftwareDetails/{id}";
    String TOKEN_HEADER_KEY = "Authorization";
    String VIEW_ALL_ADMINS = "Requests/ViewAllAdmins";
    String MASTER_DELETE_HARDWARE = "Hardware/DeleteHardware";
    String NEW_USER_REQUEST = "Requests/UserRequest";
    String CREATE_NEW_SOFTWARE = "Software/CreateSoftwareDetails";
    String MASTER_SOFTWARE_CREATE_NEW_LICENSE_TYPE = "Master/CreateLicenseType";
    String SOFTWARE_DETAILS_BY_ID_URL = "Software/GetSoftwareDetailsByID";
    String HARDWARE_DETAIL_BY_TYPE = "Hardware/ViewHardwareByType";
    String HARDWARE_DETAILS_BY_ID = "Hardware/ViewHardware";
    String VIEW_ALL_PENDING_REQUESTS = "Requests/ViewAllPendingRequests";
    String ADMIN_HOME_PAGE_SUMMARY = "Summary/HomePageSummaryCount";
    String AVAILABLE_HARDWARE_DETAILS_BY_TYPE_ID = "Hardware/ViewAvailableHardwareByType";
    String ASSIGN_REQUESTED_HARDWARE = "Requests/AssignRequestedHardware";
    String GET_ALL_USERS="Requests/ViewAllEmployees";
    String ASSIGN_HARDWARE = "Hardware/AssignHardwareActivity";
    String UPDATE_SOFTWARE = "Software/EditSoftwareDetails";
    String USER_MY_REQUESTS = "Requests/MyAllRequests";
    String USER_DELETE_MY_REQUEST = "Requests/RemoveRequest";
    String USER_IMAGE_UPLOAD_MULTIPART = "Accounts/UpdateUser";
    String VIEW_SOFTWARE_KEY = "Software/GetKeysBySoftwareID";
    String UPDATE_SOFTWARE_REQUEST = "Requests/UpdateSoftwareRequest";
    String HARDWARE_LIST_BY_USER = "Hardware/ViewAssignHardwareByUserId";
    String USER_MY_RESOURCE = "Requests/ViewAllAssignedRequests";
    String SOFTWARE_SUMMARY_COUNT = "SoftwareSummary/Summary";
    String SHOW_HARDWARE_IMAGE = "Hardware/ViewHardwareImages";
}
