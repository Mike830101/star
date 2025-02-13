-- 建立角色表
create table role (
    uid uuid constraint pk_role primary key, -- 角色唯一識別碼
    name varchar(255) not null unique, -- 角色名稱
    enable boolean default true, -- 角色是否啟用
    code varchar(100) not null unique, -- 角色代碼
    memo text -- 備註
);

comment on table role is '系統角色表';
comment on column role.uid is '角色唯一識別碼';
comment on column role.name is '角色名稱';
comment on column role.enable is '角色是否啟用';
comment on column role.code is '角色代碼';
comment on column role.memo is '備註';

-- 建立索引
create index idx_role_name on role (name);
create index idx_role_code on role (code);
create index idx_role_enable on role (enable);
--使用者
create table sys_user (
    uid uuid constraint pk_sys_user primary key , -- 使用者唯一識別碼
    acc varchar(255) not null unique, -- 使用者帳號
    password text not null, -- 使用者密碼（應加密存儲）
    name varchar(255), -- 使用者名稱
    tel_phone varchar(20), -- 連絡電話
    email varchar(255) unique, -- 電子郵件
    role_id uuid constraint fk_sys_user_role references role(uid), -- 角色 ID
    status int default 1 check (status in (0, 1)), -- 使用者狀態（1:啟用, 0:停用）
    created_time timestamp not null default current_timestamp, -- 創建時間
    created_by uuid not null, -- 創建人
    updated_time timestamp, -- 最後更新時間
    updated_by uuid, -- 最後更新人
    deleted boolean default false, -- 是否刪除（軟刪除）
    deleted_time timestamp, -- 刪除時間
    deleted_by uuid, -- 刪除人
    last_login_time timestamp-- 最後登入時間
);

comment on table sys_user is '系統使用者表';
comment on column sys_user.uid is '使用者唯一識別碼';
comment on column sys_user.acc is '使用者帳號';
comment on column sys_user.password is '使用者密碼（應加密存儲）';
comment on column sys_user.name is '使用者名稱';
comment on column sys_user.tel_phone is '連絡電話';
comment on column sys_user.email is '電子郵件';
comment on column sys_user.role_id is '角色 ID';
comment on column sys_user.status is '使用者狀態（1:啟用, 0:停用等）';
comment on column sys_user.created_time is '創建時間';
comment on column sys_user.created_by is '創建人';
comment on column sys_user.updated_time is '最後更新時間';
comment on column sys_user.updated_by is '最後更新人';
comment on column sys_user.deleted is '是否刪除（軟刪除）';
comment on column sys_user.deleted_time is '刪除時間';
comment on column sys_user.deleted_by is '刪除人';
comment on column sys_user.last_login_time is '最後登入時間';

-- 建立索引
create index idx_sys_user_acc on sys_user(acc);
create index idx_sys_user_role_id on sys_user(role_id);
create index idx_sys_user_deleted on sys_user(deleted);

