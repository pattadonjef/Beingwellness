package app.being.pattadonjaisin.beingwellness;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

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
public class Addmonth extends Fragment {

    RecyclerView listViewfood2;
    ImageButton butpush2;
    Button butadd2;
    public String user_id ;
    ArrayList<Monthmanul> foodlist2;
    FirebaseDatabase addfood2 = FirebaseDatabase.getInstance(); //เชื่อมดาต้าเบส
    FoodList.Putex putex2;
    DatabaseReference addcal2 = addfood2.getReference(); //ใช้งานแล้วอ้างอิงไป keepcal
    List<Monthmanul> foods2;
    String key;
    public Addmonth() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        FirebaseUser auth = FirebaseAuth.getInstance().getCurrentUser();

        user_id = auth.getUid();

        View view = inflater.inflate(R.layout.fragment_addmonth, container, false);
        listViewfood2 = view.findViewById(R.id.listviwfood2);
        addcal2 = FirebaseDatabase.getInstance().getReference("showfoodmonth").child(user_id);



        foods2 = new ArrayList<>();
        foodlist2 = new ArrayList<>();


        return view;
    }

    @Override
    public void onStart() {
        addcal2.addValueEventListener(new ValueEventListener() {
            @Override

            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.getValue() != null){
                    foodlist2.clear();
                int i = 0;
                for (DataSnapshot foodSnapshot : dataSnapshot.getChildren()) {
                    foodlist2.add(foodSnapshot.getValue(Monthmanul.class));
                    foodlist2.get(i).setKey2(foodSnapshot.getKey());
                    i++;
                }
                listViewfood2.setLayoutManager(new LinearLayoutManager(getContext()));
                listViewfood2.setHasFixedSize(true);
                MonthList adapter = new MonthList(getContext(), foodlist2);
                listViewfood2.setAdapter(adapter);
            }
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
            this.putex2 = (FoodList.Putex) context;
        }

    }

}
