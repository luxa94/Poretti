
INSERT INTO `owner` (`id`, `imageurl`, `name`) VALUES (1,'/images/defaultUser.jpg','Jacadmin Facadmin');

INSERT INTO `user` (`email`, `password`, `registrationconfirmed`, `role`, `username`, `id`) VALUES ('admin@admin.com','$2a$06$tWabtVmhsP/iUtVODWyeG.tZ6CzyDQhesBCW0cFVHP6oKELIOhm/K',TRUE,'SYSTEM_ADMIN','admin',1);

INSERT INTO `userpermission` (`user`, `permission`) VALUES (1, 'EDIT_ADVERTISEMENT'), (1, 'CREATE_REVIEW'), (1, 'REPORT_ADVERTISEMENT'), (1, 'EDIT_COMPANY'), (1, 'CREATE_COMPANY'), (1, 'CREATE_SYSTEM_ADMIN'), (1, 'CREATE_VERIFIER'), (1, 'BAN_USER'), (1, 'DELETE_REVIEW');

INSERT INTO `authorization` (`id`, `token`, `user_id`) VALUES (1,'102da414-847d-4602-8b2d-edca26ab26d7',1);

