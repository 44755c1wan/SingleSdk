/*
 * Copyright © Zhenjie Yan
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.permission;

import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.net.Uri;
import android.os.Build;
import android.text.TextUtils;

import com.permission.checker.DoubleChecker;
import com.permission.checker.PermissionChecker;
import com.permission.option.Option;
import com.permission.source.ActivitySource;
import com.permission.source.ContextSource;
import com.permission.source.Source;

import java.io.File;
import java.util.List;

/**
 * Created by Zhenjie Yan on 2016/9/9.
 */
public class AndPermission {

    private static final String ACTION_BRIDGE_SUFFIX = ".andpermission.bridge";

    public static String bridgeAction(Context context, String suffix) {
        return context.getPackageName() + ACTION_BRIDGE_SUFFIX + (TextUtils.isEmpty(suffix) ? "" : "." + suffix);
    }

    /**
     * With context.
     *
     * @param context {@link Context}.
     * @return {@link Option}.
     */
    public static Option with(Context context) {
        return new Boot(getContextSource(context));
    }

    /**
     * With activity.
     *
     * @param activity {@link Activity}.
     * @return {@link Option}.
     */
    public static Option with(Activity activity) {
        return new Boot(new ActivitySource(activity));
    }

    /**
     * Some privileges permanently disabled, may need to set up in the execute.
     *
     * @param context           {@link Context}.
     * @param deniedPermissions one or more permissions.
     * @return true, other wise is false.
     */
    public static boolean hasAlwaysDeniedPermission(Context context, List<String> deniedPermissions) {
        return hasAlwaysDeniedPermission(getContextSource(context), deniedPermissions);
    }

    /**
     * Some privileges permanently disabled, may need to set up in the execute.
     *
     * @param activity          {@link Activity}.
     * @param deniedPermissions one or more permissions.
     * @return true, other wise is false.
     */
    public static boolean hasAlwaysDeniedPermission(Activity activity, List<String> deniedPermissions) {
        return hasAlwaysDeniedPermission(new ActivitySource(activity), deniedPermissions);
    }

    /**
     * Has always been denied permission.
     */
    private static boolean hasAlwaysDeniedPermission(Source source, List<String> deniedPermissions) {
        for (String permission : deniedPermissions) {
            if (!source.isShowRationalePermission(permission)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Some privileges permanently disabled, may need to set up in the execute.
     *
     * @param context           {@link Context}.
     * @param deniedPermissions one or more permissions.
     * @return true, other wise is false.
     */
    public static boolean hasAlwaysDeniedPermission(Context context, String... deniedPermissions) {
        return hasAlwaysDeniedPermission(getContextSource(context), deniedPermissions);
    }


    /**
     * Some privileges permanently disabled, may need to set up in the execute.
     *
     * @param activity          {@link Activity}.
     * @param deniedPermissions one or more permissions.
     * @return true, other wise is false.
     */
    public static boolean hasAlwaysDeniedPermission(Activity activity, String... deniedPermissions) {
        return hasAlwaysDeniedPermission(new ActivitySource(activity), deniedPermissions);
    }

    /**
     * Has always been denied permission.
     */
    private static boolean hasAlwaysDeniedPermission(Source source, String... deniedPermissions) {
        for (String permission : deniedPermissions) {
            if (!source.isShowRationalePermission(permission)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Classic permission checker.
     */
    private static final PermissionChecker PERMISSION_CHECKER = new DoubleChecker();

    /**
     * Judgment already has the target permission.
     *
     * @param context     {@link Context}.
     * @param permissions one or more permissions.
     * @return true, other wise is false.
     */
    public static boolean hasPermissions(Context context, String... permissions) {
        return PERMISSION_CHECKER.hasPermission(context, permissions);
    }

    /**
     * Judgment already has the target permission.
     *
     * @param activity    {@link Activity}.
     * @param permissions one or more permissions.
     * @return true, other wise is false.
     */
    public static boolean hasPermissions(Activity activity, String... permissions) {
        return PERMISSION_CHECKER.hasPermission(activity, permissions);
    }

    /**
     * Judgment already has the target permission.
     *
     * @param context     {@link Context}.
     * @param permissions one or more permission groups.
     * @return true, other wise is false.
     */
    public static boolean hasPermissions(Context context, String[]... permissions) {
        for (String[] permission : permissions) {
            boolean hasPermission = PERMISSION_CHECKER.hasPermission(context, permission);
            if (!hasPermission) return false;
        }
        return true;
    }

    /**
     * Judgment already has the target permission.
     *
     * @param activity    {@link Activity}.
     * @param permissions one or more permission groups.
     * @return true, other wise is false.
     */
    public static boolean hasPermissions(Activity activity, String[]... permissions) {
        for (String[] permission : permissions) {
            boolean hasPermission = PERMISSION_CHECKER.hasPermission(activity, permission);
            if (!hasPermission) return false;
        }
        return true;
    }

    /**
     * Get compatible Android 7.0 and lower versions of Uri.
     *
     * @param context {@link Context}.
     * @param file    apk file.
     * @return uri.
     */
    public static Uri getFileUri(Context context, File file) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            return FileProvider.getUriForFile(context, context.getPackageName() + ".file.path.share", file);
        }
        return Uri.fromFile(file);
    }

    private static Source getContextSource(Context context) {
        if (context instanceof Activity) {
            return new ActivitySource((Activity) context);
        } else if (context instanceof ContextWrapper) {
            return getContextSource(((ContextWrapper) context).getBaseContext());
        }
        return new ContextSource(context);
    }

    private AndPermission() {
    }
}