package com.example.lsfr_gui;

import java.security.Key;
import java.util.Arrays;

public class CyberSecurity {
    int m;
    String initialValues;
    boolean[] polynomial;
    String message;
    String KeyStream;

    public CyberSecurity(String message, int m, boolean[] polynomial, String initialValues) {
        this.message = message;
        this.m = m;
        this.polynomial = polynomial;
        this.initialValues = initialValues;

        //System.out.println("Message: " + message);
        //System.out.println("M: " + m);
        //System.out.println("Polynomials: " + Arrays.toString(polynomial));
        //System.out.println("FFs Vector: " + initialValues);
    }


    protected String encryption() {
        StringBuilder cipherText = new StringBuilder();
        StringBuilder keyStream = new StringBuilder();
        boolean[] flops = new boolean[m];
        for (int i = 0; i < m; i++)
            flops[i] = initialValues.charAt(i) == '1';
        for(int i=0; i<message.length(); i++){
            for(int bit=0; bit<8; bit++){
                /// Getting Feed back by the polynomial function (which xor are in)
                boolean feedback = flops[0];
                for (int j = 0; j < polynomial.length; j++)
                    if (polynomial[j])
                        feedback ^= flops[j];
                /// Shifting our flops one bit next
                for (int j = 0; j < flops.length - 1; j++)
                    flops[j] = flops[j + 1];
                /// Set the feedback (last shift to first)
                flops[flops.length - 1] = feedback;
                /// KeyStream
                keyStream.append(flops[m-1]?1:0);
            }
            /// Encryption
            cipherText.append((char)(message.charAt(i) ^ (flops[m-1]?1:0)));
        }

        KeyStream = keyStream.toString();
        message = cipherText.toString();
        return message;
    }

    protected String decryption() {
        return encryption();
    }
    protected void printTable() {
        boolean[] flops = new boolean[m];
        for (int i = 0; i < m; i++)
            flops[i] = initialValues.charAt(i) == '1';
        //System.out.println("LSFR Table:");
        for (int k = 0; k < 3; k++) {
            for (int i = 0; i < (1 << m) - 1; i++) {
                for (boolean b : flops)
                    System.out.print(b ? "1\t" : "0\t");
                System.out.println();
                boolean feedback = flops[0];
                for (int j = 0; j < polynomial.length; j++)
                    if (polynomial[j])
                        feedback ^= flops[j];
                for (int j = 0; j < flops.length - 1; j++) {
                    flops[j] = flops[j + 1];
                }
                flops[flops.length - 1] = feedback;
            }
            System.out.println("-");
        }
    }
    protected String Table(){
        String XX = "";
        boolean[] flops = new boolean[m];
        for (int i = 0; i < m; i++)
            flops[i] = initialValues.charAt(i) == '1';
        //System.out.println("LSFR Table:");
        for (int k = 0; k < 3; k++) {
            for (int i = 0; i < (1 << m) - 1; i++) {
                for (boolean b : flops)
                    XX += (b? "1\t" : "0\t");
                XX += "\n";
                boolean feedback = flops[0];
                for (int j = 0; j < polynomial.length; j++)
                    if (polynomial[j])
                        feedback ^= flops[j];
                for (int j = 0; j < flops.length - 1; j++) {
                    flops[j] = flops[j + 1];
                }
                flops[flops.length - 1] = feedback;
            }
            XX += "----------------->\n";
        }
        return XX;
    }
}
