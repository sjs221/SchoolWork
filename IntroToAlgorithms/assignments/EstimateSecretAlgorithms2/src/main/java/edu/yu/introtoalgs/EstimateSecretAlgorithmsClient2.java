package edu.yu.introtoalgs;

public class EstimateSecretAlgorithmsClient2 {

    public static void main(String[] args){
        timeTrailAlg1();
        timeTrailAlg2();
        timeTrailAlg3();
        timeTrailAlg4();
    }

    public static void timeTrailAlg1(){
        SecretAlgorithm1 secretAlgorithm1 = new SecretAlgorithm1();
        System.out.println("Testing SecretAlgorithm1 (time recorded as milliseconds):");
        for (int n = 250; n <= 32000; n *= 2){
            secretAlgorithm1.setup(n);
            double start = System.currentTimeMillis();
            secretAlgorithm1.execute();
            double timeElapsed = System.currentTimeMillis() - start;
            System.out.printf("%10d %10.1f %10f %10.1f\n", n, timeElapsed, calculateLogBase2(n),calculateLogBase2(timeElapsed));
        }
        System.out.println();
    }

    public static void timeTrailAlg2(){
        SecretAlgorithm2 secretAlgorithm2 = new SecretAlgorithm2();
        System.out.println("Testing SecretAlgorithm2 (time recorded as nanoseconds):");
        for (int n = 250; n <= 524288000; n *= 2){
            secretAlgorithm2.setup(n);
            double start = System.nanoTime();
            secretAlgorithm2.execute();
            double timeElapsed = System.nanoTime() - start;
            System.out.printf("%10d %10.1f %10f %10.1f\n", n, timeElapsed, calculateLogBase2(n),calculateLogBase2(timeElapsed));
        }
        System.out.println();
    }

    public static void timeTrailAlg3(){
        SecretAlgorithm3 secretAlgorithm3 = new SecretAlgorithm3();
        System.out.println("Testing SecretAlgorithm3 (time recorded as milliseconds):");
        for (int n = 250; n <= 1024000; n *= 2){
            secretAlgorithm3.setup(n);
            double start = System.currentTimeMillis();
            secretAlgorithm3.execute();
            double timeElapsed = System.currentTimeMillis() - start;
            System.out.printf("%10d %10.1f %10f %10.1f\n", n, timeElapsed, calculateLogBase2(n),calculateLogBase2(timeElapsed));
        }
        System.out.println();

    }

    public static void timeTrailAlg4(){
        SecretAlgorithm4 secretAlgorithm4 = new SecretAlgorithm4();
        System.out.println("Testing SecretAlgorithm4 (time recorded as nanoseconds):");
        for (int n = 250; n <= 524288000; n *= 2){
            secretAlgorithm4.setup(n);
            double start = System.nanoTime();
            secretAlgorithm4.execute();
            double timeElapsed = System.nanoTime() - start;
            System.out.printf("%10d %10.1f %10f %10.1f\n", n, timeElapsed, calculateLogBase2(n),calculateLogBase2(timeElapsed));
        }
        System.out.println();
    }

    private static double calculateLogBase2(double n){
        return (Math.log(n)/Math.log(2));
    }

}
