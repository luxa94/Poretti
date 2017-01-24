INSERT INTO `owner` (`id`, `image_url`, `name`) VALUES (1, '/images/defaultUser.jpg', 'Jacadmin Facadmin');
INSERT INTO `owner` (`id`, `image_url`, `name`) VALUES (2, '/images/defaultUser.jpg', 'Test user');
INSERT INTO `owner` (`id`, `image_url`, `name`) VALUES (3, '/images/defaultUser.jpg', 'Test test user');
INSERT INTO `owner` (`id`, `image_url`, `name`) VALUES (4, '/images/defaultUser.jpg', 'Test verifier');
INSERT INTO `owner` (`id`, `image_url`, `name`) VALUES (5, '/images/defaultUser.jpg', 'Mika Nije Zika');
INSERT INTO `owner` (`id`, `image_url`, `name`) VALUES (6, '/images/defaultUser.jpg', 'Aminko adminkovic');
INSERT INTO `owner` (`id`, `image_url`, `name`) VALUES (7, '/images/defaultUser.jpg', 'verifiarko verifiarko');
INSERT INTO `owner` (`id`, `image_url`, `name`) VALUES (8, '/images/joey.png', 'Gabriel Iglesias');
INSERT INTO `owner` (`id`, `image_url`, `name`) VALUES (9, '/images/defaultUser.jpg', 'Joey Tribiani');
INSERT INTO `owner` (`id`, `image_url`, `name`) VALUES (10, '/images/google.png', 'google');
INSERT INTO `owner` (`id`, `image_url`, `name`) VALUES (11, '/images/chandler.png', 'Chandler Bing');

INSERT INTO `user` (`email`, `password`, `registration_confirmed`, `role`, `username`, `id`) VALUES ('admin@admin.com', '$2a$06$tWabtVmhsP/iUtVODWyeG.tZ6CzyDQhesBCW0cFVHP6oKELIOhm/K', TRUE, 'SYSTEM_ADMIN', 'admin', 1);
INSERT INTO `user` (`email`, `password`, `registration_confirmed`, `role`, `username`, `id`) VALUES ('test@user.com', '$2a$06$tWabtVmhsP/iUtVODWyeG.tZ6CzyDQhesBCW0cFVHP6oKELIOhm/K', TRUE, 'USER', 'test_user', 2);
INSERT INTO `user` (`email`, `password`, `registration_confirmed`, `role`, `username`, `id`) VALUES ('testtest@user.com', '$2a$06$tWabtVmhsP/iUtVODWyeG.tZ6CzyDQhesBCW0cFVHP6oKELIOhm/K', FALSE, 'USER', 'test_test_user', 3);
INSERT INTO `user` (`email`, `password`, `registration_confirmed`, `role`, `username`, `id`) VALUES ('test@verifier.com', '$2a$06$tWabtVmhsP/iUtVODWyeG.tZ6CzyDQhesBCW0cFVHP6oKELIOhm/K', TRUE, 'VERIFIER', 'test_verifier', 4);
INSERT INTO `user` (`email`, `password`, `registration_confirmed`, `role`, `username`, `id`) VALUES ('Rika@someEmail.com', '$2a$10$OrtHnGk8yTIRGVQB5m.6OOdLUj.3Lvr2jw3BKyfN.sbRLlm5uuSFC', TRUE, 'USER', 'Rika', 5);
INSERT INTO `user` (`email`, `password`, `registration_confirmed`, `role`, `username`, `id`) VALUES ('adminko2@com.com', '$2a$10$VniRD5kGdzSbSFPzyyNKBObfIentFCPYaUNhn499.3rHYQ9rbLJEi', TRUE, 'SYSTEM_ADMIN', 'adminko2',  6);
INSERT INTO `user` (`email`, `password`, `registration_confirmed`, `role`, `username`, `id`) VALUES ('verifiarko2@com.com', '$2a$10$uoxNFe1TnwDVpPjCYxMPMeCFmQN4SCwTdn2/2z.pT4QKm3nVFJer.', TRUE, 'VERIFIER', 'verifiarko2', 7);

