package com.cuiguo.wechatdemo.ui

import android.text.TextUtils
import android.util.Log
import com.cuiguo.wechatdemo.data.MomentsListBean
import io.reactivex.disposables.CompositeDisposable

/**
 * author:Cui Guo
 * date: 2019/12/2
 * info 朋友圈presenter
 */
class MainPresenter(
    private var view: MainContract.View
) : MainContract.Presenter {


    private val disposable: CompositeDisposable by lazy {
        CompositeDisposable()
    }
    private var pageNo = 0
    private var pageSize = 5
    private val momentsListBean = mutableListOf<MomentsListBean>()

    private val model: MainModel by lazy {
        MainModel()
    }

    override fun getUserInfo() {
        model.requestUserInfo().subscribe(
            { t ->
                view.showUserInfo(t)
            },
            { t ->
                Log.i("error", t.message.toString())

            }
        ).also { disposable.add(it) }

    }

    override fun getMoment() {
        model.requestMoment()
            .doFinally { view.refreshComplite() }
            .subscribe(
                { t ->
                    momentsListBean.clear()
                    //筛选出有内容和图片的评论
                    momentsListBean.addAll(t.filterNot { TextUtils.isEmpty(it.content) && it.images?.isEmpty() ?: true })
                    pageNo = 1
                    view.refreshList(getListPage(0, pageSize))
                },
                { t ->
                    Log.i("error", t.message.toString())
                }
            ).also { disposable.add(it) }

    }

    private fun getListPage(start: Int, end: Int): List<MomentsListBean> {
        val cacheList = mutableListOf<MomentsListBean>()
        if (momentsListBean.size > start) {
            if (momentsListBean.size < end) {
                for (i in start until momentsListBean.size) {
                    Log.d("tag", i.toString())
                    cacheList.add(momentsListBean[i])
                }
            } else {
                for (i in start until end) {
                    Log.d("tag", i.toString())
                    cacheList.add(momentsListBean[i])
                }
            }
        }
        return cacheList
    }

    override fun loadMore() {
        pageNo++
        view.showMoreInfo(getListPage(pageNo * pageSize, (pageNo + 1) * pageSize))
    }

    override fun unSubscribe() {
        disposable.dispose()
    }
}