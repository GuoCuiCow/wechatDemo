package com.cuiguo.wechatdemo.ui

import android.widget.ImageView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.cuiguo.wechatdemo.R
import com.cuiguo.wechatdemo.data.ImagesBean
import com.cuiguo.wechatdemo.util.loadRound

/**
 * author: Cui Guo
 * date: 2019/12/1
 * info: 九宫格图片adapter
 */
class ImageAdapter(layoutResId: Int, data: List<ImagesBean>?) :
    BaseQuickAdapter<ImagesBean, BaseViewHolder>(layoutResId, data) {

    override fun convert(helper: BaseViewHolder, item: ImagesBean) {
        item.url?.let {
            helper.getView<ImageView>(R.id.ivMoment).loadRound(it,4)
        }
    }
}
