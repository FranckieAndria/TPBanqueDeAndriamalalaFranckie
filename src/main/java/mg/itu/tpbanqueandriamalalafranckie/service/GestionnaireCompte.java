/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mg.itu.tpbanqueandriamalalafranckie.service;

import jakarta.annotation.sql.DataSourceDefinition;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.transaction.Transactional;
import java.util.List;
import mg.itu.tpbanqueandriamalalafranckie.entity.CompteBancaire;
import mg.itu.tpbanqueandriamalalafranckie.jsf.util.Util;

/**
 * Bean CDI Façade pour gérer les CompteBancaire
 *
 * @author andriamalala
 */
@DataSourceDefinition(
        className = "com.mysql.cj.jdbc.MysqlDataSource",
        name = "java:app/jdbc/banque",
        serverName = "localhost",
        portNumber = 3306,
        user = "franckie",
        password = "dev",
        databaseName = "banque",
        properties = {
            "useSSL=false",
            "allowPublicKeyRetrieval=true",
            "driverClass=com.mysql.cj.jdbc.Driver"
        }
)
@ApplicationScoped
public class GestionnaireCompte {

    @PersistenceContext
    private EntityManager em;

    @Transactional
    public boolean modifier(CompteBancaire compteBancaire, String nom, int solde) {
        boolean erreur = false;
        if (nom.equalsIgnoreCase("")) {
            Util.messageErreur("Le nom du compte est invalide", "Le nom du compte est invalide", "form:nom");
            erreur = true;
        }

        if (erreur) {
            return false;
        }

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

        // Attribution des nouvelles valeurs à compteBancaire avant l'update
        compteBancaire.setNom(nom);
        compteBancaire.setSolde(solde);

        this.update(compteBancaire);
        Util.addFlashInfoMessage(message);
        return true;
    }

    /**
     * Dépôt d'argent sur un compte bancaire.
     *
     * @param compteBancaire
     * @param montant
     */
    @Transactional
    public void deposer(CompteBancaire compteBancaire, int montant) {
        compteBancaire.deposer(montant);
        update(compteBancaire);
    }

    /**
     * Retrait d'argent sur un compte bancaire.
     *
     * @param compteBancaire
     * @param montant
     */
    @Transactional
    public void retirer(CompteBancaire compteBancaire, int montant) {
        compteBancaire.retirer(montant);
        update(compteBancaire);
    }

    /**
     * Créer un compte dans la base de données
     *
     * @param c
     * @return
     */
    @Transactional
    public boolean creerCompte(CompteBancaire c) {
        boolean erreur = false;

        // Contrôle si le nom est vide
        if (c.getNom().equalsIgnoreCase("")) {
            Util.messageErreur("Le compte doit avoir un nom", "Le compte doit avoir un nom", "form:nom");
            erreur = true;
        }

        if (erreur) {
            return false;
        }

        em.persist(c);
        Util.addFlashInfoMessage("Nouveau compte " + c.getNom() + " créé avec succès");
        return true;
    }

    /**
     * Effectuer un transfert entre 02 comptes
     *
     * @param idSource
     * @param idDestinataire
     * @param montant
     * @return
     */
    @Transactional
    public boolean transfert(Long idSource, Long idDestinataire, int montant) {
        boolean erreur = false;
        CompteBancaire compteSource = this.findById(idSource);
        CompteBancaire compteDestinataire = this.findById(idDestinataire);

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

        if (erreur) {
            return false;
        }

        compteSource.retirer(montant);
        compteDestinataire.deposer(montant);
        this.update(compteSource);
        this.update(compteDestinataire);

        String nomSource = compteSource.getNom();
        String nomDestination = compteDestinataire.getNom();
        Util.addFlashInfoMessage("Transfert du montant de " + montant + " depuis " + nomSource + " vers " + nomDestination + " correctement effectué");

        return true;
    }

    /**
     * Supprimer un compte bancaire
     *
     * @param compte
     */
    @Transactional
    public void supprimerCompte(CompteBancaire compte) {
        em.remove(em.merge(compte));
    }

    /**
     * Enregistrement des modifications apportées sur un compte
     *
     * @param compteBancaire
     * @return
     */
    @Transactional
    public CompteBancaire update(CompteBancaire compteBancaire) {
        return em.merge(compteBancaire);
    }

    /**
     * Récupérer un compte à partir de son id
     *
     * @param id
     * @return
     */
    public CompteBancaire findById(Long id) {
        return em.find(CompteBancaire.class, id);
    }

    /**
     * Récupérer la liste de tous les comptes bancaires
     *
     * @return
     */
    public List<CompteBancaire> getAllComptes() {
        Query query = em.createNamedQuery("CompteBancaire.findAll");
        return query.getResultList();
    }

    /**
     * Compte le nombre de comptes bancaires présent dans la base de données
     *
     * @return
     */
    public long countComptes() {
        Query query = em.createNamedQuery("CompteBancaire.count");
        return (long) query.getSingleResult();
    }

}
