package dev.m.skrzypka.pmilion.Fragments;

import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Fragment;
import android.os.CountDownTimer;
import android.os.Handler;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import dev.m.skrzypka.pmilion.Interfaces.DownloadComplete;
import dev.m.skrzypka.pmilion.Listeners.PMDragListener;
import dev.m.skrzypka.pmilion.Listeners.PMTouchListener;
import dev.m.skrzypka.pmilion.R;

import static dev.m.skrzypka.pmilion.MainActivity.mAD;
import static dev.m.skrzypka.pmilion.MainActivity.mContext;
import static dev.m.skrzypka.pmilion.MainActivity.mFragmentManager;
import static dev.m.skrzypka.pmilion.MainActivity.mLang;
import static dev.m.skrzypka.pmilion.MainActivity.setMenuFragment;

public class GameFragment extends Fragment implements DownloadComplete {

    View mGameFragment;

    static Animation anim_tv, anim_hide_logo, anim_money, anim_show1, anim_show2, anim_show3, anim_show4;

    static LinearLayout mCategoryPanel;
    static Button mCategoryA, mCategoryB, mCheckB;
    static ImageView mTV, mTVSec, mLogo1, mLogo2, mLogo3, mLogo4, mPula, mMoneyDrag, mMoneyTrapA, mMoneyTrapB, mMoneyTrapC, mMoneyTrapD, mMoneyImageA, mMoneyImageB, mMoneyImageC, mMoneyImageD;
    static Drawable mLogo;
    static TextView mTextMoney, mMoneySetA, mMoneySetB, mMoneySetC, mMoneySetD, mTextRound, mTextQuestion, mAnswer1, mAnswer2, mAnswer3, mAnswer4, mTime1, mTime2, mTime3, mTime4;
    public static Integer time, mRoundNum, mMoney, mObstA, mObstB, mObstC, mObstD, mMoneyA, mMoneyB, mMoneyC, mMoneyD, mCorrectAnswer, mLevel;
    static CountDownTimer mTimer;

    String[] mURLS;
    static String mRound, mCurrency;

    static JSONArray mEasyArray, mMediumArray, mHardArray;
    static JSONObject mQuestion;

    static InterstitialAd mInterstitialAd;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mGameFragment = inflater.inflate(R.layout.fragment_game, container, false);

//AD
        mInterstitialAd = new InterstitialAd(mContext);
        mInterstitialAd.setAdUnitId("ca-app-pub-4216639290521474/2940291055");
        mInterstitialAd.loadAd(new AdRequest.Builder().build());

//DEFINE
        if (mLang.equals("pl")) {
            mURLS = new String[]{"http://mskdevelopment.atthost24.pl/pleasyset", "http://mskdevelopment.atthost24.pl/plmediumset", "http://mskdevelopment.atthost24.pl/plhardset"};
            mLogo = getResources().getDrawable(R.drawable.titlepl_small);
            mRound = "RUNDA";
            mCurrency = "PLN";
        } else {
            mURLS = new String[]{"http://mskdevelopment.atthost24.pl/engeasyset", "http://mskdevelopment.atthost24.pl/engmediumset", "http://mskdevelopment.atthost24.pl/enghardset"};
            mLogo = getResources().getDrawable(R.drawable.titleeng_small);
            mRound = "ROUND";
            mCurrency = "USD";
        }
        mMoney = 1000000;
        mRoundNum = 1;
        mLevel = 1;

//FINDERS
        mTextQuestion = mGameFragment.findViewById(R.id.text_question);
        mTextMoney = mGameFragment.findViewById(R.id.text_money);
        mTextRound = mGameFragment.findViewById(R.id.text_round);
        mCategoryPanel = mGameFragment.findViewById(R.id.layout_category);
        mCategoryA = mGameFragment.findViewById(R.id.button_category_a);
        mCategoryB = mGameFragment.findViewById(R.id.button_category_b);
        mLogo1 = mGameFragment.findViewById(R.id.image_screenLogo1);
        mLogo2 = mGameFragment.findViewById(R.id.image_screenLogo2);
        mLogo3 = mGameFragment.findViewById(R.id.image_screenLogo3);
        mLogo4 = mGameFragment.findViewById(R.id.image_screenLogo4);
        mAnswer1 = mGameFragment.findViewById(R.id.text_answer_1);
        mAnswer2 = mGameFragment.findViewById(R.id.text_answer_2);
        mAnswer3 = mGameFragment.findViewById(R.id.text_answer_3);
        mAnswer4 = mGameFragment.findViewById(R.id.text_answer_4);
        mMoneySetA = mGameFragment.findViewById(R.id.text_moneyset_1);
        mMoneySetB = mGameFragment.findViewById(R.id.text_moneyset_2);
        mMoneySetC = mGameFragment.findViewById(R.id.text_moneyset_3);
        mMoneySetD = mGameFragment.findViewById(R.id.text_moneyset_4);
        mTime1 = mGameFragment.findViewById(R.id.text_time_1);
        mTime2 = mGameFragment.findViewById(R.id.text_time_2);
        mTime3 = mGameFragment.findViewById(R.id.text_time_3);
        mTime4 = mGameFragment.findViewById(R.id.text_time_4);
        mPula = mGameFragment.findViewById(R.id.image_pula);
        mMoneyDrag = mGameFragment.findViewById(R.id.image_drag);
        mTV = mGameFragment.findViewById(R.id.image_tv);
        mTVSec = mGameFragment.findViewById(R.id.image_tvsecond);
        mMoneyTrapA = mGameFragment.findViewById(R.id.image_trapA);
        mMoneyTrapB = mGameFragment.findViewById(R.id.image_trapB);
        mMoneyTrapC = mGameFragment.findViewById(R.id.image_trapC);
        mMoneyTrapD = mGameFragment.findViewById(R.id.image_trapD);
        mCheckB = mGameFragment.findViewById(R.id.button_check);
        mMoneyImageA = mGameFragment.findViewById(R.id.image_moneyA);
        mMoneyImageB = mGameFragment.findViewById(R.id.image_moneyB);
        mMoneyImageC = mGameFragment.findViewById(R.id.image_moneyC);
        mMoneyImageD = mGameFragment.findViewById(R.id.image_moneyD);

//SETTERS
        mPula.setImageResource(R.drawable.pula_20);
        mLogo1.setImageDrawable(mLogo);
        mLogo2.setImageDrawable(mLogo);
        mLogo3.setImageDrawable(mLogo);
        mLogo4.setImageDrawable(mLogo);

