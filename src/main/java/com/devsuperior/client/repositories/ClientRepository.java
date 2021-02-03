package com.devsuperior.client.repositories;

import com.devsuperior.client.entities.Client;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientRepository  extends JpaRepository<Client,Long> {
}
