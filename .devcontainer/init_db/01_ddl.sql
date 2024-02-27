-- Create Sequence
CREATE SEQUENCE users_id_seq START WITH 1 INCREMENT BY 1;
CREATE SEQUENCE item_id_seq START WITH 1 INCREMENT BY 1;

-- Create users table
CREATE TABLE users (
    user_id INTEGER PRIMARY KEY DEFAULT nextval('users_id_seq'),
    login_type VARCHAR(10) NOT NULL,
    username VARCHAR(100) UNIQUE NOT NULL,
    password_hash VARCHAR(100), -- フォーム認証に使用（ハッシュ化されたパスワード）
    oauth2_user_id VARCHAR(100), -- GitHubやGoogleのユーザーIDなど
    email VARCHAR(100), -- メールアドレスなどの追加情報(今は使っていないが今後使用予定のため追加)
    profile_image_url VARCHAR(255), -- プロフィール画像のURLなどの追加情報(今は使っていないが今後使用予定のため追加)
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Create item table
CREATE TABLE IF NOT EXISTS item (
    id INTEGER DEFAULT nextval('item_id_seq'),
    user_id INTEGER,
    title VARCHAR(200) NOT NULL,
    state INTEGER NOT NULL DEFAULT 0,
    time_limit DATE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (id, user_id),
    FOREIGN KEY (user_id) REFERENCES users(user_id) -- user_idを外部キーとして設定
);