        mMoneyImageA.setOnDragListener(new PMDragListener());
        mMoneyImageB.setOnDragListener(new PMDragListener());
        mMoneyImageC.setOnDragListener(new PMDragListener());
        mMoneyImageD.setOnDragListener(new PMDragListener());

        mCheckB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                stopRound();
            }
        });

        mMoneyImageA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mObstA = 0;
                mMoney += mMoneyA;
                mMoneyA = 0;
                checker();
            }
        });
        mMoneyImageB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mObstB = 0;
                mMoney += mMoneyB;
                mMoneyB = 0;
                checker();
            }
        });
        mMoneyImageC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mObstC = 0;
                mMoney += mMoneyC;
                mMoneyC = 0;
                checker();
            }
        });
        mMoneyImageD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mObstD = 0;
                mMoney += mMoneyD;
                mMoneyD = 0;
                checker();
            }
        });

        mGameFragment.setFocusableInTouchMode(true);
        mGameFragment.requestFocus();
        mGameFragment.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {
                if (i == KeyEvent.KEYCODE_BACK) {
                    mTimer.cancel();
                    setMenuFragment();
                }
                return false;
            }
        });

//ANIMATIONS_DEF
        anim_tv = AnimationUtils.loadAnimation(mContext, R.anim.anim_tv);
        anim_money = AnimationUtils.loadAnimation(mContext, R.anim.anim_money);
        anim_show1 = AnimationUtils.loadAnimation(mContext, R.anim.anim_show_1);
        anim_show2 = AnimationUtils.loadAnimation(mContext, R.anim.anim_show_2);
        anim_show3 = AnimationUtils.loadAnimation(mContext, R.anim.anim_show_3);
        anim_show4 = AnimationUtils.loadAnimation(mContext, R.anim.anim_show_4);
        anim_hide_logo = AnimationUtils.loadAnimation(mContext, R.anim.anim_hide_logo);

//ANIMATION_START
        mTV.startAnimation(anim_tv);
        mTVSec.startAnimation(anim_tv);
        mMoneyDrag.startAnimation(anim_money);
        mPula.startAnimation(anim_money);

//TIMER
        mTimer = new CountDownTimer(61000, 1000) {
            public void onTick(long millisUntilFinished) {
                switch (mLevel) {
                    case 1:
                        mTime1.setText(time + "");
                        mTime2.setText(time + "");
                        mTime3.setText(time + "");
                        mTime4.setText(time + "");
                        break;
                    case 2:
                        mTime1.setText(time + "");
                        mTime2.setText(time + "");
                        mTime3.setText(time + "");
                        break;
                    case 3:
                        mTime2.setText(time + "");
                        mTime3.setText(time + "");
                        break;
                }
                time--;
            }

            public void onFinish() {
                stopRound();
            }
        };

