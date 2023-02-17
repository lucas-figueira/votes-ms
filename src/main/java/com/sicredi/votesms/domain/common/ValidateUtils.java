package com.sicredi.votesms.domain.common;

import java.util.Objects;


public class ValidateUtils {

    private ValidateUtils(){

    }

    private static final int[] pesoCPF = { 11, 10, 9, 8, 7, 6, 5, 4, 3, 2 };

    public static boolean isValidCpf(String cpf) {

        if (Objects.isNull(cpf))
            return false;

        String cpfAjustado = cpf.replace(".", "").replace("-", "");

        if (cpfAjustado.length() != 11)
            return false;

        Integer digito1 = calcularDigito(cpfAjustado.substring(0, 9), pesoCPF);
        Integer digito2 = calcularDigito(cpfAjustado.substring(0, 9) + digito1, pesoCPF);
        return cpfAjustado.equals(cpfAjustado.substring(0, 9) + digito1.toString() + digito2.toString());
    }

    private static int calcularDigito(String str, int[] peso) {
        int soma = 0;
        for (int indice = str.length() - 1, digito; indice >= 0; indice--) {
            digito = Integer.parseInt(str.substring(indice, indice + 1));
            soma += digito * peso[peso.length - str.length() + indice];
        }
        soma = 11 - soma % 11;
        return soma > 9 ? 0 : soma;
    }

    public static String removeSpecialCharacter(String cpf){
        return cpf.replace(".", "").replace("-", "");
    }
}
