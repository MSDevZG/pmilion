package dev.m.skrzypka.pmilion.Fragments;

import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import dev.m.skrzypka.pmilion.R;

import static dev.m.skrzypka.pmilion.MainActivity.mLang;
import static dev.m.skrzypka.pmilion.MainActivity.setMenuFragment;

public class InfoFragment extends Fragment {

    View mInfoFragment;
    ImageView mTitle, mBack;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mInfoFragment = inflater.inflate(R.layout.fragment_info, container, false);

        mTitle = mInfoFragment.findViewById(R.id.info_title);
        if (mLang.equals("pl")) {
            mTitle.setImageResource(R.drawable.titlepl);
        } else {
            mTitle.setImageResource(R.drawable.titleeng);
        }

        mBack = mInfoFragment.findViewById(R.id.info_back);
        mBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setMenuFragment();
            }
        });

        return mInfoFragment;
    }

}
