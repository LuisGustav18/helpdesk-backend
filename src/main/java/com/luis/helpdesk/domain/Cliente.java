package com.luis.helpdesk.domain;

import java.util.ArrayList;
import java.util.List;

public class Cliente extends Pessoa{

    private List<Chamado> chamados = new ArrayList<>();

    public Cliente(int id, String nome, String cpf, String email, String senha, List<Chamado> chamados) {
        super(id, nome, cpf, email, senha);
    }

    public List<Chamado> getChamados() {
        return chamados;
    }

    public void setChamados(List<Chamado> chamados) {
        this.chamados = chamados;
    }
}
