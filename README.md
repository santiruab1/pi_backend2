# Backend - Project Enterprise Management System (PEMS) 💫

## Description 📋
Sistema de gestión empresarial que permite administrar usuarios, transacciones, documentos, proveedores, centros de costos y almacenes con autenticación JWT y control de acceso basado en roles (RBAC).

## 🔐 Sistema de Autenticación y Seguridad

### Características de Seguridad
- **Autenticación JWT** con tokens de acceso y refresh
- **Control de acceso basado en roles (RBAC)** con permisos granulares
- **Hash de contraseñas** con bcrypt (Argon2id disponible)
- **Auditoría completa** de eventos de autenticación y cambios
- **Blacklist de tokens** para invalidación segura
- **MFA (Multi-Factor Authentication)** preparado
- **Rate limiting** y bloqueo de cuentas por intentos fallidos

### Variables de Entorno Requeridas

```bash
# Base de datos
DB_URL=jdbc:postgresql://localhost:5432/pib2
DB_USERNAME=tu_usuario
DB_PASSWORD=tu_contraseña

# JWT Configuration
JWT_SECRET=tu_clave_secreta_muy_larga_y_segura_minimo_256_bits
JWT_ACCESS_TTL=15m
JWT_REFRESH_TTL=30d

# Opcional: Para cifrado de datos sensibles
ENCRYPTION_KEY=clave_de_cifrado_32_caracteres

# Opcional: Para JWT con RS256 (más seguro)
# JWT_PRIVATE_KEY=path/to/private.pem
# JWT_PUBLIC_KEY=path/to/public.pem
```

### Roles y Permisos

#### Roles Disponibles
- **ADMIN**: Acceso completo al sistema
- **MANAGER**: Gestión de usuarios y operaciones
- **USER**: Usuario estándar con permisos básicos
- **AUDITOR**: Solo lectura y exportación de logs
- **FINANCE**: Permisos específicos para operaciones financieras

#### Permisos Granulares
- `users.*`: Gestión de usuarios
- `roles.*`: Gestión de roles
- `permissions.*`: Gestión de permisos
- `auth.*`: Operaciones de autenticación
- `audit.*`: Acceso a logs de auditoría
- `system.*`: Administración del sistema
- `companies.*`, `cost_centers.*`, `third_parties.*`, `documents.*`, `transactions.*`, `warehouses.*`: Permisos específicos por módulo

### Seguridad de Contraseñas

**¿Por qué hash en lugar de cifrado?**
- **Hash (bcrypt/Argon2id)**: Irreversible, resistente a ataques de fuerza bruta
- **Cifrado**: Reversible, vulnerable si se compromete la clave
- **Almacenamiento**: Solo el hash se guarda, nunca la contraseña original
- **Verificación**: Se compara el hash de la entrada con el almacenado

### Gestión de Base de Datos

El proyecto usa un enfoque híbrido:

1. **Hibernate DDL** para crear/actualizar el esquema de tablas automáticamente
2. **Flyway** solo para seeds de datos iniciales

```bash
# Ejecutar la aplicación (crea tablas automáticamente + ejecuta seeds)
mvn spring-boot:run

# O ejecutar solo seeds manualmente
mvn flyway:migrate

# Verificar estado de migraciones
mvn flyway:info
```

**Estructura de migraciones (solo seeds):**
```
src/main/resources/db/migration/
├── V1__Seed_initial_roles_and_permissions.sql  # Roles y permisos iniciales
├── V2__Seed_admin_user.sql                     # Usuario administrador inicial
└── V3__Seed_test_users.sql                     # Usuarios de prueba para testing
```

**Entidades JPA** crean automáticamente las tablas:
- `User`, `Role`, `Permission` - Tablas principales
- `RefreshToken`, `AuthEvent`, `AuditLog`, `JwtBlacklist` - Tablas de soporte
- Relaciones many-to-many: `user_roles`, `role_permissions`

### Cómo Ejecutar en Local

1. **Configurar variables de entorno:**
   ```bash
   # Crear archivo .env en la raíz del proyecto
   DB_URL=jdbc:postgresql://localhost:5432/pib2
   DB_USERNAME=postgres
   DB_PASSWORD=tu_contraseña
   JWT_SECRET=mi_clave_secreta_muy_larga_y_segura_para_jwt_tokens
   JWT_ACCESS_TTL=15m
   JWT_REFRESH_TTL=30d
   ```

2. **Crear base de datos:**
   ```sql
   CREATE DATABASE pib2;
   ```

3. **Ejecutar la aplicación:**
   ```bash
   mvn spring-boot:run
   ```
   - Hibernate creará las tablas automáticamente
   - Flyway ejecutará los seeds de datos iniciales

