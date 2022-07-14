ALTER TABLE download_permission
ADD CONSTRAINT unique_permission UNIQUE (file_id, user_id);