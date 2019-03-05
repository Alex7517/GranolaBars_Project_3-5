package com.granolaBars;

import javax.persistence.*;
import java.util.Date;
import java.util.List;


public class FileIndexDBManager {
    //This creates the link from the JPA code to the actual DB, em will be used as the main reference
    //This should be moved into a better method later
    static EntityManagerFactory emf = Persistence.createEntityManagerFactory( "FileIndexDB" );
    static EntityManager em = emf.createEntityManager();

    static void testDB(){
        createFile("I am a Test Path: C:Place", new Date());
        createFile("I am another Test Path: C:Place2", new Date());
        System.out.println(loadFile("SELECT file FROM IndexedFile"));
    }

    //This will create a file to be added to the DB, then adds it
    //This is a partial stub Method
    static boolean createFile(String path, Date lastModification){
        try{
            //This creates a IndexedFile to be added to the DB
            IndexedFile file;
            file = new IndexedFile();
            file.setPath(path);
            file.setLastModification(lastModification);

            //This adds the file to the DB
            EntityTransaction tx = em.getTransaction();
            tx.begin();
            em.persist(file);
            tx.commit();
            return true;
        }
        catch (RuntimeException e){
            return false;
        }
    }

    //This will create a quarry with a passed SQL statement, then return results in a list
    //This is a partial stub Method
    static List<IndexedFile> loadFile(String sqlStatements){
        @SuppressWarnings("unchecked")
        List<IndexedFile> lndexedFileReturn;
        try{
            Query getAllFiles = em.createQuery(sqlStatements);
            lndexedFileReturn = getAllFiles.getResultList();
        }
        catch (IllegalArgumentException e)
        {
            lndexedFileReturn = null;
            System.out.println(e);
        }
        return lndexedFileReturn;
    }
}
