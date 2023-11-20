package com.yudiol.jobsearchplatform.security;

import com.yudiol.jobsearchplatform.exception.errors.UnconfirmedAccountError;
import com.yudiol.jobsearchplatform.model.User;
import com.yudiol.jobsearchplatform.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserDetailServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(username).orElseThrow(() ->
                new UsernameNotFoundException(String.format("UserDetailsService: пользователь с username '%s' не найден", username)));
        if (!user.isActive()) {
            throw new UnconfirmedAccountError("Пользователь не подтвердил свою учетную запись");
        }
        return new UserDetailsImpl(user);
    }

}
