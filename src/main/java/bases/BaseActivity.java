package bases;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by abdalla-maged on 3/27/18.
 */

public abstract class BaseActivity extends AppCompatActivity {

    protected abstract void addFragment(Fragment fragment, String title, String tag);

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(String name, Context context, AttributeSet attrs) {

        initPresenter();
        return super.onCreateView(name, context, attrs);
    }

    protected abstract void initPresenter();

    protected abstract void initViews();
}
