package www.wangyang.androidexample.fragment;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.FragmentArg;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.api.BackgroundExecutor;

import java.util.ArrayList;
import java.util.List;

import jp.wasabeef.recyclerview.animators.SlideInLeftAnimator;
import www.wangyang.androidexample.R;
import www.wangyang.androidexample.adapter.ContactAdapter;
import www.wangyang.androidexample.adapter.DividerItemDecoration;
import www.wangyang.androidexample.adapter.OnRecycleItemClickListener;
import www.wangyang.androidexample.model.Contact;

/**
 * Created by wangyang on 2016/9/5.
 */


@EFragment(R.layout.fragment_contact_list)
public class ContactListFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor> {

    @SuppressLint("InlinedApi")
    //自定义查询条件
    private static final String[] PROJECTION = {
            ContactsContract.Contacts._ID,
            // The primary display name
            Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB ?
                    ContactsContract.Data.DISPLAY_NAME_PRIMARY :
                    ContactsContract.Data.DISPLAY_NAME,
            ContactsContract.Contacts.PHOTO_URI
    };


    @ViewById(R.id.contact_rcv)
    RecyclerView recyclerView;
    @ViewById(R.id.loading_progress)
    ProgressBar progressBar;
    @FragmentArg
    String title;

    private Cursor phoneCursor;

    private List<Contact> contactList = new ArrayList<>();
    private ContactAdapter contactAdapter;

    @AfterViews
    public void afterViews() {
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.addItemDecoration(new DividerItemDecoration());
        recyclerView.setHasFixedSize(true);
        recyclerView.setItemAnimator(new SlideInLeftAnimator());
        recyclerView.getItemAnimator().setAddDuration(500);
        recyclerView.getItemAnimator().setRemoveDuration(500);

        contactAdapter = new ContactAdapter(contactList, R.layout.item_category);
        recyclerView.setAdapter(contactAdapter);

        if (title.equals("Contact"))
            getLoaderManager().initLoader(0, null, this);

        recyclerView.addOnItemTouchListener(new OnRecycleItemClickListener(recyclerView) {
            @Override
            public void onItemClick(RecyclerView.ViewHolder viewHolder) {

            }
        });
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        progressBar.setVisibility(View.VISIBLE);
        return new CursorLoader(
                getActivity(),
                ContactsContract.Contacts.CONTENT_URI,
                PROJECTION,
                null,
                null,
                null
        );
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        loadSyncContacts(data);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        resetContacts(0, 0);
    }


    private void resetContacts(int count, int position) {
        int size = contactList.size();
        contactList.clear();
        contactAdapter.notifyItemRangeRemoved(0, size);
    }

    @Background(id = "contact_task")
    void loadSyncContacts(Cursor cursor) {
        if (!cursor.moveToFirst()) {
            cursor.close();
            return;
        }

        List<Contact> contacts = null;
        contacts = new ArrayList<>();
        //先判断cursor是否关闭
        while (!cursor.isClosed() && cursor.moveToNext()) {
            String id = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID));
            String name = cursor.getString(cursor.getColumnIndex(ContactsContract.Data.DISPLAY_NAME));
            phoneCursor = getContext().getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null,
                    ContactsContract.CommonDataKinds.Phone.CONTACT_ID + "=" + id, null, null);
            //先判断cursor是否关闭
            while (!phoneCursor.isClosed() && phoneCursor.moveToNext()) { //取得电话号码(可能存在多个号码)
                int phoneFieldColumnIndex = phoneCursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER);
                String phoneNumber = phoneCursor.getString(phoneFieldColumnIndex);
                Contact contact = new Contact();
                contact.setId(id);
                contact.setName(name);
                contact.setPhoneNumber(phoneNumber);
                contacts.add(contact);
            }
            phoneCursor.close();
        }
        cursor.close();
        loadComplete(contacts);
    }


    @UiThread(id = "loadComplete")
    void loadComplete(List<Contact> contacts) {
        //这里做前置检查，防止fragment被销毁之后引用
        if (progressBar == null)
            return;

        for (int i = 0; i < contacts.size(); i++) {
            contactList.add(contacts.get(i));
            contactAdapter.notifyItemInserted(i);
        }
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        getLoaderManager().destroyLoader(0);
        phoneCursor.close();
        BackgroundExecutor.cancelAll("contact_task", true);
        BackgroundExecutor.cancelAll("loadComplete", true);
    }
}
