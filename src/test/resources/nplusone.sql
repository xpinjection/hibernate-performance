delete from ACCOUNT;
delete from CLIENT;

insert into CLIENT (ID_CLIENT, NAME, AGE) values
      (1, 'Anton', 20)
    , (2, 'Artem', 24)
    , (3, 'Igor', 19)
    , (4, 'Kolya', 21)
    , (5, 'Andrey', 25)
    , (6, 'Dima', 26)
    , (7, 'Evgeniy', 30)
    , (8, 'Nik', 29)
    , (9, 'Max', 27)
    , (10, 'Erik', 29)
    ;

insert into ACCOUNT (ID_ACCOUNT, AMOUNT, CURRENCY, ID_CLIENT) values
      (1, 150, 'EUR', 1)
    , (2, 150, 'USD', 1)
    , (3, 150, 'UAH', 1)
    , (4, 90, 'UAH',  2)
    , (5, 300, 'UAH', 2)
    , (6, 500, 'UAH', 3)
    , (7, 40, 'USD', 4)
    , (8, 190, 'USD', 5)
    , (9, 50, 'USD', 6)
    , (10, 400, 'EUR', 7)
    , (11, 200, 'EUR', 8)
    , (12, 70, 'EUR', 9)
    , (13, 20, 'EUR', 10)
    ;
