package com.xxh.coordinatorlayoutdemo;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.widget.Toolbar;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import de.hdodenhof.circleimageview.CircleImageView;

@SuppressWarnings("unused")
public class AvatarImageBehavior extends CoordinatorLayout.Behavior<CircleImageView> {

    private final static float MIN_AVATAR_PERCENTAGE_SIZE = 0.3f;
    private final static int EXTRA_FINAL_AVATAR_PADDING = 80;

    private final static String TAG = "behavior";
    private Context mContext;


    private float mCustomFinalYPosition;   //2dp
    private float mCustomStartXPosition;   //2dp
    private float mCustomStartToolbarPosition;  //2dp
    private float mCustomStartHeight;   //2dp
    private float mCustomFinalHeight;    //32dp

    private float mAvatarMaxSize;    //头像最大   size
    private float mFinalLeftAvatarPadding;  //最终 头像的 padding   16dp
    private float mStartPosition;      //开始的位置
    private int mStartXPosition;       //开始的X坐标
    private float mStartToolbarPosition;  //开始ToolBar的位置
    private int mStartYPosition;      //开始Y坐标
    private int mFinalYPosition;     //最终Y坐标
    private int mStartHeight;         //开始的高度
    private int mFinalXPosition;     //最终X 坐标
    private float mChangeBehaviorPoint;   // 切换动画的点

    public AvatarImageBehavior(Context context, AttributeSet attrs) {
        mContext = context;

        if (attrs != null) {
            TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.AvatarImageBehavior);
            mCustomFinalYPosition = a.getDimension(R.styleable.AvatarImageBehavior_finalYPosition, 0);
            mCustomStartXPosition = a.getDimension(R.styleable.AvatarImageBehavior_startXPosition, 0);
            mCustomStartToolbarPosition = a.getDimension(R.styleable.AvatarImageBehavior_startToolbarPosition, 0);
            mCustomStartHeight = a.getDimension(R.styleable.AvatarImageBehavior_startHeight, 0);
            mCustomFinalHeight = a.getDimension(R.styleable.AvatarImageBehavior_finalHeight, 0);

            a.recycle();
        }

        init();

        mFinalLeftAvatarPadding = context.getResources().getDimension(
                R.dimen.spacing_normal);

        Log.e("TAG", "mCustomFinalYPosition：" + mCustomFinalYPosition);
        Log.e("TAG", "mCustomStartXPosition：" + mCustomStartXPosition);
        Log.e("TAG", "mCustomStartToolbarPosition：" + mCustomStartToolbarPosition);
        Log.e("TAG", "mCustomStartHeight：" + mCustomStartHeight);
        Log.e("TAG", "mCustomFinalHeight：" + mCustomFinalHeight);
        Log.e("TAG", "mFinalLeftAvatarPadding：" + mFinalLeftAvatarPadding);


    }

    private void init() {
        bindDimensions();
    }

    private void bindDimensions() {
        mAvatarMaxSize = mContext.getResources().getDimension(R.dimen.image_width);
    }

    @Override
    public boolean layoutDependsOn(CoordinatorLayout parent, CircleImageView child, View dependency) {
        return dependency instanceof Toolbar;
    }

    @Override
    public boolean onDependentViewChanged(CoordinatorLayout parent, CircleImageView child, View dependency) {
        maybeInitProperties(child, dependency);


//        mCustomFinalYPosition：4.0
//         mCustomStartXPosition：4.0
//         mCustomStartToolbarPosition：4.0
//        mCustomStartHeight：4.0
//         mCustomFinalHeight：64.0
//        mFinalLeftAvatarPadding：32.0
//         mStartYPosition：344
//         mFinalYPosition：56
//        mStartHeight：240
//         mStartXPosition：384
//       mFinalXPosition：64
//         mStartToolbarPosition：344.0
//         mChangeBehaviorPoint：0.30555555

        final int maxScrollDistance = (int) (mStartToolbarPosition); //最大滑动距离为Toolbar的高度
        float expandedPercentageFactor = dependency.getY() / maxScrollDistance;//最终 固定位置为 Toolbar 高度，（已经包含了  状态栏高度）

        if (expandedPercentageFactor < mChangeBehaviorPoint) {
            float heightFactor = (mChangeBehaviorPoint - expandedPercentageFactor) / mChangeBehaviorPoint; // 改变比例

            float distanceXToSubtract = ((mStartXPosition - mFinalXPosition)
                    * heightFactor) + (child.getHeight() / 2);
            float distanceYToSubtract = ((mStartYPosition - mFinalYPosition)
                    * (1f - expandedPercentageFactor)) + (child.getHeight() / 2);

            child.setX(mStartXPosition - distanceXToSubtract);
            child.setY(mStartYPosition - distanceYToSubtract);

            float heightToSubtract = ((mStartHeight - mCustomFinalHeight) * heightFactor);

            CoordinatorLayout.LayoutParams lp = (CoordinatorLayout.LayoutParams) child.getLayoutParams();
            lp.width = (int) (mStartHeight - heightToSubtract);
            lp.height = (int) (mStartHeight - heightToSubtract);
            child.setLayoutParams(lp);
        } else {
            float distanceYToSubtract = ((mStartYPosition - mFinalYPosition)
                    * (1f - expandedPercentageFactor)) + (mStartHeight / 2);

            child.setX(mStartXPosition - child.getWidth() / 2);
            child.setY(mStartYPosition - distanceYToSubtract);

            CoordinatorLayout.LayoutParams lp = (CoordinatorLayout.LayoutParams) child.getLayoutParams();
            lp.width = (int) (mStartHeight);
            lp.height = (int) (mStartHeight);
            child.setLayoutParams(lp);
        }
        return true;
    }

    private void maybeInitProperties(CircleImageView child, View dependency) {
        if (mStartYPosition == 0)
            mStartYPosition = (int) (dependency.getY());  //toolbar   的位置

        if (mFinalYPosition == 0)
            mFinalYPosition = (dependency.getHeight() / 2);   //最终位置为 toolbar高度一半

        if (mStartHeight == 0)
            mStartHeight = child.getHeight();  // 开始高度为 120 dp

        if (mStartXPosition == 0)
            mStartXPosition = (int) (child.getX() + (child.getWidth() / 2)); //开始位置为 头像 中心

        if (mFinalXPosition == 0)   //32 dp  结束位置为  Toolbar 左偏移量 + 头像宽度1/2
            mFinalXPosition = mContext.getResources().getDimensionPixelOffset(R.dimen.abc_action_bar_content_inset_material) + ((int) mCustomFinalHeight / 2);

        if (mStartToolbarPosition == 0)
            mStartToolbarPosition = dependency.getY();  //toolBar Y坐标

        if (mChangeBehaviorPoint == 0) {
            mChangeBehaviorPoint = (child.getHeight() - mCustomFinalHeight) / (2f * (mStartYPosition - mFinalYPosition));
        }


        Log.e("TAG", "mStartYPosition：" + mStartYPosition);
        Log.e("TAG", "mFinalYPosition：" + mFinalYPosition);
        Log.e("TAG", "mStartHeight：" + mStartHeight);
        Log.e("TAG", "mStartXPosition：" + mStartXPosition);
        Log.e("TAG", "mFinalXPosition：" + mFinalXPosition);
        Log.e("TAG", "mStartToolbarPosition：" + mStartToolbarPosition);
        Log.e("TAG", "mChangeBehaviorPoint：" + mChangeBehaviorPoint);
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
