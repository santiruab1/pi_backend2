# Sistema de Encriptación para Siigo API Access Keys

## Descripción

Se ha implementado un sistema de **encriptación simétrica AES** para los `siigo_api_access_key` que permite:
- ✅ **Encriptar** el access key al guardarlo en la base de datos
- ✅ **Desencriptar** el access key cuando se necesite usar para comunicarse con Siigo
- ✅ **Migración automática** de access keys existentes (texto plano o BCrypt)

## Características Técnicas

### Algoritmo de Encriptación
- **AES-256**: Algoritmo de encriptación simétrica robusto
- **Modo ECB**: Simple y eficiente para este caso de uso
- **Padding PKCS5**: Estándar de padding para bloques
- **Base64**: Codificación para almacenamiento seguro

### Seguridad
- **Clave configurable**: Se puede configurar en `application.properties`
- **Generación automática**: Si no hay clave configurada, se genera una nueva
- **Migración segura**: Convierte automáticamente datos existentes

## Uso del Sistema

### 1. Crear Usuario (Access Key se encripta automáticamente)

```bash
POST /api/siigo-api-users
Content-Type: application/json

{
  "email": "usuario@ejemplo.com",
  "accessKey": "mi_access_key_original_de_siigo",
  "appType": "web",
  "userId": 1,
  "companyId": 1
}
```

**Respuesta:**
```json
{
  "id": 1,
  "email": "usuario@ejemplo.com",
  "accessKey": "U2FsdGVkX1+vupppZksvRf5pq5g5XjFRlipRkwB0K1Y=",
  "appType": "web",
  "userId": 1,
  "companyId": 1,
  "createdAt": "2024-01-01T00:00:00.000+00:00",
  "updatedAt": "2024-01-01T00:00:00.000+00:00"
}
```

### 2. Obtener Access Key Desencriptado para Usar con Siigo

```bash
# Por email
GET /api/siigo-api-users/email/usuario@ejemplo.com/access-key

# Por ID
GET /api/siigo-api-users/1/access-key
```

**Respuesta:**
```
mi_access_key_original_de_siigo
```

### 3. Verificar Access Key

```bash
POST /api/siigo-api-users/verify-access-key
Content-Type: application/x-www-form-urlencoded

email=usuario@ejemplo.com&accessKey=mi_access_key_original_de_siigo
```

**Respuesta:**
```json
true
```

### 4. Autenticar Usuario

```bash
POST /api/siigo-api-users/authenticate
Content-Type: application/x-www-form-urlencoded

email=usuario@ejemplo.com&accessKey=mi_access_key_original_de_siigo
```

## Comunicación con Siigo API

### Ejemplo de Uso en Código

```java
@Autowired
private SiigoApiUserService siigoApiUserService;

public void comunicarConSiigo(String userEmail) {
    // Obtener access key desencriptado
    Optional<String> accessKeyOpt = siigoApiUserService.getDecryptedAccessKey(userEmail);
    
    if (accessKeyOpt.isPresent()) {
        String accessKey = accessKeyOpt.get();
        
        // Usar el access key para comunicarse con Siigo
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + accessKey);
        
        // Realizar petición a Siigo...
    }
}
```

### Endpoints de Ejemplo para Siigo

```bash
# Obtener información de empresa
GET /api/siigo/company/usuario@ejemplo.com

# Obtener productos
GET /api/siigo/products/usuario@ejemplo.com

# Obtener clientes
GET /api/siigo/customers/usuario@ejemplo.com

# Crear producto
POST /api/siigo/products/usuario@ejemplo.com
Content-Type: application/json

{
  "name": "Producto Ejemplo",
  "price": 100.00
}
```

## Configuración

### Clave de Encriptación

En `application.properties`:

```properties
# Clave de encriptación (cambiar en producción)
app.encryption.key=tu_clave_secreta_aqui
```

### Generar Nueva Clave

Si no configuras una clave, la aplicación generará una automáticamente al iniciar:

