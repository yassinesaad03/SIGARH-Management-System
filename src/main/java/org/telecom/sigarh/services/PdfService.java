package org.telecom.sigarh.services;

import com.lowagie.text.*;
import com.lowagie.text.Font;
import com.lowagie.text.Image;
import com.lowagie.text.Rectangle;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import com.lowagie.text.pdf.draw.LineSeparator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.telecom.sigarh.entities.personnel;

import java.awt.Color;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Service
public class PdfService {

    private static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    private static final Font TITLE_FONT   = new Font(Font.HELVETICA, 18, Font.BOLD, Color.BLACK);
    private static final Font SECTION_FONT = new Font(Font.HELVETICA, 13, Font.BOLD, Color.DARK_GRAY);
    private static final Font LABEL_FONT   = new Font(Font.HELVETICA, 11, Font.BOLD);
    private static final Font VALUE_FONT   = new Font(Font.HELVETICA, 11, Font.NORMAL);

    // Dossier contenant les photos, à la racine du projet.
    // Configurable via application.properties : app.upload.dir=/chemin/vers/uploads
    // Par défaut : <racine du projet>/uploads
    @Value("${app.upload.dir:${user.dir}/uploads}")
    private String uploadDir;

    public byte[] genererFichePersonnel(personnel p) throws DocumentException, IOException {

        Document document = new Document(PageSize.A4, 36, 36, 36, 36);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        PdfWriter.getInstance(document, outputStream);
        document.open();

        // ==========================
        // Titre
        // ==========================
        Paragraph title = new Paragraph("Fiche Personnel - SIGARH", TITLE_FONT);
        title.setAlignment(Element.ALIGN_CENTER);
        title.setSpacingAfter(15);
        document.add(title);

        // ==========================
        // En-tête : infos rapides + photo
        // ==========================
        ajouterEntete(document, p);

        // ==========================
        // Informations personnelles
        // ==========================
        ajouterSection(document, "Informations personnelles", SECTION_FONT);
        ajouterLigne(document, "Matricule", p.getMatricule());
        ajouterLigne(document, "Nom", p.getNom());
        ajouterLigne(document, "Prénom", p.getPrenom());
        ajouterLigne(document, "Nom de jeune fille", p.getNomJeuneFille());
        ajouterLigne(document, "Prénom du père", p.getPrenomPere());
        ajouterLigne(document, "Prénom du grand-père", p.getPrenomGpere());
        ajouterLigne(document, "Prénom de la mère", p.getPrenomMere());
        ajouterLigne(document, "Date de naissance", formatDate(p.getDateNaissance()));
        ajouterLigne(document, "Lieu de naissance", p.getLieuNaissance());
        ajouterLigne(document, "Genre", formatEnum(p.getGenre()));
        ajouterLigne(document, "Groupe sanguin", formatEnum(p.getGroupSanguin()));
        ajouterLigne(document, "État civil", formatEnum(p.getEtatCivil()));
        ajouterLigne(document, "Maître de famille", formatBool(p.getMaitreFamille()));
        ajouterLigne(document, "Nationalité", p.getNationalite());
        ajouterLigne(document, "Pays", p.getPays());
        ajouterLigne(document, "Handicapé", formatBool(p.getHandicape()));
        ajouterLigne(document, "Nature du handicap", p.getNaturehandicape());
        ajouterLigne(document, "Permis de conduire", formatEnum(p.getPermis()));

        // ==========================
        // Pièce d'identité
        // ==========================
        ajouterSection(document, "Pièce d'identité", SECTION_FONT);
        ajouterLigne(document, "Type de pièce", formatEnum(p.getIdentite()));
        ajouterLigne(document, "Numéro", p.getNumidentite());
        ajouterLigne(document, "Date de délivrance", formatDate(p.getDateidentite()));
        ajouterLigne(document, "Lieu de délivrance", p.getDelivrance());

        // ==========================
        // Coordonnées
        // ==========================
        ajouterSection(document, "Coordonnées", SECTION_FONT);
        ajouterLigne(document, "Email", p.getMail());
        ajouterLigne(document, "Téléphone 1", p.getNumTelephone1());
        ajouterLigne(document, "Téléphone 2", p.getNumTelephone2());
        ajouterLigne(document, "Téléphone fixe", p.getNumfixe());
        ajouterLigne(document, "Adresse", p.getAdresse());
        ajouterLigne(document, "Gouvernorat", p.getGouvernorat());
        ajouterLigne(document, "Code postal", p.getCodePostal());
        ajouterLigne(document, "Lieu de travail", p.getLieutravail());
        ajouterLigne(document, "Répartition géographique", p.getRepartitionGeo());

        // ==========================
        // Assurances & couverture sociale
        // ==========================
        ajouterSection(document, "Assurances & couverture sociale", SECTION_FONT);
        ajouterLigne(document, "Assurance groupe", formatBool(p.getAssuranceGroup()));
        ajouterLigne(document, "Assurance maladie", formatBool(p.getAssuranceMaladie()));
        ajouterLigne(document, "Affiliation mutuelle", formatBool(p.getAffiliationMutuelle()));
        ajouterLigne(document, "Assurance conjoint", formatBool(p.getAssuranceConjoint()));
        ajouterLigne(document, "Prévoyance sociale", formatBool(p.getPrevoyanceSociale()));
        ajouterLigne(document, "Caisse", formatEnum(p.getCaisse()));
        ajouterLigne(document, "Numéro d'affiliation", p.getNumaffiliation());
        ajouterLigne(document, "Numéro affiliation complémentaire", p.getNumaffcomp());
        ajouterLigne(document, "Numéro affiliation conventionnelle", p.getNumaffconv());

        // ==========================
        // Situation professionnelle
        // ==========================
        ajouterSection(document, "Situation professionnelle", SECTION_FONT);
        ajouterLigne(document, "Corps", p.getCorps() != null ? p.getCorps().getName() : null);
        ajouterLigne(document, "Catégorie", p.getCategorie() != null ? p.getCategorie().getNum() : null);
        ajouterLigne(document, "Date catégorie", formatDate(p.getDateCategorie()));
        ajouterLigne(document, "Grade", p.getGrade());
        ajouterLigne(document, "Date grade", formatDate(p.getDateGrade()));
        ajouterLigne(document, "Échelle", p.getEchelle());
        ajouterLigne(document, "Date échelle", formatDate(p.getDateechelle()));
        ajouterLigne(document, "Rang", p.getRang());
        ajouterLigne(document, "Date rang", formatDate(p.getDaterang()));
        ajouterLigne(document, "Poste", p.getPoste());
        ajouterLigne(document, "Horaire", formatEnum(p.getHoraire()));
        ajouterLigne(document, "Date embauche", formatDate(p.getDateEmbauche()));
        ajouterLigne(document, "Date entrée", formatDate(p.getDateEntree()));
        ajouterLigne(document, "Service militaire", formatEnum(p.getServiceMilitaire()));
        ajouterLigne(document, "Statut", formatEnum(p.getStatut()));

        // ==========================
        // Informations bancaires
        // ==========================
        ajouterSection(document, "Informations bancaires", SECTION_FONT);
        ajouterLigne(document, "Numéro de compte", p.getNumcompte());
        ajouterLigne(document, "Compte courant", p.getComptecourant());

        // ==========================
        // Retraite
        // ==========================
        ajouterSection(document, "Retraite", SECTION_FONT);
        ajouterLigne(document, "Nombre d'années de prolongement", p.getNumAnneProlongement());
        ajouterLigne(document, "Date de retraite", formatDate(p.getDateRetraite()));

        document.close();

        return outputStream.toByteArray();
    }

