package com.devsuperior.client.service;

import com.devsuperior.client.dto.ClientDTO;
import com.devsuperior.client.entities.Client;
import com.devsuperior.client.repositories.ClientRepository;
import com.devsuperior.client.service.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class ClientService {

    @Autowired
    ClientRepository clientRepository;

    @Transactional(readOnly = true)
    public Page<ClientDTO> findAll(PageRequest pageRequest){

        Page<Client> list = clientRepository.findAll(pageRequest);

        return list.map(c -> new ClientDTO(c));
    }

    public ClientDTO findById(Long id) {

        Optional<Client> client = clientRepository.findById(id);

        return  new ClientDTO(client.orElseThrow(()-> new ResourceNotFoundException("Cliente não localizado")));
    }
}
