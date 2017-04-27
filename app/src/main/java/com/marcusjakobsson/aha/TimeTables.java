package com.marcusjakobsson.aha;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by konstantin_ay on 2017-04-18.
 */

interface TimeTables{


    /**
     * createList() kommer att lägga till samtliga element till en
     * listView i aktiviteten.
     */
    void createList();




    /**
     * stringArrToTextList(timeTable) tar in en string array och returnerar
     * en lista av TextViews innehållandes alla strings i arrayen.
     */
    ArrayList<TextView> stringArrToTextList(String[] timeTable);
}
