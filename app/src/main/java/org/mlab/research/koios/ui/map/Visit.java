package org.mlab.research.koios.ui.map;

public class Visit {

    private StayPoint stayPoint;
    private VisitEvent event;

    public Visit(StayPoint stayPoint, VisitEvent event) {
        this.stayPoint = stayPoint;
        this.event = event;
    }

    public StayPoint getStayPoint() {
        return stayPoint;
    }

    public VisitEvent getEvent() {
        return event;
    }
}
