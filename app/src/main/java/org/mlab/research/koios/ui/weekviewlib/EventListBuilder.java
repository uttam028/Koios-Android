package org.mlab.research.koios.ui.weekviewlib;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created by Tara on 27/01/2016.
 */
public class EventListBuilder<K,V> {
    private List<EventRect> mEventRects = new ArrayList<>();

    private List<? extends WeekViewEvent> mEvents;

    public EventListBuilder(){

    }

    public EventListBuilder(List<? extends WeekViewEvent> events){
        mEvents = events;
        addEvents(events);
    }

    public void addEvents(List<? extends WeekViewEvent> events) {
        mEvents = events;
        sortAndCacheEvents(events);
        // Prepare to calculate positions of each events.
        List<EventRect> tempEvents = mEventRects;
        mEventRects = new ArrayList<EventRect>();

        // Iterate through each day with events to calculate the position of the events.
        while (tempEvents.size() > 0) {
            ArrayList<EventRect> eventRects = new ArrayList<>(tempEvents.size());

            // Get first event for a day.
            EventRect eventRect1 = tempEvents.remove(0);
            eventRects.add(eventRect1);

            int i = 0;
            while (i < tempEvents.size()) {
                // Collect all other events for same day.
                EventRect eventRect2 = tempEvents.get(i);
                if (isSameDay(eventRect1.event.getStartTime(), eventRect2.event.getStartTime())) {
                    tempEvents.remove(i);
                    eventRects.add(eventRect2);
                } else {
                    i++;
                }
            }
            computePositionOfEvents(eventRects);
        }
    }

    /**
     * Sort and cache events.
     * @param events The events to be sorted and cached.
     */
    private void sortAndCacheEvents(List<? extends WeekViewEvent> events) {
        sortEvents(events);
        for (WeekViewEvent event : events) {
            cacheEvent(event);
        }
    }

    /**
     * Sorts the events in ascending order.
     * @param events The events to be sorted.
     */
    private void sortEvents(List<? extends WeekViewEvent> events) {
        Collections.sort(events, new Comparator<WeekViewEvent>() {
            @Override
            public int compare(WeekViewEvent event1, WeekViewEvent event2) {
                long start1 = event1.getStartTime().getTimeInMillis();
                long start2 = event2.getStartTime().getTimeInMillis();
                int comparator = start1 > start2 ? 1 : (start1 < start2 ? -1 : 0);
                if (comparator == 0) {
                    long end1 = event1.getEndTime().getTimeInMillis();
                    long end2 = event2.getEndTime().getTimeInMillis();
                    comparator = end1 > end2 ? 1 : (end1 < end2 ? -1 : 0);
                }
                return comparator;
            }
        });
    }


    /**
     * Calculates the left and right positions of each events. This comes handy specially if events
     * are overlapping.
     * @param eventRects The events along with their wrapper class.
     */
    private void computePositionOfEvents(List<EventRect> eventRects) {
        // Make "collision groups" for all events that collide with others.
        List<List<EventRect>> collisionGroups = new ArrayList<>();
        for (EventRect eventRect : eventRects) {
            boolean isPlaced = false;
            outerLoop:
            for (List<EventRect> collisionGroup : collisionGroups) {
                for (EventRect groupEvent : collisionGroup) {
                    if (isEventsCollide(groupEvent.event, eventRect.event)) {
                        collisionGroup.add(eventRect);
                        isPlaced = true;
                        break outerLoop;
                    }
                }
            }
            if (!isPlaced) {
                List<EventRect> newGroup = new ArrayList<>();
                newGroup.add(eventRect);
                collisionGroups.add(newGroup);
            }
        }

        for (List<EventRect> collisionGroup : collisionGroups) {
            expandEventsToMaxWidth(collisionGroup);
        }
    }

    /**
     * Checks if two events overlap.
     * @param event1 The first event.
     * @param event2 The second event.
     * @return true if the events overlap.
     */
    private boolean isEventsCollide(WeekViewEvent event1, WeekViewEvent event2) {
        long start1 = event1.getStartTime().getTimeInMillis();
        long end1 = event1.getEndTime().getTimeInMillis();
        long start2 = event2.getStartTime().getTimeInMillis();
        long end2 = event2.getEndTime().getTimeInMillis();
        return !((start1 >= end2) || (end1 <= start2));
    }


