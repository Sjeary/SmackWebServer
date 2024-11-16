package org.example.smackwebserver.service;

import org.example.smackwebserver.converter.UserConverter;
import org.example.smackwebserver.dao.User;
import org.example.smackwebserver.dao.UserRepository;
import org.example.smackwebserver.dto.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDTO getUserById(long id) {
        //获取用户所有信息（不包含密码）
        User user = userRepository.findById(id).orElseThrow(RuntimeException::new);
        return UserConverter.convertUser(user);
    }

    @Override
    public Long createUser(UserDTO userDTO) {
        //创建用户，POST方法
        List<User> userList =  userRepository.findByEmail(userDTO.getEmail());
        if(!CollectionUtils.isEmpty(userList)){
            throw new IllegalStateException("邮箱: "+ userDTO.getEmail()+" 已被注册");
        }
        User user = userRepository.save(UserConverter.convertUser(userDTO));
        return user.getId();
    }

    @Override
    public boolean authenticateById(UserDTO userDTO) {
        //验证用户是否密码正确（先验证用户ID是否存在）
        List<User> userList = userRepository.findById_(userDTO.getId());
        if (userList == null) {
            return false; // 用户不存在
        }
        // 验证密码（可以用哈希验证实际场景）
        return userList.get(0).getPasswd().equals(userDTO.getPasswd());
    }

    @Override
    public boolean authenticateByEmail(UserDTO userDTO) {
        //验证用户是否密码正确（先验证用户Email是否存在）
        List<User> userList = userRepository.findByEmail(userDTO.getEmail());
        if (userList == null) {
            return false; // 用户不存在
        }
        // 验证密码（可以用哈希验证实际场景）
        return userList.get(0).getPasswd().equals(userDTO.getPasswd());
    }

    // 生成会话令牌（可以用 JWT 或其他方式）
    public String generateToken(String name) {
        // 这里是简单返回一个伪令牌，实际中可以使用 JWT
        return "TOKEN_" + name + "_" + System.currentTimeMillis();
    }


}
