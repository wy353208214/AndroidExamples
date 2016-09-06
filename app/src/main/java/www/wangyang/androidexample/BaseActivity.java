package www.wangyang.androidexample;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import java.util.ArrayList;

import rebus.permissionutils.AskagainCallback;
import rebus.permissionutils.FullCallback;
import rebus.permissionutils.PermissionEnum;
import rebus.permissionutils.PermissionManager;
import rebus.permissionutils.PermissionUtils;

/**
 * Created by wangyang on 2016/9/6.
 */
public class BaseActivity extends AppCompatActivity {

    private static PermissionEnum permissionArrays[] = {
            PermissionEnum.READ_CONTACTS
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        checkPermission();
    }

    //6.0以上检查权限
    private void checkPermission() {
        ArrayList<PermissionEnum> enumArrayList = new ArrayList<>();
        for (PermissionEnum permissionEnum : permissionArrays) {
            if (PermissionUtils.isGranted(this, permissionEnum))
                continue;
            enumArrayList.add(permissionEnum);
        }
        if (!enumArrayList.isEmpty())
            askPermissions(enumArrayList);
    }

    private void askPermissions(ArrayList<PermissionEnum> permissionList) {
        if (permissionList != null && !permissionList.isEmpty()) {
            PermissionManager.with(this)
                    .permissions(permissionList)
                    .askagain(true)
                    .askagainCallback(new AskagainCallback() {
                        @Override
                        public void showRequestPermission(UserResponse response) {

                        }
                    })
                    .callback(new FullCallback() {
                        @Override
                        public void result(ArrayList<PermissionEnum> permissionsGranted, ArrayList<PermissionEnum> permissionsDenied, ArrayList<PermissionEnum> permissionsDeniedForever, ArrayList<PermissionEnum> permissionsAsked) {

                        }
                    })
                    .ask();
        }
    }

}

