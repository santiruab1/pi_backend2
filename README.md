# Backend - Project Enterprise Management System (PEMS) 💫

## Description 📋
Sistema de gestión empresarial que permite administrar usuarios, transacciones, documentos, proveedores, centros de costos y almacenes.

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
│   │       │   │   ├── AppUser.java
│   │       │   │   ├── Transaction.java
│   │       │   │   ├── Document.java
│   │       │   │   ├── Supplier.java
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
