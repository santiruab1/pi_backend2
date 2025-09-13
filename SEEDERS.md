# Seeders de Datos

Este proyecto incluye un sistema de seeders que se ejecuta automáticamente al iniciar la aplicación para poblar la base de datos con datos iniciales.

## ¿Qué se crea automáticamente?

### 1. Permisos (35 permisos)
Los permisos se organizan por recursos y acciones:

- **Usuarios**: create, read, update, delete
- **Roles**: create, read, update, delete  
- **Permisos**: create, read, update, delete
- **Empresas**: create, read, update, delete
- **Documentos**: create, read, update, delete
- **Terceros**: create, read, update, delete
- **Centros de Costo**: create, read, update, delete
- **Siigo API**: read, sync
- **Dashboard/Reportes**: read, export

### 2. Roles (5 roles)
- **SUPER_ADMIN**: Acceso completo a todo el sistema
- **ADMIN**: Administración general (sin gestión de roles/permisos)
- **ACCOUNTANT**: Contador con acceso a documentos y reportes
- **AUDITOR**: Auditor con acceso de solo lectura y exportación
- **USER**: Usuario básico con acceso limitado

### 3. Usuarios de Prueba (6 usuarios)

| Email | Contraseña | Rol | Nombre |
|-------|------------|-----|--------|
| admin@empresa.com | 123456 | SUPER_ADMIN | Super Admin |
| admin2@empresa.com | 123456 | ADMIN | Admin Principal |
| contador@empresa.com | 123456 | ACCOUNTANT | María González |
| auditor@empresa.com | 123456 | AUDITOR | Carlos López |
| usuario@empresa.com | 123456 | USER | Ana Martínez |
| test@empresa.com | 123456 | USER | Test User |

## Cómo funciona

1. **Ejecución automática**: Los seeders se ejecutan automáticamente al iniciar la aplicación
2. **Verificación de duplicados**: Solo crea datos que no existen previamente
3. **Relaciones**: Asigna automáticamente permisos a roles y roles a usuarios
4. **Contraseñas encriptadas**: Todas las contraseñas se encriptan con BCrypt

## Estructura de archivos

```
src/main/java/com/example/pib2/
├── config/
│   ├── DataSeeder.java          # Seeder principal
│   └── SecurityConfig.java      # Configuración de seguridad
└── repository/
    ├── PermissionRepository.java # Repositorio de permisos
    └── RoleRepository.java       # Repositorio de roles
```

## Personalización

Para agregar más datos:

1. **Nuevos permisos**: Edita el array `permissionsData` en `DataSeeder.java`
2. **Nuevos roles**: Agrega llamadas a `createRole()` en el método `createRoles()`
3. **Nuevos usuarios**: Agrega llamadas a `createUser()` en el método `createUsers()`

## Desactivar seeders

Si no quieres que se ejecuten automáticamente:

1. Comenta o elimina la anotación `@Component` en `DataSeeder.java`
2. O agrega una propiedad en `application.properties`:
   ```properties
   app.seeders.enabled=false
   ```

## Logs

Los seeders generan logs informativos durante la ejecución:
- Creación de permisos
- Creación de roles con número de permisos asignados
- Creación de usuarios con roles asignados

## Seguridad

- Todas las contraseñas se encriptan con BCrypt
- Los usuarios se crean con email verificado
- Los roles y permisos están activos por defecto


