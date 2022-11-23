import java.lang.StringBuilder;

public class IEEE754 {
    private String bits;
    private float number;

    // constructors
    public IEEE754(String bits) {
        this.bits = bits;
    }

    public IEEE754(float number) {
        this.number = number;
    }

    // methods
    public float getDecimalValue() {
        float result;
        // signbit
        if (bits.charAt(0) == '1') {
            result = -1;
        } else {
            result = 1;
        }
        result *= (1 + convertDecimal(Double.parseDouble(bits.substring(9))) / ( Math.pow(2, 23)));
        result *= (Math.pow(2, convertDecimal(Float.parseFloat(bits.substring(1, 9))) - 127));
        return result;
    }

    public String getIEEE754Value() {
        int exponent = 0;
        String result = "";
        float normalized;

        // bit sign
        if (number >= 0) {
            result += "0";
        } else {
            result += "1";
        }

        // exponent
        number = Math.abs(number);
        if (number > 1) {
            while (number >= 2) {
                number /= 2;
                exponent++;
            }
            result += convertExponent(exponent + 127);
        } else {
            while (number < 1) {
                number *= 2;
                exponent++;
            }
            result += convertExponent(127 - exponent);
        }

        // mantissa
        normalized = number - 1;
        for (int i = 0; i < 23; i++) {
            normalized *= 2;
            if (normalized >= 1) {
                result += "1";
                normalized--;
            } else {
                result += "0";
            }
        }
        return result;
    }

    public float convertDecimal(double binary) {
        float result = 0;
        int exponent = 0;
        while (binary > 0) {
            result += binary % 10 * Math.pow(2, exponent);
            binary = Math.floor(binary / 10);
            exponent++;
        }
        return result;
    }

    public String convertExponent(int biasExponent) {
        String result = "";
        int exponent = biasExponent;
        for (int i = 0; i < 8; i++) {
            result += (exponent % 2);
            exponent /= 2;
        }

        // revserse string
        StringBuilder reverse = new StringBuilder();
        reverse.append(result);
        reverse.reverse();
        result = reverse.toString();
        return result;

    }
}
