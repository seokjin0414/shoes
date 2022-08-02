package com.shin.whatshoes.service;

import com.shin.whatshoes.model.Resale;
import com.shin.whatshoes.model.Shoes;
import com.shin.whatshoes.model.Shoesfile;
import com.shin.whatshoes.model.User;
import com.shin.whatshoes.repository.ResaleRepository;
import com.shin.whatshoes.repository.ShoesRepository;
import com.shin.whatshoes.repository.ShoesfileRepository;
import com.shin.whatshoes.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class ShoesService {

    private final ShoesRepository shoesRepository;

    private final UserRepository userRepository;

    private final ShoesfileRepository shoesfileRepository;

    private final ResaleRepository resaleRepository;

    public Shoes save(String userId, Shoes shoes) {
        User user = userRepository.findByUserId(userId);
        shoes.setUser(user);
        return shoesRepository.save(shoes);
    }

    public Shoesfile saveFile(Long dir, String fName) {
        Shoesfile shoesfile = new Shoesfile();
        Shoes shoes = shoesRepository.findByShoesId(dir);
        shoesfile.setFileShoes(shoes);
        shoesfile.setShoesFileName(fName);
        return shoesfileRepository.save(shoesfile);
    }

    public void saveResale(List<List<String>> history, Long shoesId) throws ParseException {
        log.info("@@@@@@ start saveResale");
        Shoes shoes = shoesRepository.findByShoesId(shoesId);
        for (List<String> e : history) {
            Resale resale = new Resale();
            // [2022년 7월 31일, 오후 1:16, 8.5, US$189]
            resale.setResaleShoes(shoes);
            String t1 = e.get(0);
            String t2 = e.get(1);
            if (t2.substring(0, 2).equals("오전")) {
                t2 = t2.substring(3);
            } else {
                if ((t2.length() == 8)) {
                    String a = String.valueOf(12 + Integer.parseInt(t2.substring(3, 5)));
                    t2 = a + t2.substring(5, 8);
                } else {
                    String a = String.valueOf(12 + Integer.parseInt(t2.substring(3, 4)));
                    t2 = a + t2.substring(4, 7);
                }
            }
            String t3 = t1 + " " + t2;
            SimpleDateFormat format = new SimpleDateFormat("yyyy년 MM월 dd일 HH:mm");
            Date date = format.parse(t3);
            log.info("@@@@@@ t3 = (" + t3 + ")");
            resale.setResaleDate(date);
            resale.setResaleShoesSize(e.get(2));
            resale.setResalePrice(Integer.parseInt(e.get(3).replaceAll("[^\\d+]", "")));
            resaleRepository.save(resale);
        }
        log.info("@@@@@@ success save history");
    }
}
