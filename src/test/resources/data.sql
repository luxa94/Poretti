
INSERT INTO `owner` (`id`, `imageurl`, `name`) VALUES (1,'/images/defaultUser.jpg','Jacadmin Facadmin');
INSERT INTO `owner` (`id`, `imageurl`, `name`) VALUES (2,'/images/defaultUser.jpg','Test user');
INSERT INTO `owner` (`id`, `imageurl`, `name`) VALUES (3,'/images/defaultUser.jpg','Test test user');
INSERT INTO `owner` (`id`, `imageurl`, `name`) VALUES (4,'/images/defaultUser.jpg','Test verifier');

INSERT INTO `user` (`email`, `password`, `registrationconfirmed`, `role`, `username`, `id`) VALUES ('admin@admin.com','$2a$06$tWabtVmhsP/iUtVODWyeG.tZ6CzyDQhesBCW0cFVHP6oKELIOhm/K',TRUE,'SYSTEM_ADMIN','admin',1);
INSERT INTO `user` (`email`, `password`, `registrationconfirmed`, `role`, `username`, `id`) VALUES ('test@user.com','$2a$06$tWabtVmhsP/iUtVODWyeG.tZ6CzyDQhesBCW0cFVHP6oKELIOhm/K',TRUE,'USER','test_user',2);
INSERT INTO `user` (`email`, `password`, `registrationconfirmed`, `role`, `username`, `id`) VALUES ('testtest@user.com','$2a$06$tWabtVmhsP/iUtVODWyeG.tZ6CzyDQhesBCW0cFVHP6oKELIOhm/K',FALSE,'USER','test_test_user',3);
INSERT INTO `user` (`email`, `password`, `registrationconfirmed`, `role`, `username`, `id`) VALUES ('test@verifier.com','$2a$06$tWabtVmhsP/iUtVODWyeG.tZ6CzyDQhesBCW0cFVHP6oKELIOhm/K',TRUE,'VERIFIER','test_verifier',4);

INSERT INTO `userpermission` (`user`, `permission`) VALUES (1, 'EDIT_ADVERTISEMENT'), (1, 'CREATE_REVIEW'), (1, 'CREATE_ADVERTISEMENT_REPORT'), (1, 'EDIT_COMPANY'), (1, 'CREATE_COMPANY'), (1, 'CREATE_SYSTEM_ADMIN'), (1, 'CREATE_VERIFIER'), (1, 'BAN_USER'), (1, 'DELETE_REVIEW');
INSERT INTO `userpermission` (`user`, `permission`) VALUES (2, 'CREATE_ADVERTISEMENT'), (2, 'EDIT_ADVERTISEMENT'), (2, 'DELETE_ADVERTISEMENT'), (2, 'CREATE_REVIEW'), (2, 'CREATE_ADVERTISEMENT_REPORT'), (2, 'APPROVE_COMPANY_USER'), (2, 'REMOVE_USER_FROM_COMPANY'), (2, 'JOIN_COMPANY'), (2, 'LEAVE_COMPANY'), (2, 'EDIT_COMPANY');
INSERT INTO `userpermission` (`user`, `permission`) VALUES (3, 'CREATE_ADVERTISEMENT'), (3, 'EDIT_ADVERTISEMENT'), (3, 'DELETE_ADVERTISEMENT'), (3, 'CREATE_ADVERTISEMENT_REPORT'), (3, 'CREATE_REVIEW');
INSERT INTO `userpermission` (`user`, `permission`) VALUES (4, 'CREATE_REVIEW'), (4, 'CREATE_ADVERTISEMENT_REPORT'), (4, 'CHANGE_ADVERTISEMENT_STATUS');

INSERT INTO `authorization` (`id`, `token`, `user_id`) VALUES (1,'102da414-847d-4602-8b2d-edca26ab26d7',1);
INSERT INTO `authorization` (`id`, `token`, `user_id`) VALUES (2,'102da414-847d-4602-8b2d-edca26ab26d8',2);
INSERT INTO `authorization` (`id`, `token`, `user_id`) VALUES (3,'102da414-847d-4602-8b2d-edca26ab26d9',3);
INSERT INTO `authorization` (`id`, `token`, `user_id`) VALUES (4,'102da414-847d-4602-8b2d-edca26ab26e7',4);

INSERT INTO `location` (`id`,`city`, `latitude`, `longitude`, `haslatlong`, `cityArea`, `state`, `street`, `streetnumber`) VALUES (1, 'Test city', 1, 1, TRUE, 'Test city area', 'Test state', 'Test street', '123test');

INSERT INTO `realestate` (`id`, `area`, `description`, `imageurl`, `name`, `type`, `location_id`, `owner_id`)
            VALUES (1, 100, 'Test description', '/images/defaultRealEstate.jpg', 'Test name', 'APARTMENT', 1, 2);

INSERT INTO `advertisement` (`id`, `title`, `announcedon`, `editedon`, `endson`, `status`, `type`, `price`, `currency`,`realestate_id`, `advertiser_id`)
            VALUES (1, 'Advertisement title', CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP(), 'ACTIVE', 'SALE', 3000, 'RSD', 1, 2);

INSERT INTO `advertisementreview` (`id`, `comment`, `editedon`, `rating`, `author_id`, `target_id`) VALUES (1, 'Test comment', CURRENT_TIMESTAMP(), 5, 3, 1);