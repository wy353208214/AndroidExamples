package www.wangyang.androidexample.activity;

import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.view.View;
import android.widget.Toast;

import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.SystemService;

import www.wangyang.androidexample.R;
import www.wangyang.androidexample.service.JobSchedulerService;

/**
 * Created by 王洋 on 2017/2/22.
 */
@EActivity(R.layout.acitivity_jobscheduler)
public class JobSchedulerActivity extends BaseActivity {

    @SystemService
    JobScheduler jobScheduler;

    @Click(R.id.start_job_scheduler)
    public void jobScheduler(View view) {
        JobInfo.Builder builder = new JobInfo.Builder(1, new ComponentName(getApplicationContext(), JobSchedulerService.class));
        builder.setOverrideDeadline(1000);
        JobInfo jobInfo = builder.build();
        if (jobScheduler.schedule(jobInfo) == JobScheduler.RESULT_FAILURE) {
            Toast.makeText(this, "失败了", Toast.LENGTH_SHORT).show();
        }
    }

    @Click(R.id.stop_job_scheduler)
    public void stopScheduler(View view) {
        jobScheduler.cancel(1);
    }

}
