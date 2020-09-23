delete from ACCOUNT;
delete from CLIENT;

insert into CLIENT (ID_CLIENT, NAME, AGE) values
  (1000, 'Anton', 20)
, (1001, 'Oleg',  25)
;

insert into ACCOUNT (ID_ACCOUNT, AMOUNT, CURRENCY, ID_CLIENT) values
  (1000, 30, 'USD', 1000)
, (1001, 70, 'USD', 1001)
;
