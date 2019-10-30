package bases;

import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;


/**
 * Created by abdalla-maged on 3/27/18.
 */

public  abstract  class BaseActivity extends AppCompatActivity {

    protected abstract void addFragment(Fragment fragment, String title, String tag);

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
}
