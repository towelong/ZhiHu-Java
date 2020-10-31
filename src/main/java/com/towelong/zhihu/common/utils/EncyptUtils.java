/**
 * @作者 WeLong
 * @博客 $ https://towelong.cn
 * @开源项目 $ https://github.com/ToWeLong
 * @创建时间 2020/9/22 10:50
 */
package com.towelong.zhihu.common.utils;

import com.amdelamar.jhash.Hash;
import com.amdelamar.jhash.algorithms.Type;
import lombok.SneakyThrows;

public class EncyptUtils {

    /**
     * 设置密文密码
     *
     * @param password 原始密码
     */
    public static String setPasswordEncrypt(String password) {
        char[] chars = password.toCharArray();
        return Hash.password(chars).algorithm(Type.PBKDF2_SHA256).create();
    }

    /**
     *
     * @param password 用户传过来的密码
     * @param cryptPassword 数据库中加密的密码
     * @return boolean
     */
    @SneakyThrows
    public static boolean encrypt(String password, String cryptPassword) {
        char[] chars = password.toCharArray();
        return Hash.password(chars).verify(cryptPassword);
    }
}
