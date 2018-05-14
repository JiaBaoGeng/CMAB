package cn.jbg.cmab.backend.mall.service;

import cn.jbg.cmab.backend.mall.bean.Review;
import cn.jbg.cmab.backend.mall.bean.ReviewExample;
import cn.jbg.cmab.backend.mall.dao.ReviewMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by jbg on 2018/3/31.
 */
@Service
public class ReviewService {

    @Autowired
    private ReviewMapper reviewMapper;

    public List<Review> getReviewByItemId(Long itemId){
        ReviewExample example = new ReviewExample();
        example.createCriteria().andItemIdEqualTo(itemId);

        return reviewMapper.selectByExample(example);
    }
}
