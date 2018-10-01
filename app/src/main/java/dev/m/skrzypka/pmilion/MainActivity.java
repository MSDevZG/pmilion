package dev.m.skrzypka.pmilion;

import android.app.FragmentManager;
import android.content.Context;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.reward.RewardItem;
import com.google.android.gms.ads.reward.RewardedVideoAd;
import com.google.android.gms.ads.reward.RewardedVideoAdListener;

import java.util.Locale;

import dev.m.skrzypka.pmilion.Fragments.GameFragment;
import dev.m.skrzypka.pmilion.Fragments.InfoFragment;
import dev.m.skrzypka.pmilion.Fragments.MenuFragment;

import static dev.m.skrzypka.pmilion.Fragments.MenuFragment.mNoADS;

public class MainActivity extends AppCompatActivity implements RewardedVideoAdListener{

    public static Context mContext;

    public static FragmentManager mFragmentManager;
    public static android.support.v4.app.FragmentManager mSupportedFragmentManager;

    public static MenuFragment mMenuFragment = new MenuFragment();
    public static GameFragment mGameFragment = new GameFragment();
    public static InfoFragment mInfoFragment = new InfoFragment();

    public static String mLang;
    public static Boolean mAD = true, mLoaded = false;

    public static RewardedVideoAd mRewardedVideoAd;
    public static Handler mHandler = new Handler();

    public Animation mAnimAds;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mContext = getApplicationContext();
        mLang = Locale.getDefault().getLanguage().toString();
        mAnimAds = AnimationUtils.loadAnimation(this, R.anim.ads_anim);
        if(mLoaded) {
            mNoADS.startAnimation(mAnimAds);
        }

        MobileAds.initialize(this, "ca-app-pub-4216639290521474~6583920549");

        mRewardedVideoAd = MobileAds.getRewardedVideoAdInstance(this);
        mRewardedVideoAd.setRewardedVideoAdListener(this);
        mRewardedVideoAd.loadAd("ca-app-pub-4216639290521474/2509110825", new AdRequest.Builder().build());

        mFragmentManager = getFragmentManager();
        mSupportedFragmentManager = getSupportFragmentManager();
        mFragmentManager.beginTransaction().replace(R.id.container, mMenuFragment, mMenuFragment.getTag()).commit();
    }

    public static void setMenuFragment() {
        mFragmentManager.beginTransaction().replace(R.id.container, mMenuFragment, mMenuFragment.getTag()).commit();
    }

    public static void setGameFragment() {
        mFragmentManager.beginTransaction().replace(R.id.container, mGameFragment, mGameFragment.getTag()).commit();
    }

    public static void setInfoFragment() {
        mFragmentManager.beginTransaction().replace(R.id.container, mInfoFragment, mInfoFragment.getTag()).commit();
    }

    @Override
    public void onBackPressed() {
    }

//VideoAD
    @Override
    public void onRewardedVideoAdLoaded() {
        mLoaded = true;
        mNoADS.startAnimation(mAnimAds);
    }

    @Override
    public void onRewardedVideoAdOpened() {
    }

    @Override
    public void onRewardedVideoStarted() {
    }

    @Override
    public void onRewardedVideoAdClosed() {
    }

    @Override
    public void onRewarded(RewardItem rewardItem) {
        mAD = false;
    }

    @Override
    public void onRewardedVideoAdLeftApplication() {
    }
    @Override
    public void onRewardedVideoAdFailedToLoad(int i) {
        switch(i) {
            case 3:
                Log.e("X", i+"");
                mHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mRewardedVideoAd.loadAd("ca-app-pub-4216639290521474/2509110825", new AdRequest.Builder().build());
                    }
                }, 2000);
                break;
                default:break;
        }
    }
}
