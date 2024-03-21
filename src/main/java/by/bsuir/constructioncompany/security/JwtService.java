package by.bsuir.constructioncompany.security;

import by.bsuir.constructioncompany.models.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtService {
    @Value("${application.security.jwt.secret-key}")
    private String secretKey;

    @Value("${application.security.jwt.access-token.expiration}")
    private Long accessExpiration;

    @Value("${application.security.jwt.refresh-token.expiration}")
    private Long refreshExpiration;


    public String generateAccessToken(User user) {
        return generateAccessToken(new HashMap<>(), user);
    }

    public String generateAccessToken(
            Map<String, Object> extraClaims,
            User user
    ) {
        return buildToken(extraClaims, user, accessExpiration, TokenType.ACCESS);
    }

    public String generateRefreshToken(
            User user
    ) {
        return buildToken(new HashMap<>(), user, refreshExpiration, TokenType.REFRESH);
    }

    public String buildToken(
            Map<String, Object> claims,
            User user,
            Long expiration,
            TokenType tokenType
    ){
        return Jwts
                .builder()
                .setClaims(claims)
                .claim("TokenType", tokenType.name())
                .setSubject(user.getUsername())
                .claim("role", user.getRole())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(getSignInKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    public boolean isTokenValid(String token, User user, TokenType tokenType) {
        Claims tokenClaims = extractAllClaims(token);
        if(isTokenExpired(tokenClaims)){
            return false;
        }
        if(!extractSubject(tokenClaims).equals(user.getUsername())){
            return false;
        }
        if(!extractTokenType(tokenClaims).equals(tokenType.name())){
            return false;
        }
        return true;
    }

    public String extractSubject(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public String extractTokenType(String token) {
        return extractClaim(token, "TokenType");
    }

    public boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    public boolean isTokenExpired(Claims tokenClaims){
        return tokenClaims.getExpiration().before(new Date());
    }

    public String extractTokenType(Claims tokenClaims){
        return tokenClaims.get("TokenType",  String.class);
    }

    public String extractSubject(Claims tokenClaims){
        return tokenClaims.getSubject();
    }

    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    public String extractClaim(String token, String claimName) {
        Claims claims = extractAllClaims(token);
        return claims.get(claimName, String.class);
    }

    public Claims extractAllClaims(String token) {
        return Jwts
                .parserBuilder()
                .setSigningKey(getSignInKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    private Key getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public String extractAccessToken(HttpServletRequest request){
        final String authorizationHeader = request.getHeader("Authorization");
        return extractAccessToken(authorizationHeader);
    }

    public String extractAccessToken(String authorizationHeader){
        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
            return null;
        }
        return authorizationHeader.substring(7);
    }

}
