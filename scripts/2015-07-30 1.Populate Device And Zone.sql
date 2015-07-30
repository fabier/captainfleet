UPDATE device_zone_log
SET device_id = DZ.device_id,
zone_id = DZ.zone_id
FROM device_zone DZ
WHERE DZ.id = device_zone_log.device_zone_id;