insert into memegen.tags(name) values ('sfw');
insert into memegen.memes(image, user_id) values (FILE_READ('src/main/resources/images/1.jpg'), null);
insert into memegen.memes(image, user_id) values (FILE_READ('src/main/resources/images/2.jpg'), null);
insert into memegen.memes(image, user_id) values (FILE_READ('src/main/resources/images/3.png'), null);

insert into memegen.tags_images(tags_tag_id, meme_image_id) values (1, 1);
insert into memegen.tags_images(tags_tag_id, meme_image_id) values (1, 2);
insert into memegen.tags_images(tags_tag_id, meme_image_id) values (1, 3);