package www.wangyang.androidexample.activity;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import www.wangyang.androidexample.R;
import www.wangyang.androidexample.adapter.CommonFragmentAdapter;
import www.wangyang.androidexample.fragment.ContactListFragment_;
import www.wangyang.androidexample.fragment.CoordinatorBehaviorFragment_;
import www.wangyang.androidexample.fragment.ParallaxFragment_;


@EActivity(R.layout.activity_coordinator_demo)
public class CoordinatorActivity extends BaseActivity {

    @ViewById(R.id.toolbar)
    Toolbar toolbar;
    @ViewById(R.id.tabs)
    TabLayout tabLayout;
    @ViewById(R.id.vp)
    ViewPager viewPager;

    private String[] titles = {"Contact", "Behavior","Parallax"};
    private List<Fragment> fragmentList;

    @AfterViews
    public void afterViews() {
        toolbar.setTitle("CoordinatorLayout详解");
        setSupportActionBar(toolbar);
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                if (item.getItemId() == R.id.titlebar_search) {
//                    BottomBarActivity_.intent(getApplicationContext()).start();
                    startActivity(new Intent(CoordinatorActivity.this, BottomBarActivity_.class));
                }
                return false;
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        fragmentList = new ArrayList<>();
        fragmentList.add(ContactListFragment_.builder().title(titles[0]).build());
        fragmentList.add(CoordinatorBehaviorFragment_.builder().build());
        fragmentList.add(ParallaxFragment_.builder().build());

        CommonFragmentAdapter commonFragmentAdapter = new CommonFragmentAdapter(getSupportFragmentManager(), fragmentList, Arrays.asList(titles));
        viewPager.setAdapter(commonFragmentAdapter);
        viewPager.setOffscreenPageLimit(5);
        tabLayout.setupWithViewPager(viewPager);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

}
