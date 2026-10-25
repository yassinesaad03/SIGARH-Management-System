package org.telecom.sigarh.services;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.telecom.sigarh.entities.Corps;
import org.telecom.sigarh.repositories.CorpsRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CorpsService {
    private final CorpsRepository corpsRepository;

    public Corps addCorps(Corps corps) {

        return corpsRepository.save(corps);

    }

    public Corps updateCorps(Long id, Corps corps) {

        Corps existingCorps = corpsRepository.findById(id)
                .orElseThrow(() ->
                        new EntityNotFoundException("Corps introuvable avec l'id : " + id));

        if (corps.getName() != null)
            existingCorps.setName(corps.getName());

        return corpsRepository.save(existingCorps);

    }
    public List<Corps> getAllCorps(){
        return corpsRepository.findAll();
    }
}
