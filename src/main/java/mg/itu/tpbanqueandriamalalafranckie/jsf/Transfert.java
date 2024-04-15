/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSF/JSFManagedBean.java to edit this template
 */
package mg.itu.tpbanqueandriamalalafranckie.jsf;

import jakarta.inject.Named;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import java.io.Serializable;
import mg.itu.tpbanqueandriamalalafranckie.entity.CompteBancaire;
import mg.itu.tpbanqueandriamalalafranckie.jsf.util.Util;
import mg.itu.tpbanqueandriamalalafranckie.service.GestionnaireCompte;

/**
 *
 * @author andriamalala
 */
@Named(value = "transfert")
@ViewScoped
public class Transfert implements Serializable {
    
    private Long idSource;
    private Long idDestinataire;
    private int montant;
    
    @Inject
    private GestionnaireCompte gestionnaireCompte;
    
    public String transfert() {
        boolean erreur = false;
        CompteBancaire compteSource = this.gestionnaireCompte.findById(idSource);
        CompteBancaire compteDestinataire = this.gestionnaireCompte.findById(idDestinataire);

        // Vérification si les comptes existent bien dans la base de données
        if (compteSource == null) {
            Util.messageErreur("Aucun compte avec l'id : " + idSource, "Aucun compte avec l'id : " + idSource, "form:source");
            erreur = true;
        }
        if (compteDestinataire == null) {
            Util.messageErreur("Aucun compte avec l'id : " + idDestinataire, "Aucun compte avec l'id : " + idDestinataire, "form:destinataire");
            erreur = true;
        }

        // Vérification si le solde du compte source est suffisant pour le transfert
        if (compteSource != null && compteSource.getSolde() < montant) {
            Util.messageErreur("Le solde du compte source est insuffisant", "Le solde du compte source est insuffisant", "form:montant");
            erreur = true;
        }

        // Vérification si le montant est négatif
        if (montant < 0) {
            Util.messageErreur("Le montant saisi est invalide", "Le montant saisi est invalide", "form:montant");
            erreur = true;
        }

        if (!erreur) {
            this.gestionnaireCompte.transfert(compteSource, compteDestinataire, this.getMontant());
            Util.addFlashInfoMessage("Transfert de " + montant + " depuis " + compteSource.getNom() + " vers " + compteDestinataire.getNom() + " correctement effectué");
            return "listeComptes?faces-redirect=true";
        } else {
            return null;
        }
    }

    /**
     * Creates a new instance of Transfert
     */
    public Transfert() {
    }
    
    public Long getIdSource() {
        return idSource;
    }

    public void setIdSource(Long idSource) {
        this.idSource = idSource;
    }

    public Long getIdDestinataire() {
        return idDestinataire;
    }

    public void setIdDestinataire(Long idDestinataire) {
        this.idDestinataire = idDestinataire;
    }

    public int getMontant() {
        return montant;
    }

    public void setMontant(int montant) {
        this.montant = montant;
    }
    
}
