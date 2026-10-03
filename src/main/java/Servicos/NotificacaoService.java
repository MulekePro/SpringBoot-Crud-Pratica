package Servicos;

import Entidades.DTO.NotificacaoRequestDTO;
import Entidades.normal.*;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

@Service
public class NotificacaoService {


    private final List<Notificacao> repositorioEmMemoria = new ArrayList<>();
    private Long proximoId = 1L;


    public Notificacao criar(NotificacaoRequestDTO request) {
        validarRegrasNegocio(request);

        Notificacao notificacao = new Notificacao();
        notificacao.setId(proximoId++);


        mapearDtoParaModelo(request, notificacao);

        repositorioEmMemoria.add(notificacao);
        return notificacao;
    }


    public List<Notificacao> listar(String agravo, String nomePaciente, LocalDate dataDe, LocalDate dataAte, Boolean duplicadas) {
        if (dataDe != null && dataAte != null && dataDe.isAfter(dataAte)) {
            throw new IllegalArgumentException("A data inicial não pode ser posterior à data final.");
        }

        boolean somenteDuplicadas = Boolean.TRUE.equals(duplicadas);
        Set<Long> idsDuplicados = somenteDuplicadas ? buscarIdsDuplicados() : Set.of();

        List<Notificacao> resultado = new ArrayList<>();
        for (Notificacao n : repositorioEmMemoria) {
            if (somenteDuplicadas && !idsDuplicados.contains(n.getId())) {
                continue;
            }
            if (!contem(n.getDadosGerais().getAgravo(), agravo)) {
                continue;
            }
            if (!contem(n.getNotificacaoIndividual().getNomePaciente(), nomePaciente)) {
                continue;
            }
            LocalDate dataNotificacao = n.getDadosGerais().getDataNotificacao();
            if (dataDe != null && (dataNotificacao == null || dataNotificacao.isBefore(dataDe))) {
                continue;
            }
            if (dataAte != null && (dataNotificacao == null || dataNotificacao.isAfter(dataAte))) {
                continue;
            }
            resultado.add(n);
        }
        return resultado;
    }


