package com.example.fitnessforge.entity;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

@Entity
public class WorkoutLog {
    @PrimaryKey(autoGenerate = true)
    public Long id;
    public String uid;
    public Date date;
    public Long duration;

    public WorkoutLog(){}

    public WorkoutLog(String uid, Date date,  Long duration) {
        this.date = date;
        this.uid = uid;
        this.duration = duration;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Long getDuration() {
        return duration;
    }

    public void setDuration(Long duration) {
        this.duration = duration;
    }

    @Override
    public String toString() {
        return "WorkoutLog{" +
                "id=" + id +
                ", uid='" + uid + '\'' +
                ", date=" + date +
                ", duration=" + duration +
                '}';
    }

    public Map<String, Object> toMap() {
        Map<String, Object> map = new HashMap<>();
        map.put("uid", uid);
        map.put("date", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(date));
        map.put("duration", duration);
        return map;
    }

}
