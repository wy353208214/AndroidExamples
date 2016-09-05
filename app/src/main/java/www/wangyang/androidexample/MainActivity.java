package www.wangyang.androidexample;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import jp.wasabeef.recyclerview.animators.SlideInLeftAnimator;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import www.wangyang.androidexample.adapter.CommonAdapter;
import www.wangyang.androidexample.adapter.DividerItemDecoration;
import www.wangyang.androidexample.adapter.OnRecycleItemClickListener;

/**
 *                             _ooOoo_
 *                            o8888888o
 *                            88" . "88
 *                            (| -_- |)
 *                            O\  =  /O
 *                         ____/`---'\____
 *                       .'  \\|     |//  `.
 *                      /  \\|||  :  |||//  \
 *                     /  _||||| -:- |||||-  \
 *                     |   | \\\  -  /// |   |
 *                     | \_|  ''\---/''  |   |
 *                     \  .-\__  `-`  ___/-. /
 *                   ___`. .'  /--.--\  `. . __
 *                ."" '<  `.___\_<|>_/___.'  >'"".
 *               | | :  `- \`.;`\ _ /`;.`/ - ` : | |
 *               \  \ `-.   \_ __\ /__ _/   .-` /  /
 *          ======`-.____`-.___\_____/___.-`____.-'======
 *                             `=---='
 *          ^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^
 *                     佛祖保佑        永无BUG
 *            佛曰:
 *                   写字楼里写字间，写字间里程序员；
 *                   程序人员写程序，又拿程序换酒钱。
 *                   酒醒只在网上坐，酒醉还来网下眠；
 *                   酒醉酒醒日复日，网上网下年复年。
 *                   但愿老死电脑间，不愿鞠躬老板前；
 *                   奔驰宝马贵者趣，公交自行程序员。
 *                   别人笑我忒疯癫，我笑自己命太贱；
 *                   不见满街漂亮妹，哪个归得程序员？
*/

@EActivity(R.layout.activity_main)
public class MainActivity extends AppCompatActivity {

    @ViewById(R.id.main_rcv)
    RecyclerView recyclerView;
    CommonAdapter commonAdapter;
    private List<ActivityInfo> categoryList = new ArrayList<>();

    //Loading Progress
    private AlertDialog alertDialog;

    @AfterViews
    public void afterViews() {

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.addItemDecoration(new DividerItemDecoration());

        //设置动画，这里采用的是wasabeef大神的recyclerview-animators动画库
        recyclerView.setItemAnimator(new SlideInLeftAnimator());
        recyclerView.getItemAnimator().setAddDuration(1000);
        recyclerView.getItemAnimator().setRemoveDuration(1000);

        commonAdapter = new CommonAdapter(categoryList);
        recyclerView.setAdapter(commonAdapter);

        recyclerView.addOnItemTouchListener(new OnRecycleItemClickListener(recyclerView) {
            @Override
            public void onItemClick(RecyclerView.ViewHolder viewHolder) {
                int position = viewHolder.getLayoutPosition();
                ActivityInfo activityInfo = categoryList.get(position);
                Intent intent = new Intent();
                intent.setClassName(getApplicationContext(), activityInfo.name);
                startActivity(intent);
            }
        });

        //显示加载的进度框，Progressbar采用jpardogo大神的googleprogressbar库
        alertDialog = new AlertDialog.Builder(MainActivity.this)
                .setView(getLayoutInflater().inflate(R.layout.dialog_loading, null, false))
                .setCancelable(false)
                .create();
        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        alertDialog.show();

        //异步加载数据，RxJava&RxAndroid
        create().subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber());

    }


    //创建被观察者
    private Observable<List<ActivityInfo>> create() {
        return Observable.create(new Observable.OnSubscribe<List<ActivityInfo>>() {
            @Override
            public void call(Subscriber<? super List<ActivityInfo>> subscriber) {
                subscriber.onStart();
                try {
                    Thread.sleep(2000);
                    ActivityInfo[] activityInfos = getPackageManager()
                                                    .getPackageInfo(getApplicationContext().getPackageName(), PackageManager.GET_ACTIVITIES)
                                                    .activities;

                    List<ActivityInfo> activityInfoList = new ArrayList<>();
                    for (ActivityInfo activityInfo : activityInfos) {
                        if (activityInfo.name.equals(MainActivity_.class.getName()))
                            continue;
                        activityInfoList.add(activityInfo);
                    }
                    subscriber.onNext(activityInfoList);
                } catch (PackageManager.NameNotFoundException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                subscriber.onCompleted();
            }
        });
    }

    //创建观察者
    private Subscriber<List<ActivityInfo>> subscriber() {
        Subscriber<List<ActivityInfo>> activityInfoSubscriber = new Subscriber<List<ActivityInfo>>() {

            @Override
            public void onCompleted() {
                alertDialog.cancel();
            }

            @Override
            public void onError(Throwable e) {
                alertDialog.cancel();
            }

            @Override
            public void onNext(List<ActivityInfo> activityInfos) {
                for (int i = 0; i < activityInfos.size(); i++) {
                    categoryList.add(activityInfos.get(i));
                    commonAdapter.notifyItemInserted(i);
                }
            }
        };
        return activityInfoSubscriber;
    }

}
