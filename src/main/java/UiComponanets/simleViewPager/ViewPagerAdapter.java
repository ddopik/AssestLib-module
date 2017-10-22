package UiComponanets.simleViewPager;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by ddopik on 9/17/2017.
 */

public class ViewPagerAdapter  extends FragmentPagerAdapter {

    private final List mFragmentListT = new ArrayList();
    private final List<String> mFragmentTitleList = new ArrayList();

    public ViewPagerAdapter(FragmentManager manager) {
        super(manager);
    }
    @Override
    public  Fragment getItem(int position) {
        return (Fragment) mFragmentListT.get(position);
    }

    @Override
    public int getCount() {
        return mFragmentListT.size();
    }

    public <T> void addFragment(T fragment,String title) {

        mFragmentListT.add(fragment);
        mFragmentTitleList.add(title);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mFragmentTitleList.get(position);
    }
}


