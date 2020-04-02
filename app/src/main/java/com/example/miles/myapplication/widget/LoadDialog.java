package com.example.miles.myapplication.widget;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.Dialog;
import android.content.Context;
import android.view.KeyEvent;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.miles.myapplication.R;
import com.example.miles.myapplication.myAsync.AsyncManager;

/**
 * 用途：载入界面
 * 作者：Created by Miles Wang on 2018/12/26
 * 邮箱：icy-star@qq.com
 **/
public class LoadDialog extends Dialog {
    /**
     * LoadDialog
     */
    private static LoadDialog loadDialog;
    /**
     * canNotCancel, the dialog dimiss or undimiss flag
     */
    private boolean canNotCancel;
    /**
     * if the dialog don't dimiss, what is the tips.
     */
    private String tipMsg;
    private AsyncManager mAsyncTaskManager;
    private Context context;
    private long mExitTime;

    /**
     * the LoadDialog constructor
     *
     * @param ctx          Context
     * @param canNotCancel boolean
     * @param tipMsg       String
     */
    public LoadDialog(final Context ctx, boolean canNotCancel, String tipMsg) {
        super(ctx);

        this.context = ctx;
        this.canNotCancel = canNotCancel;
        this.tipMsg = tipMsg;
        this.getContext().setTheme(android.R.style.Theme_InputMethod);
        setContentView(R.layout.layout_dialog_loading);

        Window window = getWindow();
        WindowManager.LayoutParams attributesParams = window.getAttributes();
        attributesParams.flags = WindowManager.LayoutParams.FLAG_DIM_BEHIND;
        attributesParams.dimAmount = 0.5f;

        window.setLayout(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
    }

    /**
     * the LoadDialog constructor
     *
     * @param ctx          Context
     * @param canNotCancel boolean
     */
    public LoadDialog(final Context ctx, boolean canNotCancel, AsyncManager mAsyncTaskManager) {
        super(ctx);

        this.context = ctx;
        this.mAsyncTaskManager = mAsyncTaskManager;
        this.canNotCancel = canNotCancel;
        this.getContext().setTheme(android.R.style.Theme_InputMethod);
        setContentView(R.layout.layout_guide_item0);

        Window window = getWindow();
        WindowManager.LayoutParams attributesParams = window.getAttributes();
        attributesParams.flags = WindowManager.LayoutParams.FLAG_DIM_BEHIND;
        attributesParams.dimAmount = 0.5f;

        window.setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
//			if (canNotCancel) {
//				mAsyncTaskManager.cancelRequest();
//				dismiss();
//				return true;
//			}
            if ((System.currentTimeMillis() - mExitTime) > 2000) {
                Object mHelperUtils;
                Toast.makeText(context, "再按一次退出程序", Toast.LENGTH_SHORT).show();
                mExitTime = System.currentTimeMillis();
                //finish();
            } else {
                //退出所有activity
                ActivityManager am = (ActivityManager) context.getSystemService(context.ACTIVITY_SERVICE);
                am.restartPackage(context.getPackageName());
                System.exit(1);
                //finish();
            }
        }
        return super.onKeyDown(keyCode, event);
    }

    /**
     * show the dialog
     *
     * @param context
     */
    public static void show(Context context) {
        show(context, null, false);
    }

    /**
     * show the dialog
     *
     * @param context Context
     * @param message String
     */
    public static void show(Context context, String message) {
        show(context, message, false);
    }

    /**
     * show the dialog
     *
     * @param context  Context
     * @param message  String, show the message to user when isCancel is true.
     * @param isCancel boolean, true is can't dimiss，false is can dimiss
     */
    private static void show(Context context, String message, boolean isCancel) {
        if (context instanceof Activity) {
            if (((Activity) context).isFinishing()) {
                return;
            }
        }
        if (loadDialog != null && loadDialog.isShowing()) {
            return;
        }
        loadDialog = new LoadDialog(context, isCancel, message);
        loadDialog.show();
    }

    /**
     * dismiss the dialog
     */
    public static void dismiss(Context context) {
        try {
            if (context instanceof Activity) {
                if (((Activity) context).isFinishing()) {
                    loadDialog = null;
                    return;
                }
            }

            if (loadDialog != null && loadDialog.isShowing()) {
                Context loadContext = loadDialog.getContext();
                if (loadContext != null && loadContext instanceof Activity) {
                    if (((Activity) loadContext).isFinishing()) {
                        loadDialog = null;
                        return;
                    }
                }
                loadDialog.dismiss();
                loadDialog = null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            loadDialog = null;
        }
    }
}
