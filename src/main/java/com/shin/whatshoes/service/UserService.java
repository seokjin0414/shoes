package com.shin.whatshoes.service;

import com.shin.whatshoes.model.Auth;
import com.shin.whatshoes.model.User;
import com.shin.whatshoes.repository.UserRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    public User save(User user) {
        // pw 암호화 -> 다시저장
        String encodedPassword = passwordEncoder.encode(user.getUserPw());
        user.setUserPw(encodedPassword);
        user.setEnabled(true);
        Auth auth = new Auth();
//      BIGINT LONG TYPE이다
        auth.setAuthId(1L);
        user.getAuths().add(auth);

        return userRepository.save(user);
    }

}
