package captainfleet

import grails.transaction.Transactional

@Transactional
class EncoderService {

    String encodeLatitudeOrLongitude(double latitudeOrLongitude) {
        double latitudeOrLongitudeCopy = latitudeOrLongitude;
        boolean isNegative = (latitudeOrLongitudeCopy < 0.0);
        latitudeOrLongitudeCopy = Math.abs(latitudeOrLongitudeCopy);
        int degrees = (int) Math.floor(latitudeOrLongitudeCopy);
        latitudeOrLongitudeCopy -= degrees;
        latitudeOrLongitudeCopy *= 60;
        int minutes = (int) Math.floor(latitudeOrLongitudeCopy);
        latitudeOrLongitudeCopy -= minutes;
        latitudeOrLongitudeCopy *= 60;
        float seconds = (float) latitudeOrLongitudeCopy;
        int secondsAsInt = (int) ((seconds * 10000.0d) / 60.0d);
        int returnValue = (int) (degrees * 1E6 + minutes * 1E4 + secondsAsInt);
        if (isNegative) {
            returnValue = returnValue | 1 << 31;
        }
        return String.format("%08x", returnValue);
    }

    String encodeFrame(FrameExtra frameExtra) {
        StringBuilder encodedData = new StringBuilder();

        // Latitude
        String sLatitude = encodeLatitudeOrLongitude(frameExtra.latitude);
        encodedData.append(sLatitude);

        // Longitude
        String sLongitude = encodeLatitudeOrLongitude(frameExtra.longitude);
        encodedData.append(sLongitude);

        long last4Bytes = 0;
        // AA : HDOP encodé (0 : < 1.0, 1 : < 2.0, 2 : < 5.0 ou 3 : > 5.0)
        last4Bytes |= frameExtra.hdop;

        // BB : Nombre de Satellites (0 : 0-3, 1 : 4-5, 2 : 6-7, 3 : 8+)
        last4Bytes <<= 2;
        last4Bytes |= frameExtra.satelliteCount;

        // C : Flag Jour(1)/Nuit(0)
        last4Bytes <<= 1;
        last4Bytes |= frameExtra.isDay ? 1 : 0;

        // DDD : Compteur de trames
        last4Bytes <<= 3;
        last4Bytes |= frameExtra.frameCount;

        // EEEE : Durée d'acquisition GPS entre 0 et 75s ou +, pas de 5s
        last4Bytes <<= 4;
        last4Bytes |= (int) (frameExtra.gpsTimeToFix / 5.0d);

        // FFFFF : Vitesse en km/h entre 0 et 155, pas de 5km/h
        last4Bytes <<= 5;
        last4Bytes |= (int) (frameExtra.speed / 5.0d);

        // GGGGG : Cap / Azimuth entre 0° et 360°, pas de 11,25°
        last4Bytes <<= 5;
        last4Bytes |= (int) (frameExtra.azimuth / 11.25d);

        // HHHHH : Tension panneau entre 0 et 3.1V, pas de 100mV
        last4Bytes <<= 5;
        last4Bytes |= (int) (frameExtra.solarArrayVoltage / 0.1d);

        // IIIII : Tension supercapacité entre 0 et 2.91V, pas de 90mV
        last4Bytes <<= 5;
        last4Bytes |= (int) (frameExtra.superCapacitorVoltage / 0.9d);

        encodedData.append(String.format("%08x", last4Bytes));

        return encodedData.toString()
    }
}
