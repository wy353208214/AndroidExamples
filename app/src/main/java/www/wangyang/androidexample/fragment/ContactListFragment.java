package www.wangyang.androidexample.fragment;

import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.widget.RecyclerView;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import java.util.List;

import jp.wasabeef.recyclerview.animators.SlideInLeftAnimator;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import www.wangyang.androidexample.R;
import www.wangyang.androidexample.adapter.DividerItemDecoration;

/**
 * Created by wangyang on 2016/9/5.
 */


@EFragment(R.layout.fragment_contact_list)
public class ContactListFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor>{

    @ViewById(R.id.contact_rcv)
    RecyclerView recyclerView;

    public static ContactListFragment getInstance(Bundle args) {
        ContactListFragment contactListFragment = new ContactListFragment();
        contactListFragment.setArguments(args);
        return contactListFragment;
    }

    @AfterViews
    public void afterViews() {
        
        getLoaderManager().initLoader(0, null, this);

        recyclerView.addItemDecoration(new DividerItemDecoration());
        recyclerView.setHasFixedSize(true);
        recyclerView.setItemAnimator(new SlideInLeftAnimator());


        Observable<List<ContactsContract.Contacts>> observable = Observable.create(new Observable.OnSubscribe<List<ContactsContract.Contacts>>() {
            @Override
            public void call(Subscriber<? super List<ContactsContract.Contacts>> subscriber) {

            }
        });
        observable.subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<List<ContactsContract.Contacts>>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(List<ContactsContract.Contacts> contactses) {

            }
        });

    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return null;
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {

    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }
}
