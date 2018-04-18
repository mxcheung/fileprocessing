---------------------------------------------------------------------------------------
-- Setup Company Table
---------------------------------------------------------------------------------------
IF NOT EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[company]') AND type in (N'U'))
BEGIN
CREATE TABLE  company (
    id UNIQUEIDENTIFIER  NOT NULL, 
    insert_date datetime, 
    last_modified datetime, 
    abn VARCHAR(255) NOT NULL, 
    address VARCHAR(255), 
    company_name VARCHAR(255) NOT NULL, 
    parent_id UNIQUEIDENTIFIER,
    PRIMARY KEY (id) 
)
END;



ALTER TABLE company
ADD CONSTRAINT abn_constraint
UNIQUE (abn);

IF NOT EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[segment]') AND type in (N'U'))
BEGIN
CREATE TABLE segment( 
    id UNIQUEIDENTIFIER  NOT NULL, 
    insert_date datetime, 
    last_modified datetime, 
    ABN VARCHAR(255), 
    ANALYST VARCHAR(255), 
    COMPANY_ID VARCHAR(255) NOT NULL, 
    COMPANY_NAME VARCHAR(255), 
    ext_unique_key VARCHAR(255) NOT NULL, 
    segment_cd VARCHAR(255) NOT NULL, 
    PRIMARY KEY (id) 
)
END


ALTER TABLE segment
ADD CONSTRAINT segment_cd_ext_unique_key_constraint
UNIQUE (segment_cd, ext_unique_key);



---------------------------------------------------------------------------------------
-- Setup Product Table
---------------------------------------------------------------------------------------

IF NOT EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[product]') AND type in (N'U'))
BEGIN
CREATE TABLE  product( 
    id UNIQUEIDENTIFIER  NOT NULL, 
    insert_date datetime, 
    last_modified datetime, 
    APIR_CD VARCHAR(255), 
    EXT_UNIQUE_KEY VARCHAR(255) NOT NULL, 
    NOTES VARCHAR(255), 
    product_id VARCHAR(255) NOT NULL, 
    PRODUCT_NAME VARCHAR(255) NOT NULL, 
    PRODUCT_TYPE VARCHAR(255), 
    SEGMENT_CD VARCHAR(255) NOT NULL, 
    SEGMENT_ID VARCHAR(255) NOT NULL, 
    PRIMARY KEY (id) 
)
END

ALTER TABLE product
ADD CONSTRAINT segment_cd_product_id_constraint
UNIQUE (segment_cd, product_id);


---------------------------------------------------------------------------------------
-- Setup Entity Property Table
---------------------------------------------------------------------------------------

IF NOT EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[entityproperty]') AND type in (N'U'))
BEGIN
CREATE TABLE  entityproperty( 
    id UNIQUEIDENTIFIER  NOT NULL, 
    insert_date datetime, 
    last_modified datetime, 
    entity_id VARCHAR(255), 
    property_key VARCHAR(255), 
    property_type INTEGER, 
    property_value VARCHAR(255), 
    PRIMARY KEY (id) 
)
END

---------------------------------------------------------------------------------------
-- Setup Lookup Table
---------------------------------------------------------------------------------------

IF NOT EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[lookup]') AND type in (N'U'))
BEGIN
CREATE TABLE lookup( 
    id UNIQUEIDENTIFIER  NOT NULL, 
    insert_date datetime, 
    last_modified datetime, 
    lookup_code VARCHAR(255), 
    entity_id VARCHAR(255), 
    lookup_type VARCHAR(255), 
    lookup_type_name VARCHAR(255), 
    lookup_value VARCHAR(255), 
    PRIMARY KEY (id) 
)
END





	
	
