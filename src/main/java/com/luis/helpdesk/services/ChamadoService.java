package com.luis.helpdesk.services;

import com.luis.helpdesk.domain.Chamado;
import com.luis.helpdesk.repositories.ChamadoRepository;
import com.luis.helpdesk.services.exceptions.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ChamadoService {

    @Autowired
    private ChamadoRepository repository;

    public Chamado findById(Integer id){
        Optional<Chamado> obj = repository.findById(id);
        return obj.orElseThrow(() -> new ObjectNotFoundException("Objeto não encontrado! ID: " + id));
    }

    public List<Chamado> findAll(){
        return repository.findAll();
    }
}
