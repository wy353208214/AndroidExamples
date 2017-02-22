package www.wangyang.androidexample.service;

import android.app.job.JobParameters;
import android.app.job.JobService;
import android.widget.Toast;

import com.orhanobut.logger.Logger;

public class JobSchedulerService extends JobService {


    @Override
    public boolean onStartJob(JobParameters params) {
        Toast.makeText(this, "start", Toast.LENGTH_SHORT).show();
        Logger.d(JobSchedulerService.class.getCanonicalName(), "start");
        return false;
    }

    @Override
    public boolean onStopJob(JobParameters params) {
        Logger.d(JobSchedulerService.class.getCanonicalName(), "stop");
        Toast.makeText(this, "停止JobScheduler", Toast.LENGTH_SHORT).show();
        return false;
    }

}
