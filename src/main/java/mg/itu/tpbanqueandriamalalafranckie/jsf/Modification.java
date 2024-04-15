/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSF/JSFManagedBean.java to edit this template
 */
package mg.itu.tpbanqueandriamalalafranckie.jsf;

import jakarta.inject.Named;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.validation.constraints.PositiveOrZero;
import java.io.Serializable;
import mg.itu.tpbanqueandriamalalafranckie.entity.CompteBancaire;
import mg.itu.tpbanqueandriamalalafranckie.jsf.util.Util;
import mg.itu.tpbanqueandriamalalafranckie.service.GestionnaireCompte;

/**
 *
 * @author andriamalala
 */
@Named(value = "modification")
@ViewScoped
public class Modification implements Serializable {

    private Long id;
    private CompteBancaire compteBancaire;

    private String nom;

    @PositiveOrZero
    private int solde;

    @Inject
    private GestionnaireCompte gestionnaireCompte;

    /**
     * Méthode action appelée dans le formulaire
     *
     * @return
     */
    public String modifier() {
        boolean erreur = false;
        
        // Contrôle si le nom est vide
        if (nom.equalsIgnoreCase("")) {
            Util.messageErreur("Le nom du compte est invalide", "Le nom du compte est invalide", "form:nom");
            erreur = true;
        }
        
        // Création du message en fonction des modifications apportées par l'utilisateur
        String message = "";
        if (compteBancaire.getNom().equals(nom) && compteBancaire.getSolde() == solde) {
            message += "Aucune modification apportée";
        } else {
            message += "Modification réussie :";
            if (!compteBancaire.getNom().equals(nom)) {
                message += " Nom " + compteBancaire.getNom() + " changé en " + nom;
            }
            if (compteBancaire.getSolde() != solde) {
                message += " Solde " + compteBancaire.getSolde() + " changé en " + solde;
            }
        }

        if (!erreur) {
            compteBancaire.setNom(nom);
            compteBancaire.setSolde(solde);
            this.gestionnaireCompte.modifier(compteBancaire);
            Util.addFlashInfoMessage(message);
            return "listeComptes?faces-redirect=true";
        }
        return null;
    }

    public void loadCompte() {
        compteBancaire = gestionnaireCompte.findById(id);
        this.nom = this.compteBancaire.getNom();
        this.solde = this.compteBancaire.getSolde();
    }

    /**
     * Creates a new instance of Modification
     */
    public Modification() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public CompteBancaire getCompteBancaire() {
        return compteBancaire;
    }

    public void setCompteBancaire(CompteBancaire compteBancaire) {
        this.compteBancaire = compteBancaire;
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
