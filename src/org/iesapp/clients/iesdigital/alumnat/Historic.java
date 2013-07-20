/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.iesapp.clients.iesdigital.alumnat;

import java.util.ArrayList;

/**
 *
 * @author Josep
 */
    public class Historic {

        public int exp2=0;
        public String nomAlumne="";
        public String anyAcademic="";
        public String ensenyament="";
        public String estudis="";
        public String grup="";
        public ArrayList<String> profTutor;
        public ArrayList<Integer> codigoTutor;

        public Historic() {
                profTutor = new ArrayList<String>();
                codigoTutor = new ArrayList<Integer>();
        }
    }
