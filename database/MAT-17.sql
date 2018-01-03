ALTER TABLE stockdetalle ADD serie varchar(255) NULL DEFAULT '' after lote;
 update stockdetalle set serie = '';

ALTER TABLE producto add gtin varchar(13) NULL DEFAULT '' after codigo;
update producto set gtin = '';
