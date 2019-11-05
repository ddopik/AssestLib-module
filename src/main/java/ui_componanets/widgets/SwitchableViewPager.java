package ui_componanets.widgets;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;

import androidx.viewpager.widget.ViewPager;

/**
 * /**
 * Created by abdalla_maged on 10/18/2018.
 */
// This is custom ViewPager element which disables the swipe functionality between pages.

public class SwitchableViewPager extends ViewPager {

    private boolean pagingEnabled = true;

    public SwitchableViewPager(Context context) {
        super(context);
    }

    public SwitchableViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {
        // Never allow swiping to switch between pages
        return pagingEnabled && super.onInterceptTouchEvent(event);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        // Never allow swiping to switch between pages
        return pagingEnabled && super.onTouchEvent(event);
    }

    public void setPagingEnabled(boolean pagingEnabled) {
        this.pagingEnabled = pagingEnabled;
    }
}