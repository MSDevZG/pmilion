package dev.m.skrzypka.pmilion.Fragments;

import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import dev.m.skrzypka.pmilion.R;

import static dev.m.skrzypka.pmilion.MainActivity.mLang;
import static dev.m.skrzypka.pmilion.MainActivity.setMenuFragment;

public class LoseDialogFragment extends DialogFragment {

    Button nextRoundButton;
    ImageView mLogo;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final View view = View.inflate(getActivity(), R.layout.fragment_losedialog, null);
        Dialog dialog = new Dialog(getActivity(), R.style.CustomDialog);
        dialog.setContentView(view);

        mLogo = view.findViewById(R.id.image_logo);
        if (mLang.equals("pl")) {
            mLogo.setImageResource(R.drawable.titlepl);
        } else {
            mLogo.setImageResource(R.drawable.titleeng);
        }

        nextRoundButton = view.findViewById(R.id.endGame);
        nextRoundButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
                setMenuFragment();
            }
        });

        return dialog;
    }
}
