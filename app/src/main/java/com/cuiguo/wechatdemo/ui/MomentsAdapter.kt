package com.cuiguo.wechatdemo.ui

import android.text.TextUtils
import android.view.View
import android.widget.ImageView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.cuiguo.wechatdemo.data.MomentsListBean
import com.cuiguo.wechatdemo.R
import com.cuiguo.wechatdemo.data.CommentsBean
import com.cuiguo.wechatdemo.util.loadRound
import com.cuiguo.wechatdemo.data.ImagesBean


/**
 * author: Cui Guo
 * date: 2019/11/30
 * info: 朋友圈adapter
 */
class MomentsAdapter(layoutResId: Int, data: List<MomentsListBean>?) :
    BaseQuickAdapter<MomentsListBean, BaseViewHolder>(layoutResId, data) {

    override fun convert(helper: BaseViewHolder, item: MomentsListBean) {
        helper.apply {
            setText(R.id.nickname, item.sender?.nick)
            setGone(R.id.content,!TextUtils.isEmpty(item.content))
            setText(R.id.content, item.content)
            item.sender?.avatar?.let {
                getView<ImageView>(R.id.avatar).loadRound(it, 4)
            }
            item.images?.let {
                if (it.isNotEmpty()) {
                    val ivSinger = getView<ImageView>(R.id.ivSinger)
                    val rvImage = getView<RecyclerView>(R.id.rvImage)
                    if (it.size == 1) {
                        ivSinger.visibility = View.VISIBLE
                        rvImage.visibility = View.GONE
                        rvImage.removeAllViews()
                        ivSinger.loadRound(it[0].url!!, 4)
                    } else {
                        ivSinger.visibility = View.GONE
                        rvImage.visibility = View.VISIBLE
                        setImages(rvImage, it)
                    }
                }
            }
            val rvComment = getView<RecyclerView>(R.id.rvComment)
            if (item.comments==null||item.comments!!.isEmpty()){
                rvComment.visibility=View.GONE
            }else{
                rvComment.visibility=View.VISIBLE
                setComment(rvComment,item.comments!!)
            }
        }
    }

    /**
     * 设置图片列表
     */
    private fun setImages(rvImage: RecyclerView, urls: List<ImagesBean>) {
        val imageAdapter = ImageAdapter(R.layout.item_iamge, urls)
        rvImage.apply {
            adapter = imageAdapter
            layoutManager = GridLayoutManager(context, 3)
        }
    }
    /**
     * 设置评论列表
     */
    private fun setComment(rvComment: RecyclerView, urls: List<CommentsBean>) {
        val commentAdapter = CommentAdapter(R.layout.item_comment, urls)
        rvComment.apply {
            adapter = commentAdapter
            layoutManager = LinearLayoutManager(context)
        }
    }
}
