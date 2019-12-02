package com.cuiguo.wechatdemo.data

import com.google.gson.annotations.SerializedName

/**
 * author: Cui Guo
 * date: 2019/11/28
 * info: 朋友圈列表bean
 */
data class MomentsListBean(
    var content: String? = null,
    var sender: SenderBean? = null,
    var error: String? = null,
    @SerializedName("unknown error")
    var `_$UnknownError52`: String? = null,
    var images: List<ImagesBean>? = null,
    var comments: List<CommentsBean>? = null
)

data class SenderBean(
    var username: String? = null,
    var nick: String? = null,
    var avatar: String? = null
)

data class ImagesBean(var url: String? = null)

data class CommentsBean(
    var content: String? = null,
    var sender: SenderBeanX? = null

)

data class SenderBeanX(
    var username: String? = null,
    var nick: String? = null,
    var avatar: String? = null
)