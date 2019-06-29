package app.being.pattadonjaisin.beingwellness;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */

public class Addweek extends Fragment {

    RecyclerView listViewfood3;
    ImageButton butpush3;
    Button butadd3;

    public String user_id ;
    ArrayList<Weekmanul> foodlist3;
    FirebaseDatabase addfood3 = FirebaseDatabase.getInstance(); //เชื่อมดาต้าเบส
    FoodList.Putex putex3;
    DatabaseReference addcal3 = addfood3.getReference(); //ใช้งานแล้วอ้างอิงไป keepcal
    List<Weekmanul> foods3;
    String key;
    public Addweek() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        FirebaseUser auth = FirebaseAuth.getInstance().getCurrentUser();

        user_id = auth.getUid();

        View view = inflater.inflate(R.layout.fragment_addyear, container, false);
        listViewfood3 = view.findViewById(R.id.listviwfood3);
        addcal3 = FirebaseDatabase.getInstance().getReference("showfoodyear").child(user_id);



        foods3 = new ArrayList<>();
        foodlist3 = new ArrayList<>();


        return view;
    }

    @Override
    public void onStart() {
        addcal3.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.getValue() != null){
                    foodlist3.clear();
                    int i = 0;
                    for (DataSnapshot foodSnapshot : dataSnapshot.getChildren()) {
                        foodlist3.add(foodSnapshot.getValue(Weekmanul.class));
                        foodlist3.get(i).setKey2(foodSnapshot.getKey());
                        i++;
                    }
                }


                listViewfood3.setLayoutManager(new LinearLayoutManager(getContext()));
                listViewfood3.setHasFixedSize(true);
                Weeklist adapter = new Weeklist(getContext(),foodlist3);
                listViewfood3.setAdapter(adapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        super.onStart();
    }

    @Override
    public void onAttach(Context context) { //ดึงเมธอธจาก interface
        super.onAttach(context);
        if(context instanceof FoodList.Putex){
            this.putex3 = (FoodList.Putex) context;
        }

    }
}

