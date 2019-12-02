package com.cuiguo.wechatdemo.ui

import android.content.Context
import android.graphics.Color
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.cuiguo.wechatdemo.R
import com.cuiguo.wechatdemo.base.BaseActivity
import com.cuiguo.wechatdemo.data.MomentsListBean
import com.cuiguo.wechatdemo.data.UserInfoBean
import com.cuiguo.wechatdemo.util.load
import com.cuiguo.wechatdemo.util.loadRound
import kotlinx.android.synthetic.main.activity_main.*
import android.os.Build

/**
 * author:Cui Guo
 * date: 2019/12/2
 * info 朋友圈界面
 */
class MainActivity : BaseActivity(), MainContract.View {
    companion object {
        /**
         * 背景图片高度（dp）
         */
        private const val BG_HEIGHT = 400
        /**
         * icon高度（dp）
         */
        private const val ICON_HEIGHT = 48
        /**
         * 每页数
         */
        private const val PAGE_SIZE = 5
    }

    private var headView: View? = null
    private val momentsListBean = mutableListOf<MomentsListBean>()
    private val momentsAdapter by lazy {
        MomentsAdapter(
            R.layout.item_moment,
            momentsListBean
        )
    }
    private val presenter: MainPresenter by lazy {
        MainPresenter(this)
    }

    override fun setLayoutId(): Int {
        return R.layout.activity_main
    }

    override fun initView() {
        setStatusLight()
        headView = layoutInflater.inflate(R.layout.headview, null)
        momentsAdapter.addHeaderView(headView)
        momentsAdapter.setOnLoadMoreListener({
            presenter.loadMore()
        }, rvList)
        rvList.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = momentsAdapter
        }
        rvList.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            private var totalDy = 0
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                totalDy += dy
                when {
                    totalDy < dip2px(this@MainActivity, (BG_HEIGHT - ICON_HEIGHT).toFloat()) -> {
                        tvTitle.visibility = View.INVISIBLE
                        llTitle.alpha = 1.toFloat()
                        llTitle.setBackgroundColor(Color.parseColor("#00000000"))
                        ivBack.setImageResource(R.drawable.ic_chevron_left_white_24dp)
                        ivCamera.setImageResource(R.drawable.ic_camera_alt_white_24dp)
                        setStatusLight()
                    }
                    totalDy < dip2px(this@MainActivity, BG_HEIGHT.toFloat()) -> {
                        tvTitle.visibility = View.VISIBLE
                        val alpha = (dip2px(
                            this@MainActivity,
                            BG_HEIGHT.toFloat()
                        ) - totalDy) / dip2px(this@MainActivity, ICON_HEIGHT.toFloat())
                        llTitle.alpha = (1 - alpha)
                        llTitle.setBackgroundColor(Color.parseColor("#D3D3D3"))
                        ivBack.setImageResource(R.drawable.ic_chevron_left_black_24dp)
                        ivCamera.setImageResource(R.drawable.ic_camera_alt_black_24dp)
                        setStatusDark()

                    }
                    else -> {
                        tvTitle.visibility = View.VISIBLE
                        llTitle.alpha = 1.toFloat()
                        llTitle.setBackgroundColor(Color.parseColor("#D3D3D3"))
                        ivBack.setImageResource(R.drawable.ic_chevron_left_black_24dp)
                        ivCamera.setImageResource(R.drawable.ic_camera_alt_black_24dp)
                        setStatusDark()
                    }
                }

            }
        })
        swipeRefreshLayout.setOnRefreshListener {
            presenter.getMoment()
        }

    }

    private fun setStatusLight() {
        if (Build.VERSION.SDK_INT >= 21) {
            val decorView = window.decorView
            val option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
            decorView.systemUiVisibility = option
            window.statusBarColor = Color.TRANSPARENT
        }
    }

    private fun setStatusDark() {
        if (Build.VERSION.SDK_INT >= 23) {
            window.statusBarColor = Color.TRANSPARENT
            window.decorView.systemUiVisibility =
                View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        }
    }

    override fun initData() {
        presenter.getUserInfo()
        presenter.getMoment()
    }

    override fun showUserInfo(userInfo: UserInfoBean) {
        initHeaderView(userInfo)
    }

    override fun refreshList(list: List<MomentsListBean>) {
        momentsListBean.clear()
        momentsListBean.addAll(list)
        momentsAdapter.setNewData(momentsListBean)
        if (list.size < PAGE_SIZE) {
            momentsAdapter.loadMoreEnd()
        }

    }

    override fun showMoreInfo(list: List<MomentsListBean>) {
        momentsListBean.addAll(list)
        momentsAdapter.notifyDataSetChanged()
        if (list.size == PAGE_SIZE) {
            momentsAdapter.loadMoreComplete()
        } else {
            momentsAdapter.loadMoreEnd()
        }
    }

    override fun refreshComplite() {
        swipeRefreshLayout.isRefreshing = false
    }

    override fun listFail() {
        momentsAdapter.loadMoreFail()
    }

    private fun initHeaderView(userInfoBean: UserInfoBean) {
        headView?.apply {
            findViewById<ImageView>(R.id.ivAvatar).loadRound(userInfoBean.avatar ?: "", 4)
            findViewById<ImageView>(R.id.bgImage).load(userInfoBean.profileimage ?: "")
            findViewById<TextView>(R.id.name).text = userInfoBean.nick
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.unSubscribe()
    }

    private fun dip2px(context: Context, dpValue: Float): Float {
        val scale = context.resources.displayMetrics.density
        return (dpValue * scale + 0.5f)
    }

}
