package com.shin.whatshoes.controller;


import com.shin.whatshoes.model.What;
import com.shin.whatshoes.repository.WhatRepository;
import com.shin.whatshoes.service.S3Service;
import com.shin.whatshoes.service.WhatService;
import com.shin.whatshoes.service.validator.WhatValidator;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.IOException;

@Slf4j
@RequiredArgsConstructor
@Controller
@RequestMapping("/what")
public class WhatController {

    private final WhatValidator whatValidator;
    private final WhatService whatService;
    private final S3Service s3Service;
    private final WhatRepository whatRepository;

    @GetMapping("/select")
    public String showSelect(What what) {
        return "what/select";
    }

    @PostMapping("/select")
    public String postSelect(@Valid What what, @RequestParam("whatFile") MultipartFile multipartFile, HttpServletRequest req, BindingResult bindingResult) throws IOException {
        if (multipartFile.isEmpty()) {
            whatValidator.validate(what, bindingResult);
            return "what/select";
        } else if (!multipartFile.getContentType().startsWith("image")) {
            whatValidator.validate(what, bindingResult);
            return "what/select";
            }
        String ip = req.getHeader("X-Forwarded-For");
        if (ip == null) {
            ip = req.getRemoteAddr();
        }
        what.setWhatIp(ip);
        what.setWhatResult("unknown");
        what.setWhatFileName(multipartFile.getOriginalFilename());
        whatService.save(what);
        Long dir = what.getWhatId();
        log.info("whatId = " + dir);
        s3Service.upload(multipartFile,"whatShoes/what/" + dir);

        return "redirect:/what/result?whatId=" + dir;
    }

    @GetMapping("/result")
    public String showResult (Model model, @RequestParam Long whatId) {
        What what = whatRepository.findById(whatId).orElseThrow(null);
        model.addAttribute("what", what);

        return "what/result";
    }

//    @PostMapping("/upload")
//    @ResponseBody
//    public String upload(@RequestParam("data") MultipartFile multipartFile) throws IOException {
//        return s3Service.upload(multipartFile, "whatShoes/what");
//    }

}
