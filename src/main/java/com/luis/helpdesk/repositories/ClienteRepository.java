package com.luis.helpdesk.repositories;

import com.luis.helpdesk.domain.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClienteRepository extends JpaRepository<Cliente, Integer> {

}
