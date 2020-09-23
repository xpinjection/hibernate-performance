delete from ACCOUNT;
delete from CLIENT;

insert into CLIENT (ID_CLIENT, NAME, AGE) values
  (1, 'Anton', 20)
;

insert into ACCOUNT (ID_ACCOUNT, AMOUNT, CURRENCY, ID_CLIENT) values
  (1, 10, 'EUR', 1)
, (2, 20, 'EUR', 1)
, (3, 20, 'EUR', 1)
;
