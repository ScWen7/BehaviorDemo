package com.xxh.coordinatorlayoutdemo;

import android.content.Context;
import android.support.design.widget.CoordinatorLayout;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by 解晓辉 on 2017/6/22.
 * 作用：
 */

public class CustomBehavior extends CoordinatorLayout.Behavior {

    //在布局中使用 必须实现 两个参数的构造函数
    public CustomBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    //确定关联的视图
    @Override
    public boolean layoutDependsOn(CoordinatorLayout parent, View child, View dependency) {
        return super.layoutDependsOn(parent, child, dependency);
    }

    //当关联的视图发生变化回调
    //视图的大小和位置发生变化都会回调
    @Override
    public boolean onDependentViewChanged(CoordinatorLayout parent, View child, View dependency) {
        return super.onDependentViewChanged(parent, child, dependency);
    }

    //当关联的视图移除时回调
    @Override
    public void onDependentViewRemoved(CoordinatorLayout parent, View child, View dependency) {
        super.onDependentViewRemoved(parent, child, dependency);
    }
}
