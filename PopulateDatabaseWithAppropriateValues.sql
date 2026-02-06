insert into capabilities values
(1, "EDIT_TASKS", "edits_tasks"),
(2, "EDIT_CLIENTS", "edits_clients"),
(3, "EDIT_USERS", "edits_users");

insert into roles values
(1, "USER"),
(2, "ADMIN"),
(3, "SUPER_ADMIN");

-- change as you desire

insert into roles_capabilities values
(1, 1);
(2, 1),
(2, 2),
(3,1),
(3,2),
(3,3);

