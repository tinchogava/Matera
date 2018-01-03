ALTER TABLE `presupuesto` ADD COLUMN `costo_venta`  decimal(9,2) NULL AFTER `usuario`;
UPDATE presupuesto SET costo_venta = 0;