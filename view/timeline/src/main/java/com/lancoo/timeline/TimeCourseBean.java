package com.lancoo.timeline;

public class TimeCourseBean {

    private String courseName;

    private String time;

    private String room;

    private int classState;

    public TimeCourseBean(String courseName, String time, String room, int classState) {
        this.courseName = courseName;
        this.time = time;
        this.room = room;
        this.classState = classState;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getRoom() {
        return room;
    }

    public void setRoom(String room) {
        this.room = room;
    }

    public int getClassState() {
        return classState;
    }

    public void setClassState(int classState) {
        this.classState = classState;
    }

    public static class ClassState{
        public static int STATE_NOT_START = 0;
        public static int STATE_START = 1;
        public static int STATE_STARTED = 2;
    }
}