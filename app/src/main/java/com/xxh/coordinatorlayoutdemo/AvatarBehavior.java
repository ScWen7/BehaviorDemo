package com.xxh.coordinatorlayoutdemo;

import android.content.Context;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by 解晓辉 on 2017/6/22.
 * 作用：
 */

public class AvatarBehavior extends CoordinatorLayout.Behavior<CircleImageView> {

    private static final float ANIM_CHANGE_START = 0.2f;

    private Context mContext;

    private int totalScrollRange; //可滑动的范围

    private int appBarHeight; //AppBar 的高度

    private int appBarWidth;//appBar的宽度

    private int originSize;//控件原始的大小

    private int finalSize; //控件最终的大小

    private float sizeOffset; //控件半径发生的变化 ,用于计算坐标

    private float originX;

    private float originY;

    private float finalX;

    private float finalY;


    // ToolBar高度
    private int mToolBarHeight;
    // AppBar的起始Y坐标
    private float mAppBarStartY;
    // 滚动执行百分比[0~1]
    private float mPercent;
    // Y轴移动插值器
    private DecelerateInterpolator mMoveYInterpolator;
    // X轴移动插值器
    private AccelerateInterpolator mMoveXInterpolator;
    // 最终变换的视图，因为在5.0以上AppBarLayout在收缩到最终状态会覆盖变换后的视图，所以添加一个最终显示的图片
    private CircleImageView mFinalView;
    // 最终变换的视图离底部的大小
    private int mFinalViewMarginBottom;

    public AvatarBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mContext = context;
        mMoveYInterpolator = new DecelerateInterpolator();
        mMoveXInterpolator = new AccelerateInterpolator();
    }

    @Override
    public boolean layoutDependsOn(CoordinatorLayout parent, CircleImageView child, View dependency) {
        return dependency instanceof AppBarLayout;
    }

    @Override
    public boolean onDependentViewChanged(CoordinatorLayout parent, CircleImageView child, View dependency) {
        if (dependency instanceof AppBarLayout) {
            //初始化变量
            initVariable(child, dependency);


            mPercent = (mAppBarStartY - dependency.getY()) * 1.0f / totalScrollRange;

            float percentY = mMoveYInterpolator.getInterpolation(mPercent);
            AnimHelper.setViewY(child, originY, finalY - sizeOffset, percentY);

            if (mPercent >ANIM_CHANGE_START) { //大于这个系数
                float scalePercent = (mPercent - ANIM_CHANGE_START) / (1 - ANIM_CHANGE_START);
                float percentX = mMoveXInterpolator.getInterpolation(scalePercent);


                AnimHelper.setViewX(child, originX, finalX - sizeOffset, percentX);
                AnimHelper.scaleView(child, originSize, finalSize, scalePercent);

            } else {
                AnimHelper.scaleView(child, originSize, finalSize, 0);
                AnimHelper.setViewX(child, originX, finalX - sizeOffset, 0);
            }


        }


        return true;
    }

    private void initVariable(CircleImageView child, View dependency) {
        AppBarLayout appBarLayout = (AppBarLayout) dependency;

        if (totalScrollRange == 0) {
            totalScrollRange = appBarLayout.getTotalScrollRange();
        }
        if (mAppBarStartY == 0) {
            mAppBarStartY = appBarLayout.getY();
        }
        if (appBarHeight == 0) {
            appBarHeight = appBarLayout.getHeight();
        }
        if (appBarWidth == 0) {
            appBarWidth = appBarLayout.getWidth();
        }

        if (mToolBarHeight == 0) {
            mToolBarHeight = mContext.getResources().getDimensionPixelSize(R.dimen.toolbar_height);
        }

        if (finalSize == 0) {
            finalSize = mContext.getResources().getDimensionPixelSize(R.dimen.avatar_final_size);
        }
        if (originX == 0) {
            originX = child.getX();
        }
        if (originY == 0) {
            originY = child.getY();
        }
        if (finalX == 0) {
            finalX = mContext.getResources().getDimensionPixelSize(R.dimen.avator_final_x);
        }
        if (finalY == 0) {
            finalY = (mToolBarHeight - finalSize) / 2 + mAppBarStartY;
        }
        if (originSize == 0) {
            originSize = child.getWidth();
        }

        if (sizeOffset == 0) {
            sizeOffset = (originSize - finalSize) * 1.0f / 2;
        }

        if (mFinalViewMarginBottom == 0) {
            mFinalViewMarginBottom = (mToolBarHeight - finalSize) / 2;
        }


    }


}
