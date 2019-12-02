package com.cuiguo.wechatdemo.util

import android.graphics.drawable.Drawable
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestBuilder
import com.bumptech.glide.request.RequestOptions
import com.cuiguo.wechatdemo.R


/**
 * author: Cui Guo
 * date: 2019/11/30
 * info:图片加载工具方法
 */

/**
 * 占位符矩形
 */
fun ImageView.load(url: String) {
    get(url).apply(options).into(this)
}

/**
 * 占位符圆角矩形
 */
fun ImageView.loadRound(url: String,round:Int) {
    get(url).apply(options.transform(GlideRoundTransform(context, round))).into(this)
}

val options: RequestOptions = RequestOptions()
    .centerCrop()
    .placeholder(R.drawable.placeholder)
    .error(R.drawable.placeholder)

fun ImageView.get(url: String): RequestBuilder<Drawable> = Glide.with(context).load(url)
fun ImageView.get(url: Drawable): RequestBuilder<Drawable> = Glide.with(context).load(url)