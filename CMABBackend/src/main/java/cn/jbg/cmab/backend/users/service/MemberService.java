package cn.jbg.cmab.backend.users.service;

import cn.jbg.cmab.backend.users.bean.Member;
import cn.jbg.cmab.backend.users.bean.MemberExample;
import cn.jbg.cmab.backend.users.dao.MemberMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by jbg on 2018/3/30.
 */
@Service
public class MemberService {

    @Autowired
    private MemberMapper memberMapper;

    public Member getMemberInfoByUserId(Long userId){
        Member member = null;
        MemberExample example = new MemberExample();
        example.createCriteria().andUserIdEqualTo(userId);

        List<Member> members = memberMapper.selectByExample(example);
        if(members.size() != 0){
            member = members.get(0);
        }
        return member;
    }
}
