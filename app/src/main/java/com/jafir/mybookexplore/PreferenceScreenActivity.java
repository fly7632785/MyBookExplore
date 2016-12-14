package com.jafir.mybookexplore;

import android.os.Bundle;
import android.preference.CheckBoxPreference;
import android.preference.Preference;
import android.preference.PreferenceActivity;

/**
 * Created by jafir on 16/12/12.
 */

public class PreferenceScreenActivity extends PreferenceActivity {


    private CheckBoxPreference chekcb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        addPreferencesFromResource(R.xml.layout_screen);
//        init();
        getFragmentManager().beginTransaction().replace(android.R.id.content, new PrefernceScreenFragment()).commit();
    }

    private void init() {
        chekcb = (CheckBoxPreference) findPreference("autoBack");//这里就像findViewById一样,不过这里的id其实是key的值..
        //下面设置监听器
        chekcb.setOnPreferenceChangeListener(new myPreferencesChance());


    }

    class myPreferencesChance implements Preference.OnPreferenceChangeListener {

        @Override
        public boolean onPreferenceChange(Preference preference, Object newValue) {
            // TODO Auto-generated method stubd
            System.out.println("newValue" + newValue);
            return true;
        }
    }
}
