package model;

import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@XmlRootElement
public class LinkList {
    @Setter
    private List<Link> links = new ArrayList<>();

    public LinkList() {
    }

    public LinkList(List<Link> links) {
        this.links = links;
    }

    @XmlElement
    public List<Link> getLinks() {
        if (links == null)
            links = new ArrayList<>();
        return links;
    }
}