    // ==========================
    // En-tête avec photo
    // ==========================
    private void ajouterEntete(Document document, personnel p) throws DocumentException {

        PdfPTable entete = new PdfPTable(2);
        entete.setWidthPercentage(100);
        try {
            entete.setWidths(new float[]{75, 25});
        } catch (DocumentException e) {
            // ignore, fallback aux largeurs par défaut
        }
        entete.setSpacingAfter(10);

        // Colonne gauche : identité rapide
        PdfPCell cellInfos = new PdfPCell();
        cellInfos.setBorder(Rectangle.NO_BORDER);
        cellInfos.setVerticalAlignment(Element.ALIGN_MIDDLE);

        Paragraph nomComplet = new Paragraph(
                safe(p.getNom()) + " " + safe(p.getPrenom()),
                new Font(Font.HELVETICA, 15, Font.BOLD)
        );
        cellInfos.addElement(nomComplet);

        Paragraph matricule = new Paragraph(
                "Matricule : " + safe(p.getMatricule()),
                new Font(Font.HELVETICA, 11, Font.NORMAL, Color.GRAY)
        );
        cellInfos.addElement(matricule);

        Paragraph poste = new Paragraph(
                safe(p.getPoste()),
                new Font(Font.HELVETICA, 11, Font.ITALIC, Color.GRAY)
        );
        cellInfos.addElement(poste);

        entete.addCell(cellInfos);

        // Colonne droite : photo
        PdfPCell cellPhoto = new PdfPCell();
        cellPhoto.setBorder(Rectangle.NO_BORDER);
        cellPhoto.setHorizontalAlignment(Element.ALIGN_RIGHT);

        Image photo = chargerPhoto(p.getPhoto());
        if (photo != null) {
            photo.scaleToFit(90, 110);
            cellPhoto.addElement(photo);
        } else {
            Paragraph noPhoto = new Paragraph("Photo indisponible",
                    new Font(Font.HELVETICA, 9, Font.ITALIC, Color.LIGHT_GRAY));
            cellPhoto.addElement(noPhoto);
        }

        entete.addCell(cellPhoto);
        document.add(entete);

        // Ligne de séparation
        LineSeparator sep = new LineSeparator();
        sep.setLineColor(Color.LIGHT_GRAY);
        document.add(new Chunk(sep));
        document.add(Chunk.NEWLINE);
    }

