package com.jafir.mybookexplore;

import android.content.Context;
import android.preference.DialogPreference;
import android.util.AttributeSet;
import android.widget.SeekBar;

/**
 * Created by jafir on 16/12/12.
 */

public class SeekBarPreference extends DialogPreference implements SeekBar.OnSeekBarChangeListener {

    public SeekBarPreference(Context context, AttributeSet attrs) {
        super(context, attrs);
        // TODO Auto-generated constructor stub
    }
    @Override
    public void onProgressChanged(SeekBar seekBar, int progress,
                                  boolean fromUser) {
        // TODO Auto-generated method stub

    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {
        // TODO Auto-generated method stub

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
        // TODO Auto-generated method stub

    }

}