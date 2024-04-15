/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSF/JSFManagedBean.java to edit this template
 */
package mg.itu.tpbanqueandriamalalafranckie.jsf;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Named;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.validation.constraints.PositiveOrZero;
import mg.itu.tpbanqueandriamalalafranckie.entity.CompteBancaire;
import mg.itu.tpbanqueandriamalalafranckie.jsf.util.Util;
import mg.itu.tpbanqueandriamalalafranckie.service.GestionnaireCompte;

/**
 * Backing bean pour la page JSF ajoutCompte
 *
 * @author andriamalala
 */
@Named(value = "ajoutCompte")
@RequestScoped
public class AjoutCompte {

    private String nom;

    @PositiveOrZero
    private int solde;

    @Inject
    private GestionnaireCompte gestionnaireCompte;

    @Transactional
    public String action() {
        boolean erreur = false;

        // Contrôle si le nom est vide
        if (this.nom.equalsIgnoreCase("")) {
            Util.messageErreur("Le compte doit avoir un nom", "Le compte doit avoir un nom", "form:nom");
            erreur = true;
        }

        if (!erreur) {
            this.gestionnaireCompte.creerCompte(new CompteBancaire(this.nom, this.solde));
            Util.addFlashInfoMessage("Nouveau compte " + this.nom + " créé avec succès");
            return "listeComptes?faces-redirect=true";
        } else {
            return null;
        }

    }

    /**
     * Creates a new instance of AjoutCompte
     */
    public AjoutCompte() {

    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public int getSolde() {
        return solde;
    }

    public void setSolde(int solde) {
        this.solde = solde;
    }

}
