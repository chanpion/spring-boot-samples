CREATE TABLE book
(
    id bigint,
    user_id text,
    category_id int,
    click_cnt counter
        PRIMARY KEY ((user_id), category_id)
);