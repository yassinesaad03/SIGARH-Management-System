package org.telecom.sigarh.services;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telecom.sigarh.entities.Categorie;
import org.telecom.sigarh.entities.Corps;
import org.telecom.sigarh.entities.personnel;
import org.telecom.sigarh.repositories.CategorieRepository;
import org.telecom.sigarh.repositories.CorpsRepository;
import org.telecom.sigarh.repositories.personnelRepository;

import java.util.List;

@RequiredArgsConstructor
@Service
public class personnelService {

    private final personnelRepository personnelRepository;
    private final CorpsRepository corpsRepository;
    private final CategorieRepository categorieRepository;
    private final EmailService emailService;

    public personnel AddPersonnel(personnel personnel){
        personnel saved = this.personnelRepository.save(personnel);

        emailService.envoyerEmailAjout(saved);

        return saved;
    }


    public personnel updatePersonnel(Long id, personnel personnel) {
        personnel existingPersonnel = personnelRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Personnel introuvable avec l'id : " + id));

        if (personnel.getMatricule() != null)
            existingPersonnel.setMatricule(personnel.getMatricule());

        if (personnel.getHoraire() != null)
            existingPersonnel.setHoraire(personnel.getHoraire());

        if (personnel.getNom() != null)
            existingPersonnel.setNom(personnel.getNom());

        if (personnel.getPrenom() != null)
            existingPersonnel.setPrenom(personnel.getPrenom());

        if (personnel.getNomJeuneFille() != null)
            existingPersonnel.setNomJeuneFille(personnel.getNomJeuneFille());

        if (personnel.getPrenomPere() != null)
            existingPersonnel.setPrenomPere(personnel.getPrenomPere());

        if (personnel.getPrenomGpere() != null)
            existingPersonnel.setPrenomGpere(personnel.getPrenomGpere());

        if (personnel.getPrenomMere() != null)
            existingPersonnel.setPrenomMere(personnel.getPrenomMere());

        if (personnel.getDateNaissance() != null)
            existingPersonnel.setDateNaissance(personnel.getDateNaissance());

        if (personnel.getGenre() != null)
            existingPersonnel.setGenre(personnel.getGenre());

        if (personnel.getGroupSanguin() != null)
            existingPersonnel.setGroupSanguin(personnel.getGroupSanguin());

        if (personnel.getLieuNaissance() != null)
            existingPersonnel.setLieuNaissance(personnel.getLieuNaissance());

        if (personnel.getEtatCivil() != null)
            existingPersonnel.setEtatCivil(personnel.getEtatCivil());

        if (personnel.getMaitreFamille() != null)
            existingPersonnel.setMaitreFamille(personnel.getMaitreFamille());

        if (personnel.getPays() != null)
            existingPersonnel.setPays(personnel.getPays());

        if (personnel.getMail() != null)
            existingPersonnel.setMail(personnel.getMail());

        if (personnel.getNumTelephone1() != null)
            existingPersonnel.setNumTelephone1(personnel.getNumTelephone1());

        if (personnel.getNumTelephone2() != null)
            existingPersonnel.setNumTelephone2(personnel.getNumTelephone2());

        if (personnel.getIdentite() != null)
            existingPersonnel.setIdentite(personnel.getIdentite());

        if (personnel.getDateidentite() != null)
            existingPersonnel.setDateidentite(personnel.getDateidentite());

        if (personnel.getNumidentite() != null)
            existingPersonnel.setNumidentite(personnel.getNumidentite());

        if (personnel.getDelivrance() != null)
            existingPersonnel.setDelivrance(personnel.getDelivrance());

        if (personnel.getAssuranceGroup() != null)
            existingPersonnel.setAssuranceGroup(personnel.getAssuranceGroup());

        if (personnel.getAssuranceMaladie() != null)
            existingPersonnel.setAssuranceMaladie(personnel.getAssuranceMaladie());

        if (personnel.getAffiliationMutuelle() != null)
            existingPersonnel.setAffiliationMutuelle(personnel.getAffiliationMutuelle());

        if (personnel.getAssuranceConjoint() != null)
            existingPersonnel.setAssuranceConjoint(personnel.getAssuranceConjoint());

        if (personnel.getPrevoyanceSociale() != null)
            existingPersonnel.setPrevoyanceSociale(personnel.getPrevoyanceSociale());

        if (personnel.getCategorie() != null) {

            Categorie categorie = categorieRepository.findById(
                            personnel.getCategorie().getId())
                    .orElseThrow(() ->
                            new EntityNotFoundException("Catégorie introuvable"));

            existingPersonnel.setCategorie(categorie);

        }

        if (personnel.getDateCategorie() != null)
            existingPersonnel.setDateCategorie(personnel.getDateCategorie());

        if (personnel.getEchelle() != null)
            existingPersonnel.setEchelle(personnel.getEchelle());

        if (personnel.getDateechelle() != null)
            existingPersonnel.setDateechelle(personnel.getDateechelle());

        if (personnel.getCorps() != null) {

            Corps corps = corpsRepository.findById(
                            personnel.getCorps().getId())
                    .orElseThrow(() ->
                            new EntityNotFoundException("Corps introuvable"));

            existingPersonnel.setCorps(corps);

        }

        if (personnel.getGrade() != null)
            existingPersonnel.setGrade(personnel.getGrade());

        if (personnel.getDateGrade() != null)
            existingPersonnel.setDateGrade(personnel.getDateGrade());

        if (personnel.getRang() != null)
            existingPersonnel.setRang(personnel.getRang());

        if (personnel.getDaterang() != null)
            existingPersonnel.setDaterang(personnel.getDaterang());

        if (personnel.getPoste() != null)
            existingPersonnel.setPoste(personnel.getPoste());

        if (personnel.getDateEmbauche() != null)
            existingPersonnel.setDateEmbauche(personnel.getDateEmbauche());

        if (personnel.getDateEntree() != null)
            existingPersonnel.setDateEntree(personnel.getDateEntree());

        if (personnel.getServiceMilitaire() != null)
            existingPersonnel.setServiceMilitaire(personnel.getServiceMilitaire());

        if (personnel.getCaisse() != null)
            existingPersonnel.setCaisse(personnel.getCaisse());

        if (personnel.getNumcompte() != null)
            existingPersonnel.setNumcompte(personnel.getNumcompte());

        if (personnel.getComptecourant() != null)
            existingPersonnel.setComptecourant(personnel.getComptecourant());

        if (personnel.getNumaffiliation() != null)
            existingPersonnel.setNumaffiliation(personnel.getNumaffiliation());

        if (personnel.getHandicape() != null)
            existingPersonnel.setHandicape(personnel.getHandicape());

        if (personnel.getNaturehandicape() != null)
            existingPersonnel.setNaturehandicape(personnel.getNaturehandicape());

        if (personnel.getNumaffcomp() != null)
            existingPersonnel.setNumaffcomp(personnel.getNumaffcomp());

        if (personnel.getNumaffconv() != null)
            existingPersonnel.setNumaffconv(personnel.getNumaffconv());

        if (personnel.getLieutravail() != null)
            existingPersonnel.setLieutravail(personnel.getLieutravail());

        if (personnel.getPermis() != null)
            existingPersonnel.setPermis(personnel.getPermis());

        if (personnel.getRepartitionGeo() != null)
            existingPersonnel.setRepartitionGeo(personnel.getRepartitionGeo());

        if (personnel.getNationalite() != null)
            existingPersonnel.setNationalite(personnel.getNationalite());

        if (personnel.getNumfixe() != null)
            existingPersonnel.setNumfixe(personnel.getNumfixe());

        if (personnel.getAdresse() != null)
            existingPersonnel.setAdresse(personnel.getAdresse());

        if (personnel.getGouvernorat() != null)
            existingPersonnel.setGouvernorat(personnel.getGouvernorat());

        if (personnel.getCodePostal() != null)
            existingPersonnel.setCodePostal(personnel.getCodePostal());

        if (personnel.getNumAnneProlongement() != null)
            existingPersonnel.setNumAnneProlongement(personnel.getNumAnneProlongement());

        if (personnel.getDateRetraite() != null)
            existingPersonnel.setDateRetraite(personnel.getDateRetraite());

        if (personnel.getStatut() != null)
            existingPersonnel.setStatut(personnel.getStatut());

        if (personnel.getPhoto() != null)
            existingPersonnel.setPhoto(personnel.getPhoto());

        personnel updated = personnelRepository.save(existingPersonnel);

        emailService.envoyerEmailModification(updated);

        return updated;
    }

    public void deletePersonnel(Long id){
        this.personnelRepository.deleteById(id);
    }

    public List<personnel> getAllPersonnel(){
        return this.personnelRepository.findAll();
    }

    public personnel getPersonnelById(Long id){
        return this.personnelRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Personnel introuvable avec l'id : " + id));
    }


}
