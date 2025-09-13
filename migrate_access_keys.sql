-- Script para migrar access keys existentes a formato hasheado
-- IMPORTANTE: Este script debe ejecutarse ANTES de desplegar la nueva versión
-- que incluye el hasheo automático de access keys

-- Verificar si hay datos existentes en siigo_api_users
SELECT COUNT(*) as total_records FROM siigo_api_users;

-- Mostrar los access keys actuales (para verificación)
SELECT id, email, siigo_api_access_key, created_at 
FROM siigo_api_users 
ORDER BY created_at DESC;

-- NOTA: Los access keys existentes necesitarán ser hasheados manualmente
-- ya que BCrypt genera hashes únicos cada vez que se ejecuta.
-- 
-- Opciones para migrar:
-- 1. Si no hay datos importantes, eliminar todos los registros existentes
-- 2. Si hay datos importantes, crear un script Java que lea los access keys
--    actuales, los hashee y actualice la base de datos
-- 3. Contactar a los usuarios para que re-registren sus access keys

-- Opción 1: Eliminar todos los registros existentes (CUIDADO: Esto elimina todos los datos)
-- DELETE FROM siigo_api_users;

-- Opción 2: Crear una columna temporal para almacenar el access key original
-- ALTER TABLE siigo_api_users ADD COLUMN siigo_api_access_key_original TEXT;
-- UPDATE siigo_api_users SET siigo_api_access_key_original = siigo_api_access_key;

-- Después de migrar, eliminar la columna temporal:
-- ALTER TABLE siigo_api_users DROP COLUMN siigo_api_access_key_original;

-- Verificar el estado después de la migración
SELECT 
    id, 
    email, 
    CASE 
        WHEN siigo_api_access_key LIKE '$2a$%' THEN 'HASHED'
        ELSE 'PLAIN_TEXT'
    END as access_key_status,
    created_at
FROM siigo_api_users 
ORDER BY created_at DESC;
