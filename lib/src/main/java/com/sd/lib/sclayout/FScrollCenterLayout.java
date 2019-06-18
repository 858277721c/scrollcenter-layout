package com.sd.lib.sclayout;

import android.content.Context;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.FrameLayout;

import java.lang.ref.WeakReference;

public class FScrollCenterLayout extends FrameLayout
{
    private View mChild;
    private WeakReference<View> mTarget;

    private final int[] mLocation = new int[2];
    private int mDelta;
    private Integer mChildOffset;

    private FScroller mScroller;

    public FScrollCenterLayout(Context context, AttributeSet attrs)
    {
        super(context, attrs);
    }

    private View getTarget()
    {
        return mTarget == null ? null : mTarget.get();
    }

    /**
     * 让view移动到中心
     *
     * @param view
     */
    public void focusTo(View view)
    {
        final View old = getTarget();
        if (old != view)
        {
            mTarget = view == null ? null : new WeakReference<>(view);
            scrollIfNeed();
        }
    }

    private void scrollIfNeed()
    {
        if (mChild == null)
            return;

        final View target = getTarget();
        if (target == null)
            return;

        target.getLocationOnScreen(mLocation);
        final int targetX = mLocation[0] + target.getWidth() / 2;

        getLocationOnScreen(mLocation);
        final int thisX = mLocation[0] + getWidth() / 2;

        setDelta(thisX - targetX);
    }

    private void setDelta(int delta)
    {
        if (mDelta != delta)
        {
            mDelta = delta;

            final int start = mChild.getLeft();
            mChildOffset = start + delta;

            if (getScroller().isFinished())
            {
                getScroller().scrollToX(start, mChildOffset, -1);
                ViewCompat.postInvalidateOnAnimation(this);
            }
        }
    }

    private FScroller getScroller()
    {
        if (mScroller == null)
        {
            mScroller = new FScroller(getContext());
            mScroller.setCallback(new FScroller.Callback()
            {
                @Override
                public void onScrollerStart()
                {
                }

                @Override
                public void onScrollerCompute(int lastX, int lastY, int currX, int currY)
                {
                    if (mChild == null || getTarget() == null)
                    {
                        getScroller().abortAnimation();
                        return;
                    }

                    mChild.offsetLeftAndRight(currX - lastX);
                }

                @Override
                public void onScrollerFinish(boolean isAbort)
                {
                    if (mChildOffset != null && mChild != null)
                    {
                        if (mChildOffset != mChild.getLeft())
                        {
                            getScroller().scrollToX(mChild.getLeft(), mChildOffset, -1);
                            ViewCompat.postInvalidateOnAnimation(FScrollCenterLayout.this);
                        }
                    }
                }
            });
        }
        return mScroller;
    }

    @Override
    public void computeScroll()
    {
        super.computeScroll();
        if (getScroller().computeScrollOffset())
            ViewCompat.postInvalidateOnAnimation(this);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom)
    {
        super.onLayout(changed, left, top, right, bottom);
        if (mChildOffset != null && mChild != null)
            mChild.layout(mChildOffset, mChild.getTop(), mChildOffset + mChild.getMeasuredWidth(), mChild.getBottom());
    }

    @Override
    public void onViewAdded(View child)
    {
        super.onViewAdded(child);
        if (getChildCount() > 1)
            throw new RuntimeException("Can not add more child");
        mChild = child;
    }

    @Override
    public void onViewRemoved(View child)
    {
        super.onViewRemoved(child);
        mChild = null;
        mChildOffset = null;
        getScroller().abortAnimation();
    }

    @Override
    protected void onAttachedToWindow()
    {
        super.onAttachedToWindow();
        final ViewTreeObserver observer = getViewTreeObserver();
        if (observer.isAlive())
            observer.addOnPreDrawListener(mOnPreDrawListener);
    }

    private final ViewTreeObserver.OnPreDrawListener mOnPreDrawListener = new ViewTreeObserver.OnPreDrawListener()
    {
        @Override
        public boolean onPreDraw()
        {
            scrollIfNeed();
            return true;
        }
    };

    @Override
    protected void onDetachedFromWindow()
    {
        super.onDetachedFromWindow();
        final ViewTreeObserver observer = getViewTreeObserver();
        if (observer.isAlive())
            observer.removeOnPreDrawListener(mOnPreDrawListener);
    }
}
