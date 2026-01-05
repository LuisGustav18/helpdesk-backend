package com.luis.helpdesk.services;

import com.luis.helpdesk.domain.Chamado;
import com.luis.helpdesk.domain.Cliente;
import com.luis.helpdesk.domain.Tecnico;
import com.luis.helpdesk.domain.enums.Perfil;
import com.luis.helpdesk.domain.enums.Prioridade;
import com.luis.helpdesk.domain.enums.Status;
import com.luis.helpdesk.repositories.ChamadoRepository;
import com.luis.helpdesk.repositories.ClienteRepository;
import com.luis.helpdesk.repositories.TecnicoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Service
public class DBService {

    @Autowired
    private TecnicoRepository tecnicoRepository;

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private ChamadoRepository chamadoRepository;

    public void instanciaDB(){
        Tecnico tec1 = new Tecnico(null, "Valdir Cezar", "63653230269", "valdir@mail.com", "123");
        tec1.addPerfil(Perfil.ADMIN);

        Cliente cli1 = new Cliente(null, "Linus Torvalds", "80527954780", "torvalds@mail.com", "123");

        Chamado c1 = new Chamado(null, Prioridade.MEDIA, Status.ANDAMENTO, "Chamado 01", "Primeiro Chamado", tec1, cli1);


        tecnicoRepository.saveAll(Arrays.asList(tec1));
        clienteRepository.saveAll(Arrays.asList(cli1));
        chamadoRepository.saveAll(Arrays.asList(c1));
    }
}