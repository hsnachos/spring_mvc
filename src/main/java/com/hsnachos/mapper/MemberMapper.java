package com.hsnachos.mapper;


import com.hsnachos.domain.MemberVO;

public interface MemberMapper {
	MemberVO read(String userid);
//	
//	@Select("select * from tbl_auth where userid = #{userid}")
//	List<AuthVO> authVOs(String userid);
//	
}
