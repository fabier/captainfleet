-- Alertes
SELECT * FROM alert;
ALTER TABLE public.alert DROP COLUMN check_interval_in_minutes;
ALTER TABLE public.alert DROP COLUMN last_checked_date;
ALTER TABLE public.alert DROP COLUMN most_recent_classified_date;
ALTER TABLE public.alert DROP COLUMN next_check_date;
ALTER TABLE public.alert DROP COLUMN state;
ALTER TABLE public.alert DROP COLUMN url;
SELECT * FROM alert;

DROP TABLE public.alert_classified;
DROP TABLE public.classified_image;

DROP TABLE public.classified;

DROP TABLE public.image;
