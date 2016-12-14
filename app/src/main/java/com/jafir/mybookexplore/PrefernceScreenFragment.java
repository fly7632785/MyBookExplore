package com.jafir.mybookexplore;

import android.os.Bundle;
import android.preference.CheckBoxPreference;
import android.preference.ListPreference;
import android.preference.MultiSelectListPreference;
import android.preference.Preference;
import android.preference.PreferenceFragment;

/**
 * Created by jafir on 16/12/12.
 */

public class PrefernceScreenFragment extends PreferenceFragment {


    private CheckBoxPreference chekcb;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.layout_screen);
        init();
    }

    private void init() {
        chekcb = (CheckBoxPreference) findPreference("autoBack");//这里就像findViewById一样,不过这里的id其实是key的值..
        //下面设置监听器
        chekcb.setOnPreferenceChangeListener(new myPreferencesChance());


        /**
         * 需要在代码里面设置 entries 和 entryValues
         * xml 不起作用
         * 但是 defaultValue要在xml指定，且需要在entryValues中的 一个
         */
        ListPreference listPrefer = (ListPreference) findPreference("list1");//这里就像findViewById一样,不过这里的id其实是key的值..
        listPrefer.setEntries(new String[]{"1", "2", "3", "4", "5"});
        listPrefer.setEntryValues(new String[]{"1", "2", "3", "4", "5"});
        listPrefer.setDefaultValue("5");

        /**
         * 需要在代码里面设置 entries 和 entryValues
         * xml 不起作用
         * 但是 defaultValue貌似不可以指定 在xml中  代码中也无效
         */
        MultiSelectListPreference m = (MultiSelectListPreference) findPreference("MultiSelectListPreference");
//        m.setEntryValues(new String[]{"1", "2", "3", "4", "5"});
//        m.setEntries(new String[]{"1", "2", "3", "4", "5"});
//        m.setDefaultValue(new String[]{"2","3"});


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
