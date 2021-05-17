package com.cc.web.format;

import com.cc.web.entity.Format;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@CrossOrigin(origins = {"http://localhost:4200", "https://azoriussergh4.github.io"})
@RestController
@RequestMapping("/api/format")
public class FormatController {

    private final FormatService formatService;

    @Autowired
    public FormatController(FormatService formatService) {
        this.formatService = formatService;
    }

    @GetMapping("/all")
    public List<Format> getAllFormats() {
        return formatService.findAllFormats();
    }
}
