/**
 * Met à jour les colonnes calculées rssi_min, rssi_max, et rssi_avg pour la table frame
 **/

CREATE OR REPLACE FUNCTION captainfleet_update_frame_rssi_min_max_avg()
RETURNS void AS
$BODY$
BEGIN

CREATE TEMP TABLE frames_to_update AS
SELECT id, "time", device_id FROM frame
WHERE rssi_min IS NULL;

CREATE TEMP TABLE frames_to_update_with_data (id, "min", "max", "avg") AS
SELECT FU.id, min(F.rssi) AS "min", max(F.rssi) AS "max", log(avg(10^(F.rssi/10)))*10 AS "avg"
FROM frames_to_update FU
INNER JOIN frame F
ON FU."time" = F."time"
AND FU.device_id = F.device_id
GROUP BY FU.id;

UPDATE frame
SET rssi_min = FUWD."min", rssi_max = FUWD."max", rssi_avg = FUWD."avg"
FROM frames_to_update_with_data FUWD
WHERE FUWD.id = frame.id;

DROP TABLE IF EXISTS frames_to_update;
DROP TABLE IF EXISTS frames_to_update_with_data;

END
$BODY$
LANGUAGE 'plpgsql';