/**
 * Procédure lancée toutes les nuits sur le serveur pour maintenir les données de la base à jour.
 **/

CREATE OR REPLACE FUNCTION captainfleet_daily_maintenance()
RETURNS void AS
$BODY$
BEGIN

END
$BODY$
LANGUAGE 'plpgsql';