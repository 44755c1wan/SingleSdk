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
package com.permission.checker;

import android.content.ContentResolver;
import android.content.Context;
import android.os.Build;

import com.permission.runtime.Permission;

import java.util.List;

/**
 * Created by Zhenjie Yan on 2018/1/7.
 */
public final class StrictChecker implements PermissionChecker {

    public StrictChecker() {
    }

    @Override
    public boolean hasPermission(Context context, String... permissions) {
        if (Build.VERSION.SDK_INT < 21) return true;

        for (String permission : permissions) {
            if (!hasPermission(context, permission)) {
                return false;
            }
        }
        return true;
    }

    @Override
    public boolean hasPermission(Context context, List<String> permissions) {
        if (Build.VERSION.SDK_INT < 21) return true;

        for (String permission : permissions) {
            if (!hasPermission(context, permission)) {
                return false;
            }
        }
        return true;
    }

    private boolean hasPermission(Context context, String permission) {
        try {
            if (Permission.READ_CALENDAR.equals(permission)) {
                return checkReadCalendar(context);
            } else if (Permission.WRITE_CALENDAR.equals(permission)) {
                return checkWriteCalendar(context);
            } else if (Permission.CAMERA.equals(permission)) {
                return checkCamera(context);
            } else if (Permission.READ_CONTACTS.equals(permission)) {
                return checkReadContacts(context);
            } else if (Permission.WRITE_CONTACTS.equals(permission)) {
                return checkWriteContacts(context);
            } else if (Permission.GET_ACCOUNTS.equals(permission)) {
                return true;
            } else if (Permission.ACCESS_COARSE_LOCATION.equals(permission)) {
                return checkCoarseLocation(context);
            } else if (Permission.ACCESS_FINE_LOCATION.equals(permission)) {
                return checkFineLocation(context);
            } else if (Permission.RECORD_AUDIO.equals(permission)) {
                return checkRecordAudio(context);
            } else if (Permission.READ_PHONE_STATE.equals(permission)) {
                return checkReadPhoneState(context);
            } else if (Permission.CALL_PHONE.equals(permission)) {
                return true;
            } else if (Permission.READ_CALL_LOG.equals(permission)) {
                return checkReadCallLog(context);
            } else if (Permission.WRITE_CALL_LOG.equals(permission)) {
                return checkWriteCallLog(context);
            } else if (Permission.ADD_VOICEMAIL.equals(permission)) {
                return true;
            } else if (Permission.USE_SIP.equals(permission)) {
                return checkSip(context);
            } else if (Permission.PROCESS_OUTGOING_CALLS.equals(permission)) {
                return true;
            } else if (Permission.BODY_SENSORS.equals(permission)) {
                return checkSensorHeart(context);
            } else if (Permission.ACTIVITY_RECOGNITION.equals(permission)) {
                return checkSensorActivity(context);
            } else if (Permission.SEND_SMS.equals(permission) || Permission.RECEIVE_MMS.equals(permission)) {
                return true;
            } else if (Permission.READ_SMS.equals(permission)) {
                return checkReadSms(context);
            } else if (Permission.RECEIVE_WAP_PUSH.equals(permission) || Permission.RECEIVE_SMS.equals(permission)) {
                return true;
            } else if (Permission.READ_EXTERNAL_STORAGE.equals(permission)) {
                return checkReadStorage();
            } else if (Permission.WRITE_EXTERNAL_STORAGE.equals(permission)) {
                return checkWriteStorage();
            }
        } catch (Throwable e) {
            return false;
        }
        return true;
    }

    private static boolean checkReadCalendar(Context context) throws Throwable {
        PermissionTest test = new CalendarReadTest(context);
        return test.test();
    }

    private static boolean checkWriteCalendar(Context context) throws Throwable {
        PermissionTest test = new CalendarWriteTest(context);
        return test.test();
    }

    private static boolean checkCamera(Context context) throws Throwable {
        PermissionTest test = new CameraTest(context);
        return test.test();
    }

    private static boolean checkReadContacts(Context context) throws Throwable {
        PermissionTest test = new ContactsReadTest(context);
        return test.test();
    }

    private static boolean checkWriteContacts(Context context) throws Throwable {
        ContentResolver resolver = context.getContentResolver();
        PermissionTest test = new ContactsWriteTest(resolver);
        return test.test();
    }

    private static boolean checkCoarseLocation(Context context) throws Throwable {
        PermissionTest test = new LocationCoarseTest(context);
        return test.test();
    }

    private static boolean checkFineLocation(Context context) throws Throwable {
        PermissionTest test = new LocationFineTest(context);
        return test.test();
    }

    private static boolean checkRecordAudio(Context context) throws Throwable {
        PermissionTest test = new RecordAudioTest(context);
        return test.test();
    }

    private static boolean checkReadPhoneState(Context context) throws Throwable {
        PermissionTest test = new PhoneStateReadTest(context);
        return test.test();
    }

    private static boolean checkReadCallLog(Context context) throws Throwable {
        PermissionTest test = new CallLogReadTest(context);
        return test.test();
    }

    private static boolean checkWriteCallLog(Context context) throws Throwable {
        PermissionTest test = new CallLogWriteTest(context);
        return test.test();
    }

    private static boolean checkSip(Context context) throws Throwable {
        PermissionTest test = new SipTest(context);
        return test.test();
    }

    private static boolean checkSensorHeart(Context context) throws Throwable {
        PermissionTest test = new SensorHeartTest(context);
        return test.test();
    }

    private static boolean checkSensorActivity(Context context) throws Throwable {
        PermissionTest test = new SensorActivityTest(context);
        return test.test();
    }

    private static boolean checkReadSms(Context context) throws Throwable {
        PermissionTest test = new SmsReadTest(context);
        return test.test();
    }

    private static boolean checkReadStorage() throws Throwable {
        PermissionTest test = new StorageReadTest();
        return test.test();
    }

    private static boolean checkWriteStorage() throws Throwable {
        PermissionTest test = new StorageWriteTest();
        return test.test();
    }
}