-- password: asd
INSERT INTO `user` (`email`, `password`, `registration_confirmed`, `role`, `username`, `id`) VALUES ('fluffy@nadamsedanikonemaovajmail.com', '$2a$10$WqTOVKsATsk8KMzdWV9zt.1XvKsh4vsMEkr4fVwjoxkDfaX.oHp5C', TRUE, 'USER', 'fluffy', 8);
INSERT INTO `user` (`email`, `password`, `registration_confirmed`, `role`, `username`, `id`) VALUES ('joey@nadamsedanikonemaovajmai.com', '$2a$10$TjwnNm66P.Li1J88f0b3VekYcmL1qp8xrLVFwEbBkQtoKFP906A5W', TRUE, 'USER', 'joey', 9);
INSERT INTO `user` (`email`, `password`, `registration_confirmed`, `role`, `username`, `id`) VALUES ('chandler@nadamsedanikonemaovajmail.com', '$2a$10$CbCutaCJH5A5Mn1QXlYaYeAPjTlJpC/KnNA6hbOhVVdH7O43PO2Ue', TRUE, 'USER', 'chandler', 11);

INSERT INTO `location` (`id`, `city`, `city_area`, `has_lat_long`, `latitude`, `longitude`, `state`, `street`, `street_number`, `zip_code`) VALUES (1, 'Novi Sad', NULL, '\0', 1, 1, 'Serbia', 'Mileve Maric', '52', NULL);
INSERT INTO `location` (`id`, `city`, `city_area`, `has_lat_long`, `latitude`, `longitude`, `state`, `street`, `street_number`, `zip_code`) VALUES (2, 'Novi Sad', NULL, '\0', 44.77403648591521, 20.43680191040039, 'Serbia', NULL, NULL, NULL);
INSERT INTO `location` (`id`, `city`, `city_area`, `has_lat_long`, `latitude`, `longitude`, `state`, `street`, `street_number`, `zip_code`) VALUES (3, 'Dubai', NULL, '\0', 0, 0, '', NULL, NULL, NULL);
INSERT INTO `location` (`id`, `city`, `city_area`, `has_lat_long`, `latitude`, `longitude`, `state`, `street`, `street_number`, `zip_code`) VALUES (4, 'Dubai', NULL, '\0', 0, 0, NULL, NULL, NULL, NULL);

INSERT INTO `company` (`pib`, `id`, `location_id`) VALUES ('gugl', 10, 2);

INSERT INTO `real_estate` (`id`, `area`, `description`, `image_url`, `name`, `type`, `location_id`, `owner_id`) VALUES (1, 100, 'Test description', '/images/defaultRealEstate.jpg', 'Test name', 'APARTMENT', 1, 2);
INSERT INTO `real_estate` (`id`, `area`, `description`, `image_url`, `name`, `type`, `location_id`, `owner_id`) VALUES (2, 201312325, 'You know what it is.', '/images/bk.jpeg', 'Burj Khalifa', 'HOUSE', 3, 11);
INSERT INTO `real_estate` (`id`, `area`, `description`, `image_url`, `name`, `type`, `location_id`, `owner_id`) VALUES (3, 18256422, 'We need something bigger. (That\'s what she said)', '/images/palm.jpg', 'Former HQ', 'APARTMENT', 4, 10);

INSERT INTO `advertisement` (`id`, `announced_on`, `currency`, `edited_on`, `ends_on`, `price`, `status`, `title`, `type`, `advertiser_id`, `real_estate_id`) VALUES (1, '2017-01-14 20:07:13', 'RSD', '2017-01-14 20:07:13', '2017-01-14 20:07:13', 3000, 'PENDING_APPROVAL', 'Advertisement title', 'SALE', 2, 1);
INSERT INTO `advertisement` (`id`, `announced_on`, `currency`, `edited_on`, `ends_on`, `price`, `status`, `title`, `type`, `advertiser_id`, `real_estate_id`) VALUES (2, '2017-01-14 20:07:13', 'RSD', '2017-01-14 20:07:13', '2017-01-14 20:07:13', 3000, 'ACTIVE', 'New advertisement', 'RENT', 2, 1);
INSERT INTO `advertisement` (`id`, `announced_on`, `currency`, `edited_on`, `ends_on`, `price`, `status`, `title`, `type`, `advertiser_id`, `real_estate_id`) VALUES (9, '2017-01-17 19:00:43', 'EUR', '2017-01-17 19:00:43', '2018-07-24 00:00:00', 12783518652, 'ACTIVE', 'Penthouse', 'RENT', 11, 2);
INSERT INTO `advertisement` (`id`, `announced_on`, `currency`, `edited_on`, `ends_on`, `price`, `status`, `title`, `type`, `advertiser_id`, `real_estate_id`) VALUES (10, '2017-01-17 19:42:01', 'EUR', '2017-01-17 19:42:01', '2018-09-11 00:00:00', 1231414, 'ACTIVE', 'Get it while it\'s hot', 'SALE', 10, 3);

