package app.being.pattadonjaisin.beingwellness;


import android.content.Context;
import android.content.Intent;
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
public class Addfood extends Fragment {
public static final String Food_name = "foodname";
    public static final String Food_cal = "foodcal";

    RecyclerView listViewfood;
    ImageButton butpush;
    Button butadd;
    String user_id;

    ArrayList<Foodmanul> foodlist;
    FirebaseDatabase addfood = FirebaseDatabase.getInstance(); //เชื่อมดาต้าเบส
    FoodList.Putex putex;
    DatabaseReference addcal = addfood.getReference(); //ใช้งานแล้วอ้างอิงไป keepcal
List<Foodmanul> foods;
    public Addfood() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        FirebaseUser auth = FirebaseAuth.getInstance().getCurrentUser();

        user_id = auth.getUid();

        View view = inflater.inflate(R.layout.fragment_addfood, container, false);
        listViewfood = view.findViewById(R.id.listviwfood);
        addcal = FirebaseDatabase.getInstance().getReference("manuladd").child(user_id);

        butpush = view.findViewById(R.id.imgCmdShared);


foods = new ArrayList<>();
        foodlist = new ArrayList<>();
        butadd = view.findViewById(R.id.btn_add);
        butadd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), Acitivityadd.class);
                getContext().startActivity(intent);

            }
        });

        return view;
    }

    @Override
    public void onStart() {
        addcal.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                foodlist.clear();
                int i = 0;
                for (DataSnapshot foodSnapshot : dataSnapshot.getChildren()) {
                    foodlist.add(foodSnapshot.getValue(Foodmanul.class));
                    foodlist.get(i).setKey(foodSnapshot.getKey());
                    i++;
                }
                listViewfood.setLayoutManager(new LinearLayoutManager(getContext()));
                listViewfood.setHasFixedSize(true);
                FoodList adapter = new FoodList(getContext(),foodlist,putex);
                listViewfood.setAdapter(adapter);

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
            this.putex = (FoodList.Putex) context;
        }

    }
}
