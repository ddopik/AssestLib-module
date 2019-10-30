package ui_componanets.simleViewPager;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;
import com.google.android.material.tabs.TabLayout;

import java.util.List;




/**
 * Created by ddopik on 9/17/2017.
 */

public abstract class ViewPagerActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;

    public abstract Toolbar getToolbar();

    public abstract TabLayout getTabLayout();

    public abstract ViewPager getViewPager();

    public abstract int getLayout();

    public abstract boolean displayHomeAsUp();

    public abstract ViewPagerAdapter getviewPagerAdapter();

    public abstract List<Fragment> getFragments();

    public abstract String[] getFragmentsTitles();



    private <T> ViewPagerAdapter setViewPagerAdapterFragment(ViewPagerAdapter adapter, List<T> fragments, String[] fragmentTitles) {
        int i = 0;
        for (T fragment : fragments) {
            adapter.addFragment(fragment, fragmentTitles[i]);
            i++;
        }
        return adapter;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayout());


        toolbar = getToolbar();
        setSupportActionBar(toolbar);
//        getSupportActionBar().setDisplayHomeAsUpEnabled(displayHomeAsUp());

        viewPager = getViewPager();
        tabLayout = getTabLayout();
        tabLayout.setupWithViewPager(viewPager);

        viewPager.setAdapter(setViewPagerAdapterFragment(getviewPagerAdapter(), getFragments(), getFragmentsTitles()));
    }


}

