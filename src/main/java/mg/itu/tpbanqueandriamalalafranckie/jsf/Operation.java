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
import mg.itu.tpbanqueandriamalalafranckie.entity.OperationBancaire;
import mg.itu.tpbanqueandriamalalafranckie.service.GestionnaireCompte;

/**
 * Backing bean pour la page JSF Operation
 *
 * @author andriamalala
 */
@Named(value = "operation")
@ViewScoped
public class Operation implements Serializable {
    
    private Long id;
    private CompteBancaire compteBancaire;
    
    @Inject
    private GestionnaireCompte gestionnaireCompte;
    
    public List<OperationBancaire> getOperations() {
        return this.compteBancaire.getOperations();
    }
    
    /**
     * Récupérer le compte à partir de l'id
     */
    public void loadCompteBancaire() {
        compteBancaire = gestionnaireCompte.findById(this.id);
    }

    public CompteBancaire getCompteBancaire() {
        return compteBancaire;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Creates a new instance of Operation
     */
    public Operation() {
    }

}
