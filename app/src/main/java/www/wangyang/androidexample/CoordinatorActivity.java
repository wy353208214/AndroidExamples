package www.wangyang.androidexample;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;
import java.util.List;

import www.wangyang.androidexample.adapter.CommonFragmentAdapter;
import www.wangyang.androidexample.fragment.ContactListFragment;


@EActivity(R.layout.activity_coordinator_demo)
public class CoordinatorActivity extends AppCompatActivity {

    @ViewById(R.id.toolbar)
    Toolbar toolbar;
    @ViewById(R.id.tabs)
    TabLayout tabLayout;
    @ViewById(R.id.vp)
    ViewPager viewPager;

    private String[] titles = {"Tab#1","Tab#3","Tab#2"};
    private List<Fragment> fragmentList;
    private List<String> titleList;

    @AfterViews
    public void afterViews() {
        toolbar.setTitle(CoordinatorActivity.class.getCanonicalName());
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
        for (String title : titles) {
            titleList.add(title);
            fragmentList.add(new ContactListFragment());
        }
        CommonFragmentAdapter commonFragmentAdapter = new CommonFragmentAdapter(getSupportFragmentManager(), fragmentList, titleList);
        viewPager.setAdapter(commonFragmentAdapter);
        tabLayout.setupWithViewPager(viewPager);
    }

}
