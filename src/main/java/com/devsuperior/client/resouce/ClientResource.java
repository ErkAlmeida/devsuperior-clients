package com.devsuperior.client.resouce;


import com.devsuperior.client.dto.ClientDTO;
import com.devsuperior.client.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/clients")
public class ClientResource {

    @Autowired
    private ClientService clientService;

    @GetMapping
    public ResponseEntity<Page<ClientDTO>> findAll(
            @RequestParam(value = "page", defaultValue = "0") Integer page,
            @RequestParam(value = "linesPerPage", defaultValue = "12") Integer linesPerPage,
            @RequestParam(value = "direction", defaultValue = "ASC") String direction,
            @RequestParam(value = "orderBy", defaultValue = "name") String orderBy){

       PageRequest pageRequest = PageRequest.of(page,linesPerPage, Sort.Direction.valueOf(direction), orderBy);

       Page<ClientDTO> list = clientService.findAll(pageRequest);

       return ResponseEntity.ok().body(list);
    }


    @GetMapping(value ="/{id}")
    public ResponseEntity<ClientDTO> findById(@PathVariable Long id){

        ClientDTO client = clientService.findById(id);

        return ResponseEntity.ok().body(client);
    }

    @PostMapping
    public ResponseEntity<ClientDTO> insert(@RequestBody ClientDTO dto){

        ClientDTO client = clientService.insert(dto);

        return ResponseEntity.ok().body(client);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<ClientDTO> update(
            @PathVariable Long id,
            @RequestBody ClientDTO dto){

        ClientDTO client = clientService.update(id,dto);

        return ResponseEntity.ok().body(client);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<ClientDTO> delete(@PathVariable Long id){

        clientService.delete(id);

        return ResponseEntity.noContent().build();
    }
}
