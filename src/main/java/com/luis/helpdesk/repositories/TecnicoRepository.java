package com.luis.helpdesk.repositories;

import com.luis.helpdesk.domain.Tecnico;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TecnicoRepository extends JpaRepository<Tecnico, Integer> {

}
