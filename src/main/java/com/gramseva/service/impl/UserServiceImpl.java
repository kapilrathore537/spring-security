package com.gramseva.service.impl;

import com.gramseva.exception.ResourceNotFoundException;
import com.gramseva.model.User;
import com.gramseva.repository.UserRepository;
import com.gramseva.service.IUserService;
import com.gramseva.utils.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements IUserService, UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {
        Optional<User> userOptional = this.userRepository.findByUserIdAndIsActiveAndIsDeleted(userId,true,false);
        if (userOptional.isEmpty()) {
            throw new ResourceNotFoundException(Constants.USER_NOT_FOUND);
        }
        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(userOptional.get().getRole().getRoleName()));
        return new org.springframework.security.core.userdetails.User(userId, userOptional.get().getPassword(), authorities);
    }
}
