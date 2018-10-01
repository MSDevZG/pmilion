package dev.m.skrzypka.pmilion.Listeners;

import android.view.DragEvent;
import android.view.View;

import dev.m.skrzypka.pmilion.Fragments.BetFragment;

import static dev.m.skrzypka.pmilion.Fragments.GameFragment.mMoney;
import static dev.m.skrzypka.pmilion.Fragments.GameFragment.mObstA;
import static dev.m.skrzypka.pmilion.Fragments.GameFragment.mObstB;
import static dev.m.skrzypka.pmilion.Fragments.GameFragment.mObstC;
import static dev.m.skrzypka.pmilion.Fragments.GameFragment.mObstD;
import static dev.m.skrzypka.pmilion.Fragments.GameFragment.mRoundNum;
import static dev.m.skrzypka.pmilion.MainActivity.mFragmentManager;

public class PMDragListener implements View.OnDragListener {

    @Override
    public boolean onDrag(View v, DragEvent event) {
        switch (event.getAction()) {
            case DragEvent.ACTION_DRAG_STARTED:
                break;
            case DragEvent.ACTION_DRAG_ENTERED:
                break;
            case DragEvent.ACTION_DRAG_EXITED:
                break;
            case DragEvent.ACTION_DROP:
                switch(v.getTag().toString()) {
                    case "trapA":
                        betA();
                        break;
                    case "trapB":
                        betB();
                        break;
                    case "trapC":
                        betC();
                        break;
                    case "trapD":
                        betD();
                        break;
                    default:
                        break;
                }
                break;
            case DragEvent.ACTION_DRAG_ENDED:
                break;
            default:
                break;
        }
        return true;
    }

    public void betA(){
        switch (mRoundNum) {
            case 1:
            case 2:
            case 3:
            case 4:
            case 5: {
                if (mMoney > 0) {
                    if (mObstB + mObstC + mObstD != 3) {
                        BetFragment bF = new BetFragment();
                        bF.show(mFragmentManager, "A");
                    }
                }
            }
            break;
            case 6:
            case 7:
            case 8: {
                if (mMoney > 0) {
                    if (mObstB + mObstC != 2) {
                        BetFragment bF = new BetFragment();
                        bF.show(mFragmentManager, "A");
                    }
                }
            }
            break;
            case 9:
            case 10:
                break;
        }
    }

    public void betB(){
        switch (mRoundNum) {
            case 1:
            case 2:
            case 3:
            case 4:
            case 5: {
                if (mMoney > 0) {
                    if (mObstA + mObstC + mObstD != 3) {
                        BetFragment bF = new BetFragment();
                        bF.show(mFragmentManager, "B");
                    }
                }
            }
            break;
            case 6:
            case 7:
            case 8: {
                if (mMoney > 0) {
                    if (mObstA + mObstC != 2) {
                        BetFragment bF = new BetFragment();
                        bF.show(mFragmentManager, "B");
                    }
                }
            }
            break;
            case 9:
            case 10: {
                if (mMoney > 0) {
                    if (mObstC != 1) {
                        BetFragment bF = new BetFragment();
                        bF.show(mFragmentManager, "B");
                    }
                }
            }
            break;
        }
    }
    public void betC(){
        switch (mRoundNum) {
            case 1:
            case 2:
            case 3:
            case 4:
            case 5: {
                if (mMoney > 0) {
                    if (mObstA + mObstB + mObstD != 3) {
                        BetFragment bF = new BetFragment();
                        bF.show(mFragmentManager, "C");
                    }
                }
            }
            break;
            case 6:
            case 7:
            case 8: {
                if (mMoney > 0) {
                    if (mObstA + mObstB != 2) {
                        BetFragment bF = new BetFragment();
                        bF.show(mFragmentManager, "C");
                    }
                }
            }
            break;
            case 9:
            case 10: {
                if (mMoney > 0) {
                    if (mObstB != 1) {
                        BetFragment bF = new BetFragment();
                        bF.show(mFragmentManager, "C");
                    }
                }
            }
            break;
        }
    }
    public void betD(){
        switch (mRoundNum) {
            case 1:
            case 2:
            case 3:
            case 4:
            case 5: {
                if (mMoney > 0) {
                    if (mObstA + mObstB + mObstC != 3) {
                        BetFragment bF = new BetFragment();
                        bF.show(mFragmentManager, "D");
                    }
                }
            }
            break;
            case 6:
            case 7:
            case 8:
                break;
            case 9:
            case 10:
                break;
        }
    }
}
