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
        User user = userRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("User with id " + id + " not found"));
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
    public String generateToken(UserDTO userDTO) {
        // 这里是简单返回一个伪令牌，实际中可以使用 JWT
        return "TOKEN_" + userDTO.getName() + "_" + System.currentTimeMillis();
    }

    @Override
    public UserDTO getUserByEmail(String email) {
        List<User> users = userRepository.findByEmail(email);

        if (users.isEmpty()) {
            throw new IllegalArgumentException("User with email " + email + " not found");
        }

        // 假设每个邮箱唯一，取列表第一个元素
        User user = users.get(0);

        return UserConverter.convertUser(user);
    }

    @Override
    public Long updateUser(User user) {
        // 查询数据库中的用户信息
        User existingUser = userRepository.findById(user.getId())
                .orElseThrow(() -> new IllegalArgumentException("User with id " + user.getId() + " not found"));

        // 检查新的邮箱是否已存在于其他用户中
        if (user.getEmail() != null && !user.getEmail().equals(existingUser.getEmail())) {
            List<User> usersWithSameEmail = userRepository.findByEmail(user.getEmail());
            if (!usersWithSameEmail.isEmpty()) {
                throw new IllegalStateException("Email: " + user.getEmail() + " is already in use by another user.");
            }
        }

        // 更新字段，如果传输的字段为 null，则保留原值
        if (user.getName() != null) {
            existingUser.setName(user.getName());
        }
        if (user.getPasswd() != null) {
            existingUser.setPasswd(user.getPasswd());
        }
        if (user.getEmail() != null) {
            existingUser.setEmail(user.getEmail());
        }
        if (user.getCompanyName() != null) {
            existingUser.setCompanyName(user.getCompanyName());
        }
        if (user.getCompanyIntroduction() != null) {
            existingUser.setCompanyIntroduction(user.getCompanyIntroduction());
        }
        if (user.getHomepageLink() != null) {
            existingUser.setHomepageLink(user.getHomepageLink());
        }

        // 保存更新后的用户信息
        User updatedUser = userRepository.save(existingUser);

        return updatedUser.getId();
    }



}
