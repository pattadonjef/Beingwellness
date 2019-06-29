package app.being.pattadonjaisin.beingwellness;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Acitivityadd extends AppCompatActivity {
EditText editTextname;
EditText editTextCal;
EditText editTextSugar;
EditText editTextSodium;
EditText editTextFat;
Button buttad;
String user_id;
DatabaseReference datafood;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        FirebaseUser auth = FirebaseAuth.getInstance().getCurrentUser();

        user_id = auth.getUid();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_acitivityadd);
datafood = FirebaseDatabase.getInstance().getReference("manuladd");
        editTextname = findViewById(R.id.foodname);
        editTextCal = findViewById(R.id.cal);
        editTextSugar = findViewById(R.id.sug);
        editTextSodium = findViewById(R.id.sod);
        editTextFat = findViewById(R.id.fat);
        buttad = findViewById(R.id.addde);
        buttad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                addfood();
            }

        });
    }

    private void addfood(){
        String name = editTextname.getText().toString().trim();
        String cal = editTextCal.getText().toString().trim();
        String sug = editTextSugar.getText().toString().trim();
        String sod = editTextSodium.getText().toString().trim();
        String fat = editTextFat.getText().toString().trim();

        if(!TextUtils.isEmpty(name)){
            String id =  datafood.push().getKey();
            Foodmanul foodman = new Foodmanul(name,cal,sug,sod,fat);
            datafood.child(user_id).child(id).setValue(foodman);


            Toast.makeText(this,"Food add",Toast.LENGTH_LONG).show();
            finish();


        }else{
            Toast.makeText(this, "You should enter",Toast.LENGTH_LONG).show();
        }

    }
}
