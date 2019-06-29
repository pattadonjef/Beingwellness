package app.being.pattadonjaisin.beingwellness;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;




public class MainActivity extends AppCompatActivity implements Dashboard.OnFragmentInteractionListener , FoodList.Putex {
    private FragmentTransaction ft;
    private FragmentManager fm;

    Scan scan;
    Addfood addfood;
    Dashboard dash;
    Pagerdash pagerdash;
    excer excer;
     String Calories;
     String Sugar;
     String Sodium;
     String Fat;
     String submit;
    BottomNavigationView nv;
    public static Activity activityMain;
    private TextView Textocal;
    String staurselect = "dashbord";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        activityMain = this;








        Toolbar toolbar = (Toolbar) findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);
//        //รับค่าจากแอคติวีตี้food
//
//
        Calories = getIntent().getStringExtra("Cal");
        Sugar = getIntent().getStringExtra("Sug");
        Sodium = getIntent().getStringExtra("Sod");
        Fat    =  getIntent().getStringExtra("Fat");
        submit = getIntent().getStringExtra("Status");
        getIntent().removeExtra("Cal");
        getIntent().removeExtra("Sug");
        getIntent().removeExtra("Sod");
        getIntent().removeExtra("Status");

        addfood = new Addfood();
        scan = new Scan();
        excer = new excer();
        dash = new Dashboard();
        pagerdash = new Pagerdash();

        ft = getSupportFragmentManager().beginTransaction()
                .add(R.id.Framefag,pagerdash.newInstance(Calories,Sugar,Sodium,Fat,submit));
        ft.commit();
        fm = getSupportFragmentManager();

        nv = findViewById(R.id.menu);

        nv.setSelectedItemId(R.id.dash);
        nv.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.cam:
                        if (staurselect != "scan") {
                            ft = getSupportFragmentManager().beginTransaction().replace(R.id.Framefag, scan);
                            ft.commit();
                            staurselect = "scan";
                        }
                        break;
                    case R.id.dash:
                        if (staurselect != "dashbord" ) {
                            Calories = Sugar = Sodium = Fat = submit = null;
                            ft = getSupportFragmentManager().beginTransaction().replace(R.id.Framefag, new Pagerdash().newInstance(Calories,Sugar,Sodium,Fat,submit));
                            ft.commit();


                            staurselect = "dashbord";
                        }
                        break;
                    case  R.id.cart:
                        if (staurselect != "Addfood"){
                            ft = getSupportFragmentManager().beginTransaction().replace(R.id.Framefag, addfood );
                            ft.commit();
                            staurselect = "Addfood";
                        }
                        break;
//                    case R.id.bike:
//                        if(staurselect != "Activity"){
//                            ft = getSupportFragmentManager().beginTransaction().replace(R.id.Framefag, excer );
//                            ft.commit();
//                            staurselect = "Activity";
//                        }


                }
                return true;
            }
        });





    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_acbar, menu);
        return super.onCreateOptionsMenu(menu);

    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        switch (id) {
            case R.id.action_settings:
                Intent intent = new Intent(MainActivity.this, Main2Activity.class);
                startActivity(intent);

                break;
            default:
                break;
        }


        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }


    @Override
    public void putcal(String cal, String sug, String sod, String fat) {
        nv.setSelectedItemId(R.id.dash);
        ft = getSupportFragmentManager().beginTransaction().replace(R.id.Framefag, new Pagerdash().newInstance(cal,sug,sod,fat,"submit"));
        ft.commit();

    }
}