4. **Verificar que todo funciona:**
   ```sql
   -- Verificar tablas creadas
   \dt
   
   -- Verificar roles creados
   SELECT * FROM roles;
   
   -- Verificar permisos creados
   SELECT * FROM permissions;
   
   -- Verificar usuario administrador
   SELECT user_email, is_active FROM users WHERE user_email = 'admin@sistema.com';
   ```

5. **Verificar seeds de Flyway:**
   ```bash
   mvn flyway:info
   ```

### Usuarios de Prueba

El sistema incluye varios usuarios de prueba para facilitar el desarrollo y testing:

#### 👑 Administrador
- **Email**: `admin@sistema.com`
- **Contraseña**: `admin123`
- **Rol**: ADMIN (acceso completo)

#### 👨‍💼 Manager
- **Email**: `maria.gonzalez@empresa.com`
- **Contraseña**: `password123`
- **Rol**: MANAGER

#### 🔍 Auditor
- **Email**: `carlos.rodriguez@empresa.com`
- **Contraseña**: `password123`
- **Rol**: AUDITOR

#### 💰 Finance
- **Email**: `ana.martinez@empresa.com`
- **Contraseña**: `password123`
- **Rol**: FINANCE

#### 👤 Usuarios Estándar
- **Email**: `luis.fernandez@empresa.com` / `sofia.lopez@empresa.com`
- **Contraseña**: `password123`
- **Rol**: USER

#### ❌ Usuario Inactivo (para testing)
- **Email**: `pedro.garcia@empresa.com`
- **Contraseña**: `password123`
- **Estado**: Inactivo

**📋 Ver archivo `TEST_USERS.md` para detalles completos de usuarios y casos de prueba.**

**⚠️ IMPORTANTE**: Cambiar todas las contraseñas en producción

### Tablas de Autenticación

#### `users` - Usuarios del sistema
- Información personal y credenciales
- Campos de seguridad (intentos fallidos, bloqueo, MFA)
- Relación many-to-many con roles

#### `roles` - Roles del sistema
- Nombres y descripciones de roles
- Estado activo/inactivo
- Relación many-to-many con permisos

#### `permissions` - Permisos granulares
- Nombre, recurso y acción
- Control fino de acceso
- Relación many-to-many con roles

#### `refresh_tokens` - Tokens de renovación
- Hash del token (no el token plano)
- Información de dispositivo y ubicación
- Control de expiración y revocación

#### `auth_events` - Eventos de autenticación
- Log de todos los eventos de login/logout
- Metadatos de sesión (IP, User-Agent, etc.)
- Estados de éxito/fallo

#### `audit_log` - Log de auditoría
- Registro de cambios en entidades
- Diferencias antes/después
- Actor responsable del cambio

#### `jwt_blacklist` - Lista negra de tokens
- Tokens JWT revocados
- Control de expiración
- Razón de revocación

## Features ✨

### 1. Gestión de Usuarios (AppUser)
- Registro y autenticación de usuarios
- Perfiles de usuario con roles y permisos
- Gestión de datos personales

### 2. Gestión de Transacciones
- Registro de transacciones financieras
- Seguimiento de estados y fechas
- Vinculación con centros de costo

### 3. Gestión de Documentos
- Control de documentos comerciales
- Validación de numeración
- Asociación con terceros

### 4. Gestión de Proveedores
- Registro de proveedores
- Control de estado y contacto
- Historial de transacciones

### 5. Centros de Costo
- Organización por áreas
- Control presupuestario
- Reportes por centro de costo

### 6. Gestión de Almacenes
- Control de ubicaciones
- Gestión de capacidad
- Seguimiento de movimientos

## Modelo de Datos 🗂️

### Entidades Principales

#### AppUser
```java
public class AppUser {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private boolean active;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
```

#### Transaction
```java
public class Transaction {
    private Long id;
    private String type;
    private BigDecimal amount;
    private LocalDateTime date;
    private String status;
}
```

#### Document
```java
public class Document {
    private Long id;
    private String type;
    private String number;
    private LocalDate issueDate;
}
```

#### Supplier
```java
public class Supplier {
    private Long id;
    private String name;
    private String contactInfo;
    private String status;
}
```

#### CostCenter
```java
public class CostCenter {
    private Long id;
    private String name;
    private String code;
    private String description;
}
```

#### Warehouse
```java
public class Warehouse {
    private Long id;
    private String name;
    private String location;
    private Integer capacity;
}
```

## API Endpoints 🛠️

