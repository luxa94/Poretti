LOCK TABLES `owner` WRITE;
/*!40000 ALTER TABLE `owner` DISABLE KEYS */;
INSERT INTO `owner` (`id`, `image_url`, `name`) VALUES (1,'/images/defaultUser.jpg','Jacadmin Facadmin');
/*!40000 ALTER TABLE `owner` ENABLE KEYS */;
UNLOCK TABLES;

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` (`email`, `password`, `registration_confirmed`, `role`, `username`, `id`) VALUES ('admin@admin.com','$2a$06$tWabtVmhsP/iUtVODWyeG.tZ6CzyDQhesBCW0cFVHP6oKELIOhm/K',TRUE,'SYSTEM_ADMIN','admin',1);
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;

LOCK TABLES `user_permission` WRITE;
/*!40000 ALTER TABLE `user_permission` DISABLE KEYS */;
INSERT INTO `user_permission` (`user`, `permission`) VALUES (1, 'EDIT_ADVERTISEMENT'), (1, 'CREATE_REVIEW'), (1, 'REPORT_ADVERTISEMENT'), (1, 'EDIT_COMPANY'), (1, 'CREATE_COMPANY'), (1, 'CREATE_SYSTEM_ADMIN'), (1, 'CREATE_VERIFIER'), (1, 'BAN_USER'), (1, 'DELETE_REVIEW');
/*!40000 ALTER TABLE `user_permission` ENABLE KEYS */;
UNLOCK TABLES;

LOCK TABLES `authorization` WRITE;
/*!40000 ALTER TABLE `authorization` DISABLE KEYS */;
INSERT INTO `authorization` (`id`, `token`, `user_id`) VALUES (1,'102da414-847d-4602-8b2d-edca26ab26d7',1);
/*!40000 ALTER TABLE `authorization` ENABLE KEYS */;
UNLOCK TABLES;
