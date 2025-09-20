CREATE TABLE IF NOT EXISTS users (
                                     id BIGINT AUTO_INCREMENT PRIMARY KEY,
                                     username VARCHAR(255) NOT NULL UNIQUE,
                                     password VARCHAR(255) NOT NULL,
                                     enabled BOOLEAN DEFAULT TRUE,
                                     email VARCHAR(255),
                                     full_name VARCHAR(255), -- ✅ changed from fullName
                                     image VARCHAR(255),
                                     role ENUM('ROLE_ADMIN', 'ROLE_USER') NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
