package com.kk.TeamUp.domain;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Entity
@Getter
@Setter
@DynamicInsert
@EntityListeners(AuditingEntityListener.class)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="user_id",updatable = false)
    private Long id;

    @Column(name="name",nullable = false, unique = true)
    private String name;

    @Column(name="major", nullable = false)
    private String major;

    @Column(name="record")
    private Long record;

    @Column(name="penalty")
    private Long penalty;

    @OneToMany(mappedBy = "user")
    private List<UserMatching> userMatchings = new ArrayList<UserMatching>();

    @CreatedDate
    @Column(name="created_at")
    private LocalDateTime createdAt; //계정 생성 시간

    @LastModifiedDate
    @Column(name="updated_at")
    private LocalDateTime updatedAt; //계정 마지막 로그인 시간

    @Builder
    public User(String name, String major, Long record, Long penalty, String auth) {
        this.name=name;
        this.major=major;
        this.record=record;
        this.penalty=penalty;
    }

    public void update(String name) {
        this.name = name;
    }

    @PrePersist //영속성 컨텍스트로 persist 하기 전 실행됨
    public void prePersist() {
        this.penalty = this.penalty == null ? 0 : this.penalty;
        this.record = this.record == null ? 0 : this.record;
    }

    @Override // 권한 반환
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("user")); //권한 명칭이 됨
        //현재는 사용자 외 권한설정 안했기에 user 권한만 담아 반환 -> 추후 관리자 권한 추가로 변경 가능
    }

    @Override
    public String getPassword() {
        return major;
        //비밀번호 자체가 없으므로 일단 전공으로 처리
        //추후 비밀번호로 바꿀 수 있음
    }

    @Override
    public String getUsername() {
        return name; //유니크해야 함
    }

    //만료여부 체크해줌
    @Override
    public boolean isAccountNonExpired() {
        return true; //true일 시, 만료되지 않은 것임
    }

    @Override
    public boolean isAccountNonLocked() {
        return true; //잠금되지 않았음을 의미
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true; //패스워드 만료되지 않았음을 의미
    }

    @Override
    public boolean isEnabled() {
        return true; //계정 사용 가능함을 의미
    }
}
