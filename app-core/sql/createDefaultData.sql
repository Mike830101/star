INSERT INTO public.sys_user (uid, acc, password, name, tel_phone, email, role_id, status, created_time, created_by, updated_time, updated_by, deleted, deleted_time, deleted_by, last_login_time) VALUES
('90647edf-e9cc-11ef-a30a-7872643982d0', 'user123', '1234!', '王小明', '0912345678', 'xiaoming.wang@example.com', '78a7fdcb-960a-411f-8041-6e288eaf2be0', 1, '2025-02-13 13:37:07.688389', '906430bd-e9cc-11ef-a30a-7872643982d0', '2025-02-13 13:37:07.688389', '906430bd-e9cc-11ef-a30a-7872643982d0', false, null, null, null),
('96ed2a02-e9cc-11ef-a30a-7872643982d0', 'admin123', '1234!', '陳美麗', '0987654321', 'meili.chen@example.com', '1c1126d5-25ed-402b-9ebe-3a7705e09588', 1, '2025-02-13 13:37:18.650966', '96ed2a00-e9cc-11ef-a30a-7872643982d0', '2025-02-13 14:11:01.332288', '4c88921a-e9d1-11ef-992d-7872643982d0', false, null, null, null);

INSERT INTO public.role (uid, name, enable, code, memo) VALUES
('1c1126d5-25ed-402b-9ebe-3a7705e09588', 'Administrator', true, 'Admin', 'Has full access to the system'),
('78a7fdcb-960a-411f-8041-6e288eaf2be0', 'Regular User', false, 'user', 'Can access limited resources');
