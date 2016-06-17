package me.flyingrub.beermeter;

import android.os.Bundle;
import android.preference.PreferenceActivity;

/**
 * Created by fallstar on 17/06/16.
 */
public class Settings extends PreferenceActivity {


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.settings);
    }
}
