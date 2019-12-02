package com.cuiguo.wechatdemo.data

import com.google.gson.annotations.SerializedName

/**
 * author: Cui Guo
 * date: 2019/11/28
 * info: 用户信息bean
 */
data class UserInfoBean(
    @SerializedName("profile-image")
    var profileimage: String? = null,
    var avatar: String? = null,
    var nick: String? = null,
    var username: String? = null
)
