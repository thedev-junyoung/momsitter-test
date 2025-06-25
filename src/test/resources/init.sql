-- 1. 스키마 생성 및 사용
CREATE SCHEMA IF NOT EXISTS momsitter;
USE momsitter;

-- 3. 사용자(User) 테이블
CREATE TABLE momsitter.users (
                                 id BIGINT AUTO_INCREMENT PRIMARY KEY,
                                 username VARCHAR(255) NOT NULL,
                                 password VARCHAR(255) NOT NULL,
                                 name VARCHAR(255) NOT NULL,
                                 birth_date DATE NOT NULL,
                                 gender ENUM('FEMALE', 'MALE') NOT NULL,
                                 email VARCHAR(255) NOT NULL,
                                 created_at DATETIME(6) NULL,
                                 updated_at DATETIME(6) NULL,
                                 CONSTRAINT UK_users_username UNIQUE (username),
                                 CONSTRAINT UK_users_email UNIQUE (email)
);

-- 4. 부모 프로필 테이블
CREATE TABLE momsitter.parent_profiles (
                                           id BIGINT AUTO_INCREMENT PRIMARY KEY,
                                           user_id BIGINT NOT NULL,
                                           created_at DATETIME(6) NULL,
                                           updated_at DATETIME(6) NULL,
                                           CONSTRAINT UK_parent_profiles_user UNIQUE (user_id),
                                           CONSTRAINT FK_parent_profiles_user FOREIGN KEY (user_id) REFERENCES momsitter.users(id)
);

-- 5. 시터 프로필 테이블
CREATE TABLE momsitter.sitter_profiles (
                                           id BIGINT AUTO_INCREMENT PRIMARY KEY,
                                           user_id BIGINT NOT NULL,
                                           min_care_age INT NOT NULL,
                                           max_care_age INT NOT NULL,
                                           introduction TEXT NOT NULL,
                                           created_at DATETIME(6) NULL,
                                           updated_at DATETIME(6) NULL,
                                           CONSTRAINT UK_sitter_profiles_user UNIQUE (user_id),
                                           CONSTRAINT FK_sitter_profiles_user FOREIGN KEY (user_id) REFERENCES momsitter.users(id)
);

-- 6. 자녀(children) 테이블
CREATE TABLE momsitter.children (
                                    id BIGINT AUTO_INCREMENT PRIMARY KEY,
                                    parent_profile_id BIGINT NOT NULL,
                                    name VARCHAR(255) NOT NULL,
                                    birth_date DATE NOT NULL,
                                    gender ENUM('FEMALE', 'MALE') NOT NULL,
                                    created_at DATETIME(6) NULL,
                                    updated_at DATETIME(6) NULL,
                                    CONSTRAINT FK_children_parent_profile FOREIGN KEY (parent_profile_id) REFERENCES momsitter.parent_profiles(id)
);

-- 7. 돌봄 요청(care_requests) 테이블
CREATE TABLE momsitter.care_requests (
                                         id BIGINT AUTO_INCREMENT PRIMARY KEY,
                                         parent_profile_id BIGINT NOT NULL,
                                         content TEXT NOT NULL,
                                         status ENUM('ACTIVE', 'CANCELLED', 'COMPLETED', 'MATCHED') NOT NULL,
                                         created_at DATETIME(6) NULL,
                                         updated_at DATETIME(6) NULL,
                                         CONSTRAINT FK_care_requests_parent_profile FOREIGN KEY (parent_profile_id) REFERENCES momsitter.parent_profiles(id)
);

-- 8. 사용자-역할 매핑 테이블
CREATE TABLE momsitter.user_roles (
                                      user_id BIGINT NOT NULL,
                                      role VARCHAR(50) NOT NULL,
                                      PRIMARY KEY (user_id, role),
                                      FOREIGN KEY (user_id) REFERENCES momsitter.users(id)
);
