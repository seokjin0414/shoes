package com.shin.whatshoes.service;

import com.shin.whatshoes.model.What;
import com.shin.whatshoes.repository.ShoesRepository;
import com.shin.whatshoes.repository.UserRepository;
import com.shin.whatshoes.repository.WhatRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class WhatService {

    private final WhatRepository whatRepository;

    private final ShoesRepository shoesRepository;

    private final UserRepository userRepository;

    public What save(What what) {

        return whatRepository.save(what);
    }

}