INSERT INTO `advertisement_review` (`id`, `comment`, `edited_on`, `rating`, `author_id`, `target_id`) VALUES (2, 'Awesomeeeeeeeee', '2017-01-17 19:44:52', 5, 9, 9);

INSERT INTO poretti.improper_advertisement_report (description, edited_on, reason, advertisement_id, author_id) VALUES ('Reporrrrrrrrrrrrrrt.', '2017-01-22 16:50:05', 'OTHER', 9, 2);
INSERT INTO poretti.improper_advertisement_report (description, edited_on, reason, advertisement_id, author_id) VALUES ('Second reporrrrrrrrrrrrrrt.', '2017-01-22 16:50:05', 'OTHER', 1, 2);

INSERT INTO `authorization` (`id`, `token`, `user_id`) VALUES (1, '102da414-847d-4602-8b2d-edca26ab26d7', 1);
INSERT INTO `authorization` (`id`, `token`, `user_id`) VALUES (2, '102da414-847d-4602-8b2d-edca26ab26d8', 2);
INSERT INTO `authorization` (`id`, `token`, `user_id`) VALUES (3, '102da414-847d-4602-8b2d-edca26ab26d9', 3);
INSERT INTO `authorization` (`id`, `token`, `user_id`) VALUES (4, '102da414-847d-4602-8b2d-edca26ab26e7', 4);
INSERT INTO `authorization` (`id`, `token`, `user_id`) VALUES (9, 'a8b68088-75e9-4f12-a034-36c9f593db75', 2);
INSERT INTO `authorization` (`id`, `token`, `user_id`) VALUES (10, '37560b15-b17f-4770-b947-b3fe74d3e48e', 2);
INSERT INTO `authorization` (`id`, `token`, `user_id`) VALUES (11, 'b94ae38b-1b8d-4626-b985-fff14c8ca63e', 3);
INSERT INTO `authorization` (`id`, `token`, `user_id`) VALUES (12, '9fac79a6-9159-49cb-9fd6-fef743666ecd', 2);
INSERT INTO `authorization` (`id`, `token`, `user_id`) VALUES (13, '143a421e-5ea5-4d01-834c-085eea172600', 1);
INSERT INTO `authorization` (`id`, `token`, `user_id`) VALUES (14, '56ed4603-e767-4ecd-b557-3c30bb11fc54', 2);
INSERT INTO `authorization` (`id`, `token`, `user_id`) VALUES (15, '4090e5fc-e7ce-4e80-8869-d756281908bf', 2);
INSERT INTO `authorization` (`id`, `token`, `user_id`) VALUES (16, '740a4812-074a-4524-805e-99da826a08b4', 3);
INSERT INTO `authorization` (`id`, `token`, `user_id`) VALUES (17, 'fb20eaf4-5cf3-462e-8678-e381e7230685', 1);
INSERT INTO `authorization` (`id`, `token`, `user_id`) VALUES (20, 'ae492c4e-dc4d-4429-828f-7571460b4c41', 9);

INSERT INTO `membership` (`id`, `confirmed`, `approved_by_id`, `company_id`, `member_id`) VALUES (1, '', 1, 10, 11);

INSERT INTO `technical_equipment` (`real_estate`, `name`) VALUES (3, 'everything');

