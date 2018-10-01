package dev.m.skrzypka.pmilion.Fragments;

import android.os.Bundle;
import android.app.Fragment;
import android.support.design.widget.Snackbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import dev.m.skrzypka.pmilion.R;

import static dev.m.skrzypka.pmilion.MainActivity.mAD;
import static dev.m.skrzypka.pmilion.MainActivity.mLang;
import static dev.m.skrzypka.pmilion.MainActivity.mRewardedVideoAd;
import static dev.m.skrzypka.pmilion.MainActivity.setGameFragment;
import static dev.m.skrzypka.pmilion.MainActivity.setInfoFragment;

public class MenuFragment extends Fragment {

    View mMenuFragment;
    public static ImageView mTitle, mNoADS;
    Button mPlay, mInfo, mExit;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mMenuFragment = inflater.inflate(R.layout.fragment_menu, container, false);

        mTitle = mMenuFragment.findViewById(R.id.menu_title);
        mNoADS = mMenuFragment.findViewById(R.id.image_noAds);
        mNoADS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mAD) {
                    if (mRewardedVideoAd.isLoaded()) {
                        mRewardedVideoAd.show();
                    } else {
                        Snackbar.make(mMenuFragment, R.string.video, Snackbar.LENGTH_LONG).show();
                    }
                }
            }
        });

        if (mLang.equals("pl")) {
            mTitle.setImageResource(R.drawable.titlepl);
        } else {
            mTitle.setImageResource(R.drawable.titleeng);
        }

        mPlay = mMenuFragment.findViewById(R.id.button_play);
        mPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setGameFragment();
            }
        });

        mInfo = mMenuFragment.findViewById(R.id.button_info);
        mInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setInfoFragment();
            }
        });

        mExit = mMenuFragment.findViewById(R.id.button_exit);
        mExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Zrobic zapytanie o wyjscie
                System.exit(0);
            }
        });

        return mMenuFragment;
    }
}
