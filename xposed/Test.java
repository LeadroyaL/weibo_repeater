package com.leadroyal.ctfposed;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.Application;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.widget.EditText;

import com.keen.hotposed.support.HotposedInterface.IHotposedPlugin;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedHelpers;
import de.robv.android.xposed.callbacks.XC_LoadPackage;

public class Test implements IHotposedPlugin {
    private static final String TAG = "ccttff";

    private boolean shouldReplace = false;
    private static boolean hasReceiver = false;
    private static Application application;
    private static Activity seaActivity;
    private static Intent repostIntent;
    private static Intent weiboIntent;
    private static String plain = "";
    private static String mid = "";


    @Override
    public void startHotposedPlugin(XC_LoadPackage.LoadPackageParam loadPackageParam) {
        Log.e(TAG, "Loaded app: " + loadPackageParam.packageName);
        Log.e(TAG, "start hook");

        XposedHelpers.findAndHookMethod("com.weico.international.WApplication", loadPackageParam.classLoader, "onCreate", new XC_MethodHook() {
            @Override
            protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                super.afterHookedMethod(param);
                final Application app = (Application) param.thisObject;
                ActivityManager mActivityManager = (ActivityManager) app.getSystemService(Context.ACTIVITY_SERVICE);
                String pname = "";
                int pid = android.os.Process.myPid();
                for (ActivityManager.RunningAppProcessInfo appProcess : mActivityManager.getRunningAppProcesses()) {
                    if (appProcess.pid == pid) {
                        pname = appProcess.processName;
                        break;
                    }
                }
                if (pname.equals("com.weico.international")) {
                    application = app;
                    if (!hasReceiver) {
                        app.registerReceiver(new BroadcastReceiver() {
                            @Override
                            public void onReceive(Context context, final Intent intent) {
                                Log.e(TAG, "OnReceive SEND!");
                                app.startActivity(new Intent(weiboIntent).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
                                new Thread(new Runnable() {
                                    @Override
                                    public void run() {
                                        try {
                                            Thread.sleep(3000);
                                            prepareData(intent.getStringExtra("filename"));
                                            seaActivity.runOnUiThread(new Runnable() {
                                                @Override
                                                public void run() {
                                                    try {
                                                        Field f_editText = seaActivity.getClass().getDeclaredField("actComposeInput");
                                                        f_editText.setAccessible(true);
                                                        EditText editText;
                                                        editText = (EditText) f_editText.get(seaActivity);
                                                        editText.setText(plain);
                                                    } catch (Exception e) {
                                                        e.printStackTrace();
                                                    }
                                                    try {
                                                        Method m_send = seaActivity.getClass().getDeclaredMethod("send");
                                                        m_send.setAccessible(true);
                                                        Log.e(TAG, "before invoke send()");
                                                        m_send.invoke(seaActivity);
                                                    } catch (IllegalAccessException e) {
                                                        e.printStackTrace();
                                                    } catch (InvocationTargetException e) {
                                                        e.printStackTrace();
                                                    } catch (NoSuchMethodException e) {
                                                        e.printStackTrace();
                                                    }
                                                }
                                            });
                                        } catch (InterruptedException e) {
                                            e.printStackTrace();
                                        } catch (IOException e) {
                                            e.printStackTrace();
                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                }).start();
                            }
                        }, new IntentFilter("com.leadroyal.SEND"));

                        app.registerReceiver(new BroadcastReceiver() {
                            @Override
                            public void onReceive(Context context, final Intent intent) {
                                Log.e(TAG, "OnReceive REPOST!");
                                app.startActivity(new Intent(repostIntent).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
                                new Thread(new Runnable() {
                                    @Override
                                    public void run() {
                                        try {
                                            Thread.sleep(3000);
                                            prepareData(intent.getStringExtra("filename"));
                                            mid = intent.getStringExtra("mid");
                                            seaActivity.runOnUiThread(new Runnable() {
                                                @Override
                                                public void run() {
                                                    try {
                                                        Field f_editText = seaActivity.getClass().getDeclaredField("actComposeInput");
                                                        f_editText.setAccessible(true);
                                                        EditText editText;
                                                        editText = (EditText) f_editText.get(seaActivity);
                                                        editText.setText(plain);
                                                    } catch (Exception e) {
                                                        e.printStackTrace();
                                                    }
                                                    try {
                                                        Method m_send = seaActivity.getClass().getDeclaredMethod("send");
                                                        m_send.setAccessible(true);
                                                        Log.e(TAG, "before invoke send()");
                                                        m_send.invoke(seaActivity);
                                                    } catch (IllegalAccessException e) {
                                                        e.printStackTrace();
                                                    } catch (InvocationTargetException e) {
                                                        e.printStackTrace();
                                                    } catch (NoSuchMethodException e) {
                                                        e.printStackTrace();
                                                    }
                                                }
                                            });
                                        } catch (InterruptedException e) {
                                            e.printStackTrace();
                                        } catch (IOException e) {
                                            e.printStackTrace();
                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                }).start();
                            }
                        }, new IntentFilter("com.leadroyal.REPOST"));
                    }
                    hasReceiver = true;
                }
            }
        });

        XposedHelpers.findAndHookMethod("com.weico.international.activity.compose.SeaComposeActivity", loadPackageParam.classLoader, "onCreate", Bundle.class, new XC_MethodHook() {
            @Override
            protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                super.beforeHookedMethod(param);
                seaActivity = (Activity) param.thisObject;
                Intent intent = seaActivity.getIntent();
                String status = intent.getStringExtra("status");
                if (TextUtils.isEmpty(status)) {
                    weiboIntent = new Intent(intent);
                    Log.e(TAG, "weiboIntent is set!");
                } else {
                    repostIntent = new Intent(intent);
                    Log.e(TAG, "repostIntent is set!");
                }
            }
        });

        XposedHelpers.findAndHookMethod("com.weico.international.activity.compose.SeaComposeActivity", loadPackageParam.classLoader, "send", new XC_MethodHook() {
            @Override
            protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                super.beforeHookedMethod(param);
                shouldReplace = true;
            }

            @Override
            protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                super.afterHookedMethod(param);
                shouldReplace = false;
            }
        });

        XposedHelpers.findAndHookMethod("com.weico.international.model.weico.draft.DraftRepost", loadPackageParam.classLoader, "setStatus", "com.weico.international.model.sina.Status", new XC_MethodHook() {
            @Override
            protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                super.beforeHookedMethod(param);
                Object obj = param.args[0];
                Method m = obj.getClass().getMethod("toJson");
                Object json = m.invoke(obj);
                String s = json.toString();
                int len = s.length();
                for (int i = 0; i < len; i += 100) {
                    Log.e(TAG, s.substring(i, (i + 100) < s.length() ? i + 100 : s.length()));
                }
                if (shouldReplace) {
                    try {
                        Field f_idstr = obj.getClass().getDeclaredField("idstr");
                        f_idstr.setAccessible(true);
                        Field f_mid = obj.getClass().getDeclaredField("mid");
                        f_mid.setAccessible(true);
                        Field f_id = obj.getClass().getDeclaredField("id");
                        f_id.setAccessible(true);
                        f_idstr.set(obj, mid);
                        f_mid.set(obj, Long.parseLong(mid));
                        f_id.set(obj, mid);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                super.afterHookedMethod(param);
            }
        });

        XposedHelpers.findAndHookMethod("com.weico.international.model.weico.draft.Draft", loadPackageParam.classLoader, "setText", String.class, new XC_MethodHook() {
            @Override
            protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                super.beforeHookedMethod(param);
                // TODO: 这处替换应该不需要，因为已经在EditText里赋值过了，我忘了这个是不是一定要hook的了
                String obj = (String) param.args[0];
                if (shouldReplace)
                    obj = plain;
            }

            @Override
            protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                super.afterHookedMethod(param);
            }
        });

        Log.e(TAG, "end hook");

    }

    private void prepareData(String filename) throws IOException, JSONException {
        if (TextUtils.isEmpty(filename))
            return;
        byte bs[] = new byte[1024 * 1024];
        File f = new File("/sdcard/" + filename);
        FileInputStream fis = new FileInputStream(f);
        int len = fis.read(bs);
        String s = new String(bs, 0, len);
        fis.close();
        plain = s;
    }
}

