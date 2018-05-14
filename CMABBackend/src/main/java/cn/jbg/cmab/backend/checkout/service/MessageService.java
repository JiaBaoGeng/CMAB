package cn.jbg.cmab.backend.checkout.service;

import cn.jbg.cmab.backend.checkout.bean.Message;
import cn.jbg.cmab.backend.checkout.bean.MessageExample;
import cn.jbg.cmab.backend.checkout.dao.MessageMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by jbg on 2018/3/16.
 */
@Service
public class MessageService {
    @Autowired
    private MessageMapper messageMapper;

    public List<Message> queryMessage(){

        MessageExample example = new MessageExample();
        MessageExample.Criteria criteria = example.createCriteria();

        /*if(itemName!=null && !itemName.equals("")){
            itemName = "%" + itemName + "%";
            criteria.andItemNameLike(itemName);
        }*/
        return messageMapper.selectByExample(example);
    }

    public Message getMessageById(Long messageId){
        return messageMapper.selectByPrimaryKey(messageId);
    }

    public int addMessage(Message record){
        return messageMapper.insert(record);
    }

    public int updateMessage(Message record){
        return messageMapper.updateByPrimaryKeySelective(record);
    }

    public int deleteMessage(Long messageId){
        return messageMapper.deleteByPrimaryKey(messageId);
    }

}
