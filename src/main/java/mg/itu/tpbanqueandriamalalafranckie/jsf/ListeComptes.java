/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSF/JSFManagedBean.java to edit this template
 */
package mg.itu.tpbanqueandriamalalafranckie.jsf;

import jakarta.inject.Named;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import java.io.Serializable;
import java.util.List;
import mg.itu.tpbanqueandriamalalafranckie.entity.CompteBancaire;
import mg.itu.tpbanqueandriamalalafranckie.jsf.util.Util;
import mg.itu.tpbanqueandriamalalafranckie.service.GestionnaireCompte;

/**
 * Backing bean pour la page JSF listeComptes
 *
 * @author andriamalala
 */
@Named(value = "listeComptes")
@ViewScoped
public class ListeComptes implements Serializable {

    @Inject
    private GestionnaireCompte gestionCompte;

    private List<CompteBancaire> compteBancaireList;

    /**
     * Retourne la liste de tous les comptes
     *
     * @return
     */
    public List<CompteBancaire> getAllComptes() {
        if (this.compteBancaireList == null) {
            this.compteBancaireList = this.gestionCompte.getAllComptes();
        }
        return this.compteBancaireList;
    }

    /**
     * Supprimer un compte bancaire dans la base de données
     *
     * @param compteBancaire
     * @return
     */
    public String supprimerCompte(CompteBancaire compteBancaire) {
        gestionCompte.supprimerCompte(compteBancaire);
        Util.addFlashInfoMessage("Compte de " + compteBancaire.getNom() + " supprimé");
        return "listeComptes?faces-redirect=true";
    }

    /**
     * Creates a new instance of ListeComptes
     */
    public ListeComptes() {
    }

}
