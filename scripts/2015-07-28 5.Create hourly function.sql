/**
 * Procédure lancée toutes les heures sur le serveur pour maintenir les données de la base à jour.
 **/

CREATE OR REPLACE FUNCTION captainfleet_hourly_maintenance()
RETURNS void AS
$BODY$
BEGIN

END
$BODY$
LANGUAGE 'plpgsql';