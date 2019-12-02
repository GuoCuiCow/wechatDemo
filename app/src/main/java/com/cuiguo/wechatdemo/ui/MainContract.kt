package com.cuiguo.wechatdemo.ui

import com.cuiguo.wechatdemo.base.BasePresenter
import com.cuiguo.wechatdemo.data.MomentsListBean
import com.cuiguo.wechatdemo.data.UserInfoBean
import io.reactivex.Observable

/**
 * author: Cui Guo
 * date: 2019/12/1
 * info: 朋友圈契约类
 */

interface MainContract {
    interface View {

        fun showUserInfo(userInfo: UserInfoBean)

        fun refreshList(list: List<MomentsListBean>)

        fun showMoreInfo(list: List<MomentsListBean>)

        fun refreshComplite()

        fun listFail()

    }

    interface Model {

        /**
         * 获取用户信息
         */
        fun requestUserInfo(): Observable<UserInfoBean>

        /**
         * 获取朋友圈列表
         */
        fun requestMoment(): Observable<List<MomentsListBean>>

    }

    interface Presenter : BasePresenter {

        fun getUserInfo()

        fun getMoment()

        fun loadMore()

    }
}