```
NUEVA CLAVE DE ENCRIPTACIÓN GENERADA: U2FsdGVkX1+vupppZksvRf5pq5g5XjFRlipRkwB0K1Y=
IMPORTANTE: Guarda esta clave en application.properties como app.encryption.key
```

## Migración Automática

### ¿Qué Migra?
- ✅ Access keys en **texto plano** → Encriptación AES
- ✅ Access keys **hasheados con BCrypt** → Encriptación AES
- ✅ Access keys **ya encriptados** → Se saltan

### Logs de Migración

```
INFO  - Iniciando migración de access keys a encriptación AES...
INFO  - Migrando access key para usuario: usuario@ejemplo.com (ID: 1)
INFO  - Access key migrado exitosamente para usuario: usuario@ejemplo.com
INFO  - Migración completada. 5 access keys migrados, 0 ya estaban encriptados.
```

## Monitoreo y Verificación

### Verificar Estado de Encriptación

```sql
SELECT 
    id, 
    email, 
    CASE 
        WHEN siigo_api_access_key LIKE 'U2FsdGVkX1%' THEN 'ENCRYPTED'
        WHEN siigo_api_access_key LIKE '$2a$%' THEN 'BCRYPT_HASH'
        ELSE 'PLAIN_TEXT'
    END as access_key_status,
    created_at
FROM siigo_api_users 
ORDER BY created_at DESC;
```

### Testing Completo

```bash
# 1. Crear usuario
curl -X POST http://localhost:8080/api/siigo-api-users \
  -H "Content-Type: application/json" \
  -d '{
    "email": "test@ejemplo.com",
    "accessKey": "sk_test_123456789",
    "appType": "web",
    "userId": 1,
    "companyId": 1
  }'

# 2. Obtener access key desencriptado
curl http://localhost:8080/api/siigo-api-users/email/test@ejemplo.com/access-key

# 3. Verificar access key
curl -X POST http://localhost:8080/api/siigo-api-users/verify-access-key \
  -d "email=test@ejemplo.com&accessKey=sk_test_123456789"

# 4. Usar con Siigo (ejemplo)
curl http://localhost:8080/api/siigo/company/test@ejemplo.com
```

## Ventajas del Sistema

### ✅ Seguridad
- Access keys no se almacenan en texto plano
- Encriptación robusta con AES-256
- Clave de encriptación configurable

### ✅ Funcionalidad
- Desencriptación automática cuando se necesita
- Compatible con APIs externas como Siigo
- Migración automática de datos existentes

### ✅ Mantenibilidad
- Código limpio y bien documentado
- Logs detallados para monitoreo
- Configuración flexible

## Consideraciones de Producción

### 🔒 Seguridad
1. **Cambiar clave por defecto**: Usar una clave segura en producción
2. **Rotar claves**: Cambiar la clave periódicamente
3. **Backup seguro**: Guardar la clave en lugar seguro

### 📊 Monitoreo
1. **Logs de migración**: Verificar que la migración fue exitosa
2. **Errores de desencriptación**: Monitorear fallos de desencriptación
3. **Uso de access keys**: Rastrear cuándo se usan

### 🚀 Performance
1. **Caching**: Considerar cachear access keys desencriptados
2. **Pool de conexiones**: Optimizar RestTemplate para Siigo
3. **Timeouts**: Configurar timeouts apropiados

## Troubleshooting

### Error: "Error desencriptando texto"
- **Causa**: Clave de encriptación incorrecta
- **Solución**: Verificar `app.encryption.key` en `application.properties`

### Error: "Usuario no encontrado"
- **Causa**: Email no existe en la base de datos
- **Solución**: Verificar que el usuario esté registrado

### Error: "Error comunicándose con Siigo"
- **Causa**: Access key inválido o problemas de red
- **Solución**: Verificar access key y conectividad

## Conclusión

Este sistema proporciona una solución completa y segura para manejar access keys de Siigo, permitiendo:
- Almacenamiento seguro con encriptación
- Desencriptación automática para uso con APIs
- Migración transparente de datos existentes
- Integración fácil con servicios externos

El sistema está listo para producción y puede ser extendido fácilmente para otros proveedores de API.
