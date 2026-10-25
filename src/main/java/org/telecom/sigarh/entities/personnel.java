package org.telecom.sigarh.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
@Entity
@Getter
@Setter
public class personnel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long matricule;
    @Enumerated(EnumType.STRING)
    private typeHoraire horaire;
    private String nom;
    private String prenom;
    private String nomJeuneFille;
    private String prenomPere;
    private String prenomGpere;
    private String prenomMere;
    private LocalDate dateNaissance;
    @Enumerated(EnumType.STRING)
    private Genre genre;
    @Enumerated(EnumType.STRING)
    private GroupSanguin  groupSanguin;
    private String lieuNaissance;
    @Enumerated(EnumType.STRING)
    private EtatCivil etatCivil;
    private Boolean MaitreFamille;
    private String pays;
    private String mail;
    private Integer numTelephone1;
    private Integer numTelephone2;
    @Enumerated(EnumType.STRING)
    private TypeIdentite identite;
    private LocalDate dateidentite;
    private Integer numidentite;
    private String delivrance;
    private Boolean assuranceGroup;
    private Boolean assuranceMaladie;
    private Boolean affiliationMutuelle;
    private Boolean assuranceConjoint;
    private Boolean prevoyanceSociale;
    private LocalDate dateCategorie;
    private Integer echelle;
    private LocalDate dateechelle;
    private String grade;
    private LocalDate dateGrade;
    private Integer rang;
    private LocalDate daterang;
    private String poste;
    private LocalDate dateEmbauche;
    private LocalDate dateEntree;
    @Enumerated(EnumType.STRING)
    private ServiceMilitaire serviceMilitaire;
    @Enumerated(EnumType.STRING)
    private Caisse caisse;
    private Long numcompte;
    private Long comptecourant;
    private Long numaffiliation;
    private Boolean handicape;
    private String naturehandicape;
    private Long numaffcomp;
    private Long numaffconv;
    private String lieutravail;
    private Permis permis;
    private String repartitionGeo;
    private String nationalite;
    private Integer numfixe;
    private String adresse;
    private String gouvernorat;
    private Integer codePostal;
    private Integer numAnneProlongement;
    private LocalDate dateRetraite;
    @Enumerated(EnumType.STRING)
    private Statut statut;
    private String photo;
    @ManyToOne
    Corps corps;
    @ManyToOne
    private Categorie categorie;


}
