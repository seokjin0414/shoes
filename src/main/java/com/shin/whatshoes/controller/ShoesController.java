package com.shin.whatshoes.controller;



import com.shin.whatshoes.model.Resale;
import com.shin.whatshoes.model.Shoes;
import com.shin.whatshoes.model.Shoesfile;
import com.shin.whatshoes.repository.ShoesRepository;
import com.shin.whatshoes.repository.ShoesfileRepository;
import com.shin.whatshoes.service.S3Service;
import com.shin.whatshoes.service.SeleniumService;
import com.shin.whatshoes.service.ShoesService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
import java.text.ParseException;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Controller
@RequestMapping("/shoes")
public class ShoesController {

    private final ShoesRepository shoesRepository;
    private final ShoesfileRepository shoesfileRepository;
    private final ShoesService shoesService;
    private final S3Service s3Service;
    private final SeleniumService seleniumService;

    @GetMapping("/list")
    public String showList(Model model, @PageableDefault(size = 16) Pageable pageable, @RequestParam(required = false, defaultValue = "") String searchShoes) {
//        Page<Shoes> shoesData = shoesRepository.findAll(pageable);
      Page<Shoes> shoesData = shoesRepository.findByShoesStyleContainingOrBrandIdContainingOrModelIdContainingOrShoesName1ContainingOrShoesName2ContainingOrShoesColorwayContaining(searchShoes, searchShoes, searchShoes, searchShoes, searchShoes, searchShoes, pageable);
        int startPage = 1;
        int totalPage = shoesData.getTotalPages();
        model.addAttribute("startPage", startPage);
        model.addAttribute("totalPage", totalPage);
        model.addAttribute("shoesData",shoesData);
        return "shoes/list";
    }

    @GetMapping("/detail")
    public String showDetail(Model model, @RequestParam Long shoesId) {
            Shoes shoes = shoesRepository.findById(shoesId).orElse(null);
            model.addAttribute("shoes", shoes);
            Resale r = shoesService.getRecentResale(shoesId);
            log.info("@@@@@@@ RecentResale = " + r.getResaleId() + " @@@@@@@@ ");
        return "shoes/detail";
    }

    @GetMapping("/form")
    public String form(Model model, @RequestParam(required = false) Long shoesId) {
        if (shoesId == null) {
            model.addAttribute("shoes", new Shoes());
        } else {
            Shoes shoes = shoesRepository.findById(shoesId).orElse(null);
            model.addAttribute("shoes", shoes);
        }
        return "shoes/form";
    }

    @PostMapping("/form")
    public String postForm(@Valid Shoes shoes, BindingResult bindingResult, @RequestParam("shoesImg") MultipartFile multipartFile, Authentication authentication) throws IOException {
        if (bindingResult.hasErrors()) {
            return "shoes/form";
        }
//  전역변수 선언하여 get  Authentication a = SecurityContextHolder.getContext().getAuthentication();
        String userId = authentication.getName();
        shoesService.save(userId, shoes);
        if (multipartFile.getSize() > 0) {
            Long dir = shoes.getShoesId();
            log.info("shoesId = " + dir);
            List<Shoesfile> shoesf = shoesRepository.getById(dir).getShoesfiles();
            if (shoesf.size() > 0) {
                for(Shoesfile n : shoesf) {
                    shoesfileRepository.delete(n);
                }
                s3Service.deleteFolder("whatShoes/shoes/" + dir);
            }
            String fName = multipartFile.getOriginalFilename();
            shoesService.saveFile(dir, fName);
            s3Service.upload(multipartFile,"whatShoes/shoes/" + dir);
        }

        return "redirect:/shoes/list";
    }

    @GetMapping("/getResale")
    public String getResale(Long shoesId) throws ParseException {
        String shoesUrl = shoesRepository.findByShoesId(shoesId).getShoesUrl();
        List<List<String>> re = seleniumService.getResale(shoesId, shoesUrl);
        shoesService.saveResale(re, shoesId);
        return "redirect:/shoes/detail?shoesId=" + shoesId;
    }

}