    public Notificacao buscarPorId(Long id) {
        return repositorioEmMemoria.stream()
                .filter(n -> n.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Notificação com ID " + id + " não encontrada."));
    }


    public Notificacao atualizar(Long id, NotificacaoRequestDTO request) {
        validarRegrasNegocio(request);

        Notificacao notificacaoExistente = buscarPorId(id);
        mapearDtoParaModelo(request, notificacaoExistente);

        return notificacaoExistente;
    }


    public void deletar(Long id) {
        Notificacao notificacao = buscarPorId(id);
        repositorioEmMemoria.remove(notificacao);
    }


    private void validarRegrasNegocio(NotificacaoRequestDTO request) {
        Entidades.DTO.NotificacaoIndividual ind = request.notificacaoIndividual();
        Entidades.DTO.DadosDeResidencia res = request.dadosDeResidencia();


        if (ind.dataNascimento() == null && (ind.idade() == null || ind.idade().isBlank())) {
            throw new IllegalArgumentException("O campo idade é de preenchimento obrigatório quando a data de nascimento não é informada.");
        }


        if ("F".equalsIgnoreCase(ind.sexo()) && (ind.gestante() == null || ind.gestante().isBlank())) {
            throw new IllegalArgumentException("O campo gestante é de preenchimento obrigatório quando o sexo do paciente é feminino.");
        }


        boolean residenteNoExterior = res.pais() != null && !res.pais().isBlank() && !res.pais().equalsIgnoreCase("Brasil");

        if (!residenteNoExterior && (res.uf() == null || res.uf().isBlank())) {
            throw new IllegalArgumentException("A UF de residência é de preenchimento obrigatório quando o paciente reside no Brasil. Se reside em outro país, informe o país de residência.");
        }
        if (res.uf() != null && !res.uf().isBlank() && (res.municipio() == null || res.municipio().isBlank())) {
            throw new IllegalArgumentException("O município de residência é de preenchimento obrigatório quando a UF é informada.");
        }
    }


    private Set<Long> buscarIdsDuplicados() {
        Map<String, List<Notificacao>> grupos = new HashMap<>();

        for (Notificacao n : repositorioEmMemoria) {
            String agravo = normalizar(n.getDadosGerais().getAgravo());
            String paciente = normalizar(n.getNotificacaoIndividual().getNomePaciente());
            String mae = normalizar(n.getNotificacaoIndividual().getNomeMae());
            LocalDate nascimento = n.getNotificacaoIndividual().getDataNascimento();
            LocalDate dataNotificacao = n.getDadosGerais().getDataNotificacao();

            if (agravo == null || paciente == null || mae == null || nascimento == null || dataNotificacao == null) {
                continue;
            }

            String chave = agravo + "\n" + paciente + "\n" + nascimento + "\n" + mae;
            grupos.computeIfAbsent(chave, k -> new ArrayList<>()).add(n);
        }

        Set<Long> ids = new HashSet<>();
        for (List<Notificacao> grupo : grupos.values()) {
            for (int i = 0; i < grupo.size(); i++) {
                for (int j = i + 1; j < grupo.size(); j++) {
                    Notificacao a = grupo.get(i);
                    Notificacao b = grupo.get(j);
                    long dias = Math.abs(ChronoUnit.DAYS.between(
                            a.getDadosGerais().getDataNotificacao(), b.getDadosGerais().getDataNotificacao()));
                    if (dias <= 3) {
                        ids.add(a.getId());
                        ids.add(b.getId());
                    }
                }
            }
        }
        return ids;
    }


    private String normalizar(String texto) {
        if (texto == null) {
            return null;
        }
        String resultado = texto.trim().replaceAll("\\s+", " ").toLowerCase(Locale.ROOT);
        return resultado.isEmpty() ? null : resultado;
    }


    private boolean contem(String valor, String filtro) {
        String filtroNormalizado = normalizar(filtro);
        if (filtroNormalizado == null) {
            return true;
        }
        String valorNormalizado = normalizar(valor);
        return valorNormalizado != null && valorNormalizado.contains(filtroNormalizado);
    }


    private void mapearDtoParaModelo(NotificacaoRequestDTO request, Notificacao notificacao) {

        Entidades.DTO.DadosGerais dadosGeraisDTO = request.dadosGerais();
        Entidades.normal.DadosGerais dgModel = new Entidades.normal.DadosGerais();
        dgModel.setNumeroNotificacao(dadosGeraisDTO.numeroNotificacao());
        dgModel.setTipoNotificacao(dadosGeraisDTO.tipoNotificacao());
        dgModel.setAgravo(dadosGeraisDTO.agravo());
        dgModel.setCodigoCid10(dadosGeraisDTO.codigoCid10());
        dgModel.setDataNotificacao(dadosGeraisDTO.dataNotificacao());
        dgModel.setUfNotificacao(dadosGeraisDTO.ufNotificacao());
        dgModel.setMunicipioNotificacao(dadosGeraisDTO.municipioNotificacao());
        dgModel.setCodigoIbgeMunicipio(dadosGeraisDTO.codigoIbgeMunicipio());
        dgModel.setUnidadeSaude(dadosGeraisDTO.unidadeSaude());
        dgModel.setCodigoUnidadeSaude(dadosGeraisDTO.codigoUnidadeSaude());
        dgModel.setDataPrimeirosSintomas(dadosGeraisDTO.dataPrimeirosSintomas());
        notificacao.setDadosGerais(dgModel);


        Entidades.DTO.NotificacaoIndividual indDTO = request.notificacaoIndividual();
        Entidades.normal.NotificacaoIndividual indModel = new Entidades.normal.NotificacaoIndividual();
        indModel.setNomePaciente(indDTO.nomePaciente());
        indModel.setDataNascimento(indDTO.dataNascimento());
        indModel.setIdade(indDTO.idade());
        indModel.setSexo(indDTO.sexo());
        indModel.setGestante(indDTO.gestante());
        indModel.setRacaCor(indDTO.racaCor());
        indModel.setEscolaridade(indDTO.escolaridade());
        indModel.setCartaoSus(indDTO.cartaoSus());
        indModel.setNomeMae(indDTO.nomeMae());
        notificacao.setNotificacaoIndividual(indModel);


        Entidades.DTO.DadosDeResidencia resDTO = request.dadosDeResidencia();
        Entidades.normal.DadosDeResidencia resModel = new Entidades.normal.DadosDeResidencia();
        resModel.setUf(resDTO.uf());
        resModel.setMunicipio(resDTO.municipio());
        resModel.setDistrito(resDTO.distrito());
        resModel.setBairro(resDTO.bairro());
        resModel.setLogradouro(resDTO.logradouro());
        resModel.setNumero(resDTO.numero());
        resModel.setComplemento(resDTO.complemento());
        resModel.setGeoCampo1(resDTO.geoCampo1());
        resModel.setGeoCampo2(resDTO.geoCampo2());
        resModel.setPontoReferencia(resDTO.pontoReferencia());
        resModel.setCep(resDTO.cep());
        resModel.setTelefone(resDTO.telefone());
        resModel.setZona(resDTO.zona());
        resModel.setPais(resDTO.pais());
        notificacao.setDadosDeResidencia(resModel);


        Entidades.DTO.Conclusao concDTO = request.conclusao();
        Entidades.normal.Conclusao concModel = new Entidades.normal.Conclusao();
        concModel.setDataInvestigacao(concDTO.dataInvestigacao());
        concModel.setClassificacaoFinal(concDTO.classificacaoFinal());
        concModel.setCriterioConfirmacaoDescarte(concDTO.criterioConfirmacaoDescarte());
        concModel.setCasoAutoctoneResidencia(concDTO.casoAutoctoneResidencia());
        concModel.setUfInfeccao(concDTO.ufInfeccao());
        concModel.setPaisInfeccao(concDTO.paisInfeccao());
        concModel.setMunicipioInfeccao(concDTO.municipioInfeccao());
        concModel.setDistritoInfeccao(concDTO.distritoInfeccao());
        concModel.setBairroInfeccao(concDTO.bairroInfeccao());
        concModel.setDoencaRelacionadaTrabalho(concDTO.doencaRelacionadaTrabalho());
        concModel.setEvolucaoCaso(concDTO.evolucaoCaso());
        concModel.setDataObito(concDTO.dataObito());
        concModel.setDataEncerramento(concDTO.dataEncerramento());
        concModel.setObservacoesAdicionais(concDTO.observacoesAdicionais());
        concModel.setMunicipioUnidadeSaudeInvestigacao(concDTO.municipioUnidadeSaudeInvestigacao());
        concModel.setCodUnidadeSaudeInvestigacao(concDTO.codUnidadeSaudeInvestigacao());
        concModel.setNomeResponsavelInvestigacao(concDTO.nomeResponsavelInvestigacao());
        concModel.setFuncaoResponsavelInvestigacao(concDTO.funcaoResponsavelInvestigacao());
        notificacao.setConclusao(concModel);
    }
}