public class IEEE754 {

    private String bits;
    private float number;

    public IEEE754(String bits) {
        this.bits = bits;
    }

    public IEEE754(float number) {
        this.number = number;
    }

    public float getDecimalValue() {
        float signDecimal = (float) Math.pow(-1, Float.parseFloat(bits.substring(0, 1)));
        String biasedBinary = bits.substring(1, 9);
        String mantissaBinary = bits.substring(9, 32);
        float biasedDecimal = Float.parseFloat(biasedBinary);
        double mantissaDecimal = Double.parseDouble(mantissaBinary);

        float answerBiasedDecimal = 0;
        int counter = 0;
        long remainder = 0;
        while (biasedDecimal != 0) {
            remainder = (long) (biasedDecimal % 10);
            biasedDecimal /= 10;
            answerBiasedDecimal += remainder * Math.pow(2, counter);
            ++counter;
        }
        int answer = (int) (answerBiasedDecimal - 127);

        float answerMantissaDecimal = 0;
        int counter2 = 0;
        long remainder2 = 0;
        while (mantissaDecimal != 0) {
            remainder2 = (long) (mantissaDecimal % 10);
            mantissaDecimal /= 10;
            answerMantissaDecimal += remainder2 * Math.pow(2, counter2);
            counter2++;
            if (counter2 == 23) {
                break;
            }
        }
        float DecimalValue = (float) ((signDecimal) * (Math.pow(2, answer))
                * ((answerMantissaDecimal / Math.pow(2, 23)) + 1));
        System.out.println("The Decimal Value of this binary number is " + (float) (DecimalValue));
        return 0;
    }

    public String getIEEE754Value() {
        int exponentCounter = 0;
        int counter = 0;
        String sign = "";
        String mantissaList = "";
        String biasedExponentBinary = "";

        if (number >= 0) {
            sign = "0";
        } else if (number < 0) {
            sign = "1";
        }

        float absolute = (float) Math.abs(number);
        if (absolute < 1) {
            while (absolute <= 1) {
                absolute *= 2;
                exponentCounter -= 1;
            }
        } else if (absolute > 1) {
            while (absolute >= 2) {
                exponentCounter++;
                absolute /= 2;
            }
        }
        if (exponentCounter <= 0) {
            biasedExponentBinary = "0" + Integer.toBinaryString(127 + exponentCounter);
        } else {
            biasedExponentBinary += Integer.toBinaryString(127 + exponentCounter);
        }

        absolute %= 1;
        while (counter < 23) {
            counter++;
            absolute *= 2;
            if (absolute >= 1) {
                mantissaList += "1";
                absolute %= 1;
            } else if (absolute < 1) {
                mantissaList += "0";
            }
        }
        System.out.println("The Binary value of this number is " + sign + biasedExponentBinary + mantissaList);
        return " ";
    }

    public static void main(String[] args) {
        IEEE754 test = new IEEE754(2f);
        test.getIEEE754Value();
        IEEE754 test2 = new IEEE754("10111111100000000000000000000000");
        test2.getDecimalValue();
    }
}
