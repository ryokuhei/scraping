

CREATE TABLE IF NOT EXISTS `rankings` (
    `id` bigint(20) NOT NULL AUTO_INCREMENT,
    `machine_number` bigint(20) NOT NULL,
    `date` DATE NOT NULL,
    `rank` bigint(20) NOT NULL,
    `point` bigint(20) DEFAULT NULL,
    `created_at` TIMESTAMP NOT NULL,
 PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE IF NOT EXISTS `machine_data_details` (
    `id` bigint(20) NOT NULL AUTO_INCREMENT,
    `bb` bigint(20) DEFAULT NULL,
    `rb` bigint(20) DEFAULT NULL,
    `art` bigint(20) DEFAULT NULL,
    `bonus_probability` bigint(20) DEFAULT NULL,
    `total_bonus` bigint(20) DEFAULT NULL,
    `total_game` bigint(20) DEFAULT NULL,
    `total_art` bigint(20) DEFAULT NULL,
    `end_game` bigint(20) DEFAULT NULL,
    `max_medal` bigint(20) DEFAULT NULL,
    `earned_medal` bigint(20) DEFAULT NULL,
    `result_image_path` bigint(20) DEFAULT NULL,
    `created_at` TIMESTAMP NOT NULL,
 PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE IF NOT EXISTS `machine_name_list` (
    `id` bigint(20) NOT NULL AUTO_INCREMENT,
    `model_number` bigint(20) NOT NULL,
    `machine_name` varchar(255) NOT NULL,
    `created_at` TIMESTAMP NOT NULL,
 PRIMARY KEY (`id`,`model_number`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE IF NOT EXISTS `machine_data` (
    `id` bigint(20) NOT NULL AUTO_INCREMENT,
    `machine_number` bigint(20) NOT NULL,
    `machine_name` varchar(255) NOT NULL,
    `date` DATE NOT NULL,
    `machine_data_details_id` bigint(20) DEFAULT NULL,
    `determination_data_id` bigint(20) DEFAULT NULL,
    `created_at` TIMESTAMP NOT NULL,
 PRIMARY KEY (`id`),
 FOREIGN KEY (`machine_data_details_id`)
   REFERENCES machine_data_details (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE IF NOT EXISTS `madoka_magika` (
    `id` bigint(20) NOT NULL AUTO_INCREMENT,
    `estimated_setting` bigint(20) DEFAULT NULL,
    `probability_of_direct_hit_art` bigint(20) DEFAULT NULL,
    `created_at` TIMESTAMP NOT NULL,
 PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE IF NOT EXISTS `direct_hits` (
    `id` bigint(20) NOT NULL AUTO_INCREMENT,
    `determination_data_id` bigint(20) NOT NULL ,
    `game` bigint(20) NOT NULL,
    `created_at` TIMESTAMP NOT NULL,
 PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
