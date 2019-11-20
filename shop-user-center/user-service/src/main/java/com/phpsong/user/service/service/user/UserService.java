package com.phpsong.user.service.service.user;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.RandomUtil;
import com.phpsong.common.enums.ExceptionEnum;
import com.phpsong.common.exception.ShopException;
import com.phpsong.user.api.dto.user.UserDTO;
import com.phpsong.user.service.dao.user.UserMapper;
import com.phpsong.user.service.domain.entity.user.User;
import com.phpsong.user.service.utils.CodecUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @Author: qiuyisong
 * @Date: 2019/11/20 10:59
 */
@Service
public class UserService {
    @Autowired
    private UserMapper userMapper;

    @Autowired
    private StringRedisTemplate redisTemplate;


    private static final String KEY_PREFIX = "user:verify:code:";

    public UserDTO queryUser(String username, String password) {
        User record = new User();
        record.setUsername(username);

        //首先根据用户名查询用户
        User user = userMapper.selectOne(record);

        if (user == null) {
            throw new ShopException(ExceptionEnum.USER_NOT_EXIST);
        }

        String s = CodecUtils.md5Hex(password, user.getSalt());
        //检验密码是否正确
        if (!StringUtils.equals(s, user.getPassword())) {
            //密码不正确
            throw new ShopException(ExceptionEnum.PASSWORD_NOT_MATCHING);
        }

        UserDTO result = new UserDTO();
        BeanUtil.copyProperties(user, result);
        return result;
    }

    public Boolean checkData(String data, Integer type) {
        User user = new User();
        //判断校验数据的类型
        switch (type) {
            case 1:
                user.setUsername(data);
                break;
            case 2:
                user.setPhone(data);
                break;
            default:
                throw new ShopException(ExceptionEnum.INVALID_PARAM);
        }
        return (userMapper.selectCount(user) == 0);
    }

    public void sendVerifyCode(String phone) {

        //随机生成6位数字验证码
        String code = RandomUtil.randomNumbers(6);

        String key = KEY_PREFIX + phone;

        //把验证码放入Redis中，并设置有效期为5min
        redisTemplate.opsForValue().set(key, code, 5, TimeUnit.MINUTES);

        //向mq中发送消息
        Map<String, String> map = new HashMap<>();
        map.put("phone", phone);
        map.put("code", code);
        // TODO 发送mq
        //amqpTemplate.convertAndSend("ly.sms.exchange", "sms.verify.code", map);
    }

    public void register(User user, String code) {
        user.setId(null);
        user.setCreated(new Date());
        String key = KEY_PREFIX + user.getPhone();

        String value = redisTemplate.opsForValue().get(key);

        if (!StringUtils.equals(code, value)) {
            //验证码不匹配
            throw new ShopException(ExceptionEnum.VERIFY_CODE_NOT_MATCHING);
        }

        //生成盐
        String salt = RandomUtil.randomString(8);
        user.setSalt(salt);

        //生成密码
        String md5Pwd = CodecUtils.md5Hex(user.getPassword(), user.getSalt());

        user.setPassword(md5Pwd);

        //保存到数据库
        int count = userMapper.insert(user);

        if (count != 1) {
            throw new ShopException(ExceptionEnum.INVALID_PARAM);
        }

        //把验证码从Redis中删除
        redisTemplate.delete(key);
    }
}
