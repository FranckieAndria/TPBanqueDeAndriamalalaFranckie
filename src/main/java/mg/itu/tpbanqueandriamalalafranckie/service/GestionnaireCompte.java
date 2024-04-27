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
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;
import java.util.List;
import mg.itu.tpbanqueandriamalalafranckie.entity.CompteBancaire;

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
    public void modifier(CompteBancaire compteBancaire) {
        this.update(compteBancaire);
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
     */
    @Transactional
    public void creerCompte(CompteBancaire c) {
        em.persist(c);
    }

    /**
     * Effectuer un transfert entre 02 comptes
     *
     * @param compteSource
     * @param compteDestinataire
     * @param montant
     */
    @Transactional
    public void transfert(CompteBancaire compteSource, CompteBancaire compteDestinataire, int montant) {
        compteSource.retirer(montant);
        compteDestinataire.deposer(montant);
        this.update(compteSource);
        this.update(compteDestinataire);
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
        TypedQuery<CompteBancaire> query = em.createNamedQuery("CompteBancaire.findAll", CompteBancaire.class);
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
