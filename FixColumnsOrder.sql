ALTER TABLE `spring_jwt_db`.`security_tokens`
CHANGE COLUMN `user_id` `user_id` BIGINT NOT NULL AFTER `id`,
CHANGE COLUMN `token` `token` VARCHAR(255) NULL DEFAULT NULL AFTER `user_id`,
CHANGE COLUMN `valid_until` `valid_until` TIMESTAMP NULL DEFAULT NULL AFTER `token`;

ALTER TABLE `spring_jwt_db`.`books`
CHANGE COLUMN `user_id` `user_id` BIGINT NOT NULL AFTER `id`,
CHANGE COLUMN `created_at` `created_at` TIMESTAMP NULL DEFAULT NULL AFTER `price`,
CHANGE COLUMN `updated_at` `updated_at` TIMESTAMP NULL DEFAULT NULL AFTER `created_at`;

ALTER TABLE `spring_jwt_db`.`users`
CHANGE COLUMN `username` `username` VARCHAR(255) NULL DEFAULT NULL AFTER `id`,
CHANGE COLUMN `password_hash` `password_hash` VARCHAR(255) NULL DEFAULT NULL AFTER `email`,
CHANGE COLUMN `credentials_not_expired` `credentials_not_expired` BIT(1) NULL DEFAULT NULL AFTER `non_locked`,
CHANGE COLUMN `enabled` `enabled` BIT(1) NULL DEFAULT NULL AFTER `credentials_not_expired`,
CHANGE COLUMN `created_at` `created_at` TIMESTAMP NULL DEFAULT NULL AFTER `enabled`,
CHANGE COLUMN `updated_at` `updated_at` TIMESTAMP NULL DEFAULT NULL AFTER `created_at`;
