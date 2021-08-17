package model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

public class Link {
    @Getter @Setter
    private String name;
    @Getter @Setter
    private String day;
    @Getter @Setter
    private String time;
    @Getter @Setter
    private String weblink;

    public Link() {
    }

    public Link(String name, String day, String time, String weblink) {
        this.name = name;
        this.day = day;
        this.time = time;
        this.weblink = weblink;
    }

    @Override
    public String toString() {
        return name + ", " + day + ", " + time;
    }
}
