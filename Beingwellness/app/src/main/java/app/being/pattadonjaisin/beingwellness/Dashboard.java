package app.being.pattadonjaisin.beingwellness;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.data.DataSet;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link Dashboard.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link Dashboard#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Dashboard extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String pcal = "0";
    private String pSug = "0";
    private String pSod = "0";
    private String pFat = "0";


    private String pto = "2000";

    public String user_id;
    public TextView pscal;
    public TextView pssug;
    public TextView pssod;
    public TextView psfat;
    private String month;
     public static int gsfat, gssod, gssug;
    String status = null;
    FirebaseDatabase keepcal = FirebaseDatabase.getInstance(); //เชื่อมดาต้าเบส
    DatabaseReference addcal = keepcal.getReference(); //ใช้งานแล้วอ้างอิงไป keepcal

    // TODO: Rename and change types of parameters

    private float from;
    private float to;
    private String mParam1;
    private String mParam2;
    List<PieEntry> entries;
    PieChart pieChart;
  public   static ObjectAnimator progresanimator3, progresanimator2, progresanimator1;
  public   static ProgressBar progressBar3, progressBar2, progressBar1;
    private OnFragmentInteractionListener mListener;

    public Dashboard() {
        // Required empty public constructor
    }


    //ดึงค่าจากเมนแอคติวิตี้มาโชว์

    public Dashboard newInstance(String Cal, String Sug, String Sod, String Fat, String Status) {
        Dashboard cal = new Dashboard();
        Bundle savecal = new Bundle();
        savecal.putString("Cal", Cal);
        savecal.putString("Sug", Sug);
        savecal.putString("Sod", Sod);
        savecal.putString("Fat", Fat);
        savecal.putString("Status", Status);
        cal.setArguments(savecal);
        return cal;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        if (bundle != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
            pcal = getArguments().getString("Cal", "0");
            pSug = getArguments().getString("Sug", "0");
            pSod = getArguments().getString("Sod", "0");
            pFat = getArguments().getString("Fat", "0");
            status = getArguments().getString("Status");


        }

    }

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_dashboard, container, false);


        pieChart = view.findViewById(R.id.chart);
        entries = new ArrayList<>();
        FirebaseUser auth = FirebaseAuth.getInstance().getCurrentUser();

        user_id = auth.getUid();

        Date currentTime = Calendar.getInstance().getTime();
        SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy");
        SimpleDateFormat simplemonth = new SimpleDateFormat("MMM-yyyy", Locale.ENGLISH);
        SimpleDateFormat simpleyear = new SimpleDateFormat("ww-YYYY");
        final String month2 = simplemonth.format(currentTime);
        final String year = simpleyear.format(currentTime);
        final String date = df.format(currentTime);

