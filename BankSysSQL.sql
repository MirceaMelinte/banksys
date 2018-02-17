CREATE TABLE account(
    acc_no NUMBER(10, 0) GENERATED BY DEFAULT ON NULL AS IDENTITY PRIMARY KEY,
    customer_name VARCHAR(50) NOT NULL,
    balance DOUBLE PRECISION NOT NULL
);

CREATE TABLE transaction(
    id NUMBER(10, 0) GENERATED BY DEFAULT ON NULL AS IDENTITY PRIMARY KEY,
    acc_no NUMBER(10, 0) REFERENCES account (acc_no) NOT NULL,
    type VARCHAR(10) NOT NULL,
    amount DOUBLE PRECISION NOT NULL
);

-- raw test data

INSERT INTO account (customer_name, balance) VALUES ('Foo Foosen', 0); 
INSERT INTO account (customer_name, balance) VALUES ('Bar Barsen', 10);

INSERT INTO transaction (acc_no, type, amount) VALUES (2, 'withdraw', 3);
UPDATE account SET balance = balance - 3 WHERE acc_no = 2;

SELECT * FROM account;
SELECT * FROM transaction;

SELECT SQL_TEXT FROM V$SESSION INNER JOIN V$SQL
ON (V$SESSION.SQL_ID = V$SQL.SQL_ID OR V$SQL.SQL_ID = V$SESSION.PREV_SQL_ID)
WHERE SID = sys_context('userenv','SID');