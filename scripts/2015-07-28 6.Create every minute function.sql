/**
 * Procédure lancée toutes les minutes (ou toutes les 5 minutes) sur le serveur pour maintenir les données de la base à jour.
 **/

CREATE OR REPLACE FUNCTION captainfleet_every_minute_maintenance()
RETURNS void AS
$BODY$
BEGIN

PERFORM captainfleet_update_frame_rssi_min_max_avg();

END
$BODY$
LANGUAGE 'plpgsql';