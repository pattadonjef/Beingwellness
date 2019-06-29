package app.being.pattadonjaisin.beingwellness;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

public class pageradapter extends FragmentStatePagerAdapter {
    private String pcal = "0";
    private String pSug = "0";
    private String pSod = "0";
    private String pFat = "0";
    String status = null;

    int mNoOfTabs;

    public pageradapter(FragmentManager fm, int NumberOfTabs,Bundle dashintent)
    {
        super(fm);
        this.mNoOfTabs = NumberOfTabs;
        if(dashintent != null) {
            pcal = dashintent.getString("Cal");
            pSug = dashintent.getString("Sug");
            pSod = dashintent.getString("Sod");
            pFat = dashintent.getString("Fat");
            status = dashintent.getString("Status");
        }

    }



    @Override
    public Fragment getItem(int position) {
        if (position == 0) {
            return new Dashboard().newInstance(pcal,pSug,pSod,pFat,status);
        } else if (position == 1){
            pcal = pSug = pSod = pFat = status = null;

            return new Addweek();
        }
         else {
            return new Addmonth();
        }
    }
//    @Override
//    public Fragment getItem(int position) {
//        switch(position)
//        {
//
//            case 0:
//                Dashboard tab1 = new Dashboard();
//                return tab1;
//            case 1:
//                Month tab2 = new Month();
//                return  tab2;
//            case 2:
//                Yearly tab3 = new Yearly();
//                return  tab3;
//            default:
//                return
//        }
//    }


    private String tabTitles[] = new String[]{"Day", "Week", "Month"};

    @Override
    public CharSequence getPageTitle(int position) {
        return tabTitles[position];
    }

    @Override
    public int getCount() {
        return mNoOfTabs;
    }

//    @Nullable
//    @Override
//    public CharSequence getPageTitle(int position) {
//
//
//    }
}
