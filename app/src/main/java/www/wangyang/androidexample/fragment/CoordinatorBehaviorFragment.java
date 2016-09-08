package www.wangyang.androidexample.fragment;

import android.net.Uri;
import android.support.v4.app.Fragment;

import com.facebook.drawee.view.SimpleDraweeView;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import www.wangyang.androidexample.R;

/**
 * Created by wangyang on 2016/9/7.
 */
@EFragment(R.layout.fragment_coordinator_behavior)
public class CoordinatorBehaviorFragment extends Fragment {

    @ViewById(R.id.girl_sdv)
    SimpleDraweeView draweeView;

    @AfterViews
    public void afterViews() {
        StringBuilder sb = new StringBuilder("res://");
        sb.append(getContext().getPackageName());
        sb.append("/");
        sb.append(R.mipmap.girl);
        Uri uri = Uri.parse(sb.toString());
        draweeView.setImageURI(uri);
    }
}
