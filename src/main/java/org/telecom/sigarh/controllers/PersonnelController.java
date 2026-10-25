package org.telecom.sigarh.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.telecom.sigarh.entities.personnel;
import org.telecom.sigarh.repositories.personnelRepository;
import org.telecom.sigarh.services.personnelService;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/personnel")
public class PersonnelController {
    private final personnelService personnelService;

    @PostMapping("/addPersonnel")
    public personnel AddPersonnel(@RequestBody personnel personnel) {
        return personnelService.AddPersonnel(personnel);
    }
    @PatchMapping("/updatePersonnel/{id}")
    public personnel updatePersonnel(@PathVariable Long id,
                                     @RequestBody personnel personnel) {
        return personnelService.updatePersonnel(id, personnel);
    }
    @DeleteMapping("/deletePersonnel/{id}")
    public void deletePersonnel(@PathVariable Long id) {
        personnelService.deletePersonnel(id);
    }
    @GetMapping("/getAllPersonnel")
    public List<personnel> getAllPersonnel() {
        return personnelService.getAllPersonnel();
    }
    @GetMapping("/getPersonnelById/{id}")
    public personnel getPersonnelById(@PathVariable Long id) {
        return personnelService.getPersonnelById(id);
    }
}
