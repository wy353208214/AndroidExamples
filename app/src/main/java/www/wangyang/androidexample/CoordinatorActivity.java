package www.wangyang.androidexample;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.View;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;
import java.util.List;

import www.wangyang.androidexample.adapter.CommonFragmentAdapter;
import www.wangyang.androidexample.fragment.ContactListFragment_;


@EActivity(R.layout.activity_coordinator_demo)
public class CoordinatorActivity extends BaseActivity {

    @ViewById(R.id.toolbar)
    Toolbar toolbar;
    @ViewById(R.id.tabs)
    TabLayout tabLayout;
    @ViewById(R.id.vp)
    ViewPager viewPager;

    private String[] titles = {"Tab#1","Tab#2","Tab#3"};
    private List<Fragment> fragmentList;
    private List<String> titleList;

    @AfterViews
    public void afterViews() {
        toolbar.setTitle("测试");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        fragmentList = new ArrayList<>();
        titleList = new ArrayList<>();
        for (int i = 0; i < titles.length; i++) {
            titleList.add(titles[i]);
            fragmentList.add(ContactListFragment_.builder().title(titles[i]).build());
        }
        CommonFragmentAdapter commonFragmentAdapter = new CommonFragmentAdapter(getSupportFragmentManager(), fragmentList, titleList);
        viewPager.setAdapter(commonFragmentAdapter);
        tabLayout.setupWithViewPager(viewPager);
    }

}
