package bases.commonmodel

import android.provider.SyncStateContract
import utilities.UserType


open class MentionedUser {

    var mentionedUserId: Int = 0
    var mentionedUserName: String? = null
    var mentionedImage: String? = null
    var mentionedType: UserType? = null
}
