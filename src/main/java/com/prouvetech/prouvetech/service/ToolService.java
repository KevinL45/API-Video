package com.prouvetech.prouvetech.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.prouvetech.prouvetech.model.Tool;
import com.prouvetech.prouvetech.repository.ToolRepository;
import com.prouvetech.prouvetech.utils.ResponseMessage;

@Service
public class ToolService {

    @Autowired
    private ToolRepository toolRepository;

    public boolean toolExistsByName(String name) {
        return toolRepository.findByName(name) != null;
    }

    public Tool getTool(Long id) {
        return toolRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Aucun outil trouvé avec l'ID : " + id));
    }

    public ResponseMessage createTool(Tool tool) {

        toolRepository.save(tool);
        return new ResponseMessage(true, tool.getName() + " est ajouté dans la liste");
    }

    public ResponseMessage updateTool(Long id, Tool tool) {

        toolRepository.save(tool);
        return new ResponseMessage(true, tool.getName() + " est modifié.");
    }

    public ResponseMessage deleteTool(Long id) {
        if (!toolRepository.existsById(id)) {
            return new ResponseMessage(false, "Aucun outil trouvé avec l'ID : " + id);
        }
        toolRepository.deleteById(id);
        return new ResponseMessage(true, "L'outil a été supprimé.");
    }

    public List<Tool> getAllTools() {
        return toolRepository.findAll();
    }

    public List<Tool> getToolsByIds(List<Long> ids) {
        return toolRepository.findAllById(ids);
    }

}