    /**
     * Cache the event for smooth scrolling functionality.
     * @param event The event to cache.
     */
    private void cacheEvent(WeekViewEvent event) {
        if(event.getStartTime().compareTo(event.getEndTime()) >= 0)
            return;
        if (!isSameDay(event.getStartTime(), event.getEndTime())) {
            // Add first day.
            Calendar endTime = (Calendar) event.getStartTime().clone();
            endTime.set(Calendar.HOUR_OF_DAY, 23);
            endTime.set(Calendar.MINUTE, 59);
            WeekViewEvent<K,V> event1 = new WeekViewEvent<>(event.getId(), event.getName(), event.getLocation(), event.getStartTime(), endTime);
            event1.setColor(event.getColor());
            mEventRects.add(new EventRect(event1, event, null));

            // Add other days.
            Calendar otherDay = (Calendar) event.getStartTime().clone();
            otherDay.add(Calendar.DATE, 1);
            while (!isSameDay(otherDay, event.getEndTime())) {
                Calendar overDay = (Calendar) otherDay.clone();
                overDay.set(Calendar.HOUR_OF_DAY, 0);
                overDay.set(Calendar.MINUTE, 0);
                Calendar endOfOverDay = (Calendar) overDay.clone();
                endOfOverDay.set(Calendar.HOUR_OF_DAY, 23);
                endOfOverDay.set(Calendar.MINUTE, 59);
                WeekViewEvent<K,V> eventMore = new WeekViewEvent<>(event.getId(), event.getName(), overDay, endOfOverDay);
                eventMore.setColor(event.getColor());
                mEventRects.add(new EventRect(eventMore, event, null));

                // Add next day.
                otherDay.add(Calendar.DATE, 1);
            }

            // Add last day.
            Calendar startTime = (Calendar) event.getEndTime().clone();
            startTime.set(Calendar.HOUR_OF_DAY, 0);
            startTime.set(Calendar.MINUTE, 0);
            WeekViewEvent<K,V> event2 = new WeekViewEvent<>(event.getId(), event.getName(), event.getLocation(), startTime, event.getEndTime());
            event2.setColor(event.getColor());
            mEventRects.add(new EventRect(event2, event, null));
        }
        else {
            mEventRects.add(new EventRect(event, event, null));
        }
    }


    /**
     * Expands all the events to maximum possible width. The events will try to occupy maximum
     * space available horizontally.
     * @param collisionGroup The group of events which overlap with each other.
     */
    private void expandEventsToMaxWidth(List<EventRect> collisionGroup) {
        // Expand the events to maximum possible width.
        List<List<EventRect>> columns = new ArrayList<>();
        columns.add(new ArrayList<EventRect>());
        for (EventRect eventRect : collisionGroup) {
            boolean isPlaced = false;
            for (List<EventRect> column : columns) {
                if (column.size() == 0) {
                    column.add(eventRect);
                    isPlaced = true;
                }
                else if (!isEventsCollide(eventRect.event, column.get(column.size()-1).event)) {
                    column.add(eventRect);
                    isPlaced = true;
                    break;
                }
            }
            if (!isPlaced) {
                List<EventRect> newColumn = new ArrayList<>();
                newColumn.add(eventRect);
                columns.add(newColumn);
            }
        }


        // Calculate left and right position for all the events.
        // Get the maxRowCount by looking in all columns.
        int maxRowCount = 0;
        for (List<EventRect> column : columns){
            maxRowCount = Math.max(maxRowCount, column.size());
        }
        for (int i = 0; i < maxRowCount; i++) {
            // Set the left and right values of the event.
            float j = 0;
            for (List<EventRect> column : columns) {
                if (column.size() >= i+1) {
                    EventRect eventRect = column.get(i);
                    eventRect.width = 1f / columns.size();
                    eventRect.left = j / columns.size();
                    eventRect.top = eventRect.event.getStartTime().get(Calendar.HOUR_OF_DAY) * 60 + eventRect.event.getStartTime().get(Calendar.MINUTE);
                    eventRect.bottom = eventRect.event.getEndTime().get(Calendar.HOUR_OF_DAY) * 60 + eventRect.event.getEndTime().get(Calendar.MINUTE);
                    mEventRects.add(eventRect);
                }
                j++;
            }
        }
    }

    /**
     * Checks if two times are on the same day.
     *
     *
     *
     *
     * @param dayOne The first day.
     * @param dayTwo The second day.
     * @return Whether the times are on the same day.
     */
    private boolean isSameDay(Calendar dayOne, Calendar dayTwo) {
        return dayOne.get(Calendar.YEAR) == dayTwo.get(Calendar.YEAR) && dayOne.get(Calendar.DAY_OF_YEAR) == dayTwo.get(Calendar.DAY_OF_YEAR);
    }

    public List<EventRect> getEventRects() {
        return mEventRects;
    }

    public List<? extends WeekViewEvent> getEvents() {
        return mEvents;
    }
}
