package com.example.book.config.dto;

import com.example.book.domain.user.Role;
import com.example.book.domain.user.Users;
import lombok.Builder;
import lombok.Getter;

import java.util.Map;

@Getter
public class OAuthAttributes {

    private Map<String,Object> attributes;
    private String nameAttributeKey;
    private String name;
    private String email;
    private String picture;

    @Builder
    public OAuthAttributes(Map<String, Object> attributes, String nameAttributeKey, String name, String email, String picture) {
        this.attributes = attributes;
        this.nameAttributeKey = nameAttributeKey;
        this.name = name;
        this.email = email;
        this.picture = picture;
    }


    /**
     * @param registrationId
     * @param userNameAttributeName
     * @param attributes
     * @return ofGoogle(userNameAttributeName,attributes);
     *
     * OAuth2User 에서 반환하는 사용자 정보는 Map이기때문에 값 하나하나를 변환해야 한다.
     * 들어오는 값이 네이버에서 들어왔으면 네이버로, 그 외에는 구글로 로그인한다.
     */
    public static OAuthAttributes of(String registrationId, String userNameAttributeName, Map<String, Object> attributes) {
        if ("naver".equals(registrationId)) {
            return ofNaver("id", attributes);
        }
        return  ofGoogle(userNameAttributeName, attributes);
    }


    /**
     * 구글 로그인 연동시에 사용하는 클래스
     * @param userNameAttributeName
     * @param attributes
     * @return
     */
    public static OAuthAttributes ofGoogle(String userNameAttributeName,Map<String, Object> attributes) {

        return OAuthAttributes.builder()
                .name((String) attributes.get("name"))
                .email((String) attributes.get("email"))
                .picture((String) attributes.get("picture"))
                .attributes(attributes)
                .nameAttributeKey(userNameAttributeName)
                .build();
    }

    /**
     * 네이버 로그인시에 사용하는 클래스
     * @param userNameAttributeName
     * @param attributes
     * @return
     */
    private static OAuthAttributes ofNaver(String userNameAttributeName, Map<String, Object> attributes) {

            Map<String,Object> response = (Map<String, Object>) attributes.get("response");

            return OAuthAttributes.builder()
                    .name((String) response.get("name"))
                    .email((String) response.get("email"))
                    .picture((String) response.get("profile_image"))
                    .attributes(response)
                    .nameAttributeKey(userNameAttributeName)
                    .build();
    }



    public Users toEntity() {
        return Users.builder()
                .name(name)
                .email(email)
                .picture(picture)
                .role(Role.GUEST)
                .build();
    }

    @Override
    public String toString() {
        return "OAuthAttributes{" +
                "attributes=" + attributes +
                ", nameAttributeKey='" + nameAttributeKey + '\'' +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", picture='" + picture + '\'' +
                '}';
    }
}
