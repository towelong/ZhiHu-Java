/**
 * @作者 WeLong
 * @博客 $ https://towelong.cn
 * @开源项目 $ https://github.com/ToWeLong
 * @创建时间 2020/9/16 18:32
 */
package com.towelong.zhihu.common.core;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;

import java.util.Date;
import java.util.Map;


public class SingleJWT {

    private Algorithm algorithm;
    private long expire;

    private JWTVerifier verifier;
    private JWTCreator.Builder builder;

    public SingleJWT(long expire, String secret) {
        this.algorithm = Algorithm.HMAC256(secret);
        this.expire = expire;
        initCreatorBuilder();
    }

    public SingleJWT(Algorithm algorithm, long expire) {
        this.algorithm = algorithm;
        this.expire = expire;
        initCreatorBuilder();
    }

    /**
     * {
     *   "identity": 1,
     *   "scope": "welong",
     *   "type": "access",
     *   "exp": 1599966179
     * }
     * @param tokenType 令牌类型
     * @param uid 用户id
     * @param scope 标识
     * @return token
     */
    public String generateToken(String tokenType,long uid,String scope){
        Date expireTime = this.getExpireDate(expire);
        return builder.withClaim("type",tokenType)
                .withClaim("identity",uid)
                .withClaim("scope",scope)
                .withExpiresAt(expireTime)
                .sign(algorithm);
    }

    public Map<String, Claim> decodeToken(String token) {
        DecodedJWT jwt = verifier.verify(token);
        checkTokenExpired(jwt.getExpiresAt());
        return jwt.getClaims();
    }

    private void checkTokenExpired(Date expiresAt) {
        long now = new Date().getTime();
        if (expiresAt.getTime() < now) {
            throw new TokenExpiredException("token is expired");
        }
    }


    private void initCreatorBuilder() {
        verifier = JWT.require(algorithm).acceptExpiresAt(expire).build();
        builder = JWT.create();
    }

    /**
     * 获得过期时间
     *
     * @param expire 过期时间
     * @return Date
     */
    private Date getExpireDate(long expire) {
        long nowTime = new Date().getTime();
        long expireTime = nowTime + expire;
        return new Date(expireTime);
    }



}
