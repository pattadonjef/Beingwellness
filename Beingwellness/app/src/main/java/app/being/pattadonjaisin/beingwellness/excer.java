package app.being.pattadonjaisin.beingwellness;


import android.animation.ObjectAnimator;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;


/**
 * A simple {@link Fragment} subclass.
 */
public class excer extends Fragment {
    SeekBar seekBar;
    String user_id;

    int[] risklist = new int[4];
    int calresult;
    ArrayList listdata;
    EditText editTextcal;
    Button bootnplus;
    TextView textrisk;
    FirebaseDatabase keepcal = FirebaseDatabase.getInstance(); //เชื่อมดาต้าเบส
    DatabaseReference addcal = keepcal.getReference(); //ใช้งานแล้วอ้างอิงไป keepcal

    public excer() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_excer, container, false);

        FirebaseUser auth = FirebaseAuth.getInstance().getCurrentUser();

        user_id = auth.getUid();

        addcal = FirebaseDatabase.getInstance().getReference("risk").child(user_id);
        listdata = new ArrayList<>();

        textrisk = view.findViewById(R.id.textrisk);
        editTextcal = view.findViewById(R.id.editTextcal);
        bootnplus = view.findViewById(R.id.button2);

        checkriskfirst();
        bootnplus.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {


                addcal.child("totalcal").setValue(risklist[0]);
                addcal.child("totalsug").setValue(risklist[1]);
                addcal.child("totalsod").setValue(risklist[2]);
                addcal.child("totalfat").setValue(risklist[3]);
                checkrisk();

            }
        });



//        slidr.setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View v, MotionEvent event) {
//                return true;
//            }
//        });


        return view;
    }
    public void checkriskfirst(){

        addcal.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.getValue() != null){
                    risklist[0] = Integer.parseInt(dataSnapshot.child("totalcal").getValue().toString());
                    risklist[1] = Integer.parseInt(dataSnapshot.child("totalsug").getValue().toString()) ;
                    risklist[2] = Integer.parseInt(dataSnapshot.child("totalsod").getValue().toString());
                    risklist[3] = Integer.parseInt(dataSnapshot.child("totalfat").getValue().toString()) ;
                    int max = risklist[0];
                    int index = 0;
                    for (int i = 0; i < risklist.length; i++) {
                        if (risklist[i] > max) {
                            max = risklist[i];
                            index = i;
                        }
                    }
                    System.out.println();
                    if (index == 0) {
                        textrisk.setText(String.valueOf("โรคอ้วน"));
                    } else if (index == 1) {
                        textrisk.setText(String.valueOf("โรคเบาหวาน"));

                    } else if (index == 2) {
                        textrisk.setText(String.valueOf("โรคไต"));

                    } else {
                        textrisk.setText(String.valueOf("โรคไขมันอุดตันในเส้นเลือด"));

                    }
                }




            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }



    public void checkrisk(){
        calresult = Integer.parseInt(editTextcal.getText().toString()) * 10;
        final int sugresult = Integer.parseInt(editTextcal.getText().toString()) * 20;
        final int sodresult = Integer.parseInt(editTextcal.getText().toString()) * 30 / 1000;
        final int fatresult = Integer.parseInt(editTextcal.getText().toString()) * 40;
        addcal.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                risklist[0] = Integer.parseInt(dataSnapshot.child("totalcal").getValue().toString()) - calresult;
                risklist[1] = Integer.parseInt(dataSnapshot.child("totalsug").getValue().toString()) - sugresult;
                risklist[2] = Integer.parseInt(dataSnapshot.child("totalsod").getValue().toString()) - sodresult;
                risklist[3] = Integer.parseInt(dataSnapshot.child("totalfat").getValue().toString()) - fatresult;
                int max = risklist[0];
                int index = 0;
                for (int i = 0; i < risklist.length; i++) {
                    if (risklist[i] > max) {
                        max = risklist[i];
                        index = i;
                    }
                }
                System.out.println();
                if (index == 0) {
                    textrisk.setText(String.valueOf("โรคอ้วน"));
                } else if (index == 1) {
                    textrisk.setText(String.valueOf("โรคเบาหวาน"));

                } else if (index == 2) {
                    textrisk.setText(String.valueOf("โรคไต"));

                } else {
                    textrisk.setText(String.valueOf("โรคไขมันอุดตันในเส้นเลือด"));

                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

}
