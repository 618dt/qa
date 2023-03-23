package com.lcg.qa.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lcg.qa.model.entity.Feedback;
import com.lcg.qa.model.entity.Question;
import com.lcg.qa.model.result.Result;
import com.lcg.qa.model.vo.AdminParam;
import com.lcg.qa.service.AdminService;
import com.lcg.qa.service.FeedbackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;



import static com.lcg.qa.constant.HttpStatusEnum.PARAM_ERROR;

/**
 * @author LCG
 * 后台管理员控制层
 */
@RestController
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private AdminService adminService;

    @Autowired
    private FeedbackService feedbackService;

    @PostMapping("/login")
    public Result loginAdmin(@RequestBody AdminParam adminParam) {
        return adminService.loginAdmin(adminParam);
    }

    @GetMapping("/question/{page}/{limit}")
    public Result pageList(@PathVariable int page, @PathVariable int limit, Integer type) {
        if (StringUtils.isEmpty(page) || StringUtils.isEmpty(limit)) {
            return Result.error(PARAM_ERROR);
        }
        if (StringUtils.isEmpty(type)) {
            type = -1;
        }
        Page<Question> pageParam = new Page<>(page, limit);
        IPage<Question> pageModel = adminService.selectPage(pageParam, type);
        return Result.data("pageModel", pageModel);
    }

    @GetMapping("/question/{id}")
    public Result getQuestionById(@PathVariable Long id) {
        if (StringUtils.isEmpty(id)) {
            return Result.error();
        }
        Question question = adminService.getQuestionById(id);
        return Result.data("question", question);
    }

    /**
     * 新增问答对
     */
    @PostMapping("/question/add")
    public Result addQuestion(@RequestBody Question question) {
        boolean flag = adminService.addQuestion(question);
        if (flag) {
            return Result.ok();
        }
        return Result.error();
    }

    @PostMapping("/question/update")
    public Result updateQuestion(@RequestBody Question question) {
        boolean flag = adminService.updateQuestion(question);
        if (flag) {
            return Result.ok();
        }
        return Result.error();
    }

    /**
     * 删除问答对
     * @param id
     * @return
     */
    @DeleteMapping("/question/delete/{id}")
    public Result deleteQuestion(@PathVariable Long id) {
        boolean flag = adminService.deleteQuestion(id);
        if (flag) {
            return Result.ok();
        }
        return Result.error();
    }

    /**
     * 反馈列表分页
     * @param page
     * @param limit
     * @param status
     * @return
     */
    @GetMapping("/feedback/{page}/{limit}")
    public Result getFeedbackPage(@PathVariable int page, @PathVariable int limit, Integer status) {
        if (StringUtils.isEmpty(page) || StringUtils.isEmpty(limit)) {
            return Result.error(PARAM_ERROR);
        }
        if (StringUtils.isEmpty(status)) {
            status = -1;
        }
        Page<Feedback> pageParam = new Page<>(page, limit);
        IPage<Feedback> pageModel = feedbackService.getPage(pageParam, status);
        return Result.data("pageModel", pageModel);
    }

    /**
     * 根据id删除反馈内容
     * @param id
     * @return
     */
    @DeleteMapping("/feedback/delete/{id}")
    public Result deleteFeedback(@PathVariable Long id) {
        boolean flag = feedbackService.deleteFeedback(id);
        if (flag) {
            return Result.ok();
        }
        return Result.error();
    }

    @PutMapping("/feedback/update/{id}/{status}")
    public Result updateFeedback(@PathVariable Long id,@PathVariable int status) {
        boolean flag = feedbackService.updateFeedback(id,status);
        if (flag) {
            return Result.ok();
        }
        return Result.error();
    }
}
