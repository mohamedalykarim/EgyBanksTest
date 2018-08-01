package mohalim.android.egybankstest.Fragments;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.widget.DatePicker;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;

public class DatePickerFragment extends DialogFragment
        implements DatePickerDialog.OnDateSetListener{

    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    DatabaseReference userReference;
    String date;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the current date as the default date in the picker
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);


        if (mAuth.getCurrentUser() != null){
            userReference = FirebaseDatabase.getInstance().getReference("users")
                    .child(mAuth.getUid()).child("birthdate");

        }



        // Create a new instance of DatePickerDialog and return it
        return new DatePickerDialog(getActivity(), this, year, month, day);
    }

    public void onDateSet(DatePicker view, int year, int month, int day) {
        date = day+"/"+month+"/"+year;

        if (mAuth.getCurrentUser() != null){
            userReference.setValue(date);
        }
    }





}
