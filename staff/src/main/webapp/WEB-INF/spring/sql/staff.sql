DROP TABLE STAFF;
CREATE TABLE STAFF (
    SNO VARCHAR2(5 BYTE) NOT NULL PRIMARY KEY, 
    NAME VARCHAR2(32 BYTE), 
    DEPT VARCHAR2(20 BYTE) NOT NULL, 
    SALARY NUMBER NOT NULL
);