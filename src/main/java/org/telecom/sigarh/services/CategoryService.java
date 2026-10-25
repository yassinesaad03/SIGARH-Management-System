package org.telecom.sigarh.services;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.telecom.sigarh.entities.Categorie;
import org.telecom.sigarh.entities.Corps;
import org.telecom.sigarh.repositories.CategorieRepository;
import org.telecom.sigarh.repositories.CorpsRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategorieRepository categorieRepository;
    private final CorpsRepository corpsRepository;

    public Categorie addCategorie(Categorie categorie, Long corpsId) {
        Corps corps = corpsRepository.findById(corpsId)
                .orElseThrow(() ->
                        new EntityNotFoundException("Corps introuvable"));

        categorie.setCorps(corps);

        return categorieRepository.save(categorie);

    }

    public Categorie updateCategorie(Long id, Categorie categorie) {

        Categorie existingCategorie = categorieRepository.findById(id)
                .orElseThrow(() ->
                        new EntityNotFoundException("Catégorie introuvable"));

        if (categorie.getNum() != null)
            existingCategorie.setNum(categorie.getNum());

        if (categorie.getGrade() != null)
            existingCategorie.setGrade(categorie.getGrade());

        if (categorie.getCorps() != null) {

            Corps corps = corpsRepository.findById(
                            categorie.getCorps().getId())
                    .orElseThrow(() ->
                            new EntityNotFoundException("Corps introuvable"));

            existingCategorie.setCorps(corps);

        }

        return categorieRepository.save(existingCategorie);

    }



    public List<Categorie> getCategoriesByCorps(Long corpsId) {

        return categorieRepository.findByCorpsId(corpsId);

    }
}