//
//
//        PieDataSet set = new PieDataSet(entries, "");
//        pieChart.setHoleColor(Color.rgb(9, 132, 227));
//        pieChart.setTransparentCircleRadius(0);
//        set.setDrawValues(false);
//        pieChart.getDescription().setEnabled(false);
//        PieData data = new PieData(set);
//        pieChart.setData(data);
//        final int[] MY_COLORS = {Color.rgb(253, 114, 114), Color.rgb(154, 236, 219)};
//        ArrayList<Integer> colors = new ArrayList<Integer>();
//        for (int c : MY_COLORS) colors.add(c);
//
//
//        set.setColors(colors);


        pscal = view.findViewById(R.id.pCal);
        pssug = view.findViewById(R.id.pSugar);
        pssod = view.findViewById(R.id.pSod);
        psfat = view.findViewById(R.id.pFat);
        progressBar3 = view.findViewById(R.id.progrees3);
        progressBar2 = view.findViewById(R.id.progrees2);
        progressBar1 = view.findViewById(R.id.progrees1);


        if (status == null) {

            addcal.child("showfooddata").child(user_id).child(date).addListenerForSingleValueEvent(new ValueEventListener() {

                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {


                    if (dataSnapshot.getValue() != null) {
                        int a=2000;

                        int gscal = Integer.parseInt(dataSnapshot.child("totalcal").getValue().toString());
                        int gssug = Integer.parseInt(dataSnapshot.child("totalsug").getValue().toString());
                        int gssod = Integer.parseInt(dataSnapshot.child("totalsod").getValue().toString());
                        int gsfat = Integer.parseInt(dataSnapshot.child("totalfat").getValue().toString());
                        int gsto2 = a - gscal;

                        if (gsto2 < 0) {
                            gsto2 = 0;
                        }


                        pscal.setText(String.valueOf(gscal));
                        pssug.setText(String.valueOf(gssug));
                        pssod.setText(String.valueOf(gssod));
                        psfat.setText(String.valueOf(gsfat));
                        chart(gsto2, gscal);
                        initday2(gsfat, gssod, gssug);





                    } else {


                        DatabaseReference zerostatus = addcal.child("showfooddata").child(user_id).child(date);
                        zerostatus.child("totalcal").setValue("0");
                        zerostatus.child("totalsug").setValue("0");
                        zerostatus.child("totalsod").setValue("0");
                        zerostatus.child("totalfat").setValue("0");

                        pscal.setText(pcal);
                        pssug.setText(pSug);
                        pssod.setText(pSod);
                        psfat.setText(pFat);


                        int gscal = Integer.parseInt(pcal);
                        int gsto = 0;
                        int gsfat = 0;
                        int gssod = 0;
                        int gssug = 0;

                        chart(gsto, gscal);
                        initday2(gsfat, gssod, gssug);


                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        } else if (status.equals("submit")) {


            addcal.child("showfooddata").child(user_id).child(date).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {


                    int gscal = Integer.parseInt(dataSnapshot.child("totalcal").getValue().toString()) + Integer.parseInt(pcal);
                    int gssug = Integer.parseInt(dataSnapshot.child("totalsug").getValue().toString()) + Integer.parseInt(pSug);
                    int gssod = Integer.parseInt(dataSnapshot.child("totalsod").getValue().toString()) + Integer.parseInt(pSod);
                    int gsfat = Integer.parseInt(dataSnapshot.child("totalfat").getValue().toString()) + Integer.parseInt(pFat);
                    int gsto = Integer.parseInt(pto) - gscal;
                    if (gsto < 0) {
                        gsto = 0;
                    }
                    DatabaseReference zerostatus = addcal.child("showfooddata").child(user_id).child(date);
                    zerostatus.child("totalcal").setValue(gscal);
                    zerostatus.child("totalsug").setValue(gssug);
                    zerostatus.child("totalsod").setValue(gssod);
                    zerostatus.child("totalfat").setValue(gsfat);
                    pscal.setText(String.valueOf(gscal));
                    pssug.setText(String.valueOf(gssug));
                    pssod.setText(String.valueOf(gssod));
                    psfat.setText(String.valueOf(gsfat));
//
//                    DatabaseReference monthcal = addcal.child("showfoodmonth").child(user_id).child(month2);
//                    monthcal.child("totalcal").setValue(gscal);
//                    monthcal.child("totalsug").setValue(gssug);
//                    monthcal.child("totalsod").setValue(gssod);
//                    monthcal.child("totalfat").setValue(gsfat);

                    addcal.child("showfoodmonth").child(user_id).child(month2).addListenerForSingleValueEvent(new ValueEventListener() {

                        @Override

                        public void onDataChange(@NonNull DataSnapshot dataSnapshot2) {
                            if (dataSnapshot2.getValue() != null) {
                                int gscal2 = Integer.parseInt(dataSnapshot2.child("totalcal").getValue().toString()) + Integer.parseInt(pcal);
                                int gssug2 = Integer.parseInt(dataSnapshot2.child("totalsug").getValue().toString()) + Integer.parseInt(pSug);
                                int gssod2 = Integer.parseInt(dataSnapshot2.child("totalsod").getValue().toString()) + Integer.parseInt(pSod);
                                int gsfat2 = Integer.parseInt(dataSnapshot2.child("totalfat").getValue().toString()) + Integer.parseInt(pFat);
                                DatabaseReference monthcal = addcal.child("showfoodmonth").child(user_id).child(month2);
                                monthcal.child("totalcal").setValue(String.valueOf(gscal2)).toString();
                                monthcal.child("totalsug").setValue(String.valueOf(gssug2)).toString();
                                monthcal.child("totalsod").setValue(String.valueOf(gssod2)).toString();
                                monthcal.child("totalfat").setValue(String.valueOf(gsfat2)).toString();
                            } else {

                                DatabaseReference monthcal = addcal.child("showfoodmonth").child(user_id).child(month2);
                                monthcal.child("totalcal").setValue(String.valueOf(pcal)).toString();
                                monthcal.child("totalsug").setValue(String.valueOf(pSug)).toString();
                                monthcal.child("totalsod").setValue(String.valueOf(pSod)).toString();
                                monthcal.child("totalfat").setValue(String.valueOf(pFat)).toString();
                            }


                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                    addcal.child("showfoodyear").child(user_id).child(year).addListenerForSingleValueEvent(new ValueEventListener() {

                        @Override

                        public void onDataChange(@NonNull DataSnapshot dataSnapshot2) {
                            if (dataSnapshot2.getValue() != null) {
                                int gscal2 = Integer.parseInt(dataSnapshot2.child("totalcal").getValue().toString()) + Integer.parseInt(pcal);
                                int gssug2 = Integer.parseInt(dataSnapshot2.child("totalsug").getValue().toString()) + Integer.parseInt(pSug);
                                int gssod2 = Integer.parseInt(dataSnapshot2.child("totalsod").getValue().toString()) + Integer.parseInt(pSod);
                                int gsfat2 = Integer.parseInt(dataSnapshot2.child("totalfat").getValue().toString()) + Integer.parseInt(pFat);
                                DatabaseReference monthcal = addcal.child("showfoodyear").child(user_id).child(year);
                                HashMap<String,String> week = new HashMap<>();
                                week.put("totalcal",String.valueOf(gscal2));
                                week.put("totalsug",String.valueOf(gssug2));
                                week.put("totalsod",String.valueOf(gssod2));
                                week.put("totalfat",String.valueOf(gsfat2));
                                monthcal.setValue(week);




//
//                                monthcal.child("totalcal").setValue(String.valueOf(gscal2)).toString();
//                                monthcal.child("totalsug").setValue(String.valueOf(gssug2)).toString();
//                                monthcal.child("totalsod").setValue(String.valueOf(gssod2)).toString();
//                                monthcal.child("totalfat").setValue(String.valueOf(gsfat2)).toString();
                            } else {
                                DatabaseReference monthcal = addcal.child("showfoodyear").child(user_id).child(year);
                                HashMap<String,String> week = new HashMap<>();
                                week.put("totalcal",String.valueOf(pcal));
                                week.put("totalsug",String.valueOf(pSug));
                                week.put("totalsod",String.valueOf(pSod));
                                week.put("totalfat",String.valueOf(pFat));
                                monthcal.setValue(week);
//
//                                DatabaseReference monthcal = addcal.child("showfoodyear").child(user_id).child(year);
//                                monthcal.child("totalcal").setValue(String.valueOf(pcal)).toString();
//                                monthcal.child("totalsug").setValue(String.valueOf(pSug)).toString();
//                                monthcal.child("totalsod").setValue(String.valueOf(pSod)).toString();
//                                monthcal.child("totalfat").setValue(String.valueOf(pFat)).toString();
                            }


                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }

                    });

                    addcal.child("risk").child(user_id).addListenerForSingleValueEvent(new ValueEventListener() {

                        @Override

                        public void onDataChange(@NonNull DataSnapshot dataSnapshot2) {
                            if (dataSnapshot2.getValue() != null) {
                                int gscal2 = Integer.parseInt(dataSnapshot2.child("totalcal").getValue().toString()) + Integer.parseInt(pcal);
                                int gssug2 = Integer.parseInt(dataSnapshot2.child("totalsug").getValue().toString()) + Integer.parseInt(pSug);
                                int gssod2 = Integer.parseInt(dataSnapshot2.child("totalsod").getValue().toString()) + Integer.parseInt(pSod);
                                int gsfat2 = Integer.parseInt(dataSnapshot2.child("totalfat").getValue().toString()) + Integer.parseInt(pFat);
                                DatabaseReference monthcal = addcal.child("risk").child(user_id);
                                monthcal.child("totalcal").setValue(String.valueOf(gscal2));
                                monthcal.child("totalsug").setValue(String.valueOf(gssug2));
                                monthcal.child("totalsod").setValue(String.valueOf(gssod2));
                                monthcal.child("totalfat").setValue(String.valueOf(gsfat2));
                            } else {

                                DatabaseReference monthcal = addcal.child("risk").child(user_id);
                                monthcal.child("totalcal").setValue(String.valueOf(pcal));
                                monthcal.child("totalsug").setValue(String.valueOf(pSug));
                                monthcal.child("totalsod").setValue(String.valueOf(pSod));
                                monthcal.child("totalfat").setValue(String.valueOf(pFat));
                            }


                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }

                    });


//                    progresanimator = ObjectAnimator.ofInt(progressBar3,"progress",gsfat);
//                    progresanimator.setDuration(2000);
//                    progressBar3.setMax(100);
//                    progresanimator.start();

//                    entries.add(new PieEntry(gsto, "Total"));
//                    entries.add(new PieEntry(gscal, "Today"));
//                    pieChart.animateXY(1700, 1700); // animate horizontal and vertical 3000 milliseconds
//
//                    PieDataSet set = new PieDataSet(entries, "");
//                    pieChart.setHoleColor(Color.rgb(9, 132, 227));
//                    pieChart.setTransparentCircleRadius(0);
//                    set.setDrawValues(true);
//                    pieChart.getDescription().setEnabled(false);
//                    PieData data = new PieData(set);
//                    final int[] MY_COLORS = {Color.rgb(154, 236, 219), Color.rgb(253, 114, 114)};
//                    ArrayList<Integer> colors = new ArrayList<Integer>();
//                    for (int c : MY_COLORS) colors.add(c);
//
//                    set.setColors(colors);
//                    pieChart.setData(data);
                    initday(gsfat, gssod, gssug);

                    chart(gsto, gscal);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }

        return view;


    }


    public void chart(int gsto, int gscal) {
        entries.add(new PieEntry(gsto, "Total"));
        entries.add(new PieEntry(gscal, "Today"));
        pieChart.animateXY(1700, 1700); // animate horizontal and vertical 3000 milliseconds
        pieChart.setDrawHoleEnabled(true);
        pieChart.setTransparentCircleRadius(30f);
        pieChart.setHoleRadius(50f);
        PieDataSet set = new PieDataSet(entries, "");
        pieChart.setTransparentCircleRadius(0);
        set.setDrawValues(true);
        pieChart.getDescription().setEnabled(false);
        PieData data = new PieData(set);
        final int[] MY_COLORS = {Color.rgb(154, 236, 219), Color.rgb(253, 114, 114)};
        ArrayList<Integer> colors = new ArrayList<Integer>();
        for (int c : MY_COLORS) colors.add(c);

        set.setColors(colors);
        pieChart.setData(data);
        pieChart.invalidate(); // refresh


    }

    public void initday(int gsfat1, int gssod1, int gssug1) {
    gsfat =gsfat1;
    gssod = gssod1;
    gssug = gssug1;

        progresanimator3 = ObjectAnimator.ofInt(progressBar3, "progress", gsfat);
        progresanimator2 = ObjectAnimator.ofInt(progressBar2, "progress", gssod);
        progresanimator1 = ObjectAnimator.ofInt(progressBar1, "progress", gssug);
        progresanimator1.setDuration(10000);
        progresanimator2.setDuration(1000);
        progresanimator3.setDuration(8000);
        progressBar2.setMax(2400);
        progressBar3.setMax(65);
        progressBar1.setMax(24);
        progresanimator3.start();
        progresanimator2.start();
        progresanimator1.start();
    }


    public void initday2(int gsfat1, int gssod1, int gssug1) {
        gsfat =gsfat1;
        gssod = gssod1;
        gssug = gssug1;

        progresanimator3 = ObjectAnimator.ofInt(progressBar3, "progress", gsfat);
        progresanimator2 = ObjectAnimator.ofInt(progressBar2, "progress", gssod);
        progresanimator1 = ObjectAnimator.ofInt(progressBar1, "progress", gssug);

        progressBar2.setMax(2400);
        progressBar3.setMax(65);
        progressBar1.setMax(24);
        progresanimator3.start();
        progresanimator2.start();
        progresanimator1.start();
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }


    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    private void init(View view) {

    }


    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
