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

    /**
     * Créer un compte dans la base de données
     *
     * @param c
     */
    public void creerCompte(CompteBancaire c) {
        em.persist(c);
    }

    /**
     * Effectuer un transfert entre 02 comptes
     *
     * @param idSource
     * @param idDestinataire
     * @param montant
     */
    @Transactional
    public void transfert(Long idSource, Long idDestinataire, int montant) {
        CompteBancaire compteSource = this.findById(idSource);
        CompteBancaire compteDestinataire = this.findById(idDestinataire);

        compteSource.retirer(montant);
        compteDestinataire.deposer(montant);

        this.update(compteSource);
        this.update(compteDestinataire);
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
