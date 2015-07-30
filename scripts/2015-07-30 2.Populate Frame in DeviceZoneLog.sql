-- SELECT DZL.device_id, DZL.zone_id, DZL.date_created, count(F.id)
-- FROM device_zone_log DZL
-- LEFT JOIN frame F
-- ON F.device_id = DZL.device_id
-- AND F.duplicate = FALSE
-- AND F.date_created between DZL.date_created - interval '10 second' AND DZL.date_created + interval '10 second'
-- GROUP BY DZL.device_id, DZL.zone_id, DZL.date_created
-- HAVING count(F.id) = 1;

UPDATE device_zone_log
SET frame_id = F.id
FROM frame F
WHERE frame_id IS NULL
AND F.device_id = device_zone_log.device_id
AND F.duplicate = FALSE
AND F.date_created between device_zone_log.date_created - interval '10 second' AND device_zone_log.date_created + interval '10 second';