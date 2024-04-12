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
import mg.itu.tpbanqueandriamalalafranckie.service.GestionnaireCompte;

/**
 * Backing bean pour la page JSF ajoutCompte
 *
 * @author andriamalala
 */
@Named(value = "ajoutCompte")
@ViewScoped
public class AjoutCompte implements Serializable {
    
    private String nom;
    
    @PositiveOrZero
    private int montant;
    
    @Inject
    private GestionnaireCompte gestionnaireCompte;
    
    public String action() {
        CompteBancaire compteBancaire = new CompteBancaire(this.nom, this.montant);
        boolean succes = this.gestionnaireCompte.creerCompte(compteBancaire);
        if (!succes) {
            return null;
        }
        return "listeComptes?faces-redirect=true";
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

    public int getMontant() {
        return montant;
    }

    public void setMontant(int montant) {
        this.montant = montant;
    }

}
