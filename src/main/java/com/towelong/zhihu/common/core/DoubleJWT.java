/**
 * @作者 WeLong
 * @博客 $ https://towelong.cn
 * @开源项目 $ https://github.com/ToWeLong
 * @创建时间 2020/9/22 15:22
 */
package com.towelong.zhihu.common.core;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.InvalidClaimException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.towelong.zhihu.vo.TokenVo;

import java.util.Date;
import java.util.Map;

public class DoubleJWT {
    private static final String SCOPE = "welong";
    private static final String ACCESS_TYPE = "access";
    private static final String REFRESH_TYPE = "refresh";
    private long accessExpire;

    private long refreshExpire;

    private Algorithm algorithm;

    private JWTVerifier accessVerifier;

    private JWTVerifier refreshVerifier;

    private JWTCreator.Builder builder;

    public DoubleJWT(Algorithm algorithm, long accessExpire, long refreshExpire) {
        this.algorithm = algorithm;
        this.accessExpire = accessExpire;
        this.refreshExpire = refreshExpire;
    }


    public DoubleJWT(String secret, long accessExpire, long refreshExpire) {
        this.algorithm = Algorithm.HMAC256(secret);
        this.accessExpire = accessExpire;
        this.refreshExpire = refreshExpire;
        this.initBuilderAndVerifier();
    }

    public String generateToken(String tokenType, long identity, String scope, long expire) {
        Date expireDate = getExpireDate(expire);
        return builder
                .withClaim("type", tokenType)
                .withClaim("identity", identity)
                .withClaim("scope", scope)
                .withExpiresAt(expireDate)
                .sign(algorithm);
    }

    public TokenVo generateTokens(long id){
        String accessToken = this.generateAccessToken(id);
        String refreshToken = this.generateRefreshToken(id);
        TokenVo tokenVo = new TokenVo();
        tokenVo.setAccessToken(accessToken);
        tokenVo.setRefreshToken(refreshToken);
        return tokenVo;
    }


    public String generateAccessToken(long identity) {
        return this.generateToken(ACCESS_TYPE, identity, "welong", this.accessExpire);
    }

    public String generateRefreshToken(long identity) {
        return this.generateToken(REFRESH_TYPE, identity, "welong", this.refreshExpire);
    }

    /**
     * 会校验令牌是否过期
     *
     * @param token token令牌
     * @return 验证并解析后的token信息
     */
    public Map<String, Claim> decodeAccessToken(String token) {
        DecodedJWT jwt = accessVerifier.verify(token);
        checkTokenExpired(jwt.getExpiresAt());
        checkTokenType(jwt.getClaim("type").asString(), ACCESS_TYPE);
        checkTokenScope(jwt.getClaim("scope").asString());
        return jwt.getClaims();
    }

    /**
     * 会校验令牌是否过期
     *
     * @param token token令牌
     * @return 验证并解析后的token信息
     */
    public Map<String, Claim> decodeRefreshToken(String token) {
        DecodedJWT jwt = refreshVerifier.verify(token);
        checkTokenExpired(jwt.getExpiresAt());
        checkTokenType(jwt.getClaim("type").asString(), REFRESH_TYPE);
        checkTokenScope(jwt.getClaim("scope").asString());
        return jwt.getClaims();
    }

    /**
     * 用于accessToken换取refreshToken
     * 不校验令牌是否过期，只为了拿到token内的信息
     *
     * @param token token令牌
     * @return 解析token里面的信息
     */
    public Map<String, Claim> decodeJWT(String token) {
        DecodedJWT jwt = JWT.decode(token);
        return jwt.getClaims();
    }

    private void checkTokenExpired(Date expiresAt) {
        long now = System.currentTimeMillis();
        if (expiresAt.getTime() < now) {
            throw new TokenExpiredException("token is expired");
        }
    }

    private void checkTokenScope(String scope) {
        if (scope == null || !scope.equals(SCOPE)) {
            throw new InvalidClaimException("token scope is invalid");
        }
    }

    private void checkTokenType(String type, String accessType) {
        if (type == null || !type.equals(accessType)) {
            throw new InvalidClaimException("token type is invalid");
        }
    }


    private void initBuilderAndVerifier() {
        accessVerifier = JWT.require(algorithm)
                .acceptExpiresAt(this.accessExpire)
                .build();
        refreshVerifier = JWT.require(algorithm)
                .acceptExpiresAt(this.refreshExpire)
                .build();
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
