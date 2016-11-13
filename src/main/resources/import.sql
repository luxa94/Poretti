LOCK TABLES `image` WRITE;
/*!40000 ALTER TABLE `image` DISABLE KEYS */;
INSERT INTO `image` (`id`, `url`) VALUES (1,'/image/defaultUser.jpg');
/*!40000 ALTER TABLE `image` ENABLE KEYS */;
UNLOCK TABLES;

LOCK TABLES `location` WRITE;
/*!40000 ALTER TABLE `location` DISABLE KEYS */;
INSERT INTO `location` (`id`, `city`, `city_area`, `has_lat_long`, `latitude`, `longitude`, `state`, `street`, `street_number`, `zip_code`) VALUES (1,'Admin town',NULL,'\0',0,0,NULL,NULL,NULL,NULL);
/*!40000 ALTER TABLE `location` ENABLE KEYS */;
UNLOCK TABLES;

LOCK TABLES `owner` WRITE;
/*!40000 ALTER TABLE `owner` DISABLE KEYS */;
INSERT INTO `owner` (`id`, `name`, `image_id`, `location_id`) VALUES (1,'Jacadmin Facadmin',1,1);
/*!40000 ALTER TABLE `owner` ENABLE KEYS */;
UNLOCK TABLES;

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` (`email`, `password`, `registration_confirmed`, `role`, `username`, `id`) VALUES ('admin@admin.com','$2a$06$tWabtVmhsP/iUtVODWyeG.tZ6CzyDQhesBCW0cFVHP6oKELIOhm/K',TRUE,'SYSTEM_ADMIN','admin',1);
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;

LOCK TABLES `user_permission` WRITE;
/*!40000 ALTER TABLE `user_permission` DISABLE KEYS */;
INSERT INTO `user_permission` (`user`, `permission`) VALUES (1, 'EDIT_ADVERTISEMENT'), (1, 'CREATE_REVIEW'), (1, 'EDIT_REVIEW'), (1, 'REPORT_ADVERTISEMENT'), (1, 'EDIT_COMPANY_ADVERTISEMENT'), (1, 'APPROVE_COMPANY_USER'), (1, 'REMOVE_USER_FROM_COMPANY'), (1, 'EDIT_COMPANY'), (1, 'CHANGE_ADVERTISEMENT_STATUS'), (1, 'CREATE_COMPANY'), (1, 'CREATE_COMPANY_ADMIN'), (1, 'CREATE_VERIFIER'), (1, 'BAN_USER'), (1, 'DELETE_REVIEW');
/*!40000 ALTER TABLE `user_permission` ENABLE KEYS */;
UNLOCK TABLES;
