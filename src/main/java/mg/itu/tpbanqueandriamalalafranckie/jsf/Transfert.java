/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSF/JSFManagedBean.java to edit this template
 */
package mg.itu.tpbanqueandriamalalafranckie.jsf;

import jakarta.inject.Named;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import java.io.Serializable;
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
        boolean succes = this.gestionnaireCompte.transfert(this.getIdSource(), this.getIdDestinataire(), this.getMontant());
        if (!succes) {
            return null;
        }
        return "listeComptes?faces-redirect=true";
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
