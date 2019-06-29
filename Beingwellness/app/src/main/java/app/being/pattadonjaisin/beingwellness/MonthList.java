package app.being.pattadonjaisin.beingwellness;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Random;

import static android.graphics.Color.rgb;


public class MonthList extends RecyclerView.Adapter<MonthList.ViewHolder> {

    FirebaseDatabase addfood2 = FirebaseDatabase.getInstance(); //เชื่อมดาต้าเบส

    DatabaseReference addcal = addfood2.getReference(); //ใช้งานแล้วอ้างอิงไป keepcal
    private Context context2;
    CardView cardView;

    ArrayList<Monthmanul> foodlists2;

    public MonthList(Context context, ArrayList<Monthmanul> foodlist) {
        this.context2 = context;
        foodlists2 = foodlist;


    }


    @NonNull
    @Override
    public MonthList.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.listmonth, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MonthList.ViewHolder viewHolder, int i) {
        viewHolder.textViewname2.setText(foodlists2.get(i).getKey2().toString());
        viewHolder.textViewcal2.setText(foodlists2.get(i).getTotalcal().toString());

        viewHolder.textViewsug.setText(foodlists2.get(i).getTotalsug().toString());
        viewHolder.textViewsod.setText(foodlists2.get(i).getTotalsod().toString());
        viewHolder.textViewfat.setText(foodlists2.get(i).getTotalfat().toString());

    }

    @Override
    public int getItemCount() {
        return (foodlists2 == null ? 0 : foodlists2.size());
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView textViewname2, textViewcal2, textViewsug, textViewsod, textViewfat;
        TableLayout tableview2;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewcal2 = itemView.findViewById(R.id.textcal2);
            textViewname2 = itemView.findViewById(R.id.textcal11);
            textViewsug = itemView.findViewById(R.id.textsug);
            textViewsod = itemView.findViewById(R.id.textsosd);
            textViewfat = itemView.findViewById(R.id.textFat);



            cardView = itemView.findViewById(R.id.cardmonth);

            cardView = itemView.findViewById(R.id.cardmonth);

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
            cardView.setCardBackgroundColor(randomColor);

        }


    }
}