package UiComponanets.simleViewPager;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import java.util.List;

import butterknife.ButterKnife;


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

        ButterKnife.bind(this);
        toolbar = getToolbar();
        setSupportActionBar(toolbar);
//        getSupportActionBar().setDisplayHomeAsUpEnabled(displayHomeAsUp());

        viewPager = getViewPager();
        tabLayout = getTabLayout();
        tabLayout.setupWithViewPager(viewPager);

        viewPager.setAdapter(setViewPagerAdapterFragment(getviewPagerAdapter(), getFragments(), getFragmentsTitles()));
    }


}

