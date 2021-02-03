package com.devsuperior.client.service;

import com.devsuperior.client.dto.ClientDTO;
import com.devsuperior.client.entities.Client;
import com.devsuperior.client.repositories.ClientRepository;
import com.devsuperior.client.service.exception.DatabaseException;
import com.devsuperior.client.service.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
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

    @Transactional(readOnly = true)
    public ClientDTO findById(Long id) {

        Optional<Client> client = clientRepository.findById(id);

        return  new ClientDTO(client.orElseThrow(()-> new ResourceNotFoundException("Cliente não localizado")));
    }

    @Transactional
    public ClientDTO insert(ClientDTO dto) {

        Client entity = new Client();

        entity.setName(dto.getName());
        entity.setCpf(dto.getCpf());
        entity.setIncome(dto.getIncome());
        entity.setBirthDate(dto.getBirthDate());
        entity.setChildren(dto.getChildren());

        entity = clientRepository.save(entity);

        return new ClientDTO(entity);
    }

    @Transactional
    public ClientDTO update(Long id, ClientDTO dto) {
        try {
        Client entity = clientRepository.getOne(id);

        entity.setName(dto.getName());
        entity.setCpf(dto.getCpf());
        entity.setIncome(dto.getIncome());
        entity.setBirthDate(dto.getBirthDate());
        entity.setChildren(dto.getChildren());
        entity = clientRepository.save(entity);

        return new ClientDTO(entity);

        }catch (EntityNotFoundException e){
            throw  new ResourceNotFoundException("Cliente não encontrado Id: "+id);
        }
    }

    public void delete(Long id) {

        try {
            clientRepository.deleteById(id);
        }catch (EmptyResultDataAccessException e){
            throw  new ResourceNotFoundException("Cliente não encontrado Id: "+id);
        }catch (DataIntegrityViolationException e){
            throw new DatabaseException("Violação de integridade");
        }
    }

}
