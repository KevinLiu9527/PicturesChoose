package com.kevin.pictureschoose;

import android.app.Application;

import com.facebook.drawee.backends.pipeline.Fresco;

/**
 * 项目名称：PicturesChoose
 * 创建人：KevinLiu
 * 创建时间：2017/7/3 10:51
 * 描述：
 */
public class AppAplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Fresco.initialize(this);
    }
}
