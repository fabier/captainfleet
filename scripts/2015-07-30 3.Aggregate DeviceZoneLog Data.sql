CREATE OR REPLACE FUNCTION captainfleet_aggregate_device_zone_data()
RETURNS void AS $$
DECLARE

is_raised BOOLEAN;

rowDeviceZoneLog device_zone_log%ROWTYPE;

cursorDeviceZone CURSOR FOR
SELECT id FROM device_zone;

cursorDeviceZoneLog CURSOR (device_zone_id_param integer) FOR
SELECT * FROM device_zone_log
WHERE device_zone_id = device_zone_id_param
ORDER BY date_created;

BEGIN

TRUNCATE TABLE device_zone_log_aggregate;

FOR rowDeviceZone IN cursorDeviceZone LOOP
	is_raised := NULL;
	FOR rowDeviceZoneLog IN cursorDeviceZoneLog(rowDeviceZone.id) LOOP
	     IF is_raised IS NULL OR is_raised != rowDeviceZoneLog.is_raised THEN
		INSERT INTO device_zone_log_aggregate(id, "version", date_created, device_id, frame_id, is_raised, last_updated, zone_id)
		VALUES (
                    nextval('hibernate_sequence'),
                    0,
                    rowDeviceZoneLog.date_created,
                    rowDeviceZoneLog.device_id,
                    rowDeviceZoneLog.frame_id,
                    rowDeviceZoneLog.is_raised,
                    rowDeviceZoneLog.last_updated,
                    rowDeviceZoneLog.zone_id);
	     END IF;
	     is_raised := rowDeviceZoneLog.is_raised;	 
	END LOOP;
END LOOP;
END;
$$ LANGUAGE plpgsql;

-- SELECT * FROM captainfleet_aggregate_device_zone_data();

-- SELECT * FROM device_zone_log_aggregate;