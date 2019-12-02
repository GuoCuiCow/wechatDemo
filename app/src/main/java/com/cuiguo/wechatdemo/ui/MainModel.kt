package com.cuiguo.wechatdemo.ui

import com.cuiguo.wechatdemo.api.RetrofitManager
import com.cuiguo.wechatdemo.data.MomentsListBean
import com.cuiguo.wechatdemo.data.UserInfoBean
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/**
 * author:Cui Guo
 * date: 2019/12/2
 * info 朋友圈model层
 */
class MainModel : MainContract.Model {
    override fun requestUserInfo(): Observable<UserInfoBean> {
        return RetrofitManager.service
            .getUserInfo()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
    }

    override fun requestMoment(): Observable<List<MomentsListBean>> {
        return RetrofitManager.service
            .getMomentList()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
    }
}