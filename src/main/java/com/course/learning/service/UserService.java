package com.course.learning.service;

import com.course.learning.common.BusinessException;
import com.course.learning.common.JwtUtil;
import com.course.learning.entity.User;
import com.course.learning.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * 用户服务：注册、登录（账号密码 + 微信 openid）
 */
@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;

    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    /** 账号密码注册 */
    public Map<String, Object> register(String username, String password, String nickname) {
        if (!StringUtils.hasText(username) || !StringUtils.hasText(password)) {
            throw new BusinessException(400, "用户名和密码不能为空");
        }
        if (userRepository.findByUsername(username).isPresent()) {
            throw new BusinessException("用户名已存在");
        }
        User user = new User();
        user.setUsername(username);
        user.setPassword(encoder.encode(password));
        user.setNickname(StringUtils.hasText(nickname) ? nickname : username);
        user.setRole("student");
        userRepository.save(user);
        return buildLoginResult(user);
    }

    /** 账号密码登录 */
    public Map<String, Object> login(String username, String password) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new BusinessException("用户名或密码错误"));
        if (!encoder.matches(password, user.getPassword())) {
            throw new BusinessException("用户名或密码错误");
        }
        return buildLoginResult(user);
    }

    /**
     * 微信小程序登录
     * 通过 wx_openid 自动注册或登录（无密码，静默登录）
     */
    public Map<String, Object> wxLogin(String wxOpenid, String nickname) {
        if (!StringUtils.hasText(wxOpenid)) {
            throw new BusinessException(400, "wx_openid 不能为空");
        }
        User user = userRepository.findByWxOpenid(wxOpenid).orElse(null);
        if (user == null) {
            user = new User();
            user.setWxOpenid(wxOpenid);
            user.setNickname(StringUtils.hasText(nickname) ? nickname : "微信用户" + wxOpenid.substring(Math.max(0, wxOpenid.length() - 4)));
            user.setRole("student");
            userRepository.save(user);
        }
        return buildLoginResult(user);
    }

    /** 获取用户信息（脱敏） */
    public Map<String, Object> getUserInfo(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new BusinessException("用户不存在"));
        Map<String, Object> map = new HashMap<>();
        map.put("id", user.getId());
        map.put("username", user.getUsername());
        map.put("nickname", user.getNickname());
        map.put("avatar", user.getAvatar());
        map.put("role", user.getRole());
        map.put("classId", user.getClassId());
        return map;
    }

    /** 绑定班级 */
    public void bindClass(Long userId, Long classId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new BusinessException("用户不存在"));
        user.setClassId(classId);
        userRepository.save(user);
    }

    /** 构造登录返回：token + 用户信息 */
    private Map<String, Object> buildLoginResult(User user) {
        String token = jwtUtil.generateToken(user.getId(), user.getNickname());
        Map<String, Object> map = new HashMap<>();
        map.put("token", token);
        map.put("user", getUserInfo(user.getId()));
        return map;
    }
}
