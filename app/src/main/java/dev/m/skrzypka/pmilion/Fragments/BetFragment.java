package dev.m.skrzypka.pmilion.Fragments;

import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

import dev.m.skrzypka.pmilion.R;

import static dev.m.skrzypka.pmilion.Fragments.GameFragment.mCurrency;
import static dev.m.skrzypka.pmilion.Fragments.GameFragment.mMoney;
import static dev.m.skrzypka.pmilion.Fragments.GameFragment.setZapadnia;

public class BetFragment extends DialogFragment {

    TextView mBetText;
    SeekBar mBetSeek;
    Button mBetOk;
    Integer mBetValue;
    String mBetTag;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final View view = View.inflate(getActivity(), R.layout.fragment_bet, null);
        Dialog dialog = new Dialog(getActivity(), R.style.CustomDialog);
        dialog.setContentView(view);

        mBetValue = 0;
        mBetTag = this.getTag();

        mBetText = view.findViewById(R.id.betMoney);
        mBetSeek = view.findViewById(R.id.betSeek);
        mBetOk = view.findViewById(R.id.betButton);

        mBetSeek.setMax(mMoney/25000);
        mBetSeek.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                mBetText.setText((i*25000)+" "+mCurrency);
                mBetValue= (i*25000);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });

        mBetOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setZapadnia(mBetTag, mBetValue);
                dismiss();
            }
        });

        return dialog;
    }
}
