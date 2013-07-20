package org.iesapp.clients.iesdigital.fitxes;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */



import java.util.ArrayList;

/**
 *
 * @author Josep
 */
public class TasquesPendentsStruct {
    public ArrayList<Integer> idTasks;
    public ArrayList<String>  detallTasks;

    public TasquesPendentsStruct()
    {
        idTasks = new ArrayList<Integer>();
        detallTasks = new ArrayList<String>();
    }
}
