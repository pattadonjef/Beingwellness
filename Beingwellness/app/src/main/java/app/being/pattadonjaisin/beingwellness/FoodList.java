package app.being.pattadonjaisin.beingwellness;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Random;

import static android.graphics.Color.rgb;


public class FoodList extends RecyclerView.Adapter<FoodList.ViewHolder> {

    public interface Putex{
        void putcal(String cal,String sug,String sod,String fat);
    }
    FirebaseDatabase addfood = FirebaseDatabase.getInstance(); //เชื่อมดาต้าเบส

    DatabaseReference addcal = addfood.getReference(); //ใช้งานแล้วอ้างอิงไป keepcal
    String user_id;
    FirebaseUser auth = FirebaseAuth.getInstance().getCurrentUser();

    private Context context;
    public String id ;
    Button updatebut;
    Button delete;
Putex putexs;
CardView cardViewfood;
    ArrayList<Foodmanul> foodlists;
    public FoodList(Context context, ArrayList<Foodmanul> foodlist,Putex putex) {
        this.context = context;
        foodlists = foodlist;
this.putexs = putex;


    }
    private boolean updatefood(String foodname, String calad, String sugad, String sodad, String fatad,String key){
        user_id = auth.getUid();

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("manuladd").child(user_id).child(key);
    Foodmanul foodmanul = new Foodmanul( foodname,calad,sugad,sodad,fatad);
    databaseReference.setValue(foodmanul);
return true;

    }
    private void deletefood(String key){
        user_id = auth.getUid();

        DatabaseReference deldata = FirebaseDatabase.getInstance().getReference("manuladd").child(user_id).child(key);


        deldata.removeValue();

        Toast.makeText(context,"Food is deleted",Toast.LENGTH_LONG).show();

    }



    @NonNull
    @Override
    public FoodList.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.listfood,viewGroup,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FoodList.ViewHolder viewHolder, int i) {
viewHolder.setData(this.foodlists.get(i),i);
    }

    @Override
    public int getItemCount() {
        return  (foodlists == null ?0:foodlists.size());
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView textViewname,textViewcal,textViewsug,textViewsod,textViewfat;
        TableLayout tableview;
        String key;
        ImageButton putbutton;
        String sug,sod,fat;
        int position;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            putbutton = itemView.findViewById(R.id.imgCmdShared);
            textViewname = itemView.findViewById(R.id.textname);
            textViewcal = itemView.findViewById(R.id.textcal);
            textViewsug = itemView.findViewById(R.id.foodsug);
            textViewsod = itemView.findViewById(R.id.foodsod);
            textViewfat= itemView.findViewById(R.id.foodfat);
//            tableview = itemView.findViewById(R.id.tableLayout1);

            cardViewfood = itemView.findViewById(R.id.cardfood);

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
            cardViewfood.setCardBackgroundColor(randomColor);



            textViewname.setOnClickListener(this);
            textViewcal.setOnClickListener(this);


            cardViewfood.setOnClickListener(this);
putbutton.setOnClickListener(this);
        }
        public void setData(Foodmanul foodmanul,int position){
            textViewname.setText(foodmanul.getFoodname());
            textViewcal.setText(foodmanul.getCalad());
            textViewsug.setText(foodmanul.getSugad());
            textViewsod.setText(foodmanul.getSodad());
            textViewfat.setText(foodmanul.getFatad());
            sug = foodmanul.getSugad();
            sod= foodmanul.getSodad();
            fat = foodmanul.getFatad();
            key=foodmanul.getKey();
            this.position = position;
        }


        @Override
        public void onClick(View v) {
            if(v.getId()== R.id.imgCmdShared){
                putexs.putcal(textViewcal.getText().toString(),sug,sod,fat);
            }else if(v.getId() == R.id.cardfood){
                showdialogupdate(textViewname.getText().toString(),textViewcal.getText().toString(),key,this.position);

            }

        }

    }
    private void showdialogupdate(String Foodname, String Foodcal, final String key,int position){
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(context);
        LayoutInflater inflater = (LayoutInflater)context.getSystemService(context.LAYOUT_INFLATER_SERVICE);

        final View dialogView = inflater.inflate(R.layout.updatedialog, null);
        dialogBuilder.setView(dialogView);
        final TextView textViewName = dialogView.findViewById(R.id.textupfate);

        final EditText editTextName = (EditText) dialogView.findViewById(R.id.updatename);
        final  EditText editTextCal = (EditText) dialogView.findViewById(R.id.updatecal);
        final  EditText editTexSug = (EditText) dialogView.findViewById(R.id.updatesug);
        final  EditText editTextSod = (EditText) dialogView.findViewById(R.id.updatesod);
        final  EditText editTextFat = (EditText) dialogView.findViewById(R.id.updatefat);
        final Button Buutonupdaet  = (Button) dialogView.findViewById(R.id.updatebut);
        final Button Buttondelete = (Button) dialogView.findViewById(R.id.deletebut);
        editTextName.setText(this.foodlists.get(position).getFoodname());
        editTextCal.setText(this.foodlists.get(position).getCalad());
        editTexSug.setText(this.foodlists.get(position).getSugad());
        editTextSod.setText(this.foodlists.get(position).getSodad());
        editTextFat.setText(this.foodlists.get(position).getFatad());







        final AlertDialog alertDialog = dialogBuilder.create();
        alertDialog.show();

        Buutonupdaet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = editTextName.getText().toString().trim();
                String cal = editTextCal.getText().toString().trim();
                String Sug = editTexSug.getText().toString().trim();
                String Sod = editTextSod.getText().toString().trim();
                String Fat = editTextFat.getText().toString().trim();


                if(TextUtils.isEmpty(name)){


                    editTextName.setError("enter name pls");
                return;
                }
                else if(TextUtils.isEmpty(cal)){
                    editTextCal.setError("enter cal pls");
                    return;
                }
                else if(TextUtils.isEmpty(Sug)){
                    editTexSug.setError("enter Sug pls");
                    return;
                }
                else if(TextUtils.isEmpty(Sod)){
                    editTextSod.setError("enter Sod pls");
                    return;
                }
                else if(TextUtils.isEmpty(Fat)){
                    editTextFat.setError("enter fat pls");
                    return;
                }
                updatefood(name,cal,Sug,Sod,Fat,key);
                alertDialog.dismiss();
            }
        });
        Buttondelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deletefood(key);
                alertDialog.dismiss();
            }
        });


    }

}

