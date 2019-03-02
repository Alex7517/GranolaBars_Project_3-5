package com.granolaBars;

import javax.persistence.*;
import java.util.Date;

@Entity
public class IndexedFile {
    @Id @GeneratedValue
    private int id;
    private String path;
    //This is an issue with DATA, I think the DB states it needs more info
    //private Date lastModification;

    public IndexedFile () {
    }

    public IndexedFile (String path, Date lastModification) {
        this.path = path;
        //this.lastModification = lastModification;
    }

    public int getId() {
        return id;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getPath() {
        return path;
    }

    /*public void setLastModification(Date lastModification) {
        this.lastModification = lastModification;
    }

    public Date getLastModification() {
        return lastModification;
    }*/
}