### Usuarios
- GET `/api/users` - Listar usuarios
- POST `/api/users` - Crear usuario
- GET `/api/users/{id}` - Obtener usuario
- PUT `/api/users/{id}` - Actualizar usuario
- DELETE `/api/users/{id}` - Eliminar usuario

### Transacciones
- GET `/api/transactions` - Listar transacciones
- POST `/api/transactions` - Crear transacción
- GET `/api/transactions/{id}` - Obtener transacción
- PUT `/api/transactions/{id}` - Actualizar transacción

### Documentos
- GET `/api/documents` - Listar documentos
- POST `/api/documents` - Crear documento
- GET `/api/documents/{id}` - Obtener documento
- PUT `/api/documents/{id}` - Actualizar documento

### Proveedores
- GET `/api/suppliers` - Listar proveedores
- POST `/api/suppliers` - Crear proveedor
- GET `/api/suppliers/{id}` - Obtener proveedor
- PUT `/api/suppliers/{id}` - Actualizar proveedor

### Centros de Costo
- GET `/api/cost-centers` - Listar centros
- POST `/api/cost-centers` - Crear centro
- GET `/api/cost-centers/{id}` - Obtener centro
- PUT `/api/cost-centers/{id}` - Actualizar centro

### Almacenes
- GET `/api/warehouses` - Listar almacenes
- POST `/api/warehouses` - Crear almacén
- GET `/api/warehouses/{id}` - Obtener almacén
- PUT `/api/warehouses/{id}` - Actualizar almacén

## Reglas de Negocio 📋

1. **Usuarios**
   - Email único en el sistema
   - Contraseña segura requerida
   - Activación/desactivación controlada

2. **Transacciones**
   - Asociación obligatoria a centro de costo
   - No modificables una vez completadas
   - Registro de fecha y usuario

3. **Documentos**
   - Número único por tipo
   - Validación de fechas
   - Control de estados

4. **Proveedores**
   - Información de contacto requerida
   - Estado activo/inactivo
   - Validación de documentos

5. **Centros de Costo**
   - Código único
   - Jerarquía organizacional
   - Control presupuestario

6. **Almacenes**
   - Control de capacidad máxima
   - Ubicación única
   - Registro de movimientos

## Estructura del Proyecto 📁

```
src/
├── main/
│   ├── java/
│   │   └── com/example/pib2/
│   │       ├── config/
│   │       │   ├── SecurityConfig.java
│   │       │   └── SwaggerConfig.java
│   │       ├── controller/
│   │       │   ├── UserController.java
│   │       │   ├── TransactionController.java
│   │       │   ├── DocumentController.java
│   │       │   ├── SupplierController.java
│   │       │   ├── CostCenterController.java
│   │       │   └── WarehouseController.java
│   │       ├── model/
│   │       │   ├── entity/
│   │       │   │   ├── BaseEntity.java
│   │       │   │   ├── User.java
│   │       │   │   ├── Role.java
│   │       │   │   ├── Permission.java
│   │       │   │   ├── RefreshToken.java
│   │       │   │   ├── AuthEvent.java
│   │       │   │   ├── AuditLog.java
│   │       │   │   ├── JwtBlacklist.java
│   │       │   │   ├── Transaction.java
│   │       │   │   ├── Document.java
│   │       │   │   ├── ThirdParty.java
│   │       │   │   ├── CostCenter.java
│   │       │   │   └── Warehouse.java
│   │       │   └── dto/
│   │       │       ├── UserDTO.java
│   │       │       ├── TransactionDTO.java
│   │       │       ├── DocumentDTO.java
│   │       │       ├── SupplierDTO.java
│   │       │       ├── CostCenterDTO.java
│   │       │       └── WarehouseDTO.java
│   │       ├── repository/
│   │       │   ├── UserRepository.java
│   │       │   ├── TransactionRepository.java
│   │       │   ├── DocumentRepository.java
│   │       │   ├── SupplierRepository.java
│   │       │   ├── CostCenterRepository.java
│   │       │   └── WarehouseRepository.java
│   │       └── service/
│   │           ├── UserService.java
│   │           ├── TransactionService.java
│   │           ├── DocumentService.java
│   │           ├── SupplierService.java
│   │           ├── CostCenterService.java
│   │           └── WarehouseService.java
│   └── resources/
│       ├── application.properties
│       ├── application-dev.properties
│       └── application-prod.properties
└── test/
    └── java/
        └── com/example/pib2/
            └── service/
                ├── UserServiceTest.java
                ├── TransactionServiceTest.java
                ├── DocumentServiceTest.java
                ├── SupplierServiceTest.java
                ├── CostCenterServiceTest.java
                └── WarehouseServiceTest.java
```
