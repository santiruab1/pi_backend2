-- Script para agregar columnas de timestamp a todas las tablas
-- Ejecutar este script en PostgreSQL

-- Actualizar tabla users
ALTER TABLE users ADD COLUMN IF NOT EXISTS created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP;
ALTER TABLE users ADD COLUMN IF NOT EXISTS updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP;

-- Actualizar tabla roles
ALTER TABLE roles ADD COLUMN IF NOT EXISTS created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP;
ALTER TABLE roles ADD COLUMN IF NOT EXISTS updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP;

-- Actualizar tabla permissions
ALTER TABLE permissions ADD COLUMN IF NOT EXISTS created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP;
ALTER TABLE permissions ADD COLUMN IF NOT EXISTS updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP;

-- Actualizar tabla companies
ALTER TABLE companies ADD COLUMN IF NOT EXISTS created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP;
ALTER TABLE companies ADD COLUMN IF NOT EXISTS updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP;

-- Actualizar tabla documents
ALTER TABLE documents ADD COLUMN IF NOT EXISTS created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP;
ALTER TABLE documents ADD COLUMN IF NOT EXISTS updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP;

-- Actualizar tabla third_parties
ALTER TABLE third_parties ADD COLUMN IF NOT EXISTS created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP;
ALTER TABLE third_parties ADD COLUMN IF NOT EXISTS updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP;

-- Actualizar tabla cost_centers
ALTER TABLE cost_centers ADD COLUMN IF NOT EXISTS created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP;
ALTER TABLE cost_centers ADD COLUMN IF NOT EXISTS updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP;

-- Actualizar tabla warehouses
ALTER TABLE warehouses ADD COLUMN IF NOT EXISTS created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP;
ALTER TABLE warehouses ADD COLUMN IF NOT EXISTS updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP;

-- Actualizar tabla transactions
ALTER TABLE transactions ADD COLUMN IF NOT EXISTS created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP;
ALTER TABLE transactions ADD COLUMN IF NOT EXISTS updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP;

-- Actualizar tabla siigo_api_users (remover timestamps manuales si existen)
ALTER TABLE siigo_api_users DROP COLUMN IF EXISTS created_at;
ALTER TABLE siigo_api_users DROP COLUMN IF EXISTS updated_at;
ALTER TABLE siigo_api_users ADD COLUMN IF NOT EXISTS created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP;
ALTER TABLE siigo_api_users ADD COLUMN IF NOT EXISTS updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP;

-- Actualizar valores NULL en columnas NOT NULL
UPDATE users SET is_email_verified = false WHERE is_email_verified IS NULL;
UPDATE users SET mfa_enabled = false WHERE mfa_enabled IS NULL;
UPDATE users SET password_algo = 'bcrypt' WHERE password_algo IS NULL;

-- Verificar que las columnas se crearon correctamente
SELECT table_name, column_name, data_type, is_nullable 
FROM information_schema.columns 
WHERE table_name IN ('users', 'roles', 'permissions', 'companies', 'documents', 'third_parties', 'cost_centers', 'warehouses', 'transactions', 'siigo_api_users')
AND column_name IN ('created_at', 'updated_at')
ORDER BY table_name, column_name;