    /**
     * Tente de charger la photo à partir d'un chemin de fichier, d'une URL,
     * ou d'une chaîne encodée en base64. Retourne null si indisponible.
     */
    private Image chargerPhoto(String photoRef) {
        if (photoRef == null || photoRef.isBlank()) {
            return null;
        }
        try {
            if (photoRef.startsWith("http://") || photoRef.startsWith("https://")) {
                return Image.getInstance(new java.net.URL(photoRef));
            }
            if (photoRef.startsWith("data:image")) {
                String base64 = photoRef.substring(photoRef.indexOf(",") + 1);
                byte[] bytes = java.util.Base64.getDecoder().decode(base64);
                return Image.getInstance(bytes);
            }

            // Cas normal : photoRef ne contient que le nom du fichier
            // (ex: "photo123.jpg"), stocké dans <racine du projet>/uploads
            File file = new File(uploadDir, photoRef);
            if (file.exists() && file.isFile()) {
                return Image.getInstance(file.getAbsolutePath());
            }

            System.err.println("Photo introuvable : " + file.getAbsolutePath());
        } catch (Exception e) {
            // photo illisible ou introuvable : on l'ignore proprement
            System.err.println("Erreur lors du chargement de la photo '" + photoRef + "' : " + e.getMessage());
            return null;
        }
        return null;
    }

    // ==========================
    // Helpers d'affichage
    // ==========================

    private void ajouterSection(Document document, String titre, Font font) throws DocumentException {
        Paragraph section = new Paragraph(titre, font);
        section.setSpacingBefore(15);
        section.setSpacingAfter(8);
        document.add(section);

        LineSeparator sep = new LineSeparator();
        sep.setLineColor(Color.LIGHT_GRAY);
        sep.setLineWidth(0.5f);
        document.add(new Chunk(sep));
        document.add(Chunk.NEWLINE);
    }

    private void ajouterLigne(Document document, String label, Object valeur) throws DocumentException {
        String texteValeur = (valeur != null && !valeur.toString().isBlank()) ? valeur.toString() : "-";

        Paragraph p = new Paragraph();
        p.add(new Chunk(label + " : ", LABEL_FONT));
        p.add(new Chunk(texteValeur, VALUE_FONT));
        p.setSpacingAfter(4);

        document.add(p);
    }

    private String formatDate(LocalDate date) {
        return date != null ? date.format(DATE_FORMAT) : null;
    }

    private String formatBool(Boolean valeur) {
        if (valeur == null) return null;
        return valeur ? "Oui" : "Non";
    }

    private String formatEnum(Enum<?> valeur) {
        return valeur != null ? valeur.name() : null;
    }

    private String safe(Object valeur) {
        return valeur != null ? valeur.toString() : "";
    }
}