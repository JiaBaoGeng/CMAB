package cn.jbg.cmab.backend.mall.controller;

import cn.jbg.cmab.backend.mall.bean.Review;
import cn.jbg.cmab.backend.mall.bean.ReviewDetail;
import cn.jbg.cmab.backend.mall.service.ReviewService;
import cn.jbg.cmab.backend.users.bean.Member;
import cn.jbg.cmab.backend.users.bean.Users;
import cn.jbg.cmab.backend.users.service.MemberService;
import cn.jbg.cmab.backend.users.service.UsersService;
import cn.jbg.cmab.common.util.DateUtils;
import cn.jbg.cmab.common.util.ResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by jbg on 2018/3/31.
 */
@RestController
@RequestMapping("/ReviewController")
public class ReviewController {

    @Autowired
    private ReviewService reviewService;

    @Autowired
    private UsersService usersService;

    @Autowired
    private MemberService memberService;

    @RequestMapping(value = "/getReviewDetailByItemId", method = RequestMethod.POST)
    public ResponseUtil getReviewDetailByItemId(@RequestBody Map param){
        ResponseUtil responseUtil = new ResponseUtil();
        List<ReviewDetail>  reviewDetails = new ArrayList<ReviewDetail>();
        try{
            Long itemId = Long.parseLong(param.get("itemId").toString());
            List<Review> reviewList = reviewService.getReviewByItemId(itemId);
            for(Review review : reviewList){
                reviewDetails.add(reviewToDetail(review));
            }
            responseUtil.setResultCode(ResponseUtil.SUCCESS);
            responseUtil.setResultObject(reviewDetails);
        }catch (Exception e){
            e.printStackTrace();
            responseUtil.setResultCode(ResponseUtil.FAIL);
            responseUtil.setResultMsg(e.getMessage());
        }

        return responseUtil;
    }

    private ReviewDetail reviewToDetail(Review review){
        ReviewDetail reviewDetail = new ReviewDetail();

        Long userId = review.getUserId();
        Users users = usersService.getUsersInfoById(userId);
        Member member = memberService.getMemberInfoByUserId(userId);

        reviewDetail.setReview(review);
        reviewDetail.setAvatarUrl(users.getAvatarUrl());
        reviewDetail.setNickName(users.getNickName());
        reviewDetail.setMemberLevel(member.getMemberLevel());
        reviewDetail.setDateString(DateUtils.getFormatedDateTime(review.getReviewTime()));
        return reviewDetail;
    }
}
