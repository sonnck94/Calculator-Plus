package com.example.arch1.testapplication;

import android.content.res.TypedArray;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import androidx.appcompat.widget.Toolbar;

import android.util.TypedValue;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Switch;

public class GeneralSettingsActivity extends AppCompatActivity {

    private AppPreferences preferences;
    private Switch numberFormatterSwitch, smartCalculationSwitch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        preferences = AppPreferences.getInstance(this);
        setTheme(Theme.getTheme(preferences.getStringPreference(AppPreferences.APP_THEME)));

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_general_settings);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        String themeName = preferences.getStringPreference(AppPreferences.APP_THEME);

        TypedValue typedValue = new TypedValue();
        TypedArray a = obtainStyledAttributes(typedValue.data, new int[]{R.attr.colorPrimary});
        int color = a.getColor(0, 0);
        a.recycle();

        if (themeName.equals(Theme.DEFAULT)) {
            color = getResources().getColor(R.color.colorMaterialSteelGrey);
            toolbar.setTitleTextColor(getResources().getColor(R.color.colorWhite));
            toolbar.setNavigationIcon(R.drawable.ic_arrow_back_white_24dp);
        } else if (themeName.equals(Theme.MATERIAL_LIGHT)) {
            toolbar.setTitleTextColor(getResources().getColor(R.color.gray));
            toolbar.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp);
        } else {
            toolbar.setTitleTextColor(getResources().getColor(R.color.colorWhite));
            toolbar.setNavigationIcon(R.drawable.ic_arrow_back_white_24dp);
        }

        //setting toolbar style manually
        toolbar.setBackgroundColor(color);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        numberFormatterSwitch = findViewById(R.id.switch1);
        smartCalculationSwitch = findViewById(R.id.switch2);


        //setting preference values
        numberFormatterSwitch.setChecked(preferences.getBooleanPreference(AppPreferences.APP_NUMBER_FORMATTER));
        smartCalculationSwitch.setChecked(preferences.getBooleanPreference(AppPreferences.APP_SMART_CALCULATIONS));


        numberFormatterSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                preferences.setBooleanPreference(AppPreferences.APP_NUMBER_FORMATTER, isChecked);
            }
        });

        smartCalculationSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (!isChecked) {
                    //show warning
                    AlertDialog.Builder builder = new AlertDialog.Builder(GeneralSettingsActivity.this);
                    builder.setTitle("Warning")
                            .setMessage("This action will disable smart calculations. Calculator Plus" +
                                    " will no longer be able to auto-complete or auto-correct " +
                                    "your equations. We recommend to enable this feature for faster and " +
                                    "easy usage of Calculator Plus.")
                            .setPositiveButton("Ok", null);
                    builder.show();
                }
                preferences.setBooleanPreference(AppPreferences.APP_SMART_CALCULATIONS, isChecked);
            }
        });

    }

    public void smartCalClick(View view) {
        smartCalculationSwitch.setChecked(!smartCalculationSwitch.isChecked());
    }

    public void numFormatClick(View view) {
        numberFormatterSwitch.setChecked(!numberFormatterSwitch.isChecked());
    }
}
