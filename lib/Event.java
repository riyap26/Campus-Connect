package lib;

import java.time.*;

public class Event {
    private String name;
    private String location;
    private LocalDateTime time;
    private int RSVPCount;
    private int maxCapacity;
    private int cost;
    private String admin;

    public Event(String name, String location, LocalDateTime time, int count, int maxCapacity, int cost, String admin) {
        this.name = name;
        this.location = location;
        this.time = time;
        this.RSVPCount = count;
        this.maxCapacity = maxCapacity;
        this.cost = cost;
        this.admin = admin;
    }
}
