package com.example.pib2.config;

import com.example.pib2.model.entity.Permission;
import com.example.pib2.model.entity.Role;
import com.example.pib2.model.entity.User;
import com.example.pib2.repository.PermissionRepository;
import com.example.pib2.repository.RoleRepository;
import com.example.pib2.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

@Component
public class DataSeeder implements CommandLineRunner {

    private static final Logger logger = LoggerFactory.getLogger(DataSeeder.class);

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PermissionRepository permissionRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        logger.info("Iniciando seeders de datos...");
        
        // Crear permisos primero
        createPermissions();
        
        // Crear roles con permisos
        createRoles();
        
        // Crear usuarios con roles
        createUsers();
        
        logger.info("Seeders completados exitosamente!");
    }

    private void createPermissions() {
        logger.info("Creando permisos...");
        
        String[][] permissionsData = {
            // Usuarios
            {"user.create", "Crear usuarios", "user", "create"},
            {"user.read", "Ver usuarios", "user", "read"},
            {"user.update", "Actualizar usuarios", "user", "update"},
            {"user.delete", "Eliminar usuarios", "user", "delete"},
            
            // Roles
            {"role.create", "Crear roles", "role", "create"},
            {"role.read", "Ver roles", "role", "read"},
            {"role.update", "Actualizar roles", "role", "update"},
            {"role.delete", "Eliminar roles", "role", "delete"},
            
            // Permisos
            {"permission.create", "Crear permisos", "permission", "create"},
            {"permission.read", "Ver permisos", "permission", "read"},
            {"permission.update", "Actualizar permisos", "permission", "update"},
            {"permission.delete", "Eliminar permisos", "permission", "delete"},
            
            // Empresas
            {"company.create", "Crear empresas", "company", "create"},
            {"company.read", "Ver empresas", "company", "read"},
            {"company.update", "Actualizar empresas", "company", "update"},
            {"company.delete", "Eliminar empresas", "company", "delete"},
            
            // Documentos
            {"document.create", "Crear documentos", "document", "create"},
            {"document.read", "Ver documentos", "document", "read"},
            {"document.update", "Actualizar documentos", "document", "update"},
            {"document.delete", "Eliminar documentos", "document", "delete"},
            
            // Terceros
            {"thirdparty.create", "Crear terceros", "thirdparty", "create"},
            {"thirdparty.read", "Ver terceros", "thirdparty", "read"},
            {"thirdparty.update", "Actualizar terceros", "thirdparty", "update"},
            {"thirdparty.delete", "Eliminar terceros", "thirdparty", "delete"},
            
            // Centros de costo
            {"costcenter.create", "Crear centros de costo", "costcenter", "create"},
            {"costcenter.read", "Ver centros de costo", "costcenter", "read"},
            {"costcenter.update", "Actualizar centros de costo", "costcenter", "update"},
            {"costcenter.delete", "Eliminar centros de costo", "costcenter", "delete"},
            
            // Siigo API
            {"siigo.read", "Ver datos de Siigo", "siigo", "read"},
            {"siigo.sync", "Sincronizar con Siigo", "siigo", "sync"},
            
            // Dashboard/Reportes
            {"dashboard.read", "Ver dashboard", "dashboard", "read"},
            {"report.read", "Ver reportes", "report", "read"},
            {"report.export", "Exportar reportes", "report", "export"}
        };

        for (String[] permissionData : permissionsData) {
            String name = permissionData[0];
            String description = permissionData[1];
            String resource = permissionData[2];
            String action = permissionData[3];

            if (!permissionRepository.existsByName(name)) {
                Permission permission = new Permission();
                permission.setName(name);
                permission.setDescription(description);
                permission.setResource(resource);
                permission.setAction(action);
                permission.setIsActive(true);
                
                permissionRepository.save(permission);
                logger.info("Permiso creado: {}", name);
            }
        }
    }

    private void createRoles() {
        logger.info("Creando roles...");
        
        // Rol Super Admin
        createRole("SUPER_ADMIN", "Super Administrador", Arrays.asList(
            "user.create", "user.read", "user.update", "user.delete",
            "role.create", "role.read", "role.update", "role.delete",
            "permission.create", "permission.read", "permission.update", "permission.delete",
            "company.create", "company.read", "company.update", "company.delete",
            "document.create", "document.read", "document.update", "document.delete",
            "thirdparty.create", "thirdparty.read", "thirdparty.update", "thirdparty.delete",
            "costcenter.create", "costcenter.read", "costcenter.update", "costcenter.delete",
            "siigo.read", "siigo.sync",
            "dashboard.read", "report.read", "report.export"
        ));

        // Rol Admin
        createRole("ADMIN", "Administrador", Arrays.asList(
            "user.create", "user.read", "user.update",
            "company.read", "company.update",
            "document.create", "document.read", "document.update", "document.delete",
            "thirdparty.create", "thirdparty.read", "thirdparty.update", "thirdparty.delete",
            "costcenter.create", "costcenter.read", "costcenter.update", "costcenter.delete",
            "siigo.read", "siigo.sync",
            "dashboard.read", "report.read", "report.export"
        ));

        // Rol Usuario
        createRole("USER", "Usuario", Arrays.asList(
            "user.read",
            "company.read",
            "document.read",
            "thirdparty.read",
            "costcenter.read",
            "siigo.read",
            "dashboard.read", "report.read"
        ));

        // Rol Contador
        createRole("ACCOUNTANT", "Contador", Arrays.asList(
            "user.read",
            "company.read",
            "document.create", "document.read", "document.update",
            "thirdparty.create", "thirdparty.read", "thirdparty.update",
            "costcenter.create", "costcenter.read", "costcenter.update",
            "siigo.read", "siigo.sync",
            "dashboard.read", "report.read", "report.export"
        ));

        // Rol Auditor
        createRole("AUDITOR", "Auditor", Arrays.asList(
            "user.read",
            "company.read",
            "document.read",
            "thirdparty.read",
            "costcenter.read",
            "siigo.read",
            "dashboard.read", "report.read", "report.export"
        ));
    }

    private void createRole(String roleName, String description, java.util.List<String> permissionNames) {
        if (!roleRepository.existsByName(roleName)) {
            Role role = new Role();
            role.setName(roleName);
            role.setDescription(description);
            role.setIsActive(true);

            Set<Permission> permissions = new HashSet<>();
            for (String permissionName : permissionNames) {
                permissionRepository.findByName(permissionName).ifPresent(permissions::add);
            }
            role.setPermissions(permissions);

            roleRepository.save(role);
            logger.info("Rol creado: {} con {} permisos", roleName, permissions.size());
        }
    }

    private void createUsers() {
        logger.info("Creando usuarios...");
        
        // Usuario Super Admin
        createUser("admin@empresa.com", "1234567890", "Super", "Admin", "3001234567", "SUPER_ADMIN");
        
        // Usuario Admin
        createUser("admin2@empresa.com", "1234567891", "Admin", "Principal", "3001234568", "ADMIN");
        
        // Usuario Contador
        createUser("contador@empresa.com", "1234567892", "María", "González", "3001234569", "ACCOUNTANT");
        
        // Usuario Auditor
        createUser("auditor@empresa.com", "1234567893", "Carlos", "López", "3001234570", "AUDITOR");
        
        // Usuario Regular
        createUser("usuario@empresa.com", "1234567894", "Ana", "Martínez", "3001234571", "USER");
        
        // Usuario de prueba
        createUser("test@empresa.com", "1234567895", "Test", "User", "3001234572", "USER");
    }

    private void createUser(String email, String identificationNumber, String firstName, String lastName, 
                          String phoneNumber, String roleName) {
        if (userRepository.findByUserEmail(email) == null) {
            User user = new User();
            user.setUserEmail(email);
            user.setIdentificationNumber(identificationNumber);
            user.setFirstName(firstName);
            user.setUserSurName(lastName);
            user.setUserPhoneNumber(phoneNumber);
            user.setUserPassword(passwordEncoder.encode("123456")); // Contraseña por defecto
            user.setIsActive(true);
            user.setIsEmailVerified(true);
            user.setPasswordAlgo("bcrypt");

            // Asignar rol
            roleRepository.findByName(roleName).ifPresent(role -> {
                user.setRoles(new HashSet<>(Arrays.asList(role)));
            });

            userRepository.save(user);
            logger.info("Usuario creado: {} con rol {}", email, roleName);
        }
    }
}
