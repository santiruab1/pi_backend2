# Usuarios de Prueba - Sistema de Autenticación

## Credenciales de Usuarios

Todos los usuarios de prueba usan la contraseña: **`password123`**

### 👑 Administrador
- **Email**: `admin@sistema.com`
- **Contraseña**: `admin123`
- **Rol**: ADMIN
- **Estado**: Activo, Email verificado
- **Permisos**: Acceso completo al sistema

### 👨‍💼 Manager
- **Email**: `maria.gonzalez@empresa.com`
- **Contraseña**: `password123`
- **Rol**: MANAGER
- **Estado**: Activo, Email verificado
- **Permisos**: Gestión de usuarios y operaciones

### 🔍 Auditor
- **Email**: `carlos.rodriguez@empresa.com`
- **Contraseña**: `password123`
- **Rol**: AUDITOR
- **Estado**: Activo, Email verificado
- **Permisos**: Solo lectura y exportación de logs

### 💰 Finance
- **Email**: `ana.martinez@empresa.com`
- **Contraseña**: `password123`
- **Rol**: FINANCE
- **Estado**: Activo, Email verificado
- **Permisos**: Operaciones financieras específicas

### 👤 Usuario Estándar 1
- **Email**: `luis.fernandez@empresa.com`
- **Contraseña**: `password123`
- **Rol**: USER
- **Estado**: Activo, Email verificado
- **Permisos**: Permisos básicos de lectura

### 👤 Usuario Estándar 2
- **Email**: `sofia.lopez@empresa.com`
- **Contraseña**: `password123`
- **Rol**: USER
- **Estado**: Activo, Email verificado
- **Permisos**: Permisos básicos de lectura

### ❌ Usuario Inactivo
- **Email**: `pedro.garcia@empresa.com`
- **Contraseña**: `password123`
- **Rol**: Sin rol asignado
- **Estado**: Inactivo, Email no verificado
- **Permisos**: Ninguno (para testing de usuarios bloqueados)

## Casos de Prueba

### ✅ Casos Exitosos
1. **Login con admin**: `admin@sistema.com` / `admin123`
2. **Login con manager**: `maria.gonzalez@empresa.com` / `password123`
3. **Login con auditor**: `carlos.rodriguez@empresa.com` / `password123`
4. **Login con finance**: `ana.martinez@empresa.com` / `password123`
5. **Login con usuario**: `luis.fernandez@empresa.com` / `password123`

### ❌ Casos de Error
1. **Usuario inactivo**: `pedro.garcia@empresa.com` / `password123`
2. **Credenciales incorrectas**: Cualquier email con contraseña incorrecta
3. **Email inexistente**: `usuario@inexistente.com` / `password123`

## Testing de Roles y Permisos

### ADMIN
- ✅ Acceso a todas las funcionalidades
- ✅ Gestión de usuarios, roles y permisos
- ✅ Acceso a logs de auditoría
- ✅ Configuración del sistema

### MANAGER
- ✅ Gestión de usuarios (crear, modificar)
- ✅ Operaciones de negocio
- ✅ Acceso a reportes
- ❌ No puede gestionar roles del sistema

### AUDITOR
- ✅ Solo lectura de datos
- ✅ Exportación de logs
- ✅ Acceso a reportes de auditoría
- ❌ No puede modificar datos

### FINANCE
- ✅ Operaciones financieras
- ✅ Gestión de transacciones
- ✅ Aprobación de documentos
- ❌ No puede gestionar usuarios

### USER
- ✅ Lectura de datos básicos
- ✅ Operaciones personales
- ❌ No puede acceder a funciones administrativas

## Notas de Seguridad

⚠️ **IMPORTANTE**: 
- Estas credenciales son solo para desarrollo y testing
- Cambiar todas las contraseñas en producción
- El usuario administrador debe usar una contraseña más segura
- Considerar implementar MFA para usuarios administrativos

## Comandos SQL para Verificar

```sql
-- Ver todos los usuarios
SELECT user_email, is_active, is_email_verified FROM users;

-- Ver roles asignados
SELECT u.user_email, r.name as role_name 
FROM users u 
JOIN user_roles ur ON u.id = ur.user_id 
JOIN roles r ON ur.role_id = r.id 
ORDER BY u.user_email;

-- Ver permisos por rol
SELECT r.name as role_name, p.name as permission_name
FROM roles r
JOIN role_permissions rp ON r.id = rp.role_id
JOIN permissions p ON rp.permission_id = p.id
ORDER BY r.name, p.name;
```


