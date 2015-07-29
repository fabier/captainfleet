-- select table_name from information_schema.columns where column_name LIKE '%alert%';

ALTER TABLE alert RENAME TO "zone";
ALTER TABLE device_alert RENAME TO "device_zone";
ALTER TABLE device_alert_log RENAME TO "device_zone_log";
ALTER TABLE user_alert RENAME TO "user_zone";

ALTER TABLE device_zone RENAME COLUMN "alert_id" TO "zone_id";
ALTER TABLE user_zone RENAME COLUMN "alert_id" TO "zone_id";
ALTER TABLE device_zone_log RENAME COLUMN "device_alert_id" TO "device_zone_id";