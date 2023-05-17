DROP TABLE QNA;
DROP TABLE FOOD;
DROP TABLE PERSON;
DROP TABLE WANTED;

CREATE TABLE WANTED (
    WANTED_NO              NUMBER                     PRIMARY KEY,
    WANTED_TITLE           VARCHAR2(100 BYTE)         NULL,
    WANTED_CONTENT         VARCHAR2(100 BYTE)         NULL,
    WANTED_DATE            TIMESTAMP                  NULL,
    WANTED_PRICE           NUMBER                     NULL,
    WANTED_THUMBNAIL       NUMBER                     NULL  -- 썸네일 있으면 1, 없으면 0 
);

CREATE TABLE PERSON (
    PER_ID                 VARCHAR2(20 BYTE)          PRIMARY KEY,
    PER_PW                 VARCHAR2(20 BYTE)          NOT NULL,
    PER_NAME               VARCHAR2(20 BYTE)          NOT NULL,
    PER_PHONENO            NUMBER                     NOT NULL,
    PER_POSTADDRESS        VARCHAR2(5 BYTE)           NULL,
    PER_ROADADDRESS        VARCHAR2(100 BYTE)         NULL,
    PER_JIBUNADDRESS       VARCHAR2(100 BYTE)         NULL,
    PER_DETAILADDRESS      VARCHAR2(100 BYTE)         NOT NULL,
    PER_EMAIL              VARCHAR2(20 BYTE)          NOT NULL,
    PER_GENDER             NUMBER                     NOT NULL
);

CREATE TABLE FOOD (
    FD_NO                  NUMBER                     PRIMARY KEY,
    FD_TITLE               VARCHAR2(100 BYTE)         NOT NULL,
    FD_TOOL                VARCHAR2(1000 BYTE)        NULL,       -- 재료
    FD_CONTENT1            VARCHAR2(200 BYTE)         NULL,
    FD_CONTENT2            CLOB                       NOT NULL,
    FD_REPLY               VARCHAR2(1000 BYTE)        NOT NULL,   -- 댓글
    FD_DATE                TIMESTAMP                  NULL,
    FD_EDIT                TIMESTAMP                  NULL,       -- 수정일
    FD_WRITER              VARCHAR2(20 BYTE)          NOT NULL,
    CONSTRAINT FK_FD_WRITER FOREIGN KEY(FD_WRITER) REFERENCES PERSON(PER_ID)
);


CREATE TABLE QNA(
    QNA_NO                 NUMBER                    PRIMARY KEY,
    QNA_TITLE              VARCHAR2(100 BYTE)        NOT NULL,
    QNA_CONTENT            CLOB                      NOT NULL,
    QNA_ID                 VARCHAR2(20 BYTE)         NOT NULL,
    QNA_DATE               TIMESTAMP                 NULL,
    QNA_REPLY              VARCHAR2(1000 BYTE)       NOT NULL,
    CONSTRAINT FK_QNA_ID FOREIGN KEY(QNA_ID) REFERENCES PERSON(PER_ID)
);

DROP SEQUENCE FOOD_SEQ;
CREATE SEQUENCE FOOD_SEQ NOCACHE;
DROP SEQUENCE PERSON_SEQ;
CREATE SEQUENCE PERSON_SEQ NOCACHE;
DROP SEQUENCE QNA_SEQ;
CREATE SEQUENCE QNA_SEQ NOCACHE;


