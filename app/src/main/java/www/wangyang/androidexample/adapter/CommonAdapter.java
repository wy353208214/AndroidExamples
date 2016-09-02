package www.wangyang.androidexample.adapter;

import android.content.pm.ActivityInfo;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import www.wangyang.androidexample.R;

/**
 * Created by wangyang on 2016/9/1.
 */
public class CommonAdapter extends RecyclerView.Adapter<CommonAdapter.ViewHolder> {

    private List<ActivityInfo> strings;

    public CommonAdapter(List<ActivityInfo> strings) {
        this.strings = strings;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_category, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.mTextView.setText(position + "、" + strings.get(position).name);
    }

    @Override
    public int getItemCount() {
        return strings.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView mTextView;
        public ViewHolder(View v) {
            super(v);
            mTextView = (TextView) v.findViewById(R.id.item_category_tv);;
        }
    }

}
