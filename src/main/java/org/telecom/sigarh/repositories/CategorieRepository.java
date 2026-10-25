package org.telecom.sigarh.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.telecom.sigarh.entities.Categorie;

import java.util.List;

public interface CategorieRepository extends JpaRepository<Categorie,Long> {
List<Categorie> findByCorpsId(Long id);

}
