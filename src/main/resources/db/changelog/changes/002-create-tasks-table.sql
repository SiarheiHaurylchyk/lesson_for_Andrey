CREATE TABLE IF NOT EXISTS tasks (
    id BIGSERIAL PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    description TEXT,
    completed BOOLEAN DEFAULT FALSE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    todo_item_id BIGINT NOT NULL,
    CONSTRAINT fk_todo_item FOREIGN KEY (todo_item_id) REFERENCES todo_items(id) ON DELETE CASCADE
    );

