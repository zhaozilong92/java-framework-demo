create table if not exists tbl_train_tickets
(
    id        int not null primary key auto_increment,
    count      NUMBER(100),
    type       VARCHAR(256)
);