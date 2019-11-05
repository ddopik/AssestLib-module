package utilities

val PERMEATION_REQUEST_CODE__WRITE_EXTERNAL_CAMERA = 1223
val REQUEST_CODE_GALLERY = 1224
val REQUEST_CODE_CAMERA = 1225
val REQUEST_CODE_LOCATION = 1225
//    String  DEFAULT_PROFILE_IMAGE =1225;

val SOCIAL_FRAGMENT_PAGING_THRESHOLD = 15

val QUERY_SEARCH_TIME_OUT = 600


//////NavigationMangerConst
enum class NavigationHelper {
    CAMPAIGN, PROFILE, HOME, NOTIFICATION, UPLOAD_PHOTO, EARNING_LIST, EARNING_INNER, LOGOUT, EDIT_PROFILE
}


enum class UploadImageTypes {
    CAMPAIGN_IMG, NORMAL_IMG
}

enum class UserType {

    USER_TYPE_BUSINESS, USER_TYPE_PHOTOGRAPHER
}

/**
 * PERSONAL --->Case image specified for current user
 */
enum class ImageType {
    PERSONAL, SOCIAL
}

///////////////////////Entity-type For socialFragment
val ENTITY_PROFILE = 1
val ENTITY_CAMPAIGN = 2
val ENTITY_ALBUM = 4
val ENTITY_IMAGE = 3
val ENTITY_BRAND = 5

///////////////Entity Display-types
val PROFILE_DISPLAY_TYPE_1 = "101"
val PROFILE_DISPLAY_TYPE_2 = "102"
val PROFILE_DISPLAY_TYPE_3 = "103"
val PROFILE_DISPLAY_TYPE_4 = "104"
val PROFILE_DISPLAY_TYPE_5 = "105"
val PROFILE_DISPLAY_TYPE_6 = "106"

val CAMPAIGN_DISPLAY_TYPE_1 = "201"
val CAMPAIGN_DISPLAY_TYPE_2 = "202"
val CAMPAIGN_DISPLAY_TYPE_3 = "203"
val CAMPAIGN_DISPLAY_TYPE_4 = "204"
val CAMPAIGN_DISPLAY_TYPE_5 = "205"


val ALBUM_DISPLAY_TYPE_1 = "301"
val ALBUM_DISPLAY_TYPE_2 = "302"
val ALBUM_DISPLAY_TYPE_3 = "303"
val ALBUM_DISPLAY_TYPE_4 = "405"
val ALBUM_DISPLAY_TYPE_5 = "305"

val IMGS_DISPLAY_TYPE_1 = "401"
val IMGS_DISPLAY_TYPE_2 = "402"
val IMGS_DISPLAY_TYPE_3 = "403"
val IMGS_DISPLAY_TYPE_4 = "404"
val IMGS_DISPLAY_TYPE_5 = "405"

val BRAND_DISPLAY_TYPE_1 = "501"
val BRAND_DISPLAY_TYPE_2 = "502"
val BRAND_DISPLAY_TYPE_3 = "503"
val BRAND_DISPLAY_TYPE_4 = "504"
val BRAND_DISPLAY_TYPE_5 = "505"

///////////////////////////////


enum class PhotosListType {
    SOCIAL_LIST, CURRENT_PHOTOGRAPHER_PHOTOS_LIST, CURRENT_PHOTOGRAPHER_SAVED_LIST
}


enum class CommentListType {
    VIEW_REPLIES, MAIN_COMMENT, REPLAY_ON_COMMENT
}

interface CampaignStatus {
    companion object {
        val CAMPAIGN_STATUS_CANCELLED = -1
        val CAMPAIGN_STATUS_DRAFT = 0
        val CAMPAIGN_STATUS_REQUEST = 1
        val CAMPAIGN_STATUS_PENDING = 2
        val CAMPAIGN_STATUS_APPROVED = 3
        val CAMPAIGN_STATUS_RUNNING = 4
        val CAMPAIGN_STATUS_FINISHED = 5
        val CAMPAIGN_STATUS_PRIZE_PROCESSING = 6
        val CAMPAIGN_STATUS_COMPLETED = 7
    }
}

interface MainActivityRedirectionValue {
    companion object {
        val VALUE = MainActivityRedirectionValue::class.java.simpleName
        val PAYLOAD = "payload"
        val TO_PROFILE = 1
        val TO_POPUP = 2
    }
}

interface PopupType {
    companion object {
        val LEVEL_UP = 1
        val WON_CAMPAIGN = 2
        val NONE = 0
    }
}

val STATUS_LOGGED_IN = 1
val STATUS_LOGGED_OUT = 0