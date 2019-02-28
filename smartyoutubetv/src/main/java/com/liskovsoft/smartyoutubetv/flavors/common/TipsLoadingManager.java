package com.liskovsoft.smartyoutubetv.flavors.common;

import android.app.Activity;
import android.os.Handler;
import android.view.View;
import android.widget.TextView;
import com.liskovsoft.smartyoutubetv.R;
import com.liskovsoft.smartyoutubetv.fragments.LoadingManager;

import java.util.Random;

public class TipsLoadingManager implements LoadingManager {
    private final Activity mContext;
    private final Random mRandom;

    private final int[] mTips = {
            R.string.tip_show_main_screen
    };

    public TipsLoadingManager(Activity context) {
        mContext = context;
        mRandom = new Random(System.currentTimeMillis());
    }

    @Override
    public void showRandomTip() {
        int next = mRandom.nextInt(mTips.length) & Integer.MAX_VALUE; // only positive ints
        showMessage(mTips[next]);
    }

    /**
     * Dynamically load main view since it may not be initialized yet.
     * @return root view
     */
    private View getRootView() {
        return mContext.findViewById(R.id.loading_main);
    }

    @Override
    public void show() {
        showRandomTip();
        getRootView().setVisibility(View.VISIBLE);
    }

    @Override
    public void hide() {
        new Handler(mContext.getMainLooper())
                .postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        getRootView().setVisibility(View.GONE);
                    }
                }, 500);
    }

    @Override
    public void showMessage(int resId) {
        String msg = mContext.getResources().getString(resId);
        showMessage(msg);
    }

    @Override
    public void showMessage(String message) {
        TextView msgView = getRootView().findViewById(R.id.loading_message);
        msgView.setText(message);
    }
}
