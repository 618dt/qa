package com.lcg.qa.repository;

import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;


/**
 * 查询职业规划知识图谱
 * @author ASUS
 */
public interface QuestionRepository extends Neo4jRepository<String,Long> {

    /**
     * 模板0: nc公司简介
     * @param nc 公司名称
     * @return 公司简介
     */
    @Query("match(n:Company) where n.company_name={nc} return n.company_info")
    String getCompanyInfo(@Param("nc") String nc);

    /**
     * 模板1: nc 成立日期
     * @param nc 公司名称
     * @return 成立日期
     */
    @Query("match(n:Company) where n.company_name={nc} return n.company_data")
    String getCompanyDate(@Param("nc") String nc);

    /**
     * 模板2: nc 公司规模
     * @param nc
     * @return
     */
    @Query("match(n:Company) where n.company_name={nc} return n.company_size")
    Integer getCompanySize(@Param("nc") String nc);

    /**
     * 模板3: nc 公司企业类型  阿里巴巴企业类型是什么
     * @param nc
     * @return
     */
    @Query("match(n:Company) -[:belong]-(e:Enterprise) where n.company_name={nc} return e.enterprise_name")
    String getCompanyEnterpriseType(@Param("nc") String nc);

    /**
     * 模板4: nc 公司行业类型  阿里巴巴属于什么行业
     * @param nc
     * @return
     */
    @Query("match(n:Company) -[:hold]-(i:Industry) where n.company_name={nc} return i.industry_name")
    List<String> getCompanyIndustry(@Param("nc") String nc);

    /**
     * 模板5: nc 公司地区 阿里巴巴在哪些地方办公
     * @param nc
     * @return
     */
    @Query("match(n:Company)-[:have]-(a:Area) where n.company_name={nc} return a.area_name")
    List<String> getAreaListOfCompany(@Param("nc") String nc);

    /**
     * 模板6: nc 公司职位 深信服提供哪些岗位
     * @param nc
     * @return
     */
    @Query("match(n:Company)-[:provide]-(m:Job) where n.company_name={nc} return m.job_name")
    List<String> getJobListOfCompany(@Param("nc") String nc);

    /**
     * 模板7: na 地区公司 在上海有哪些公司
     * @param na
     * @return
     */
    @Query("match(n:Area)-[:have]->(c:Company) where n.area_name={na} return c.company_name ORDER BY c.company_size DESC")
    List<String> getCompanyListOfArea(@Param("na") String na);

    /**
     * 模板8: nj 职位简介 信息安全工程师工作内容
     * @param nj
     * @return
     */
    @Query("match(n:Job) where n.job_name={nj} return n.job_info")
    String getJobInfo(@Param("nj") String nj);

    /**
     * 模板9: nj 职位薪资 后端开发工程师的薪资水平是多少
     * @param nj
     * @return
     */
    @Query("match(n:Job) where n.job_name={nj} return n.job_salary")
    String getJobSalary(@Param("nj") String nj);

    /**
     * 模板10: nj 职位发展 渗透测试工程师如何成长
     * @param nj
     * @return
     */
    @Query("match(n:Job) where n.job_name={nj} return n.job_dev")
    String getJobDev(@Param("nj") String nj);

    /**
     * 模板11: nj 职位技能 后端开发工程师需要掌握哪些技能
     * @param nj
     * @return
     */
    @Query("match(n:Job)-[:need]->(t:Tech) where n.job_name={nj} return t.tech_name")
    List<String> getTechListOfJob(@Param("nj") String nj);

    /**
     * 模板12: nj 职位公司 哪些公司开设了前端开发工程师岗位
     * @param nj
     * @return
     */
    @Query("match(n:Job)-[:provide]-(c:Company) where n.job_name={nj} return c.company_name")
    List<String> getCompanyListOfJob(@Param("nj") String nj);

    /**
     * 模板13 ne 企业类型说明  什么是国企
     * @param ne
     * @return
     */
    @Query("match(n:Enterprise) where n.enterprise_name={ne} return n.enterprise_info")
    String getEnterpriseTypeInfo(@Param("ne") String ne);

    /**
     * 模板14 ne 企业类型公司  有哪些公司是上市公司
     * @param ne
     * @return
     */
    @Query("match(n:Enterprise)-[:belong]-(c:Company) where n.enterprise_name={ne} return c.company_name")
    List<String> getCompanyListOfEnterprise(@Param("ne") String ne);

    /**
     * 模板15: 行业定义  什么是信息安全行业
     * @param ni
     * @return
     */
    @Query("match(n:Industry) where n.industry_name={ni} return n.industry_info")
    String getIndustryInfo(@Param("ni") String ni);


    /**
     * 模板16 行业公司 有哪些计算机软件公司
     * @param ni
     * @return
     */
    @Query("match(n:Industry)-[:hold]->(c:Company) where n.industry_name={ni} return c.company_name")
    List<String> getCompanyListOfIndustry(@Param("ni") String ni);

    /**
     * 模板17: 技能简介 计算机基础是什么
     * @param nt
     * @return
     */
    @Query("match(n:Tech) where n.tech_name={nt} return n.tech_info")
    String getTechInfo(@Param("nt") String nt);

    /**
     * 模板18: 技能书籍 学习编程语言推荐什么书籍
     * @param nt
     * @return
     */
    @Query("match(n:Tech) where n.tech_name={nt} return n.tech_book")
    String getBookOfTech(@Param("nt") String nt);

    /**
     * 模板19: na ne 地区类型公司 在北京有哪些上市公司
     * @param na
     * @param ne
     * @return
     */
    @Query("match(a:Area)-[:have]-(c:Company) where a.area_name={na} "
            + "match(e:Enterprise)-[:belong]-(c) where e.enterprise_name={ne} return c.company_name")
    List<String> getCompanyListByAreaAndEnterprise(@Param("na") String na, @Param("ne") String ne);

    /**
     * 模板20: na ni 地区行业公司 在上海有哪些信息安全公司
     * @param na
     * @param ni
     * @return
     */
    @Query("match(n:Area{area_name:{na}})-[have]-(c:Company)-[hold]-(m:Industry{industry_name:{ni}}) return c.company_name")
    List<String> getCompanyListByAreaAndIndustry(@Param("na") String na, @Param("ni") String ni);





    /**
     *  对应问题模板6 == nnt(演员) ng(电影类型) 电影作品
     *
     * @param name  演员名
     * @param gname 电影类型名称
     * @return 返回电影名称列表
     */
    @Query("match(a:Area)-[:have]-(c:Company) where a.area_name={na} "
            + "match(i:Industry)-[:hold]-(c) where i.industry_name={ni}")
    List<String> getActorMoviesByType(@Param("name") String name, @Param("gname") String gname);
}

