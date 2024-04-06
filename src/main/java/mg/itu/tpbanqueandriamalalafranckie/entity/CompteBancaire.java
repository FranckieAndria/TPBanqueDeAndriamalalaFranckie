/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mg.itu.tpbanqueandriamalalafranckie.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import java.io.Serializable;

/**
 * Entité de COMPTEBANCAIRE
 *
 * @author andriamalala
 */
@Entity
@NamedQueries({
    @NamedQuery(name = "CompteBancaire.findAll", query = "SELECT c FROM CompteBancaire c"),
    @NamedQuery(name = "CompteBancaire.count", query = "SELECT count(c) FROM CompteBancaire c")
})
public class CompteBancaire implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nom;
    private int solde;

    /**
     * Constructeur sans paramètre pour JPA
     */
    public CompteBancaire() {
        
    }

    /**
     * Contructeur prenant en paramètre le nom du propriétaire et le sodle initial
     * @param nom
     * @param solde 
     */
    public CompteBancaire(String nom, int solde) {
        this.nom = nom;
        this.solde = solde;
    }

    /**
     * Méthode pour ajouter de l'argent
     * @param montant 
     */
    public void deposer(int montant) {
        solde += montant;
    }

    /**
     * Méthode pour retirer de l'argent
     * @param montant 
     */
    public void retirer(int montant) {
        if (montant < solde) {
            solde -= montant;
        } else {
            solde = 0;
        }
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

    public Long getId() {
        return id;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CompteBancaire)) {
            return false;
        }
        CompteBancaire other = (CompteBancaire) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "mg.itu.tpbanqueandriamalalafranckie.entity.CompteBancaire[ id=" + id + " ]";
    }

}