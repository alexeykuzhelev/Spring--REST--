package ru.bitmaster.paymentserver.service;

import com.querydsl.core.types.dsl.BooleanExpression;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import ru.bitmaster.paymentserver.critery.ClientCritery;
import ru.bitmaster.paymentserver.entity.Client;
import ru.bitmaster.paymentserver.entity.QClient;
import ru.bitmaster.paymentserver.repository.ClientRepository;

import javax.transaction.Transactional;
import java.util.Arrays;
import java.util.List;

@Service("clientService")
@Transactional
public class ClientServiceImpl extends ACrudService<Client, Long>
        implements ClientService {

    @Autowired(required=false)
    ClientRepository clientRepository;

    @Override
    public ClientRepository getRepository() {
        return clientRepository;
    }

    @Override
    public Client findByName(String name) {
        ClientCritery critery = new ClientCritery(null,name);
        List<Client> clients = findByCritery(critery);
        return clients.size()>0? clients.get(0):null;
    }

    @Override
    public List<Client> findByIdIn(Long[] ids) {
        ClientCritery critery = new ClientCritery(Arrays.asList(ids),"");
        return findByCritery(critery);
    }

    @Override
    public List<Client> findByCritery(ClientCritery critery) {
        QClient qClient = QClient.client;
        int pageSize = 5;
        int pageNum = critery.getPageNum();
        Sort sort = Sort.by("name");
        Pageable pageable = PageRequest.of(pageNum - 1, pageSize, sort);
        BooleanExpression expression = getBooleanExpression(
                critery, qClient, ClientCritery.class, QClient.class);
        Page<Client> page = clientRepository.findAll(expression, pageable);
        List<Client> listClients = page.getContent();
        return listClients;
    }

}
