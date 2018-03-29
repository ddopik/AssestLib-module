package bases;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

/**
 * Created by abdalla-maged on 3/27/18.
 */

public abstract class BaseFragment extends Fragment {


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        initViews();
        initPresenter();
    }

    protected abstract void initPresenter();
    protected abstract void initViews();
}
