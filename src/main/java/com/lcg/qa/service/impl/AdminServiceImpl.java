package com.lcg.qa.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lcg.qa.mapper.AdminMapper;
import com.lcg.qa.mapper.QuestionMapper;
import com.lcg.qa.mapper.UserMapper;
import com.lcg.qa.model.entity.Question;
import com.lcg.qa.model.entity.User;
import com.lcg.qa.model.result.Result;
import com.lcg.qa.model.vo.AdminParam;
import com.lcg.qa.service.AdminService;
import com.lcg.qa.service.ThreadService;
import com.lcg.qa.utils.JwtHelper;
import lombok.extern.slf4j.Slf4j;
import org.apache.hadoop.yarn.webapp.hamlet.Hamlet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.Optional;

import static com.lcg.qa.constant.HttpStatusEnum.INSERT_ERROR;
import static com.lcg.qa.constant.HttpStatusEnum.PASSWORD_INCONSISTENT;

@Service
@Slf4j
public class AdminServiceImpl implements AdminService {
    @Autowired
    private AdminMapper adminMapper;

    @Autowired
    private QuestionMapper questionMapper;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private ThreadService threadService;

    @Override
    public Result loginAdmin(AdminParam adminParam) {
        String name = adminParam.getUsername();
        String password = adminParam.getPassword();
        if (StringUtils.isEmpty(name) || StringUtils.isEmpty(password)) {
            return Result.error(INSERT_ERROR);
        }
        try {
            String originPassword = Optional.ofNullable(adminMapper.findPasswordByAdmin(name)).orElse("");
            if (password.equals(originPassword)) {
                //校验成功,构造token返回
                String token = JwtHelper.createToken(1L, name);
                return Result.data("token", token);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return Result.error(PASSWORD_INCONSISTENT);
    }

    @Override
    public IPage<Question> selectPage(Page<Question> pageParam, Integer type) {
        //构造查询条件
        QueryWrapper<Question> wrapper = new QueryWrapper<>();
        if (type==0||type==1) {
            wrapper.eq("type", type);
        }
        IPage<Question> pages = questionMapper.selectPage(pageParam, wrapper);
        //进行用户信息封装
        pages.getRecords().forEach(this::packageQuestion);
        return pages;
    }

    @Override
    public boolean addQuestion(Question question) {
        int num = 0;
        //如果问题或答案不为空
        if (isNotBlank(question.getQuestion(), question.getAnswer())) {
            //新增问答对
            Question questionDO = new Question();
            //封装questionDO
            questionDO.setQuestion(question.getQuestion());
            questionDO.setAnswer(question.getAnswer());
            questionDO.setProvider(0L);
            questionDO.setType(1);
            questionDO.setCreateTime(new Date());
            try {
                num = questionMapper.insert(questionDO);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return num==1;
    }

    @Override
    public boolean updateQuestion(Question question) {
        int num = 0;
        //修改问答对
        if (isNotBlank(question.getId(), question.getQuestion(), question.getAnswer())) {
            Long id = question.getId();
            //从数据库查询出来的数据
            Question questionDO = Optional.ofNullable(questionMapper.selectById(id)).orElse(new Question());
            //如果从数据库查询出来的数据为空,说明id无效
            if (StringUtils.isEmpty(questionDO.getQuestion())) return false;
            //接下来,将修改的数据替换到DO中,如果问题本来是未回答的,那么还需要发送邮件提醒用户
            if (questionDO.getType() == 0&&questionDO.getProvider()!=0) {
                //查询出用户信息
                User userInfo = Optional.ofNullable(userMapper.selectById(questionDO.getProvider())).orElse(new User());
                String email = userInfo.getEmail();
                String username = userInfo.getName();
                String que = questionDO.getQuestion();
                // 正文内容
                String content = "亲爱的用户"+username+"：\n" +
                        "您提交的问题：[" +
                        que + "]" +
                        "已由专业老师解答,请登录系统进行查看\n" +
                        "感谢您的使用！";
                threadService.sendSimpleMail(email,"问题已回答",content);
            }
            //替换修改
            questionDO.setAnswer(question.getAnswer());
            questionDO.setType(1);
            questionDO.setUpdateTime(new Date());
            try {
                num = questionMapper.updateById(questionDO);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return num==1;
    }

    @Override
    public boolean deleteQuestion(Long id) {
        int num = 0;
        try {
            num = questionMapper.deleteById(id);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return num==1;
    }

    @Override
    public Question getQuestionById(Long id) {
        Question questionDO = Optional.ofNullable(questionMapper.selectById(id)).orElse(new Question());
        Long providerId = questionDO.getProvider();
        if (providerId == 0) {
            questionDO.getParam().put("name", "管理员");
            questionDO.getParam().put("email", "admin@xx.com");
        }else {
            User userInfo = userMapper.selectById(providerId);
            if (isNotBlank(userInfo.getName())) {
                questionDO.getParam().put("name", userInfo.getName());
                questionDO.getParam().put("email", userInfo.getEmail());
            }
        }
        return questionDO;
    }

    private void packageQuestion(Question question){
        //得到用户id
        Long providerId = question.getProvider();
        if (providerId == 0) {
            //如果是管理员
            question.getParam().put("name", "管理员");
            question.getParam().put("email", "admin@xx.com");
        }else {
            //通过用户id查询到用户信息
            User user = Optional.ofNullable(userMapper.selectById(providerId)).orElse(new User());
            log.info(user.getName()+" "+user.getEmail());
            if (isNotBlank(user.getName(),user.getEmail())) {
                log.info("开始封装用户信息");
                question.getParam().put("name", user.getName());
                question.getParam().put("email", user.getEmail());
            }
        }
    }

    private boolean isNotBlank(Object... args) {
        for (Object a : args) {
            if (StringUtils.isEmpty(a)) {
                return false;
            }
        }
        return true;
    }
}
