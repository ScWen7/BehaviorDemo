package com.xxh.coordinatorlayoutdemo;

import android.content.Context;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.widget.Toolbar;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by 解晓辉 on 2017/6/23.
 * 作用：
 */

public class AvatarBehavior2 extends CoordinatorLayout.Behavior<CircleImageView> {


    private final static float MIN_AVATAR_PERCENTAGE_SIZE = 0.3f;
    private final static int EXTRA_FINAL_AVATAR_PADDING = 80;

    private final static String TAG = "behavior";
    private Context mContext;
    private float mStartToolbarPosition;
    private int mFinalHeight;
    private int mStartYPosition;
    private int mFinalYPosition;
    private int mStartHeight;
    private int mStartXPosition;
    private int mFinalXPosition;
    private Toolbar mToolbar;


    public AvatarBehavior2(Context context, AttributeSet attrs) {
        mContext = context;
        init();

    }

    private void init() {
        bindDimensions();
    }

    private void bindDimensions() {

    }

    @Override
    public boolean layoutDependsOn(CoordinatorLayout parent, CircleImageView child, View dependency) {
        return dependency instanceof Toolbar;
    }

    @Override
    public boolean onDependentViewChanged(CoordinatorLayout parent, CircleImageView child, View dependency) {

        if (dependency instanceof Toolbar) {

            if (mToolbar == null) {
                mToolbar = (Toolbar) parent.findViewById(R.id.toolbar);
            }

            maybeInitProperties(parent, child, dependency);


            final int maxScrollDistance = (int) (mStartToolbarPosition); //最大滑动距离为Toolbar的高度
            float expandedPercentageFactor = mToolbar.getY() / maxScrollDistance;//最终 固定位置为 Toolbar 高度，（已经包含了  状态栏高度）
//
            if (expandedPercentageFactor < MIN_AVATAR_PERCENTAGE_SIZE) {
                float heightFactor = (MIN_AVATAR_PERCENTAGE_SIZE - expandedPercentageFactor) / MIN_AVATAR_PERCENTAGE_SIZE; // 高度改变的比例

                float distanceXToSubtract = ((mStartXPosition - mFinalXPosition)
                        * heightFactor) + (child.getHeight() / 2);
                float distanceYToSubtract = ((mStartYPosition - mFinalYPosition)
                        * (1f - expandedPercentageFactor)) + (child.getHeight() / 2);

                float x = mStartXPosition - distanceXToSubtract;
                float y = mStartYPosition - distanceYToSubtract;

                child.setX(x);
                child.setY(y);


                Log.e("TAG", "x:   " + x + "    y:" + y);

                float heightToSubtract = ((mStartHeight - mFinalHeight) * heightFactor);

                CoordinatorLayout.LayoutParams lp = (CoordinatorLayout.LayoutParams) child.getLayoutParams();
                lp.width = (int) (mStartHeight - heightToSubtract);
                lp.height = (int) (mStartHeight - heightToSubtract);
                child.setLayoutParams(lp);
            } else {
                float distanceYToSubtract = ((mStartYPosition - mFinalYPosition)
                        * (1f - expandedPercentageFactor)) + (mStartHeight / 2);


                int x = mStartXPosition - child.getWidth() / 2;
                float y = mStartYPosition - distanceYToSubtract;
                Log.e("TAG", "x:" + x + "y:" + y);
                child.setX(x);
                child.setY(y);

                CoordinatorLayout.LayoutParams lp = (CoordinatorLayout.LayoutParams) child.getLayoutParams();
                lp.width = (int) (mStartHeight);
                lp.height = (int) (mStartHeight);
                child.setLayoutParams(lp);
            }
            return true;
        } else {
            return false;
        }


//        if (dependency instanceof Toolbar) {
//
//
//            if (mToolbar == null) {
//                mToolbar = (Toolbar) parent.findViewById(toolbar);
//            }
//
//            maybeInitProperties(parent, child, dependency);
//
//
//            final int maxScrollDistance = (int) (mStartToolbarPosition); //最大滑动距离为Toolbar的高度
//
//
//            float expandedPercentageFactor = dependency.getY() / maxScrollDistance;//  折叠的比例
//
//
//            if (expandedPercentageFactor < MIN_AVATAR_PERCENTAGE_SIZE) {
//
//                float XFactor = expandedPercentageFactor / MIN_AVATAR_PERCENTAGE_SIZE;
//                Log.e("TAG", "XFactor:" + XFactor);
//
//                float xPosition = new FloatEvaluator().evaluate(XFactor, mFinalXPosition, mStartXPosition);
//
//                Log.e("TAG", "xPosition:" + xPosition);
//
//                float YPosition = new FloatEvaluator().evaluate(expandedPercentageFactor, mFinalYPosition, mStartYPosition);
//
//
//                Log.e("TAG", "YPosition:" + YPosition);
//                child.setX(xPosition);
//                child.setY(YPosition);
//
//                float size = new FloatEvaluator().evaluate(XFactor, mFinalHeight, mStartHeight);
//                Log.e("TAG", "size:" + size);
//
//                CoordinatorLayout.LayoutParams lp = (CoordinatorLayout.LayoutParams) child.getLayoutParams();
//                lp.width = (int) (size);
//                lp.height = (int) (size);
//                child.setLayoutParams(lp);
//            } else {
//                Log.e("TAG", "大于的情况");
//                float YPosition = new FloatEvaluator().evaluate(expandedPercentageFactor, mFinalYPosition, mStartYPosition);
//
//                Log.e("TAG", "YPosition:" + YPosition);
//                child.setX(mStartXPosition);
//                child.setY(YPosition);
//
//                CoordinatorLayout.LayoutParams lp = (CoordinatorLayout.LayoutParams) child.getLayoutParams();
//                lp.width = (int) (mStartHeight);
//                lp.height = (int) (mStartHeight);
//                child.setLayoutParams(lp);
//            }
//            return true;
//        } else {
//            return false;
//        }
    }

    private void maybeInitProperties(CoordinatorLayout parent, CircleImageView child, View dependency) {


        if (mFinalHeight == 0) {
            mFinalHeight = mContext.getResources().getDimensionPixelSize(R.dimen.image_final_width);
        }

        if (mStartYPosition == 0) {
            mStartYPosition = (int) (mToolbar.getY());  //  控件最初的 Y 坐标
        }

        Log.e("TAG", "mStartYPosition:" + mStartYPosition);

        if (mFinalYPosition == 0) {

            mFinalYPosition = (mToolbar.getHeight() / 2);   //最终位置为 toolbar高度一半
        }

        if (mStartHeight == 0) {

            mStartHeight = child.getHeight();  // 开始高度为 120 dp
        }

        if (mStartXPosition == 0) {

            mStartXPosition = (int) (child.getX() + (child.getWidth() / 2)); //开始位置为 头像 中心
        }


        Log.e("TAG", "mStartXPosition:" + mStartXPosition);

        if (mFinalXPosition == 0) {
            //32 dp  结束位置为  Toolbar 左偏移量 + 头像宽度1/2
            mFinalXPosition = mContext.getResources().getDimensionPixelOffset(R.dimen.abc_action_bar_content_inset_material) + mFinalHeight / 2;
        }

        if (mStartToolbarPosition == 0) {

            mStartToolbarPosition = mToolbar.getY();  //toolBar Y坐标
        }


    }

    public int getStatusBarHeight() {
        int result = 0;
        int resourceId = mContext.getResources().getIdentifier("status_bar_height", "dimen", "android");

        if (resourceId > 0) {
            result = mContext.getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }


}
