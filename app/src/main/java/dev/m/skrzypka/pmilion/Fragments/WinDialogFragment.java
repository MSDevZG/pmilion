package dev.m.skrzypka.pmilion.Fragments;

import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONException;

import dev.m.skrzypka.pmilion.R;

import static dev.m.skrzypka.pmilion.Fragments.GameFragment.mMoney;
import static dev.m.skrzypka.pmilion.Fragments.GameFragment.mRoundNum;
import static dev.m.skrzypka.pmilion.MainActivity.mLang;
import static dev.m.skrzypka.pmilion.MainActivity.setMenuFragment;

public class WinDialogFragment extends DialogFragment {

    TextView mWinText;
    Button mNextRoundButton;
    ImageView mLogo;
    String mCurrency;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final View view = View.inflate(getActivity(), R.layout.fragment_windialog, null);
        Dialog dialog = new Dialog(getActivity(), R.style.CustomDialog);
        dialog.setContentView(view);

        mWinText = view.findViewById(R.id.wintext);
        mNextRoundButton = view.findViewById(R.id.nextRound);
        mLogo = view.findViewById(R.id.winLogo);
        if (mLang.equals("pl")) {
            mLogo.setImageResource(R.drawable.titlepl_small);
            mCurrency = "PLN";
        } else {
            mLogo.setImageResource(R.drawable.titleeng_small);
            mCurrency = "USD";
        }

        mWinText.setText(getString(R.string.win) + "\n" + mMoney + " " + mCurrency);
        switch (mRoundNum) {
            case 10:
                mNextRoundButton.setText(getString(R.string.end));
                mNextRoundButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dismiss();
                        setMenuFragment();
                    }
                });
                break;
            default:
                mNextRoundButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dismiss();
                        mRoundNum++;
                        try {
                            GameFragment.Rounder();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
                break;
        }
        return dialog;
    }
}
