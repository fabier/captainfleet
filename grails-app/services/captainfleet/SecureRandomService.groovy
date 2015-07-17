package captainfleet

import java.security.SecureRandom

/**
 * Created by fabier on 06/05/15.
 */
class SecureRandomService {
    SecureRandom random = new SecureRandom()

    double generateLatitude() {
        double latitude = random.nextDouble() * 180.0 - 90.0
        assert latitude >= -90.0
        assert latitude <= 90.0
        return latitude
    }

    double generateLongitude() {
        double longitude = random.nextDouble() * 360.0 - 180.0
        assert longitude >= -180.0
        assert longitude <= 180.0
        return longitude
    }

    boolean generateBoolean() {
        return random.nextBoolean()
    }

    int generate2Bits() {
        return generateBits(2)
    }

    int generate3Bits() {
        return generateBits(3)
    }

    int generate4Bits() {
        return generateBits(4)
    }

    int generate5Bits() {
        return generateBits(5)
    }

    int generateBits(int bitCount) {
        assert bitCount >= 0
        assert bitCount <= 32

        if (bitCount == 0) {
            return 0
        } else if (bitCount == 32) {
            return random.nextInt()
        } else {
            // 0 < bitCount < 32
            int bitFilter = (1 << bitCount) - 1
            return random.nextInt() & bitFilter
        }
    }

    int generateInt(int value) {
        return random.nextInt(value)
    }

    float generateFloat(float value) {
        return random.nextFloat() * value
    }

    double generateDouble(double value) {
        return random.nextDouble() * value
    }
}
