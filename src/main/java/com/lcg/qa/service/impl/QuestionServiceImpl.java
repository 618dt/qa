package com.lcg.qa.service.impl;

import com.lcg.qa.core.CoreProcessor;
import com.lcg.qa.mapper.QuestionMapper;
import com.lcg.qa.model.entity.Question;
import com.lcg.qa.model.result.Result;
import com.lcg.qa.repository.QuestionRepository;
import com.lcg.qa.service.QuestionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class QuestionServiceImpl implements QuestionService {
    @Autowired
    private QuestionMapper questionMapper;

    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private CoreProcessor queryProcess;

    @Override
    public Result findAnswer(String question) {
        //直接查询数据库
        Question res = Optional.ofNullable(questionMapper.findAnswer(question)).orElse(new Question());
        if (StringUtils.isEmpty(res.getQuestion())) {
            //问题为空，不存在该问题
            return Result.data("answer","no question");
        } else if (StringUtils.isEmpty(res.getAnswer())&&res.getType()==0) {
            //问题不为空，但答案为空
            return Result.data("answer","no answer");
        } else if (!StringUtils.isEmpty(res.getAnswer())&&res.getType()==1) {
            return Result.data("answer", res.getAnswer());
        }
        return Result.error();
    }

    @Override
    public String getAnswerForNeo4j(String question) throws Exception {

        //问句分词解析
        List<String> queryList = queryProcess.analysis(question);

        int modelIndex = Integer.parseInt(queryList.get(0));
        String answer =null;
        String param = queryList.get(1);

        /**匹配问题模板*/
        switch (modelIndex) {
            case 0:
                //公司信息
                answer = questionRepository.getCompanyInfo(param);
                break;
            case 1:
                //公司成立日期
                answer = param+"注册成立的日期为"+questionRepository.getCompanyDate(param);
                break;
            case 2:
                //公司规模
                answer = getCompanyEmployeesNumber(queryList);
                break;
            case 3:
                //公司企业性质
                answer = param+"的企业性质为"+questionRepository.getCompanyEnterpriseType(param);
                break;
            case 4:
                //公司行业类型
                answer = handleList(questionRepository.getCompanyIndustry(param));
                break;
            case 5:
                //公司办公地点
                answer = param+"的主要办公地点为："+handleList(questionRepository.getAreaListOfCompany(param))
                        +"等城市";
                break;
            case 6:
                //公司职位列表
                answer = param+"公司主要开设的职位有："+handleList(questionRepository.getJobListOfCompany(param))
                        +"等";
                break;
            case 7:
                //地区公司列表
                answer = param+"有很多优秀的企业,例如"+handleList(questionRepository.getCompanyListOfArea(param))+"等";
                break;
            case 8:
                //职位简介
                answer = questionRepository.getJobInfo(param);
                break;
            case 9:
                //职位薪资
                answer = questionRepository.getJobSalary(param);
                break;
            case 10:
                //职位发展
                answer = questionRepository.getJobDev(param);
                break;
            case 11:
                //职位技能
                answer = handleList(questionRepository.getTechListOfJob(param));
                break;
            case 12:
                //职位公司
                answer = handleList(questionRepository.getCompanyListOfJob(param));
                break;
            case 13:
                //企业类型
                answer = questionRepository.getEnterpriseTypeInfo(param);
                break;
            case 14:
                //企业类型公司列表
                answer = handleList(questionRepository.getCompanyListOfEnterprise(param));
                break;
            case 15:
                //行业定义
                answer = questionRepository.getIndustryInfo(param);
                break;
            case 16:
                //行业公司列表
                answer = handleList(questionRepository.getCompanyListOfIndustry(param));
                break;
            case 17:
                //技能简介
                answer = questionRepository.getTechInfo(param);
                break;
            case 18:
                //技能书籍
                answer = "学习"+param+"可以阅读"+questionRepository.getBookOfTech(param)+"等书籍";
                break;
            case 19:
                //na ne 地区类型公司 在北京有哪些上市公司
                answer = getCompanyByAreaAndEnterpriseType(queryList);
                break;
            case 20:
                //na ni 地区行业公司 在上海有哪些信息安全公司
                answer = getCompanyByAreaAndIndustry(queryList);
                break;
            default:
                break;
        }
        System.out.println(answer);
        if (answer != null && !"".equals(answer) && !("\\N").equals(answer)) {
            return answer;
        } else {
            return "sorry,小主,我没有找到你要的答案";
        }
    }

    /**
     * nc 公司规模
     * 10001:一万人以上
     * 9999: 1000~10000人
     * 999: 500~999人
     * 499: 500人以下
     * @param queryList
     * @return
     */
    private String getCompanyEmployeesNumber(List<String> queryList){
        int res = questionRepository.getCompanySize(queryList.get(1));
        String ans=null;
        switch (res) {
            case 10001:
                ans = "公司员工人数达10000人以上";
                break;
            case 9999:
                ans = "公司员工人数在1000~9999之间";
                break;
            case 999:
                ans = "公司员工人数在500~999之间";
                break;
            case 499:
                ans = "公司员工人数在500人以内";
                break;
            default:
                break;
        }
        return ans;
    }



    /**
     * 根据地区,企业类型查询公司
     * @param queryList
     * @return
     */
    private String getCompanyByAreaAndEnterpriseType(List<String> queryList){
        String na = queryList.get(1);
        String ne = queryList.get(2);
        List<String> res = questionRepository.getCompanyListByAreaAndEnterprise(na, ne);
        return handleList(res);
    }

    private String getCompanyByAreaAndIndustry(List<String> queryList) {
        String na = queryList.get(1);
        String ni = queryList.get(2);
        List<String> res = questionRepository.getCompanyListByAreaAndIndustry(na, ni);
        return handleList(res);
    }

    /** 统一将list处理成string **/
    private String handleList(List<String> res) {
        String ans;
        if (res.size() == 0) {
            ans = null;
        }else {
            ans = res.toString().replace("[", "").replace("]", "");
        }
        return ans;
    }

    @Override
    public boolean submitQuestion(String question, Long userId) {
        //插入用户
        Question questionDo = new Question();
        int num = 0;
        if (!StringUtils.isEmpty(question) && !StringUtils.isEmpty(userId)) {
            questionDo.setQuestion(question);
            questionDo.setProvider(userId);
            //刚提交,问题未回答
            questionDo.setType(0);
            //新增录入问题时,需要设置创建时间
            questionDo.setCreateTime(new Date());
            try {
                num = questionMapper.insert(questionDo);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return num == 1;
    }
}
