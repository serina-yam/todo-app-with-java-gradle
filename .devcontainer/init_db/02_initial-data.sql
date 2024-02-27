WITH inserted_user AS (
    INSERT INTO users (login_type, username, password_hash)
    VALUES ('Form', 'testUser', '$2a$10$s1Q4RBVb7QcrqdsCqYVL2e4i8l9442zQW3HaHDHI3HC2V2qGZg8lW')
    RETURNING user_id
)
INSERT INTO item (user_id, title, state, time_limit)
SELECT user_id, 'マッシュルを観る', 0, CAST('2024-03-16' AS DATE)
FROM inserted_user
UNION ALL
SELECT user_id, '葬送のフリーレンを観る', 0, CAST('2024-03-17' AS DATE)
FROM inserted_user
UNION ALL
SELECT user_id, '薬屋のひとりごとを観る', 0, CAST('2024-03-18' AS DATE)
FROM inserted_user;
