package com.gree.dbup.service;

import com.gree.dbup.dbexpand.jpa.parameter.LinkEnum;
import com.gree.dbup.dbexpand.jpa.parameter.Operator;
import com.gree.dbup.dbexpand.jpa.parameter.PredicateBuilder;
import com.gree.dbup.entity.User;
import com.gree.dbup.model.UserQo;
import com.gree.dbup.redis.UserRedis;
import com.gree.dbup.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;


@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserRedis userRedis;
    private static final String keyHead = "mysql:get:user:";

    public User findById(Long id) {
        User user = userRedis.get(keyHead + id);
        if(user == null){
            user = userRepository.findById(id).get();
            if(user != null)
                userRedis.add(keyHead + id, 30L, user);
        }
        return user;
    }

    public User create(User user) {
        User newUser = userRepository.save(user);
        if(newUser != null)
            userRedis.add(keyHead + newUser.getId(), 30L, newUser);
        return newUser;
    }

    public User update(User user) {
        if(user != null) {
            userRedis.delete(keyHead + user.getId());
            userRedis.add(keyHead + user.getId(), 30L, user);
        }
        return userRepository.save(user);
    }

    public void delete(Long id) {
        userRedis.delete(keyHead + id);
        userRepository.deleteById(id);
    }

    public Page<User> findPage(UserQo userQo){
        Pageable pageable = new PageRequest(userQo.getPage(), userQo.getSize(), new Sort(Sort.Direction.ASC, "id"));

        PredicateBuilder pb  = new PredicateBuilder();

        if (!StringUtils.isEmpty(userQo.getName())) {
            pb.add("name","%" + userQo.getName() + "%", LinkEnum.LIKE);
        }
        if (!StringUtils.isEmpty(userQo.getCreatedateStart())) {
            pb.add("createdate",userQo.getCreatedateStart(), LinkEnum.GE);
        }
        if (!StringUtils.isEmpty(userQo.getCreatedateEnd())) {
            pb.add("createdate",userQo.getCreatedateEnd(), LinkEnum.LE);
        }

        return userRepository.findAll(pb.build(), Operator.AND, pageable);
    }
}
