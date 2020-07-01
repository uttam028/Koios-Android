package org.mlab.research.koios.ui.map;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.RectF;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import org.mlab.research.koios.R;
import org.mlab.research.koios.ui.weekviewlib.DateTimeInterpreter;
import org.mlab.research.koios.ui.weekviewlib.MonthLoader;
import org.mlab.research.koios.ui.weekviewlib.WeekView;
import org.mlab.research.koios.ui.weekviewlib.WeekViewEvent;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class VisitActivity extends AppCompatActivity implements WeekView.EventClickListener, MonthLoader.MonthChangeListener{

    private WeekView weekView;
    private TextView tvPlaceName;
    private StayPoint stayPoint;
    private ArrayList<VisitEvent> visitEvents;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_visit);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle("Details");


        stayPoint = getIntent().getParcelableExtra(MapFragment.SIG_LOC_PARCEL);

        tvPlaceName = findViewById(R.id.tvPlaceName);
        tvPlaceName.setText(stayPoint.getName());

        weekView = (WeekView) findViewById(R.id.weekView);
        weekView.setMonthChangeListener(this);
        weekView.setOnEventClickListener(this);
        weekView.setNumberOfVisibleDays(7);
        setupDateTimeInterpreter(false);

        visitEvents = MapDataProvider.getAllVisits(stayPoint);
        Log.d("jodatime", visitEvents.size()+ ", number of events");
    }

    /**
     * Set up a date time interpreter which will show short date values when in week view and long
     * date values otherwise.
     *
     * @param shortDate True if the date values should be short.
     */
    private void setupDateTimeInterpreter(final boolean shortDate) {
        weekView.setDateTimeInterpreter(new DateTimeInterpreter() {
            @Override
            public String interpretDate(Calendar date) {
                SimpleDateFormat weekdayNameFormat = new SimpleDateFormat("E", Locale.getDefault());
                String weekday = weekdayNameFormat.format(date.getTime());
                SimpleDateFormat format = new SimpleDateFormat("E M/d", Locale.getDefault());

                // All android api level do not have a standard way of getting the first letter of
                // the week day name. Hence we get the first char programmatically.
                // Details: http://stackoverflow.com/questions/16959502/get-one-letter-abbreviation-of-week-day-of-a-date-in-java#answer-16959657
                if (shortDate)
                    weekday = String.valueOf(weekday.charAt(0));
                return format.format(date.getTime());
            }

            @Override
            public String interpretTime(int hour, int minutes) {
                String strMinutes = String.format("%02d", minutes);
                if(hour==12){
                    return minutes==0 ? "12 PM" : "12:" + strMinutes + " PM";
                } else if (hour > 11) {
                    return minutes==0 ? (hour - 12) + " PM" : (hour - 12) + ":" + strMinutes + " PM";
                } else {
                    if (hour == 0) {
                        return minutes==0 ? "00" + " AM" : "00:" + strMinutes + " AM";
                    } else {
                        return minutes==0 ? hour  + " AM" : hour + ":" + strMinutes + " AM";
                    }
                }
            }
        });
    }

    protected String getEventTitle(Calendar time) {
        return String.format("Event of %02d:%02d %s/%d", time.get(Calendar.HOUR_OF_DAY), time.get(Calendar.MINUTE), time.get(Calendar.MONTH) + 1, time.get(Calendar.DAY_OF_MONTH));
    }

    @Override
    public void onEventClick(WeekViewEvent event, RectF eventRect) {
//        Toast.makeText(this, "Clicked " + event.getName(), Toast.LENGTH_SHORT).show();
    }






    @Override
    public List<? extends WeekViewEvent> onMonthChange(int newYear, int newMonth) {

        //newMonth of the week-view (1-12), whereas in calendar month is (0-11)
        Log.d("jodatime", "month from week view-"+ newMonth);
        List<WeekViewEvent> events = new ArrayList<>();
        if (visitEvents != null){
            for (int i=0 ; i<visitEvents.size() ; i++){
                Calendar startTime = visitEvents.get(i).getStartTime();
                Calendar endTime = visitEvents.get(i).getEndTime();
                Log.d("jodatime", "parsing event "+ i + ", " + startTime.get(Calendar.MONTH) + ", " + startTime.get(Calendar.DAY_OF_MONTH)
                + ", " + startTime.get(Calendar.HOUR_OF_DAY) + ", " + endTime.get(Calendar.HOUR_OF_DAY) + ", " + endTime.get(Calendar.MINUTE));
                if (startTime.get(Calendar.MONTH) == newMonth-1){
                    WeekViewEvent event = new WeekViewEvent(i, "", startTime, endTime);
                    event.setColor(getResources().getColor(R.color.colorNdGold));
                    events.add(event);
                }
            }
        }


        return events;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return super.onSupportNavigateUp();
    }
}