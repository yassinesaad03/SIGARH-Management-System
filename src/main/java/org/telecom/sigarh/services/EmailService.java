package org.telecom.sigarh.services;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.telecom.sigarh.entities.personnel;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

@Service
public class EmailService {

    private final JavaMailSender mailSender;
    private final PdfService pdfService;

    @Value("${spring.mail.username}")
    private String expediteur;

    private static final String NOM_AFFICHAGE = "SIGARH";   // 🔹 NOUVEAU

    public EmailService(JavaMailSender mailSender, PdfService pdfService) {
        this.mailSender = mailSender;
        this.pdfService = pdfService;
    }

    @Async("emailTaskExecutor")
    public void envoyerEmailAjout(personnel p) {
        envoyerEmail(
                p,
                "Confirmation d'enregistrement - SIGARH",
                "Bonjour " + p.getPrenom() + " " + p.getNom() + ",\n\n" +
                        "Nous vous confirmons que vos données ont été enregistrées avec succès dans le système SIGARH.\n" +
                        "Vous trouverez ci-joint votre fiche personnelle récapitulative au format PDF.\n\n" +
                        "Cordialement,\nL'équipe SIGARH"
        );
    }

    @Async("emailTaskExecutor")
    public void envoyerEmailModification(personnel p) {
        envoyerEmail(
                p,
                "Mise à jour de vos données - SIGARH",
                "Bonjour " + p.getPrenom() + " " + p.getNom() + ",\n\n" +
                        "Nous vous confirmons que vos données ont été mises à jour avec succès dans le système SIGARH.\n" +
                        "Vous trouverez ci-joint votre fiche personnelle récapitulative actualisée au format PDF.\n\n" +
                        "Cordialement,\nL'équipe SIGARH"
        );
    }

    private void envoyerEmail(personnel p, String sujet, String corps) {

        if (p.getMail() == null || p.getMail().isBlank()) {
            return;
        }

        try {

            byte[] pdf = pdfService.genererFichePersonnel(p);

            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);

            // 🔹 NOUVEAU : nom d'affichage au lieu de l'adresse brute
            helper.setFrom(new InternetAddress(expediteur, NOM_AFFICHAGE, "UTF-8"));

            helper.setTo(p.getMail());
            helper.setSubject(sujet);
            helper.setText(corps);

            helper.addAttachment(
                    "fiche_personnel_" + p.getMatricule() + ".pdf",
                    new org.springframework.core.io.ByteArrayResource(pdf)
            );

            mailSender.send(message);
        }
        catch (MessagingException
               | com.lowagie.text.DocumentException
               | IOException e) {

                System.err.println("Erreur lors de l'envoi de l'email : " + e.getMessage());
            }
    }
}