package com.cuiguo.wechatdemo.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

/**
 * author: Cui Guo
 * date: 2019/12/1
 * info:activity基类
 */
abstract class BaseActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(setLayoutId())
        initView()
        initData()
    }

    protected abstract fun setLayoutId(): Int
    protected abstract fun initView()
    protected abstract fun initData()
}
