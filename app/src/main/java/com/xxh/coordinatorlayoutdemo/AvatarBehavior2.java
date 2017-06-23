package com.xxh.coordinatorlayoutdemo;

import android.content.Context;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.util.AttributeSet;
import android.view.View;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by 解晓辉 on 2017/6/23.
 * 作用：
 */

public class AvatarBehavior2 extends CoordinatorLayout.Behavior<CircleImageView> {


    public static final float ANIM_START_RANGE = 0.3F;

    //初始的大小
    private int startSize;

    private int finalSize;

    //初始的X坐标
    private int startX;








    public AvatarBehavior2(Context context, AttributeSet attrs) {
        super(context, attrs);

    }

    @Override
    public boolean layoutDependsOn(CoordinatorLayout parent, CircleImageView child, View dependency) {
        //AppBarLayout 跟随AppBarLayout进行变化
        return dependency instanceof AppBarLayout;
    }

    @Override
    public boolean onDependentViewChanged(CoordinatorLayout parent, CircleImageView child, View dependency) {
        
        if(dependency instanceof AppBarLayout) {
            //初始化变量
            initVariable(child, dependency);





            return  true;
        }
        
        
        return super.onDependentViewChanged(parent, child, dependency);
    }

    private void initVariable(CircleImageView child, View dependency) {

    }


}
