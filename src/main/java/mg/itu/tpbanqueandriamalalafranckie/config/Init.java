/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mg.itu.tpbanqueandriamalalafranckie.config;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.context.Initialized;
import jakarta.enterprise.event.Observes;
import jakarta.inject.Inject;
import jakarta.servlet.ServletContext;
import jakarta.transaction.Transactional;
import java.util.logging.Logger;
import mg.itu.tpbanqueandriamalalafranckie.entity.CompteBancaire;
import mg.itu.tpbanqueandriamalalafranckie.service.GestionnaireCompte;

/**
 * Pour la création de 04 comptes au démarrage de l'application
 *
 * @author andriamalala
 */
@ApplicationScoped
public class Init {
    
    private final static Logger logger = Logger.getLogger("mg.itu.tpbanqueandriamalalafranckie.Init");

    @Inject
    private GestionnaireCompte gestionnaireCompte;

    @Transactional
    public void init(
            @Observes
            @Initialized(ApplicationScoped.class) ServletContext context) {       
        if (this.gestionnaireCompte.countComptes() != 0) {
            logger.info("La base de données n'est pas vide");
            return;
        }
        logger.warning("Aucun compte dans la base de données. Création de comptes");
        CompteBancaire compteJohn = new CompteBancaire("John Lennon", 150000);
        CompteBancaire comptePaul = new CompteBancaire("Paul McCartney", 950000);
        CompteBancaire compteRingo = new CompteBancaire("Ringo Starr", 20000);
        CompteBancaire compteGeorges = new CompteBancaire("Georges Harrisson", 100000);

        this.gestionnaireCompte.creerCompte(compteJohn);
        this.gestionnaireCompte.creerCompte(comptePaul);
        this.gestionnaireCompte.creerCompte(compteRingo);
        this.gestionnaireCompte.creerCompte(compteGeorges);
    }

}
