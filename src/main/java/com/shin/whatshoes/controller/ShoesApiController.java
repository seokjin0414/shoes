package com.shin.whatshoes.controller;



import com.shin.whatshoes.model.Shoes;
import com.shin.whatshoes.model.Shoesfile;
import com.shin.whatshoes.repository.ResaleRepository;
import com.shin.whatshoes.repository.ShoesRepository;
import com.shin.whatshoes.repository.ShoesfileRepository;
import com.shin.whatshoes.service.S3Service;
import com.shin.whatshoes.service.SeleniumService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;
import org.thymeleaf.util.StringUtils;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
class ShoesApiController {

    private final ShoesRepository shoesRepository;
    private final ShoesfileRepository shoesfileRepository;
    private final ResaleRepository resaleRepository;
    private final S3Service s3Service;
    private final SeleniumService seleniumService;

    @GetMapping("/shoes")
    List<Shoes> all(@RequestParam(required = false, defaultValue = "") String shoesName1,
                    @RequestParam(required = false, defaultValue = "") String modelId) {
        if (StringUtils.isEmpty(shoesName1) && StringUtils.isEmpty(modelId)) {
            return shoesRepository.findAll();
        } else {
            return shoesRepository.findByShoesName1OrModelId(shoesName1, modelId);
        }
    }

    @PostMapping("/shoes")
    Shoes newShoes(@RequestBody Shoes newShoes) {

        return shoesRepository.save(newShoes);
    }

    @GetMapping("/shoes/{shoesId}")
    Shoes one(@PathVariable Long shoesId) {

        return shoesRepository.findById(shoesId).orElseThrow(null);
    }

    @PutMapping("/shoes/{shoesId}")
    Shoes replaceShoes(@RequestBody Shoes newShoes, @PathVariable Long shoesId) {

        return shoesRepository.findById(shoesId)
                .map(shoes -> {
                    shoes.setShoesStyle(newShoes.getShoesStyle());
                    shoes.setBrandId(newShoes.getBrandId());
                    shoes.setModelId(newShoes.getModelId());
                    shoes.setShoesSizeType(newShoes.getShoesSizeType());
                    shoes.setShoesName1(newShoes.getShoesName1());
                    shoes.setShoesName2(newShoes.getShoesName2());
                    shoes.setShoesColorway(newShoes.getShoesColorway());
                    shoes.setShoesPrice(newShoes.getShoesPrice());
                    shoes.setShoesRelease(newShoes.getShoesRelease());
                    return shoesRepository.save(shoes);
                })
                .orElseGet(() -> {
                    newShoes.setShoesId(shoesId);
                    return shoesRepository.save(newShoes);
                });
    }

    //    server단 auth 식별
    @Secured("ROLE_ADMIN")
    @DeleteMapping("/shoes/{shoesId}")
    void deleteShoes(@PathVariable Long shoesId) {
        List<Shoesfile> shoesf = shoesRepository.getById(shoesId).getShoesfiles();

        if (shoesf.size() > 0) {
//            log.info("fileId = " + shoesfile.getShoesFileId());
//            shoesfileRepository.delete(shoesfile);

            log.info("shoesId = " + shoesId);
            s3Service.deleteFolder("whatShoes/shoes/" + shoesId);
            shoesRepository.deleteById(shoesId);
        } else {
            shoesRepository.deleteById(shoesId);
        }
    }

//    @Secured("ROLE_ADMIN")
//    @GetMapping("/shoes/getResale/{shoesId}")
//    void getResale(@PathVariable Long shoesId) {
//        String shoesStyle = shoesRepository.findByShoesId(shoesId).getShoesStyle();
//        seleniumService.getResale(shoesId, shoesStyle);
//    }


}