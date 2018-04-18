---------------------------------------------------------------------------------------
-- Setup Company Table
---------------------------------------------------------------------------------------

CREATE TABLE IF NOT EXISTS company (
    id BINARY NOT NULL, 
    INSERT_DATE TIMESTAMP, 
    LAST_MODIFIED TIMESTAMP, 
    abn VARCHAR(255) NOT NULL, 
    address VARCHAR(255), 
    COMPANY_NAME VARCHAR(255) NOT NULL, 
    PARENT_ID BINARY,
    PRIMARY KEY (id) 
);


ALTER TABLE company
ADD CONSTRAINT abn_constraint
UNIQUE (abn);


CREATE TABLE IF NOT EXISTS segment( 
    id BINARY NOT NULL, 
    INSERT_DATE TIMESTAMP, 
    LAST_MODIFIED TIMESTAMP, 
    ABN VARCHAR(255), 
    ANALYST VARCHAR(255), 
    COMPANY_ID VARCHAR(255) NOT NULL, 
    COMPANY_NAME VARCHAR(255), 
    ext_unique_key VARCHAR(255) NOT NULL, 
    segment_cd VARCHAR(255) NOT NULL, 
    PRIMARY KEY (id) 
);


ALTER TABLE segment
ADD CONSTRAINT segment_cd_ext_unique_key_constraint
UNIQUE (segment_cd, ext_unique_key);



---------------------------------------------------------------------------------------
-- Setup Product Table
---------------------------------------------------------------------------------------

CREATE TABLE IF NOT EXISTS product( 
    id BINARY NOT NULL, 
    INSERT_DATE TIMESTAMP, 
    LAST_MODIFIED TIMESTAMP, 
    APIR_CD VARCHAR(255), 
    EXT_UNIQUE_KEY VARCHAR(255) NOT NULL, 
    NOTES VARCHAR(255), 
    product_id VARCHAR(255) NOT NULL, 
    PRODUCT_NAME VARCHAR(255) NOT NULL, 
    PRODUCT_TYPE VARCHAR(255), 
    SEGMENT_CD VARCHAR(255) NOT NULL, 
    SEGMENT_ID VARCHAR(255) NOT NULL, 
    PRIMARY KEY (id) 
);

ALTER TABLE product
ADD CONSTRAINT segment_cd_product_id_constraint
UNIQUE (segment_cd, product_id);


---------------------------------------------------------------------------------------
-- Setup Entity Property Table
---------------------------------------------------------------------------------------


CREATE TABLE IF NOT EXISTS entityproperty( 
    id BINARY NOT NULL, 
    INSERT_DATE TIMESTAMP, 
    LAST_MODIFIED TIMESTAMP, 
    ENTITY_ID VARCHAR(255), 
    PROPERTY_KEY VARCHAR(255), 
    PROPERTY_TYPE INTEGER, 
    PROPERTY_VALUE VARCHAR(255),     
    PRIMARY KEY (id)
    
);


---------------------------------------------------------------------------------------
-- Setup Lookup Table
---------------------------------------------------------------------------------------

CREATE TABLE IF NOT EXISTS lookup( 
    id BINARY NOT NULL, 
    INSERT_DATE TIMESTAMP, 
    LAST_MODIFIED TIMESTAMP, 
    LOOKUP_CODE VARCHAR(255), 
    ENTITY_ID VARCHAR(255), 
    LOOKUP_TYPE VARCHAR(255), 
    LOOKUP_TYPE_NAME VARCHAR(255), 
    LOOKUP_VALUE VARCHAR(255), 
    PRIMARY KEY (id)
    
);