delete from ACCOUNT;
delete from CLIENT;

insert into CLIENT (ID_CLIENT, NAME, AGE) values
  (1, 'Anton', 20)
;

insert into ACCOUNT (ID_ACCOUNT, AMOUNT, CURRENCY, ID_CLIENT) values
  (1, 5,  'USD', 1)
, (2, 15, 'USD', 1)
;
