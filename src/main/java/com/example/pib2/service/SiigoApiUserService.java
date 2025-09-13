package com.example.pib2.service;

import com.example.pib2.model.dto.SiigoApiUserCreateDTO;
import com.example.pib2.model.dto.SiigoApiUserDTO;
import com.example.pib2.model.entity.SiigoApiUser;
import com.example.pib2.repository.SiigoApiUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class SiigoApiUserService {
    
    @Autowired
    private SiigoApiUserRepository siigoApiUserRepository;
    
    // Crear nuevo usuario de API de Siigo
    public SiigoApiUserDTO createSiigoApiUser(SiigoApiUserCreateDTO createDTO) {
        // Validar que no exista el email para el usuario
        if (createDTO.getUserId() != null && 
            siigoApiUserRepository.existsByEmailAndUserId(createDTO.getEmail(), createDTO.getUserId())) {
            throw new RuntimeException("Ya existe un usuario de Siigo con este email para el usuario especificado");
        }
        
        // Validar que no exista el email para la empresa
        if (createDTO.getCompanyId() != null && 
            siigoApiUserRepository.existsByEmailAndCompanyId(createDTO.getEmail(), createDTO.getCompanyId())) {
            throw new RuntimeException("Ya existe un usuario de Siigo con este email para la empresa especificada");
        }
        
        SiigoApiUser siigoApiUser = new SiigoApiUser();
        siigoApiUser.setEmail(createDTO.getEmail());
        siigoApiUser.setAccessKey(createDTO.getAccessKey());
        siigoApiUser.setAppType(createDTO.getAppType());
        siigoApiUser.setUserId(createDTO.getUserId());
        siigoApiUser.setCompanyId(createDTO.getCompanyId());
        
        SiigoApiUser savedUser = siigoApiUserRepository.save(siigoApiUser);
        return convertToDTO(savedUser);
    }
    
    // Obtener por ID
    public Optional<SiigoApiUserDTO> getById(Long id) {
        return siigoApiUserRepository.findById(id)
                .map(this::convertToDTO);
    }
    
    // Obtener todos los usuarios
    public List<SiigoApiUserDTO> getAll() {
        return siigoApiUserRepository.findAll()
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }
    
    // Obtener por email
    public Optional<SiigoApiUserDTO> getByEmail(String email) {
        return siigoApiUserRepository.findByEmail(email)
                .map(this::convertToDTO);
    }
    
    // Obtener por usuario
    public List<SiigoApiUserDTO> getByUserId(Long userId) {
        return siigoApiUserRepository.findByUserId(userId)
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }
    
    // Obtener por empresa
    public List<SiigoApiUserDTO> getByCompanyId(Long companyId) {
        return siigoApiUserRepository.findByCompanyId(companyId)
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }
    
    // Obtener por tipo de aplicación
    public List<SiigoApiUserDTO> getByAppType(String appType) {
        return siigoApiUserRepository.findByAppType(appType)
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }
    
    // Obtener por usuario y empresa
    public List<SiigoApiUserDTO> getByUserIdAndCompanyId(Long userId, Long companyId) {
        return siigoApiUserRepository.findByUserIdAndCompanyId(userId, companyId)
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }
    
    // Buscar por email que contenga un patrón
    public List<SiigoApiUserDTO> searchByEmailPattern(String emailPattern) {
        return siigoApiUserRepository.findByEmailContaining(emailPattern)
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }
    
    // Obtener por rango de fechas
    public List<SiigoApiUserDTO> getByDateRange(Date startDate, Date endDate) {
        return siigoApiUserRepository.findByCreatedAtBetween(startDate, endDate)
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }
    
    // Obtener usuarios recientes por usuario
    public List<SiigoApiUserDTO> getRecentByUser(Long userId, Date startDate) {
        return siigoApiUserRepository.findRecentByUser(userId, startDate)
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }
    
    // Obtener usuarios recientes por empresa
    public List<SiigoApiUserDTO> getRecentByCompany(Long companyId, Date startDate) {
        return siigoApiUserRepository.findRecentByCompany(companyId, startDate)
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }
    
    // Actualizar usuario
    public Optional<SiigoApiUserDTO> updateSiigoApiUser(Long id, SiigoApiUserCreateDTO updateDTO) {
        return siigoApiUserRepository.findById(id)
                .map(existingUser -> {
                    // Validar email único si cambió
                    if (!existingUser.getEmail().equals(updateDTO.getEmail())) {
                        if (updateDTO.getUserId() != null && 
                            siigoApiUserRepository.existsByEmailAndUserId(updateDTO.getEmail(), updateDTO.getUserId())) {
                            throw new RuntimeException("Ya existe un usuario de Siigo con este email para el usuario especificado");
                        }
                        if (updateDTO.getCompanyId() != null && 
                            siigoApiUserRepository.existsByEmailAndCompanyId(updateDTO.getEmail(), updateDTO.getCompanyId())) {
                            throw new RuntimeException("Ya existe un usuario de Siigo con este email para la empresa especificada");
                        }
                    }
                    
                    existingUser.setEmail(updateDTO.getEmail());
                    existingUser.setAccessKey(updateDTO.getAccessKey());
                    existingUser.setAppType(updateDTO.getAppType());
                    existingUser.setUserId(updateDTO.getUserId());
                    existingUser.setCompanyId(updateDTO.getCompanyId());
                    
                    SiigoApiUser updatedUser = siigoApiUserRepository.save(existingUser);
                    return convertToDTO(updatedUser);
                });
    }
    
    // Eliminar por ID
    public boolean deleteById(Long id) {
        if (siigoApiUserRepository.existsById(id)) {
            siigoApiUserRepository.deleteById(id);
            return true;
        }
        return false;
    }
    
    // Obtener estadísticas
    public SiigoApiUserStatsDTO getStats() {
        SiigoApiUserStatsDTO stats = new SiigoApiUserStatsDTO();
        stats.setTotalUsers(siigoApiUserRepository.count());
        return stats;
    }
    
    // Obtener estadísticas por empresa
    public SiigoApiUserStatsDTO getStatsByCompany(Long companyId) {
        SiigoApiUserStatsDTO stats = new SiigoApiUserStatsDTO();
        stats.setTotalUsers(siigoApiUserRepository.countByCompanyId(companyId));
        return stats;
    }
    
    // Obtener estadísticas por tipo de aplicación
    public SiigoApiUserStatsDTO getStatsByAppType(String appType) {
        SiigoApiUserStatsDTO stats = new SiigoApiUserStatsDTO();
        stats.setTotalUsers(siigoApiUserRepository.countByAppType(appType));
        return stats;
    }
    
    // Convertir entidad a DTO
    private SiigoApiUserDTO convertToDTO(SiigoApiUser siigoApiUser) {
        SiigoApiUserDTO dto = new SiigoApiUserDTO();
        dto.setId(siigoApiUser.getId());
        dto.setEmail(siigoApiUser.getEmail());
        dto.setAccessKey(siigoApiUser.getAccessKey());
        dto.setAppType(siigoApiUser.getAppType());
        // Convert LocalDateTime to Date for DTO setters
        if (siigoApiUser.getCreatedAt() != null) {
            dto.setCreatedAt(java.sql.Timestamp.valueOf(siigoApiUser.getCreatedAt()));
        }
        if (siigoApiUser.getUpdatedAt() != null) {
            dto.setUpdatedAt(java.sql.Timestamp.valueOf(siigoApiUser.getUpdatedAt()));
        }
        dto.setUserId(siigoApiUser.getUserId());
        dto.setCompanyId(siigoApiUser.getCompanyId());
        return dto;
    }
    
    // Clase interna para estadísticas
    public static class SiigoApiUserStatsDTO {
        private Long totalUsers;
        
        public Long getTotalUsers() { return totalUsers; }
        public void setTotalUsers(Long totalUsers) { this.totalUsers = totalUsers; }
    }
}
