package Utils;

import android.app.AppOpsManager;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.support.v4.app.NotificationManagerCompat;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Created by iziss on 29/5/18.
 */
public class NotificationsUtils {

    private static final String CHECK_OP_NO_THROW = "checkOpNoThrow";
    private static final String OP_POST_NOTIFICATION = "OP_POST_NOTIFICATION";

    public static boolean isNotificationEnabledOlderApproach(Context context) {

        AppOpsManager mAppOps = (AppOpsManager) context.getSystemService(Context.APP_OPS_SERVICE);

        ApplicationInfo appInfo = context.getApplicationInfo();

        String pkg = context.getApplicationContext().getPackageName();

        int uid = appInfo.uid;

        Class appOpsClass = null; /* Context.APP_OPS_MANAGER */

        try {

            appOpsClass = Class.forName(AppOpsManager.class.getName());

            Method checkOpNoThrowMethod = appOpsClass.getMethod(CHECK_OP_NO_THROW, Integer.TYPE, Integer.TYPE, String.class);

            Field opPostNotificationValue = appOpsClass.getDeclaredField(OP_POST_NOTIFICATION);
            int value = (int) opPostNotificationValue.get(Integer.class);

            return ((int) checkOpNoThrowMethod.invoke(mAppOps, value, uid, pkg) == AppOpsManager.MODE_ALLOWED);

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return false;
    }


    /***
     * This will return Notifications are Enabled or not
     *
     * @param context
     * @return
     */
    public static boolean isNotificationEnabled(Context context) {

        return NotificationManagerCompat.from(context).areNotificationsEnabled();

    }

}
