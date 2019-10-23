package helper;

import java.io.UnsupportedEncodingException;
import java.util.Date;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;

public class JWTUtil {
    //设置过期时间 单位：s
    private static final long EXPIRE_TIME = 60 * 60 * 24L;
    //Algorithm.HMAC256(String)中的参数，用于设密
    private static final String secret = "secret";
    /**
     *
     * @param id 用户id
     * @return 返回加密的Token
     */

    public String sign(String id){
        Date date = new Date(System.currentTimeMillis() + EXPIRE_TIME);
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);
            return JWT.create()
                    .withClaim("id",id)
                    .withExpiresAt(date)
                    .sign(algorithm);
        } catch (UnsupportedEncodingException e) {
             e.printStackTrace();
             return null;
        }
    }

    /**
     * 校验token是否正确
     * @param  token 密钥
     * @param id 用户id
     * @return 是否正确
     */

    public boolean verify(String token,String id){
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);
            JWTVerifier verifier = JWT.require(algorithm)
                                        .withClaim("id", id)
                                        .build();
            verifier.verify(token);
            return true;
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return false;
    }

}
