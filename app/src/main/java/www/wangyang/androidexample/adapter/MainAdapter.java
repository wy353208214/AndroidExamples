package www.wangyang.androidexample.adapter;

import android.content.pm.ActivityInfo;
import android.widget.TextView;

import java.util.List;

import www.wangyang.androidexample.R;

/**
 * Created by wangyang on 2016/9/6.
 */
public class MainAdapter extends CommonAdapter<ActivityInfo> {

    public MainAdapter(List<ActivityInfo> items, int layoutId) {
        super(items, layoutId);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        TextView textView = holder.getView(R.id.item_category_tv);
        textView.setText(items.get(position).loadLabel(textView.getContext().getPackageManager()));
    }

}
