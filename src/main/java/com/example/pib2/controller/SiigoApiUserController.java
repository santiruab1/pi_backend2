package com.example.pib2.controller;

import com.example.pib2.model.dto.SiigoApiUserCreateDTO;
import com.example.pib2.model.dto.SiigoApiUserDTO;
import com.example.pib2.service.SiigoApiUserService;
import com.example.pib2.service.SiigoApiUserService.SiigoApiUserStatsDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/siigo-api-users")
public class SiigoApiUserController {
    
    @Autowired
    private SiigoApiUserService siigoApiUserService;
    
    @PostMapping
    public ResponseEntity<SiigoApiUserDTO> createSiigoApiUser(@RequestBody SiigoApiUserCreateDTO createDTO) {
        try {
            SiigoApiUserDTO createdUser = siigoApiUserService.createSiigoApiUser(createDTO);
            return new ResponseEntity<>(createdUser, HttpStatus.CREATED);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
    
    @GetMapping
    public ResponseEntity<List<SiigoApiUserDTO>> getAllSiigoApiUsers() {
        List<SiigoApiUserDTO> userList = siigoApiUserService.getAll();
        return new ResponseEntity<>(userList, HttpStatus.OK);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<SiigoApiUserDTO> getSiigoApiUserById(@PathVariable Long id) {
        Optional<SiigoApiUserDTO> user = siigoApiUserService.getById(id);
        return user.map(dto -> new ResponseEntity<>(dto, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
    
    @GetMapping("/email/{email}")
    public ResponseEntity<SiigoApiUserDTO> getSiigoApiUserByEmail(@PathVariable String email) {
        Optional<SiigoApiUserDTO> user = siigoApiUserService.getByEmail(email);
        return user.map(dto -> new ResponseEntity<>(dto, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
    
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<SiigoApiUserDTO>> getSiigoApiUsersByUser(@PathVariable Long userId) {
        List<SiigoApiUserDTO> userList = siigoApiUserService.getByUserId(userId);
        return new ResponseEntity<>(userList, HttpStatus.OK);
    }
    
    @GetMapping("/company/{companyId}")
    public ResponseEntity<List<SiigoApiUserDTO>> getSiigoApiUsersByCompany(@PathVariable Long companyId) {
        List<SiigoApiUserDTO> userList = siigoApiUserService.getByCompanyId(companyId);
        return new ResponseEntity<>(userList, HttpStatus.OK);
    }
    
    @GetMapping("/app-type/{appType}")
    public ResponseEntity<List<SiigoApiUserDTO>> getSiigoApiUsersByAppType(@PathVariable String appType) {
        List<SiigoApiUserDTO> userList = siigoApiUserService.getByAppType(appType);
        return new ResponseEntity<>(userList, HttpStatus.OK);
    }
    
    @GetMapping("/user/{userId}/company/{companyId}")
    public ResponseEntity<List<SiigoApiUserDTO>> getSiigoApiUsersByUserAndCompany(
            @PathVariable Long userId, 
            @PathVariable Long companyId) {
        List<SiigoApiUserDTO> userList = siigoApiUserService.getByUserIdAndCompanyId(userId, companyId);
        return new ResponseEntity<>(userList, HttpStatus.OK);
    }
    
    @GetMapping("/search/email")
    public ResponseEntity<List<SiigoApiUserDTO>> searchSiigoApiUsersByEmail(
            @RequestParam String emailPattern) {
        List<SiigoApiUserDTO> userList = siigoApiUserService.searchByEmailPattern(emailPattern);
        return new ResponseEntity<>(userList, HttpStatus.OK);
    }
    
    @GetMapping("/date-range")
    public ResponseEntity<List<SiigoApiUserDTO>> getSiigoApiUsersByDateRange(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date endDate) {
        List<SiigoApiUserDTO> userList = siigoApiUserService.getByDateRange(startDate, endDate);
        return new ResponseEntity<>(userList, HttpStatus.OK);
    }
    
    @GetMapping("/user/{userId}/recent")
    public ResponseEntity<List<SiigoApiUserDTO>> getRecentSiigoApiUsersByUser(
            @PathVariable Long userId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date startDate) {
        List<SiigoApiUserDTO> userList = siigoApiUserService.getRecentByUser(userId, startDate);
        return new ResponseEntity<>(userList, HttpStatus.OK);
    }
    
    @GetMapping("/company/{companyId}/recent")
    public ResponseEntity<List<SiigoApiUserDTO>> getRecentSiigoApiUsersByCompany(
            @PathVariable Long companyId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date startDate) {
        List<SiigoApiUserDTO> userList = siigoApiUserService.getRecentByCompany(companyId, startDate);
        return new ResponseEntity<>(userList, HttpStatus.OK);
    }
    
    @GetMapping("/stats")
    public ResponseEntity<SiigoApiUserStatsDTO> getSiigoApiUserStats() {
        SiigoApiUserStatsDTO stats = siigoApiUserService.getStats();
        return new ResponseEntity<>(stats, HttpStatus.OK);
    }
    
    @GetMapping("/stats/company/{companyId}")
    public ResponseEntity<SiigoApiUserStatsDTO> getSiigoApiUserStatsByCompany(@PathVariable Long companyId) {
        SiigoApiUserStatsDTO stats = siigoApiUserService.getStatsByCompany(companyId);
        return new ResponseEntity<>(stats, HttpStatus.OK);
    }
    
    @GetMapping("/stats/app-type/{appType}")
    public ResponseEntity<SiigoApiUserStatsDTO> getSiigoApiUserStatsByAppType(@PathVariable String appType) {
        SiigoApiUserStatsDTO stats = siigoApiUserService.getStatsByAppType(appType);
        return new ResponseEntity<>(stats, HttpStatus.OK);
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<SiigoApiUserDTO> updateSiigoApiUser(
            @PathVariable Long id, 
            @RequestBody SiigoApiUserCreateDTO updateDTO) {
        try {
            Optional<SiigoApiUserDTO> updatedUser = siigoApiUserService.updateSiigoApiUser(id, updateDTO);
            return updatedUser.map(dto -> new ResponseEntity<>(dto, HttpStatus.OK))
                    .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSiigoApiUser(@PathVariable Long id) {
        boolean deleted = siigoApiUserService.deleteById(id);
        return deleted ? new ResponseEntity<>(HttpStatus.NO_CONTENT) 
                      : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