INSERT INTO `user_permission` (`user`, `permission`) VALUES (1, 'EDIT_ADVERTISEMENT'), (1, 'CREATE_REVIEW'), (1, 'CREATE_ADVERTISEMENT_REPORT'), (1, 'EDIT_COMPANY'), (1, 'CREATE_COMPANY'), (1, 'CREATE_SYSTEM_ADMIN'), (1, 'CREATE_VERIFIER'), (1, 'BAN_USER'), (1, 'DELETE_REVIEW');
INSERT INTO `user_permission` (`user`, `permission`) VALUES (2, 'CREATE_ADVERTISEMENT'), (2, 'EDIT_ADVERTISEMENT'), (2, 'DELETE_ADVERTISEMENT'), (2, 'CREATE_REVIEW'), (2, 'DELETE_REVIEW'), (2, 'CREATE_ADVERTISEMENT_REPORT'), (2, 'APPROVE_COMPANY_USER'), (2, 'REMOVE_USER_FROM_COMPANY'), (2, 'JOIN_COMPANY'), (2, 'LEAVE_COMPANY'), (2, 'EDIT_COMPANY');
INSERT INTO `user_permission` (`user`, `permission`) VALUES (3, 'CREATE_ADVERTISEMENT'), (3, 'EDIT_ADVERTISEMENT'), (3, 'DELETE_ADVERTISEMENT'), (3, 'CREATE_REVIEW'), (3, 'DELETE_REVIEW'), (3, 'CREATE_ADVERTISEMENT_REPORT'), (3, 'APPROVE_COMPANY_USER'), (3, 'REMOVE_USER_FROM_COMPANY'), (3, 'JOIN_COMPANY'), (3, 'LEAVE_COMPANY'), (3, 'EDIT_COMPANY');
INSERT INTO `user_permission` (`user`, `permission`) VALUES (4, 'CREATE_REVIEW'), (4, 'CREATE_ADVERTISEMENT_REPORT'), (4, 'CHANGE_ADVERTISEMENT_STATUS');
INSERT INTO `user_permission` (`user`, `permission`) VALUES (5, 'CREATE_ADVERTISEMENT'), (5, 'EDIT_ADVERTISEMENT'), (5, 'DELETE_ADVERTISEMENT'), (5, 'CREATE_REVIEW'), (5, 'DELETE_REVIEW'), (5, 'CREATE_ADVERTISEMENT_REPORT'), (5, 'APPROVE_COMPANY_USER'), (5, 'REMOVE_USER_FROM_COMPANY'), (5, 'JOIN_COMPANY'), (5, 'LEAVE_COMPANY'), (5, 'EDIT_COMPANY');
INSERT INTO `user_permission` (`user`, `permission`) VALUES (6, 'EDIT_ADVERTISEMENT'), (6, 'CREATE_REVIEW'), (6, 'CREATE_ADVERTISEMENT_REPORT'), (6, 'EDIT_COMPANY'), (6, 'CREATE_COMPANY'), (6, 'CREATE_SYSTEM_ADMIN'), (6, 'CREATE_VERIFIER'), (6, 'BAN_USER'), (6, 'DELETE_REVIEW');
INSERT INTO `user_permission` (`user`, `permission`) VALUES (7, 'CREATE_REVIEW'), (7, 'CREATE_ADVERTISEMENT_REPORT'), (7, 'CHANGE_ADVERTISEMENT_STATUS');
INSERT INTO `user_permission` (`user`, `permission`) VALUES (8, 'CREATE_ADVERTISEMENT'), (8, 'EDIT_ADVERTISEMENT'), (8, 'DELETE_ADVERTISEMENT'), (8, 'CREATE_REVIEW'), (8, 'DELETE_REVIEW'), (8, 'CREATE_ADVERTISEMENT_REPORT'), (8, 'APPROVE_COMPANY_USER'), (8, 'REMOVE_USER_FROM_COMPANY'), (8, 'JOIN_COMPANY'), (8, 'LEAVE_COMPANY'), (8, 'EDIT_COMPANY');
INSERT INTO `user_permission` (`user`, `permission`) VALUES (9, 'CREATE_ADVERTISEMENT'), (9, 'EDIT_ADVERTISEMENT'), (9, 'DELETE_ADVERTISEMENT'), (9, 'CREATE_REVIEW'), (9, 'DELETE_REVIEW'), (9, 'CREATE_ADVERTISEMENT_REPORT'), (9, 'APPROVE_COMPANY_USER'), (9, 'REMOVE_USER_FROM_COMPANY'), (9, 'JOIN_COMPANY'), (9, 'LEAVE_COMPANY'), (9, 'EDIT_COMPANY');
INSERT INTO `user_permission` (`user`, `permission`) VALUES (11, 'CREATE_ADVERTISEMENT'), (11, 'EDIT_ADVERTISEMENT'), (11, 'DELETE_ADVERTISEMENT'), (11, 'CREATE_REVIEW'), (11, 'DELETE_REVIEW'), (11, 'CREATE_ADVERTISEMENT_REPORT'), (11, 'APPROVE_COMPANY_USER'), (11, 'REMOVE_USER_FROM_COMPANY'), (11, 'JOIN_COMPANY'), (11, 'LEAVE_COMPANY'), (11, 'EDIT_COMPANY');
