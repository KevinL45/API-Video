package com.prouvetech.prouvetech.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.prouvetech.prouvetech.model.Status;
import com.prouvetech.prouvetech.repository.StatusRepository;
import com.prouvetech.prouvetech.utils.ResponseMessage;

@Service
public class StatusService {

    @Autowired
    private StatusRepository statusRepository;

    public Status getStatus(Long id) {
        return statusRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Aucun statut trouvé avec l'ID : " + id));
    }

    public List<Status> getAllStatus() {
        return statusRepository.findAll();
    }

    public ResponseMessage createStatus(Status status) {
        if (statusRepository.findByName(status.getName()) != null) {
            return new ResponseMessage(false, "Nom déjà utilisé.");
        }
        statusRepository.save(status);
        return new ResponseMessage(true, status.getName() + " ajouté avec succès.");
    }

    public ResponseMessage updateStatus(Long id, Status status) {
        Status statusExist = getStatus(id);

        if (statusRepository.findByName(status.getName()) != null &&
                !statusExist.getName().equalsIgnoreCase(status.getName())) {
            return new ResponseMessage(false, "Nom déjà utilisé.");
        }

        statusExist.setName(status.getName());
        statusRepository.save(statusExist);
        return new ResponseMessage(true, status.getName() + " ajouté avec succès.");
    }

    public ResponseMessage deleteStatus(Long id) {
        if (!statusRepository.existsById(id)) {
            return new ResponseMessage(false, "Aucun statut trouvé avec l'ID : " + id);
        }
        statusRepository.deleteById(id);
        return new ResponseMessage(true, "Le statut a été supprimé avec succès.");
    }
}
