package www.wangyang.androidexample.activity;

import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;
import java.util.List;

import www.wangyang.androidexample.R;
import www.wangyang.androidexample.adapter.CommonFragmentAdapter;
import www.wangyang.androidexample.fragment.ContactListFragment_;


@EActivity(R.layout.activity_bottom_bar)
public class BottomBarActivity extends AppCompatActivity {

    @ViewById(R.id.bottom_navigation_view)
    BottomNavigationView bottomNavigationView;
    @ViewById(R.id.vp1)
    ViewPager viewPager;

    private List<Fragment> fragmentList;


    @AfterViews
    public void afterView() {
        fragmentList = new ArrayList<>();
        fragmentList.add(ContactListFragment_.builder().title("Contact").build());
        fragmentList.add(ContactListFragment_.builder().title("Contact").build());
        fragmentList.add(ContactListFragment_.builder().title("Contact").build());
        fragmentList.add(ContactListFragment_.builder().title("Contact").build());
        fragmentList.add(ContactListFragment_.builder().title("Contact").build());

        CommonFragmentAdapter commonFragmentAdapter = new CommonFragmentAdapter(getSupportFragmentManager(), fragmentList, new ArrayList<String>());
        viewPager.setAdapter(commonFragmentAdapter);
        viewPager.setOffscreenPageLimit(4);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.action_one:
                        viewPager.setCurrentItem(0);
                        break;
                    case R.id.action_two:
                        viewPager.setCurrentItem(1);
                        break;
                    case R.id.action_three:
                        viewPager.setCurrentItem(2);
                        break;
                    case R.id.action_four:
                        viewPager.setCurrentItem(3);
                        break;
                    case R.id.action_five:
                        viewPager.setCurrentItem(4);
                        break;
                }
                return true;
            }
        });

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

}
