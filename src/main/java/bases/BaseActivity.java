package bases;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by abdalla-maged on 3/27/18.
 */

public  abstract  class BaseActivity extends AppCompatActivity {

    protected abstract void addFragment(Fragment fragment,String title,String tag);

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
}
