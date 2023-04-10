package com.lcg.qa.config;

import com.lcg.qa.utils.CustomDictWordUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.io.*;

/**
 * 全局配启动 -- 初始化项目时，执行命令，将相关额外的自定义词典加载下
 * @author ASUS
 */
@Component
public class ConfigRunner implements CommandLineRunner {

    @Value("${HanLP.CustomDictionary.path.areaDict}")
    private String areaDictPath;

    @Value("${HanLP.CustomDictionary.path.companyDict}")
    private String companyDictPath;

    @Value("${HanLP.CustomDictionary.path.enterpriseDict}")
    private String enterpriseDictPath;

    @Value("${HanLP.CustomDictionary.path.industryDict}")
    private String industryDictPath;

    @Value("${HanLP.CustomDictionary.path.jobDict}")
    private String jobDictPath;

    @Value("${HanLP.CustomDictionary.path.techDict}")
    private String techDictPath;

    @Value("${HanLP.CustomDictionary.cache.path}")
    private String cacheDictPath;

    @Override
    public void run(String... args){

        //先删除缓存
        File file = new File(cacheDictPath);
        if(file.exists()){
            boolean res = file.delete();
            System.out.println("CustomDictionary.txt.bin delete success .");
        }
        loadDict(areaDictPath,0);
        loadDict(companyDictPath,1);
        loadDict(enterpriseDictPath,2);
        loadDict(industryDictPath,3);
        loadDict(jobDictPath,4);
        loadDict(techDictPath,5);
    }

    /**
     * 加载自定义词性字典
     * @param path 字典路径
     * @param type 类型
     */
    public void loadDict(String path,Integer type) {
        File file = new File(path);
        BufferedReader br;
        try {
            br = new BufferedReader(new FileReader(file));
            addCustomDictionary(br, type);
        } catch (FileNotFoundException e1) {
            e1.printStackTrace();
        }
    }


    /**
     * 添加自定义分词及其词性，注意数字0表示频率，不能没有
     *
     * @param br 字节流（读）
     * @param type 字典类型
     */
    public void addCustomDictionary(BufferedReader br, int type) {

        String word;
        try {
            while ((word = br.readLine()) != null) {
                switch (type) {
                    /**设置地区名词 词性 == na 0*/
                    case 0:
                        CustomDictWordUtils.setNatureAndFrequency(word,"na 0",true);
                        break;
                    /**设置公司名词 词性 == nc 0*/
                    case 1:
                        CustomDictWordUtils.setNatureAndFrequency(word,"nc 0",true);
                        break;
                    /**设置企业类型名词 词性 == ne 0*/
                    case 2:
                        CustomDictWordUtils.setNatureAndFrequency(word,"ne 0",true);
                        break;
                    /**设置公司行业名词 词性 == ni 0*/
                    case 3:
                        CustomDictWordUtils.setNatureAndFrequency(word,"ni 0",true);
                        break;
                    /**设置职位名词 词性 == nj 0*/
                    case 4:
                        CustomDictWordUtils.setNatureAndFrequency(word,"nj 0",true);
                        break;
                    /**设置技能名词 词性 == nt 0*/
                    case 5:
                        CustomDictWordUtils.setNatureAndFrequency(word,"nt 0",true);
                        break;
                    default:
                        break;
                }
            }
            br.close();
        } catch (NumberFormatException | IOException e) {
            e.printStackTrace();
        }
    }
}
