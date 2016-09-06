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
import android.support.v7.widget.RecyclerView;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.FragmentArg;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;
import java.util.List;

import jp.wasabeef.recyclerview.animators.SlideInLeftAnimator;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import www.wangyang.androidexample.R;
import www.wangyang.androidexample.adapter.ContactAdapter;
import www.wangyang.androidexample.adapter.DividerItemDecoration;
import www.wangyang.androidexample.model.Contact;

/**
 * Created by wangyang on 2016/9/5.
 */


@EFragment(R.layout.fragment_contact_list)
public class ContactListFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor>{

    @ViewById(R.id.contact_rcv)
    RecyclerView recyclerView;
    @FragmentArg
    String title;

    private List<Contact> contactList = new ArrayList<>();
    private ContactAdapter contactAdapter;

    @SuppressLint("InlinedApi")
    private static final String[] PROJECTION = {
            ContactsContract.Contacts._ID,
            ContactsContract.Contacts.LOOKUP_KEY,
            Build.VERSION.SDK_INT
                    >= Build.VERSION_CODES.HONEYCOMB ?
                    ContactsContract.Contacts.DISPLAY_NAME_PRIMARY :
                    ContactsContract.Contacts.DISPLAY_NAME
    };

    @AfterViews
    public void afterViews() {
        recyclerView.addItemDecoration(new DividerItemDecoration());
        recyclerView.setHasFixedSize(true);
        recyclerView.setItemAnimator(new SlideInLeftAnimator());
        contactAdapter = new ContactAdapter(contactList, R.layout.item_category);
        recyclerView.setAdapter(contactAdapter);
        getLoaderManager().initLoader(0, null, this);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
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
        final Cursor cursor = data;

        //这里采用rxjava绑定数据
        Observable.create(new Observable.OnSubscribe<List<Contact>>() {
            @Override
            public void call(Subscriber<? super List<Contact>> subscriber) {
                subscriber.onStart();
                if (!cursor.moveToFirst()) {
                    subscriber.onCompleted();
                    return;
                }

                List<Contact> contacts = new ArrayList<>();
                while (cursor.moveToNext()) {
                    String id = cursor.getString(cursor.getColumnIndex(ContactsContract.Data.CONTACT_ID));
                    String name = cursor.getString(cursor.getColumnIndex(ContactsContract.Data.DISPLAY_NAME));
                    Cursor phone =  getContext().getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null,
                            ContactsContract.CommonDataKinds.Phone.CONTACT_ID + "=" + id, null, null);
                    while(phone.moveToNext()){ //取得电话号码(可能存在多个号码)
                        int phoneFieldColumnIndex = phone.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER);
                        String phoneNumber = phone.getString(phoneFieldColumnIndex);
                        Contact contact = new Contact();
                        contact.setId(id);
                        contact.setName(name);
                        contact.setPhoneNumber(phoneNumber);
                        contacts.add(contact);
                    }
                }
                subscriber.onNext(contacts);
                subscriber.onCompleted();
                cursor.close();
            }
        }).subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<List<Contact>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(List<Contact> contacts) {
                        for (int i = 0; i < contacts.size(); i++) {
                            contactList.add(contacts.get(i));
                            contactAdapter.notifyItemInserted(i);
                        }
                    }
                });
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        contactList.clear();

    }

}
