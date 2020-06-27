package org.mlab.research.koios.ui.map;


import java.util.Calendar;

public class VisitEvent {

    private Calendar startTime;
    private Calendar endTime;

    public VisitEvent(Calendar startTime, Calendar endTime) {
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public Calendar getStartTime() {
        return startTime;
    }

    public Calendar getEndTime() {
        return endTime;
    }
}
