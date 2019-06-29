package app.being.pattadonjaisin.beingwellness;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.kingja.supershapeview.core.SuperManager;
import com.kingja.supershapeview.view.SuperShapeTextView;

import java.util.List;

public class Food extends AppCompatActivity {
    private TextView Foodname, Cal, Sugar, Sodium, Fat, Name;
    private Button Buttonsave;



    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food);
        Toolbar toolbar = (Toolbar) findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);

        Intent intent = getIntent();

        //รับค่าจาก fragment_scan
        final String Name = intent.getStringExtra("Name");
        final String Calories = intent.getStringExtra("Calories");
        final String Sugar = intent.getStringExtra("Sugar");
        final String Sodium = intent.getStringExtra("Sodium");
        final String Fat = intent.getStringExtra("Fat");

//ส่งค่าไปต่อที่แอคติวิตี้เมนเนื่องจากระบบจะเรียกเมนก่อน
        Buttonsave = findViewById(R.id.save);
        Buttonsave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Food.this, MainActivity.class);
                intent.putExtra("name",Name);
                intent.putExtra("Cal",Calories);

                intent.putExtra("Sug",Sugar);
                intent.putExtra("Sod",Sodium);
                intent.putExtra("Fat",Fat);
                intent.putExtra("Status","submit");
                startActivity(intent);

                finish();


            }
        });


        Foodname = findViewById(R.id.textViewfoodName);
        Cal = findViewById(R.id.textViewCal);
        this.Sugar = findViewById(R.id.textViewSugar);
        this.Sodium = findViewById(R.id.textViewSod);
        this.Fat = findViewById(R.id.textViewFat);



//ใส่ this ข้างหน้าในกรณที่ชื่อตัวแปรซ้ำกันเพื่อให้ชี้ไปยังตัวแปรที่กำหนด
        this.Foodname.setText(Name);
        this.Cal.setText(Calories);
        this.Sugar.setText(Sugar);
        this.Sodium.setText(Sodium);
        this.Fat.setText(Fat);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_acbar, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }
}
