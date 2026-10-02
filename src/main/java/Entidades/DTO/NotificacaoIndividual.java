package Entidades.DTO;

import java.time.LocalDate;

public record NotificacaoIndividual(

        String nomePaciente,
        LocalDate dataNascimento,
        String idade,
        String sexo,
        String gestante,
        String racaCor,
        String escolaridade,
        String cartaoSus,
        String nomeMae


) {
}
