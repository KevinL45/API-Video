package com.prouvetech.prouvetech.controller;

import java.io.IOException;
import java.util.List;

import org.apache.tika.Tika;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.prouvetech.prouvetech.dto.ToolDTO;
import com.prouvetech.prouvetech.model.Tool;
import com.prouvetech.prouvetech.service.ToolService;
import com.prouvetech.prouvetech.utils.ResponseMessage;
import com.prouvetech.prouvetech.utils.Uploads;

@RestController
@RequestMapping("/tool")
public class ToolController {

    public ToolController() {

    }

    @Autowired
    private ToolService toolService;

    @Autowired
    private Uploads uploads;

    @PostMapping("/create")
    public ResponseEntity<ResponseMessage> addTool(@ModelAttribute ToolDTO toolDTO) throws IOException {

        if (toolService.toolExistsByName(toolDTO.getName())) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    // .body("Le nom existe déjà, choisissez un autre nom.");
                    .body(new ResponseMessage(false, "Le nom existe déja."));
        }

        Tika tika = new Tika();

        String detectedType = tika.detect(toolDTO.getFileLogo().getInputStream());

        if (!detectedType.startsWith("image/")) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    // .body("Le fichier doit être une image");
                    .body(new ResponseMessage(false, "Le fichier doit être une image."));

        }

        String logoUrl = uploads.saveFile(toolDTO.getFileLogo());
        Tool tool = new Tool(toolDTO.getName(), logoUrl);

        ResponseMessage result = toolService.createTool(tool);

        if (!result.isSuccess()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(result);
        }

        return ResponseEntity.status(HttpStatus.CREATED).body(result);

    }

    @PutMapping("/update/{id}")
    public ResponseEntity<String> updateTool(
            @PathVariable Long id,
            @ModelAttribute ToolDTO toolDTO) throws IOException {

        if (toolService.toolExistsByName(toolDTO.getName())) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Le nom existe déjà, choisissez un autre nom.");
        }

        Tika tika = new Tika();

        String detectedType = tika.detect(toolDTO.getFileLogo().getInputStream());

        if (!detectedType.startsWith("image/")) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Le fichier doit être une image");
        }

        Tool tool = this.toolService.getTool(id);
        String logoUrl = uploads.updateFile(toolDTO.getFileLogo(), tool.getLogo());

        tool.setName(toolDTO.getName());
        tool.setLogo(logoUrl);

        ResponseMessage result = this.toolService.updateTool(id, tool);

        if (!result.isSuccess()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(result.getMessage());
        }

        return ResponseEntity.status(HttpStatus.CREATED).body(result.getMessage());
    }

    @GetMapping("/list")
    public ResponseEntity<List<ToolDTO>> listTools() {
        List<Tool> tools = toolService.getAllTools();
        List<ToolDTO> toolDTOs = tools.stream().map(t -> new ToolDTO(
                t.getId(),
                t.getName(),
                t.getLogo())).toList();
        return ResponseEntity.ok(toolDTOs);
    }

    @GetMapping("/details/{id}")
    public ResponseEntity<ToolDTO> getTool(@PathVariable Long id) {
        Tool tool = toolService.getTool(id);

        ToolDTO toolDTO = new ToolDTO(
                tool.getId(),
                tool.getName(),
                tool.getLogo());

        return ResponseEntity.ok(toolDTO);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseMessage deleteTool(@PathVariable Long id) {
        Tool tool = toolService.getTool(id);
        uploads.deleteFile(tool.getLogo());
        return toolService.deleteTool(id);
    }

}
