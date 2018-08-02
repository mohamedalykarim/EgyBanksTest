package mohalim.android.egybankstest;

import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.ArrayList;

import mohalim.android.egybankstest.Fragments.SubpageFragment;
import mohalim.android.egybankstest.Models.MainMenuItem;

public class BanqueMisrActivity extends AppCompatActivity {

    public String BANQUEMISR_MENU = "banquemisr_menu";
    Fragment alahlyFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_banque_misr);



        ArrayList<MainMenuItem> menuItems = new ArrayList<>();
        menuItems.add(new MainMenuItem(R.drawable.iq_icon,getString(R.string.iq_test), getString(R.string.iq_desc)));
        menuItems.add(new MainMenuItem(R.drawable.english_icon,getString(R.string.engish_test), getString(R.string.english_desc)));
        menuItems.add(new MainMenuItem(R.drawable.technical_icon,getString(R.string.technical_test), getString(R.string.technicat_desc)));

        if (getSupportFragmentManager().getFragments().size()==0){
            alahlyFragment = SubpageFragment.newInstance(menuItems,BANQUEMISR_MENU);
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.banquemisr_fragment,alahlyFragment)
                    .commit();
        }

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }
}
