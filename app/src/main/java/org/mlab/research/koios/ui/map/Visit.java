package org.mlab.research.koios.ui.map;

import android.os.Parcelable;

public class Visit {

    private SignificantLocation significantLocation;
    private VisitEvent event;

    public Visit(SignificantLocation significantLocation, VisitEvent event) {
        this.significantLocation = significantLocation;
        this.event = event;
    }

    public SignificantLocation getSignificantLocation() {
        return significantLocation;
    }

    public VisitEvent getEvent() {
        return event;
    }
}
