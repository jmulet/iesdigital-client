/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.iesapp.clients.iesdigital.fitxes;

/**
 *
 * @author Josep
 */
public class Permisos {

        public short dadesPersonals_edit;
        public short dadesPersonals_view;
        public short dadesPrimaria_view;
        
        public short fitxaActual_edit;
        public short fitxaActual_view;
        public short fitxaAnterior_edit;
        public short fitxaAnterior_view;
        
        public short informeResumFitxa_gen;
        public short informeFitxaTutoria_gen;
        public short informePasswords_gen;
        public short informeAccions;
        
        public short fitxesCtrl_crear;
        public short fitxesCtrl_esborrar;
        
        public short imported_edit;
        
        public short accions_view;
        public short accions_fullEdit;
        public short accions_esborrar;
        public short accions_tancar;
        
        public short cerca_mostraAvancada;
        public short cerca_permetAccions;
        public short medicaments_edit;
        public short medicaments_view;
        public short medicaments_give;
        public short informeMedicaments;
        
        public short nese_view;
        public short nese_edit;
        public short informeSancions;
        
        public short entrevistaPares_view;
        public short entrevistaPares_edit;
        public short informeSGD_gen;
        public short justificarFaltes;
        
       
        
      
      public Permisos() {
          
      }

      public Permisos(short i) {
        dadesPersonals_edit = i;
        dadesPersonals_view = i;
        dadesPrimaria_view = i;

        fitxaActual_edit = i;
        fitxaActual_view = i;
        fitxaAnterior_edit = i;
        fitxaAnterior_view = i;

        informeResumFitxa_gen = i;
        informeFitxaTutoria_gen = i;
        informePasswords_gen = i;
        informeAccions = i;
        informeSancions = i;
        informeSGD_gen = i;

        fitxesCtrl_crear = i;
        fitxesCtrl_esborrar = i;

        imported_edit = i;

        accions_view = i;
        accions_fullEdit = i;
        accions_esborrar = i;
        accions_tancar = i;

        cerca_mostraAvancada = i;
        cerca_permetAccions = i;
        
        medicaments_edit = i;
        medicaments_view = i;
        medicaments_give = i;
        informeMedicaments = i;
    
        nese_edit = i;
        nese_view = i;
        
        entrevistaPares_view= i;
        entrevistaPares_edit= i;
        
        justificarFaltes = i;
       }

    }
