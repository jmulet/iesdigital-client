/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.iesapp.clients.iesdigital.guardies;
/**
 *
 * @author Josep
 */
public class RowModel {
    public static final int NH=8;
    public CellModel[] cells;


    public RowModel()   //default constructor
    {
        cells = new CellModel[NH];
        for (int i=0; i < NH; i++) {
            cells[i] = new CellModel();
        }
    }

    public RowModel(int n)   //default constructor
    {
        cells = new CellModel[n];
        for (int i=0; i<n; i++) {
            cells[i] = new CellModel();
        }
    }

    public boolean toBeShown()
    {
        boolean show = true;
        show = cells[1].text.isEmpty();
        for(int i=2; i<8; i++) {
            show &= cells[i].text.isEmpty();
        }
       
        return (!show);
    }
  
}
