package Entidades.DTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;

public record NotificacaoIndividual(
        @NotBlank(message = "O nome do paciente ")
        String nomePaciente,
        @NotNull(message = "A data de nascimento")
        LocalDate dataNascimento,
        String idade,
        @NotBlank(message = "O sexo é obrigatório")
        String sexo,
        String gestante,
        String racaCor,
        String escolaridade,
        String cartaoSus,
        String nomeMae
) {}