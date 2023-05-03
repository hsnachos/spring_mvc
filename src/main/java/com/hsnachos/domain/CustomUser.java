package com.hsnachos.domain;

import java.util.Collection;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

import lombok.Getter;

public class CustomUser extends User {
	@Getter
	private MemberVO member;
	
	public CustomUser(MemberVO vo) {
		super(vo.getUserid(), vo.getUserpw(), vo.getAuths().stream()
				.map(auth-> new SimpleGrantedAuthority(auth.getAuth())).collect(Collectors.toList()));
		member = vo;
	}

}
