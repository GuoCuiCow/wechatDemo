package com.cuiguo.wechatdemo.ui

import android.text.SpannableStringBuilder
import android.text.Spanned
import android.text.style.TextAppearanceSpan
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.cuiguo.wechatdemo.R
import com.cuiguo.wechatdemo.data.CommentsBean


/**
 * author: Cui Guo
 * date: 2019/12/1
 * info: 评论adapter
 */
class CommentAdapter(layoutResId: Int, data: List<CommentsBean>?) :
    BaseQuickAdapter<CommentsBean, BaseViewHolder>(layoutResId, data) {


    override fun convert(helper: BaseViewHolder, item: CommentsBean) {
        val name = item.sender?.nick + ":"
        val content = item.content
        val ssb = SpannableStringBuilder(name+content)
        ssb.setSpan(
            TextAppearanceSpan(
               mContext,R.style.name_style
            ), 0, name.length, Spanned.SPAN_INCLUSIVE_INCLUSIVE
        )

        helper.setText(R.id.tvComment, ssb)
    }
}
