package com.cuiguo.wechatdemo.api

import com.cuiguo.wechatdemo.data.MomentsListBean
import com.cuiguo.wechatdemo.data.UserInfoBean
import io.reactivex.Observable
import retrofit2.http.GET

/**
 * author:Cui Guo
 * date: 2019/12/2
 * info 接口
 */
interface ApiService {

    /**
     * 获取用户信息
     */
    @GET("user/jsmith")
    fun getUserInfo(): Observable<UserInfoBean>

    /**
     * 获取朋友圈列表
     */
    @GET("user/jsmith/tweets")
    fun getMomentList(): Observable<List<MomentsListBean>>

}
