package br.unipar.programacaoweb.estacaocemtempobrow.model;

public enum statusEstacao {
    ATIVA,
    INATIVA,
    MANUTENCAO;

    public static statusEstacao fromString(String text) {
        for (statusEstacao status : statusEstacao.values()) {
            if (status.name().equalsIgnoreCase(text)) {
                return status;
            }
        }
        throw new IllegalArgumentException("Status inválido: " + text);
    }
}

