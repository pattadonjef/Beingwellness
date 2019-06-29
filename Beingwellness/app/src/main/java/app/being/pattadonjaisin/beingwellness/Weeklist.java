package app.being.pattadonjaisin.beingwellness;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableLayout;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Random;

import static android.graphics.Color.rgb;


public class Weeklist extends RecyclerView.Adapter<Weeklist.ViewHolder> {

    FirebaseDatabase addfood3 = FirebaseDatabase.getInstance(); //เชื่อมดาต้าเบส

    DatabaseReference addcal = addfood3.getReference(); //ใช้งานแล้วอ้างอิงไป keepcal
    private Context context3;
    CardView cardView;

    ArrayList<Weekmanul> foodlists3;

    public Weeklist(Context context, ArrayList<Weekmanul> weekmanuls) {
        this.context3 = context;
        foodlists3 = weekmanuls;


    }



    @NonNull
    @Override
    public Weeklist.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.listyears, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Weeklist.ViewHolder viewHolder, int i) {
        viewHolder.textViewcal3.setText(foodlists3.get(i).getTotalcal());
        viewHolder.textViewname3.setText(foodlists3.get(i).getKey2());
        viewHolder.textViewsug3.setText(foodlists3.get(i).getTotalsug().toString());
        viewHolder.textViewsod3.setText(foodlists3.get(i).getTotalsod().toString());
        viewHolder.textViewfat3.setText(foodlists3.get(i).getTotalsug().toString());



    }

    @Override
    public int getItemCount() {
        return (foodlists3 == null ? 0 : foodlists3.size());
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView textViewname3, textViewcal3, textViewsug3, textViewsod3, textViewfat3;
        TableLayout tableview2;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewcal3 = itemView.findViewById(R.id.weekcal);
            textViewname3 = itemView.findViewById(R.id.weekname);
            textViewsug3 = itemView.findViewById(R.id.weeksug);
            textViewsod3 = itemView.findViewById(R.id.weeksod);
            textViewfat3 = itemView.findViewById(R.id.weekfat);


            cardView = itemView.findViewById(R.id.cardyear);


            int[] array = new int[12];
            array[0] = rgb(85, 239, 196);
            array[1] = rgb(0, 184, 148);
            array[2] = rgb(255, 234, 167);
            array[3] = rgb(129, 236, 236);
            array[4] = rgb(0, 206, 201);
            array[5] = rgb(250, 177, 160);
            array[6] = rgb(116, 185, 255);
            array[7] = rgb(162, 155, 254);
            array[8] = rgb(255, 118, 117);
            array[9] = rgb(247, 215, 148);
            array[10] = rgb(253, 121, 168);
            array[11] = rgb(99, 205, 218);

            int randomColor = array[new Random().nextInt(array.length)];
            cardView.setCardBackgroundColor(randomColor);        }


    }
}