//QDOWNLOADER
//        final QDownloader task = new QDownloader(this);
//        task.baseHttpUrl = mURLS;
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                downloadCompleted();
            }
        }, 1000);

        return mGameFragment;
    }

    public static void Rounder() throws JSONException {
        JSONObject catA = null, catB = null;
        mTextRound.setText(mRound + " " + mRoundNum);
        mTextMoney.setText(mMoney + " " + mCurrency);
        time = 59;
        mMoneyDrag.setOnTouchListener(null);
        mObstA = 0;
        mObstB = 0;
        mObstC = 0;
        mObstD = 0;
        mMoneyA = 0;
        mMoneyB = 0;
        mMoneyC = 0;
        mMoneyD = 0;
        mTextQuestion.setVisibility(View.INVISIBLE);
        mCategoryPanel.setVisibility(View.VISIBLE);
        mAnswer1.setText("");
        mAnswer2.setText("");
        mAnswer3.setText("");
        mAnswer4.setText("");
        mLogo1.setVisibility(View.VISIBLE);
        mLogo2.setVisibility(View.VISIBLE);
        mLogo3.setVisibility(View.VISIBLE);
        mLogo4.setVisibility(View.VISIBLE);
        mMoneySetA.setText("");
        mMoneySetB.setText("");
        mMoneySetC.setText("");
        mMoneySetD.setText("");
        mTime1.setText("");
        mTime2.setText("");
        mTime3.setText("");
        mTime4.setText("");
        mMoneyTrapA.setImageResource(R.drawable.table_close);
        mMoneyTrapB.setImageResource(R.drawable.table_close);
        mMoneyTrapC.setImageResource(R.drawable.table_close);
        mMoneyTrapD.setImageResource(R.drawable.table_close);
        mMoneyImageA.setClickable(true);
        mMoneyImageB.setClickable(true);
        mMoneyImageC.setClickable(true);
        mMoneyImageD.setClickable(true);

        switch (mRoundNum) {
            case 1:
                catA = mEasyArray.getJSONObject(0);
                catB = mEasyArray.getJSONObject(1);
                mCategoryA.setText(catA.getString("category"));
                mCategoryB.setText(catB.getString("category"));
                break;
            case 2:
                catA = mEasyArray.getJSONObject(2);
                catB = mEasyArray.getJSONObject(3);
                mCategoryA.setText(catA.getString("category"));
                mCategoryB.setText(catB.getString("category"));
                break;
            case 3:
                if(mAD) {
                    if(mInterstitialAd.isLoaded()) {
                        mInterstitialAd.show();
                    }
                }
                catA = mEasyArray.getJSONObject(4);
                catB = mEasyArray.getJSONObject(5);
                mCategoryA.setText(catA.getString("category"));
                mCategoryB.setText(catB.getString("category"));
                break;
            case 4:
                mInterstitialAd.loadAd(new AdRequest.Builder().build());
                catA = mEasyArray.getJSONObject(6);
                catB = mEasyArray.getJSONObject(7);
                mCategoryA.setText(catA.getString("category"));
                mCategoryB.setText(catB.getString("category"));
                break;
            case 5:
                catA = mEasyArray.getJSONObject(8);
                catB = mEasyArray.getJSONObject(9);
                mCategoryA.setText(catA.getString("category"));
                mCategoryB.setText(catB.getString("category"));
                break;
            case 6:
                if(mAD) {
                    if(mInterstitialAd.isLoaded()) {
                        mInterstitialAd.show();
                    }
                }
                mLevel = 2;
                catA = mMediumArray.getJSONObject(0);
                catB = mMediumArray.getJSONObject(1);
                mCategoryA.setText(catA.getString("category"));
                mCategoryB.setText(catB.getString("category"));
                break;
            case 7:
                mInterstitialAd.loadAd(new AdRequest.Builder().build());
                catA = mMediumArray.getJSONObject(2);
                catB = mMediumArray.getJSONObject(3);
                mCategoryA.setText(catA.getString("category"));
                mCategoryB.setText(catB.getString("category"));
                break;
            case 8:
                catA = mMediumArray.getJSONObject(4);
                catB = mMediumArray.getJSONObject(5);
                mCategoryA.setText(catA.getString("category"));
                mCategoryB.setText(catB.getString("category"));
                break;
            case 9:
                if(mAD) {
                    if(mInterstitialAd.isLoaded()) {
                        mInterstitialAd.show();
                    }
                }
                mLevel = 3;
                catA = mHardArray.getJSONObject(0);
                catB = mHardArray.getJSONObject(1);
                mCategoryA.setText(catA.getString("category"));
                mCategoryB.setText(catB.getString("category"));
                break;
            case 10:
                catA = mHardArray.getJSONObject(2);
                catB = mHardArray.getJSONObject(3);
                mCategoryA.setText(catA.getString("category"));
                mCategoryB.setText(catB.getString("category"));
                break;
        }
        final JSONObject finalCatA = catA;
        mCategoryA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mCategoryPanel.setVisibility(View.INVISIBLE);
                mQuestion = finalCatA;
                startRound();
                hideLogos();
            }
        });
        final JSONObject finalCatB = catB;
        mCategoryB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mCategoryPanel.setVisibility(View.INVISIBLE);
                mQuestion = finalCatB;
                startRound();
                hideLogos();
            }
        });
    }

    public static void startRound() {
        final Handler handler = new Handler();
        switch (mLevel) {
            case 1:
                try {
                    mAnswer1.setText(mQuestion.getString("answer_1"));
                    mAnswer2.setText(mQuestion.getString("answer_2"));
                    mAnswer3.setText(mQuestion.getString("answer_3"));
                    mAnswer4.setText(mQuestion.getString("answer_4"));
                    mTextQuestion.setText(mQuestion.getString("question"));
                    mCorrectAnswer = mQuestion.getInt("correct");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                mAnswer1.startAnimation(anim_show1);
                mAnswer2.startAnimation(anim_show2);
                mAnswer3.startAnimation(anim_show3);
                mAnswer4.startAnimation(anim_show4);

                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mTextQuestion.setVisibility(View.VISIBLE);
                        mMoneySetA.setText("0");
                        mMoneySetB.setText("0");
                        mMoneySetC.setText("0");
                        mMoneySetD.setText("0");
                        mMoneyDrag.setOnTouchListener(new PMTouchListener());
                        mTimer.start();
                    }
                }, 3500);
                break;
            case 2:
                try {
                    mAnswer1.setText(mQuestion.getString("answer_1"));
                    mAnswer2.setText(mQuestion.getString("answer_2"));
                    mAnswer3.setText(mQuestion.getString("answer_3"));
                    mTextQuestion.setText(mQuestion.getString("question"));
                    mCorrectAnswer = mQuestion.getInt("correct");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                mAnswer1.startAnimation(anim_show1);
                mAnswer2.startAnimation(anim_show2);
                mAnswer3.startAnimation(anim_show3);

                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mTextQuestion.setVisibility(View.VISIBLE);
                        mMoneySetA.setText("0");
                        mMoneySetB.setText("0");
                        mMoneySetC.setText("0");
                        mMoneyDrag.setOnTouchListener(new PMTouchListener());
                        mTimer.start();
                    }
                }, 3500);
                break;
            case 3:
                try {
                    mAnswer2.setText(mQuestion.getString("answer_1"));
                    mAnswer3.setText(mQuestion.getString("answer_2"));
                    mTextQuestion.setText(mQuestion.getString("question"));
                    mCorrectAnswer = mQuestion.getInt("correct");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                mAnswer2.startAnimation(anim_show1);
                mAnswer3.startAnimation(anim_show2);

                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mTextQuestion.setVisibility(View.VISIBLE);
                        mMoneySetB.setText("0");
                        mMoneySetC.setText("0");
                        mMoneyDrag.setOnTouchListener(new PMTouchListener());
                        mTimer.start();
                    }
                }, 3500);

                break;
        }
    }

    public static void setZapadnia(String zap, int val) {
        switch (zap) {
            case "A":
                if (val > 0) {
                    mObstA = 1;
                    mMoneyA += val;
                    mMoney -= val;
                }
                break;
            case "B":
                if (val > 0) {
                    mObstB = 1;
                    mMoneyB += val;
                    mMoney -= val;
                }
                break;
            case "C":
                if (val > 0) {
                    mObstC = 1;
                    mMoneyC += val;
                    mMoney -= val;
                }
                break;
            case "D":
                if (val > 0) {
                    mObstD = 1;
                    mMoneyD += val;
                    mMoney -= val;
                }
                break;
        }
        checker();
    }

    public static void stopRound() {
        Handler hand = new Handler();
        mMoneyImageA.setClickable(false);
        mMoneyImageB.setClickable(false);
        mMoneyImageC.setClickable(false);
        mMoneyImageD.setClickable(false);

        mTimer.cancel();

        if (mMoney != 0) {
            LoseDialogFragment loseDialog = new LoseDialogFragment();
            loseDialog.show(mFragmentManager, "OK");
        } else {
            mCheckB.setVisibility(View.INVISIBLE);
            hand.postDelayed(new Runnable() {
                @Override
                public void run() {
                    checkResult();
                }
            }, 1500);
        }
    }

    public static void checkResult() {
        final WinDialogFragment winDialog = new WinDialogFragment();
        final LoseDialogFragment loseDialog = new LoseDialogFragment();
        Handler hand = new Handler();

        try {
            int q = mQuestion.getInt("correct");
            switch (q) {
                case 1:
                    mMoneyB = 0; mMoneyC = 0; mMoneyD = 0;
                    mMoney = mMoneyA; mMoneyA = 0;
                    switch(mLevel) {
                        case 1:
                            mMoneyTrapB.setImageResource(R.drawable.table_open); mMoneyTrapC.setImageResource(R.drawable.table_open); mMoneyTrapD.setImageResource(R.drawable.table_open);
                            mMoneyImageB.setImageDrawable(null); mMoneyImageC.setImageDrawable(null); mMoneyImageD.setImageDrawable(null);
                            break;
                        case 2:
                            mMoneyTrapB.setImageResource(R.drawable.table_open); mMoneyTrapC.setImageResource(R.drawable.table_open);
                            mMoneyImageB.setImageDrawable(null); mMoneyImageC.setImageDrawable(null);
                            break;
                    }
                    break;
                case 2:
                    mMoneyA = 0; mMoneyC = 0; mMoneyD = 0;
                    mMoney = mMoneyB; mMoneyB = 0;
                    switch(mLevel) {
                        case 1:
                            mMoneyTrapA.setImageResource(R.drawable.table_open); mMoneyTrapC.setImageResource(R.drawable.table_open); mMoneyTrapD.setImageResource(R.drawable.table_open);
                            mMoneyImageA.setImageDrawable(null); mMoneyImageC.setImageDrawable(null); mMoneyImageD.setImageDrawable(null);
                            break;
                        case 2:
                            mMoneyTrapA.setImageResource(R.drawable.table_open); mMoneyTrapC.setImageResource(R.drawable.table_open);
                            mMoneyImageA.setImageDrawable(null); mMoneyImageC.setImageDrawable(null);
                            break;
                        case 3:
                            mMoneyTrapC.setImageResource(R.drawable.table_open);
                            mMoneyImageC.setImageDrawable(null);
                            break;
                    }
                    break;
                case 3:
                    mMoneyA = 0; mMoneyB = 0; mMoneyD = 0;
                    mMoney = mMoneyC; mMoneyC = 0;

                    switch(mLevel) {
                        case 1:
                            mMoneyTrapA.setImageResource(R.drawable.table_open); mMoneyTrapB.setImageResource(R.drawable.table_open); mMoneyTrapD.setImageResource(R.drawable.table_open);
                            mMoneyImageA.setImageDrawable(null); mMoneyImageB.setImageDrawable(null); mMoneyImageD.setImageDrawable(null);
                            break;
                        case 2:
                            mMoneyTrapA.setImageResource(R.drawable.table_open); mMoneyTrapB.setImageResource(R.drawable.table_open);
                            mMoneyImageA.setImageDrawable(null); mMoneyImageB.setImageDrawable(null);
                            break;
                        case 3:
                            mMoneyTrapB.setImageResource(R.drawable.table_open);
                            mMoneyImageB.setImageDrawable(null);
                            break;
                    }
                    break;
                case 4:
                    mMoneyA = 0; mMoneyB = 0; mMoneyC = 0;
                    mMoney = mMoneyD; mMoneyD = 0;

                    mMoneyTrapA.setImageResource(R.drawable.table_open); mMoneyTrapB.setImageResource(R.drawable.table_open); mMoneyTrapC.setImageResource(R.drawable.table_open);
                    mMoneyImageA.setImageDrawable(null); mMoneyImageB.setImageDrawable(null); mMoneyImageC.setImageDrawable(null);
                    break;
            }
            hand.postDelayed(new Runnable() {
                @Override
                public void run() {
                    if (mMoney == 0) {
                        loseDialog.show(mFragmentManager, "OK");
                    } else {
                        winDialog.show(mFragmentManager, "OK");
                    }
                    checker();
                }
            },1500);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public static void checker() {
        mTextMoney.setText(mMoney + " " + mCurrency);
        switch (mRoundNum) {
            case 1:
            case 2:
            case 3:
            case 4:
            case 5:
                mMoneySetA.setText(mMoneyA.toString());
                mMoneySetB.setText(mMoneyB.toString());
                mMoneySetC.setText(mMoneyC.toString());
                mMoneySetD.setText(mMoneyD.toString());
                break;
            case 6:
            case 7:
            case 8:
                mMoneySetA.setText(mMoneyA.toString());
                mMoneySetB.setText(mMoneyB.toString());
                mMoneySetC.setText(mMoneyC.toString());
                break;
            case 9:
            case 10:
                mMoneySetB.setText(mMoneyB.toString());
                mMoneySetC.setText(mMoneyC.toString());
                break;
        }
        imageSetter(mMoneyImageA, mMoneyA);
        imageSetter(mMoneyImageB, mMoneyB);
        imageSetter(mMoneyImageC, mMoneyC);
        imageSetter(mMoneyImageD, mMoneyD);
        imageSetter(mPula, mMoney);
        if (mMoney == 0) {
            mMoneyDrag.setImageDrawable(null);
            mCheckB.setVisibility(View.VISIBLE);
        } else {
            mMoneyDrag.setImageResource(R.drawable.money_drag);
            mCheckB.setVisibility(View.INVISIBLE);
        }
    }

    public static void imageSetter(ImageView img, Integer money) {
        if (money == 0) img.setImageDrawable(null);
        if (money > 0 && money <= 50000) img.setImageResource(R.drawable.pula_1);
        if (money > 50000 && money <= 100000) img.setImageResource(R.drawable.pula_2);
        if (money > 100000 && money <= 150000) img.setImageResource(R.drawable.pula_3);
        if (money > 150000 && money <= 200000) img.setImageResource(R.drawable.pula_4);
        if (money > 200000 && money <= 250000) img.setImageResource(R.drawable.pula_5);
        if (money > 250000 && money <= 300000) img.setImageResource(R.drawable.pula_6);
        if (money > 300000 && money <= 350000) img.setImageResource(R.drawable.pula_7);
        if (money > 350000 && money <= 400000) img.setImageResource(R.drawable.pula_8);
        if (money > 400000 && money <= 450000) img.setImageResource(R.drawable.pula_9);
        if (money > 450000 && money <= 500000) img.setImageResource(R.drawable.pula_10);
        if (money > 500000 && money <= 550000) img.setImageResource(R.drawable.pula_11);
        if (money > 550000 && money <= 600000) img.setImageResource(R.drawable.pula_12);
        if (money > 600000 && money <= 650000) img.setImageResource(R.drawable.pula_13);
        if (money > 650000 && money <= 700000) img.setImageResource(R.drawable.pula_14);
        if (money > 700000 && money <= 750000) img.setImageResource(R.drawable.pula_15);
        if (money > 750000 && money <= 800000) img.setImageResource(R.drawable.pula_16);
        if (money > 800000 && money <= 850000) img.setImageResource(R.drawable.pula_17);
        if (money > 850000 && money <= 900000) img.setImageResource(R.drawable.pula_18);
        if (money > 900000 && money <= 950000) img.setImageResource(R.drawable.pula_19);
        if (money > 950000 && money <= 1000000) img.setImageResource(R.drawable.pula_20);
    }

    @Override
    public void downloadCompleted() {
        try {
            mEasyArray = new JSONArray("[\n" +
                    "{\n" +
                    "\"category\":\"SPORT\",\n" +
                    "\"question\":\"Rafael Nadal to słynny tenisista ...?\",\n" +
                    "\"answer_1\":\"portugalski\",\n" +
                    "\"answer_2\":\"włoski\",\n" +
                    "\"answer_3\":\"hiszpański\",\n" +
                    "\"answer_4\":\"grecki\",\n" +
                    "\"correct\":\"3\"\n" +
                    "},{\n" +
                    "\"category\":\"BIBLIA\",\n" +
                    "\"question\":\"Z ilu ksiąg w Biblii składa się Stary Testament?\",\n" +
                    "\"answer_1\":\"46\",\n" +
                    "\"answer_2\":\"22\",\n" +
                    "\"answer_3\":\"4\",\n" +
                    "\"answer_4\":\"11\",\n" +
                    "\"correct\":\"1\"\n" +
                    "},{\n" +
                    "\"category\":\"MITOLOGIA\",\n" +
                    "\"question\":\"Bogini chaosu , niezgody o złośliwym charakterze to?\",\n" +
                    "\"answer_1\":\"Hera\",\n" +
                    "\"answer_2\":\"Ate\",\n" +
                    "\"answer_3\":\"Dysmonia\",\n" +
                    "\"answer_4\":\"Eris\",\n" +
                    "\"correct\":\"4\"\n" +
                    "},{\n" +
                    "\"category\":\"MOTORYZACJA\",\n" +
                    "\"question\":\"Grand Scenic to model samochodu marki ...?\",\n" +
                    "\"answer_1\":\"Peugeot\",\n" +
                    "\"answer_2\":\"Renault\",\n" +
                    "\"answer_3\":\"Citroen\",\n" +
                    "\"answer_4\":\"Ford\",\n" +
                    "\"correct\":\"2\"\n" +
                    "},{\n" +
                    "\"category\":\"SKRZYPCE\",\n" +
                    "\"question\":\"Ile koncertów skrzypcowych napisał Chopin?\",\n" +
                    "\"answer_1\":\"0\",\n" +
                    "\"answer_2\":\"2\",\n" +
                    "\"answer_3\":\"6\",\n" +
                    "\"answer_4\":\"7\",\n" +
                    "\"correct\":\"1\"\n" +
                    "},{\n" +
                    "\"category\":\"CZŁOWIEK\",\n" +
                    "\"question\":\"Ile w organizmie znajduje się litrów krwi?\",\n" +
                    "\"answer_1\":\"1-2 litry\",\n" +
                    "\"answer_2\":\"3-4 litry\",\n" +
                    "\"answer_3\":\"5-6 litrów\",\n" +
                    "\"answer_4\":\"7-8 litrów\",\n" +
                    "\"correct\":\"3\"\n" +
                    "},{\n" +
                    "\"category\":\"MUZYKA\",\n" +
                    "\"question\":\"Kto jest liderem grupy Kombi?\",\n" +
                    "\"answer_1\":\"Jan Borysewicz\",\n" +
                    "\"answer_2\":\"Janusz Panasewicz\",\n" +
                    "\"answer_3\":\"Grzegorz Markowski\",\n" +
                    "\"answer_4\":\"Grzegorz Skawiński\",\n" +
                    "\"correct\":\"4\"\n" +
                    "},{\n" +
                    "\"category\":\"FILM\",\n" +
                    "\"question\":\"W polskiej komedii 'Och, Karol 2' rolę tytułowego Karola zagrał...?\",\n" +
                    "\"answer_1\":\"Piotr Adamczyk\",\n" +
                    "\"answer_2\":\"Cezaty Pazura\",\n" +
                    "\"answer_3\":\"Andrzej Grabowski\",\n" +
                    "\"answer_4\":\"Borys Szyc\",\n" +
                    "\"correct\":\"1\"\n" +
                    "},{\n" +
                    "\"category\":\"SPECJALISTA\",\n" +
                    "\"question\":\"Lekarz leczący zęby nazywa się...?\",\n" +
                    "\"answer_1\":\"Ortopeda\",\n" +
                    "\"answer_2\":\"Dentysta\",\n" +
                    "\"answer_3\":\"Rodzinny\",\n" +
                    "\"answer_4\":\"Okulista\",\n" +
                    "\"correct\":\"2\"\n" +
                    "},{\n" +
                    "\"category\":\"SMARTFON\",\n" +
                    "\"question\":\"CloudMobile A to smartfon produkowany przez firmę?\",\n" +
                    "\"answer_1\":\"Nokia\",\n" +
                    "\"answer_2\":\"Samsung\",\n" +
                    "\"answer_3\":\"Acer\",\n" +
                    "\"answer_4\":\"LG\",\n" +
                    "\"correct\":\"3\"\n" +
                    "}\n" +
                    "]");
            mMediumArray = new JSONArray("[\n" +
                    "{\n" +
                    "\"category\":\"PRZYSŁOWIA\",\n" +
                    "\"question\":\"Co nie przynosi pecha?\",\n" +
                    "\"answer_1\":\"Skradziony zegarek\",\n" +
                    "\"answer_2\":\"Rozsypana sól\",\n" +
                    "\"answer_3\":\"Stłuczone lustro\",\n" +
                    "\"correct\":\"1\"\n" +
                    "},\n" +
                    "{\n" +
                    "\"category\":\"MUZYKA\",\n" +
                    "\"question\":\"W jakim kraju urodziła się Ewa Farna?\",\n" +
                    "\"answer_1\":\"Polska\",\n" +
                    "\"answer_2\":\"Serbia\",\n" +
                    "\"answer_3\":\"Czechy\",\n" +
                    "\"correct\":\"3\"\n" +
                    "},\n" +
                    "{\n" +
                    "\"category\":\"ZNAK ZODIAKU\",\n" +
                    "\"question\":\"Jaki jest Twój znak zodiaku jesli urodziłeś się 15 lipca?\",\n" +
                    "\"answer_1\":\"Byk\",\n" +
                    "\"answer_2\":\"Rak\",\n" +
                    "\"answer_3\":\"Lew\",\n" +
                    "\"correct\":\"2\"\n" +
                    "},\n" +
                    "{\n" +
                    "\"category\":\"FIRMA\",\n" +
                    "\"question\":\"Z jakiego kraju pochodzi firma Lenovo?\",\n" +
                    "\"answer_1\":\"USA\",\n" +
                    "\"answer_2\":\"Japonia\",\n" +
                    "\"answer_3\":\"Chiny\",\n" +
                    "\"correct\":\"3\"\n" +
                    "},\n" +
                    "{\n" +
                    "\"category\":\"DELFINY\",\n" +
                    "\"question\":\"Co jest prawdą o delfinach?\",\n" +
                    "\"answer_1\":\"Nie występują w Europie\",\n" +
                    "\"answer_2\":\"Są ssakami\",\n" +
                    "\"answer_3\":\"Istnieją tylko dwa gatunki\",\n" +
                    "\"correct\":\"2\"\n" +
                    "},\n" +
                    "{\n" +
                    "\"category\":\"CHOROBA\",\n" +
                    "\"question\":\"Chorobami układu nerwowego zajmuje się?\",\n" +
                    "\"answer_1\":\"Neurolog\",\n" +
                    "\"answer_2\":\"Nefrolog\",\n" +
                    "\"answer_3\":\"Psychiatra\",\n" +
                    "\"correct\":\"1\"\n" +
                    "}\n" +
                    "]");
            mHardArray = new JSONArray("[\n" +
                    "{\n" +
                    "\"category\":\"KUCHNIA\",\n" +
                    "\"question\":\"Carpaccio to danie kuchni?\",\n" +
                    "\"answer_1\":\"Włoskiej\",\n" +
                    "\"answer_2\":\"Francuskiej\",\n" +
                    "\"correct\":\"2\"\n" +
                    "},\n" +
                    "{\n" +
                    "\"category\":\"KRESKÓWKA\",\n" +
                    "\"question\":\"W jakim mieście mieszka Johny Bravo?\",\n" +
                    "\"answer_1\":\"Acme City\",\n" +
                    "\"answer_2\":\"Aron City\",\n" +
                    "\"correct\":\"3\"\n" +
                    "},\n" +
                    "{\n" +
                    "\"category\":\"MIASTO\",\n" +
                    "\"question\":\"W jakim miescie znajduje sie Sky Tower?\",\n" +
                    "\"answer_1\":\"w Warszawie\",\n" +
                    "\"answer_2\":\"we Wrocławiu\",\n" +
                    "\"correct\":\"2\"\n" +
                    "},\n" +
                    "{\n" +
                    "\"category\":\"FILM\",\n" +
                    "\"question\":\"Jak nazywa się antynagroda przyznawana najgorszym filmom, która 'uzupełnia' Oscary?\",\n" +
                    "\"answer_1\":\"Złota Malina\",\n" +
                    "\"answer_2\":\"Zgniły pomidor\",\n" +
                    "\"correct\":\"2\"\n" +
                    "}\n" +
                    "]");
            Rounder();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public static void hideLogos() {
        switch(mLevel) {
            case 1:
                mLogo1.startAnimation(anim_hide_logo);
                mLogo2.startAnimation(anim_hide_logo);
                mLogo3.startAnimation(anim_hide_logo);
                mLogo4.startAnimation(anim_hide_logo);
                mLogo1.setVisibility(View.INVISIBLE);
                mLogo2.setVisibility(View.INVISIBLE);
                mLogo3.setVisibility(View.INVISIBLE);
                mLogo4.setVisibility(View.INVISIBLE);
                break;
            case 2:
                mLogo1.startAnimation(anim_hide_logo);
                mLogo2.startAnimation(anim_hide_logo);
                mLogo3.startAnimation(anim_hide_logo);
                mLogo1.setVisibility(View.INVISIBLE);
                mLogo2.setVisibility(View.INVISIBLE);
                mLogo3.setVisibility(View.INVISIBLE);
                break;
            case 3:
                mLogo2.startAnimation(anim_hide_logo);
                mLogo3.startAnimation(anim_hide_logo);
                mLogo2.setVisibility(View.INVISIBLE);
                mLogo3.setVisibility(View.INVISIBLE);
                break;
        }
    }
}

//class QDownloader extends AsyncTask<Void, Void, String[]> {
//    DownloadComplete listener;
//    String[] baseHttpUrl, arrayJsonQ;
//
//    public QDownloader(DownloadComplete listener) {
//        this.listener = listener;
//        arrayJsonQ = new String[3];
//    }
//
//    @Override
//    protected String[] doInBackground(Void... params) {
//        try {
//            for (int i = 0; i <= 2; i++) {
//                URL url = new URL(baseHttpUrl[i]);
//                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
//                conn.connect();
//
//                InputStream in = conn.getInputStream();
//                StringBuilder stringBuilder = new StringBuilder();
//                BufferedReader reader = new BufferedReader(new InputStreamReader(in));
//                String line;
//                while ((line = reader.readLine()) != null) {
//                    stringBuilder.append(line);
//                }
//
//                arrayJsonQ[i] = stringBuilder.toString();
//            }
//            return arrayJsonQ;
//        } catch (MalformedURLException e) {
//            return null;
//        } catch (IOException e) {
//            return null;
//        }
//    }
//
//    @Override
//    protected void onPostExecute(String[] result) {
//        if (result != null) {
//            listener.downloadCompleted(result);
//        } else {
//
//        }
//    }
//}