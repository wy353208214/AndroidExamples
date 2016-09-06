package www.wangyang.androidexample.adapter;

import android.widget.TextView;

import java.util.List;

import www.wangyang.androidexample.R;
import www.wangyang.androidexample.model.Contact;

/**
 * Created by wangyang on 2016/9/6.
 */
public class ContactAdapter extends CommonAdapter<Contact> {

    public ContactAdapter(List<Contact> items, int layoutId) {
        super(items, layoutId);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Contact contact = getItem(position);
        StringBuilder sb = new StringBuilder();
        sb.append(contact.getName())
                .append("：")
                .append(contact.getPhoneNumber());
        TextView textView = holder.getView(R.id.item_category_tv);
        textView.setText(sb.toString());
    }

}
