package com.granolaBars;

import javax.persistence.*;
import java.util.Date;

@Entity
public class IndexedFile {
    @Id @GeneratedValue
    private int id;
    private String name;
    private String path;
    //This is an issue with DATA, I think the DB states it needs more info
    @Temporal(value=TemporalType.DATE)
    private Date lastModification;

    public IndexedFile () {
    }

    public IndexedFile (String name, String path, Date lastModification) {
        this.name = name;
        this.path = path;
        this.lastModification = lastModification;
    }

    public int getId() {
        return id;
    }

    public void setName(String name) { this.name = name; }

    public String getName() { return name; }

    public void setPath(String path) {
        this.path = path;
    }

    public String getPath() {
        return path;
    }

    public void setLastModification(Date lastModification) {
        this.lastModification = lastModification;
    }

    public Date getLastModification() {
        return lastModification;
    }
}
